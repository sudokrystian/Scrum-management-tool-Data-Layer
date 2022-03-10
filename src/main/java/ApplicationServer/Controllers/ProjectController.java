package ApplicationServer.Controllers;

import ApplicationServer.JPA.*;
import ApplicationServer.Model.*;
import ApplicationServer.Model.CompositeKeys.AdministratorProjectKey;
import ApplicationServer.Model.CompositeKeys.UserProjectKey;
import ApplicationServer.Model.Statuses.BacklogStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ProjectController {
    private ProjectRepository projectRepository;
    private UsersInProjectsRepository usersInProjectsRepository;
    private AdministratorsInProjectsRepository administratorsInProjectsRepository;
    private SprintRepository sprintRepository;
    private ProductBacklogRepository productBacklogRepository;
    private SprintBacklogRepository sprintBacklogRepository;

    public ProjectController(ProjectRepository projectRepository, UsersInProjectsRepository usersInProjectsRepository, AdministratorsInProjectsRepository administratorsInProjectsRepository, SprintRepository sprintRepository, ProductBacklogRepository productBacklogRepository, SprintBacklogRepository sprintBacklogRepository) {
        this.projectRepository = projectRepository;
        this.usersInProjectsRepository = usersInProjectsRepository;
        this.administratorsInProjectsRepository = administratorsInProjectsRepository;
        this.sprintRepository = sprintRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.sprintBacklogRepository = sprintBacklogRepository;
    }

    //region Add User POST
    /**
     * Add new entry to UsersInProjects table. Takes UserProjectKey object as an argument
     * <p>
     *  <b>EXAMPLE</b>:
     *      http://{host}:6969/api/addUser
     *
     *  <b>BODY</b>:
     *      {
     *          "username" : "David",
     *          "projectId" : 1
     *      }
     *  </p>
     *
     * @param userEntry UserProjectKey object containing username of new user and project id
     * @return <i>HTTP 201 - CREATED</i> code with saved object in body if user is added to the project. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public ResponseEntity<?> addUserToProject(@RequestBody UserProjectKey userEntry){
        try {
            UsersInProjects entry = new UsersInProjects(userEntry);
            var responseFromDb = usersInProjectsRepository.save(entry);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseFromDb);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //endregion

    //region Remove User POST
    /**
     * Remove row from UsersInProjects table. Takes UserProjectKey object as an argument
     *
     * EXAMPLE:
     *  http://{host}:6969/api/removeUser
     *
     *  Body:
     *  {
     *      "username" : "David",
     *      "projectId" : 1
     *  }
     *
     * @param userEntry UserProjectKey object containing username of new user and project id
     * @return <i>HTTP 201 - CREATED</i> code if user is removed from the project. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    public ResponseEntity<?> removeUserFromProject(@RequestBody UserProjectKey userEntry){
        try {
            var objectToRemove = usersInProjectsRepository.findByUserProjectKey(userEntry);
            usersInProjectsRepository.delete(objectToRemove);
            return ResponseEntity.status(HttpStatus.CREATED).body("User deleted");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entry not found");
        }
    }
    //endregion

    //region Get Project GET
    /**
     * Finds all projects by status, specific one by project ID or all projects for username.
     *
     * EXAMPLE:
     *  http://{host}:8080/api/project?status=completed
     *  http://{host}:8080/api/project?id=5
     *  http://{host}:8080/api/project?username=David
     *
     * @param status status of the project (ongoing/completed).
     * @param id id of the project.
     * @param username unique username to get all relevant projects.
     * @return <i>HTTP 200 - OK</i> code if project/projects were returned with List of projects as a body. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "username", required = false) String username) {
        if (status != null) {
            return ResponseEntity.status(HttpStatus.OK).body(projectRepository.findAllByStatus(status));
        } else if (id != null) {
            return ResponseEntity.status(HttpStatus.OK).body(projectWithId(id));
        } else if (username != null) {
            return ResponseEntity.status(HttpStatus.OK).body(projectsForUser(username));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not allowed to get all database Projects");
    }

    private Project projectWithId(Integer id) {
        Project project = projectRepository.findByProjectId(id);

        var administratorsInProject = administratorsInProjectsRepository.findByAdministratorProjectKeyProjectId(project.getProjectId());

        List<String> administratorUsername = new ArrayList<>();

        for (AdministratorsInProjects admin : administratorsInProject){
            administratorUsername.add(admin.getAdministratorProjectKey().getUsername());
        }

        return new Project(project, administratorUsername);
    }

    private List<?> projectsForUser(String username) {
        List<UsersInProjects> projectIds = usersInProjectsRepository.findByUserProjectKeyUsername(username);

        List<Project> projectList = new ArrayList<>();

        // Loop through all UsersInProject entries and find all Projects with given IDs
        for (UsersInProjects usersInProjects : projectIds) {
            var project = projectRepository.findByProjectId(usersInProjects.getUserProjectKey().getProjectId());
            var administratorsInProject = administratorsInProjectsRepository.findByAdministratorProjectKeyProjectId(project.getProjectId());

            List<String> administratorUsername = new ArrayList<>();

            for (AdministratorsInProjects admin : administratorsInProject){
                administratorUsername.add(admin.getAdministratorProjectKey().getUsername());
            }

            // Using custom constructor to notify Business Logic Server who is an administrator of every project.
            projectList.add(new Project(project, administratorUsername));
        }

        return projectList;
    }

    //endregion

    //region Create Project POST
    /**
     * Method for creating a new project. Processing data in JSON form sent from client site of the system.
     * Creates a project and saves it to the database. Creates all relevant data for project such as Product Backlog,
     * Sprint, Sprint Backlog.
     * Assigns creator as an administrator and user of the project.
     * If the project was created successfully it returns  a HTTP Response Status with code '200 OK'
     * If the project failed to be created  it returns  a HTTP Response Status with code '400 Bad Request'
     *
     * Example:
     *  http://{host}:6969/api/project?username=David
     *
     *  BODY:
     *     {
     * 	        "name": "{Project name}",
     * 	        "status": "completed / ongoing",
     * 	        "numberOfIterations": {integer},
     * 	        "lengthOfSprint": {integer}
     *     }
     *
     * @param project Project object in format of Json received from client
     * @param username Name of the user who created the project so he will automatically be assigned as an administrator.
     * @return <i>HTTP 200 - OK</i> code if project created. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public ResponseEntity<String> createProject(
            @RequestBody Project project,
            @RequestParam(value = "username") String username) {
        Project savedProject = projectRepository.save(project);
        if (savedProject != null) {
            AdministratorsInProjects adminEntry = new AdministratorsInProjects(new AdministratorProjectKey(username, savedProject.getProjectId()));
            UsersInProjects userEntry = new UsersInProjects((new UserProjectKey(username, savedProject.getProjectId())));
            ProductBacklog productBacklogEntry = new ProductBacklog(savedProject.getProjectId(), BacklogStatus.UNLOCKED);
            try {
                // Create administrator for new project with provided username
                administratorsInProjectsRepository.save(adminEntry);

                // Create administrator for new project with provided username
                usersInProjectsRepository.save(userEntry);

                // Create Product Backlog
                productBacklogRepository.save(productBacklogEntry);

                // Create all Sprints for Project based on number of iterations. Some variables are null and have
                // to be initialized during sprint planning.
                for (int a = 0; a < project.getNumberOfIterations(); a++){
                    createSprint(project.getProjectId(), a + 1);
                }
                return ResponseEntity.status(HttpStatus.OK).body("Project created");
            } catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
        // If anything above goes wrong
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Creation of the Project failed");
    }

    private void createSprint(int projectId, int sprintNumber) {
        // Create Sprint object
        Sprint sprint = new Sprint(projectId, sprintNumber);
        sprintRepository.save(sprint);

        // Create Sprint Backlog
        SprintBacklog sprintBacklog = new SprintBacklog(sprint.getSprintId(), BacklogStatus.UNLOCKED);
        sprintBacklog = sprintBacklogRepository.save(sprintBacklog);

        //TODO Create Sprint Review
        //TODO Create Sprint Plan
    }
    //endregion

}