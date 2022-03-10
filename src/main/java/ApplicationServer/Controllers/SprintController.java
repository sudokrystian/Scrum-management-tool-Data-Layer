package ApplicationServer.Controllers;

import ApplicationServer.JPA.SprintRepository;
import ApplicationServer.Model.Remote.ScrumRole;
import ApplicationServer.Model.Sprint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SprintController {
    private SprintRepository sprintRepository;

    /**
     * Public constructor user by dependency injection
     *
     * @param sprintRepository JPA Repository
     */
    public SprintController(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    //region Get Sprint GET
    /**
     * Used to get information about sprints for specified project or information about sprint specified by id.
     * <p>
     * EXAMPLE
     * http://{host}:6969/api/sprint?projectId=3
     * http://{host}:6969/api/sprint?id=18
     *
     * @param id        of sprint
     * @param projectId of project
     * @return One sprint if 'id' specified or List of sprints for project with given 'projectId'.
     */
    @RequestMapping(value = "/sprint", method = RequestMethod.GET)
    public ResponseEntity<?> getSprint(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "projectId", required = false) Integer projectId) {
        if (id != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sprintRepository.findBySprintId(id));
        } else if (projectId != null) {
            return ResponseEntity.status(HttpStatus.OK).body(sprintRepository.findAllByProjectId(projectId));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not allowed to get all database Sprints");
    }
    //endregion

    //region Create Sprint POST
    /**
     * <p></>Post method for creating sprint. Used while Sprint Planning. Have to contain all variables. Overrides the original sprint in database
     * which had some variables nulls.</p>
     *
     * EXAMPLE:
     *  http://{host}:6969/api/sprint
     *
     * BODY:
     *  {
     *      "projectId" : 8,
     *      "sprintNumber" : 1,
     *      "dateStarted" : "1999-12-31",
     *      "dateFinished" : "1999-12-31",
     *      "productOwnerUsername" : 2,
     *      "scrumMasterUsername" : 1,
     *      "status": "ongoing"
     *  }
     *
     * @param sprint Sprint object passed from Business Tier.
     * @return Returns relevant HttpStatus. CREATED if creation was successful or BAD_REQUEST if creation failed.
     */
    @RequestMapping(value = "/sprint", method = RequestMethod.POST)
    public ResponseEntity<?> createSprint(@RequestBody Sprint sprint) {
        System.out.println(sprint.toString());
        try {
            var savedSprint = sprintRepository.save(sprint);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSprint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving sprint " + e.getMessage());
        }
    }
    //endregion

    //region Assign Product Owner POST
    /**
     * Assigns a product owner to the sprint. Taking parameters of JSON ScrumRole object
     *
     * EXAMPLE:
     *  http://{host}:6969/api/productOwner
     *
     *  Body
     *  {
     * 	    "username": "Krystianko",
     * 	    "sprintId": 48
     *  }
     *
     * @param scrumRole ScrumRole JSON Object containing username and sprint id
     * @return <i>HTTP 200 - OK</i> code with sprint object if product owner is assigned. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/productOwner", method = RequestMethod.POST)
    public ResponseEntity<?> assignProductOwner(
            @RequestBody ScrumRole scrumRole
    ){
        try {
            System.out.println("Trying to make a product owner out of " + scrumRole.getUsername());
            var originalSprint = sprintRepository.findBySprintId(scrumRole.getSprintId()).get(0);
            originalSprint.setProductOwnerUsername(scrumRole.getUsername());
            var updatedSprint = sprintRepository.save(originalSprint);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSprint);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //endregion

    //region Assign Scrum Master POST
    /**
     * Assigns a scrum master to the sprint. Taking parameters of JSON ScrumRole object
     *
     * EXAMPLE:
     *  http://{host}:6969/api/scrumMaster
     *
     *  Body
     *  {
     * 	    "username": "Krystianko",
     * 	    "sprintId": 48
     *  }
     *
     * @param scrumRole ScrumRole JSON Object containing username and sprint id
     * @return <i>HTTP 200 - OK</i> code with sprint object if scrum master is assigned. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/scrumMaster", method = RequestMethod.POST)
    public ResponseEntity<?> assignScrumMaster(
            @RequestBody ScrumRole scrumRole
    ){
        try {
            var originalSprint = sprintRepository.findBySprintId(scrumRole.getSprintId()).get(0);
            originalSprint.setScrumMasterUsername(scrumRole.getUsername());
            var updatedSprint = sprintRepository.save(originalSprint);
            return ResponseEntity.status(HttpStatus.OK).body(updatedSprint);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //endregion POST
}
