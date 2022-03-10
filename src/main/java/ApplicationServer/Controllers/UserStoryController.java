package ApplicationServer.Controllers;

import ApplicationServer.JPA.ProductBacklogRepository;
import ApplicationServer.JPA.SprintBacklogRepository;
import ApplicationServer.JPA.SprintRepository;
import ApplicationServer.JPA.UserStoryRepository;
import ApplicationServer.Model.UserStory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserStoryController {
    private UserStoryRepository userStoryRepository;
    private ProductBacklogRepository productBacklogRepository;
    private SprintRepository sprintRepository;

    public UserStoryController(UserStoryRepository userStoryRepository, ProductBacklogRepository productBacklogRepository, SprintRepository sprintRepository) {
        this.userStoryRepository = userStoryRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.sprintRepository = sprintRepository;
    }

    //region Get User Story     ARGS: ID/ProjectBacklogId   METHOD: GET
    /**
     * Returns User Story/Use Stories from the database. If specified only Id, returns one User Story.
     * If specified backlogId, returns List of User Stories for Project Backlog with given ID.
     *
     * EXAMPLE:
     *  http://{host}:6969/api/userStory?userStoryId=52
     *  http://{host}:6969/api/userStory?projectId=7
     *  http://{host}:6969/api/userStory?sprintId=7
     *
     * @param projectId Id of the desired Project
     * @param userStoryId Id of desired User Story
     * @param sprintId Id of the desired Sprint
     * @return <i>HTTP 200 - OK</i> code with all relevant User Stories. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/userStory", method = RequestMethod.GET)
    public ResponseEntity<?> getUserStory(
            @RequestParam(value = "projectId", required = false) Integer projectId,
            @RequestParam(value = "userStoryId", required = false) Integer userStoryId,
            @RequestParam(value = "sprintId", required = false) Integer sprintId
    ) {
        try {
            if (projectId != null) {
                var productBacklogId = productBacklogRepository.findByProjectId(projectId).getProductBacklogId();
                return ResponseEntity.status(HttpStatus.OK).body(userStoryRepository.findAllByProductBacklogId(productBacklogId));
            } else if (userStoryId != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userStoryRepository.findByUserStoryId(userStoryId));
            } else if (sprintId != null){
                System.out.println("Sprint id: " + sprintId);
                int idOfProject = sprintRepository.findBySprintId(sprintId).get(0).getProjectId();
                var productBacklogId = productBacklogRepository.findByProjectId(idOfProject).getProductBacklogId();
                return ResponseEntity.status(HttpStatus.OK).body(userStoryRepository.findAllByProductBacklogId(productBacklogId));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id not specified");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");

        }
    }
    //endregion

    //region Create User Story     ARGS: UserStory RequestBody METHOD: POST
    /**
     * Creates User Story in Project Backlog. Requires RequestBody with JSON UserStory object.
     *
     * EXAMPLE:
     *  http://{host}:6969/api/userStory
     *
     *  Body
     *  {
     *      "productBacklogId" : 4,
     *      "priority" : "low",
     *      "description" : "Lorem Ipsum is simply dummy text",
     *      "difficulty" : 56,
     *      "status" : "completed"
     *  }
     *
     * @param userStory JSON of UserStory object
     * @return <i>HTTP 201 - CREATED</i> code with created row if User Story is created. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/userStory", method = RequestMethod.POST)
    public ResponseEntity<?> createUserStory(
            @RequestBody UserStory userStory
    ){
        try {
            int productBacklogId = productBacklogRepository.findByProjectId(userStory.getProductBacklogId()).getProductBacklogId();
            userStory.setProductBacklogId(productBacklogId);
            UserStory savedUserStory = userStoryRepository.save(userStory);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserStory);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion

    //region Remove User Story DELETE   ARGS: UserStoryId METHOD: DELETE

    /**
     * Remove user story with provided ID
     *
     * EXAMPLE:
     *      http://{host}:6969/api/userStory?userStoryId=8
     *
     * @param userStoryId Id of User Story
     * @return <i>HTTP 200 - OK</i> code if User Story was deleted. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/userStory", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeUserStory(
            @RequestParam(value = "userStoryId") Integer userStoryId
    ){
        try{
            UserStory userStoryToDelete = userStoryRepository.findByUserStoryId(userStoryId).get(0);
            userStoryRepository.delete(userStoryToDelete);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion
}