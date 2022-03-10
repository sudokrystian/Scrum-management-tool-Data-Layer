package ApplicationServer.Controllers;

import ApplicationServer.JPA.UserTasksRepository;
import ApplicationServer.Model.CompositeKeys.UserTaskKey;
import ApplicationServer.Model.UserTasks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserTasksController {
    private UserTasksRepository userTasksRepository;

    public UserTasksController(UserTasksRepository userTasksRepository) {
        this.userTasksRepository = userTasksRepository;
    }

    //region Get User Tasks for Username GET
    /**
     * Returns List of User Tasks entries for provided username
     *
     * EXAMPLE:
     *  http://{host}:6969/api/userTask?username=david
     *
     * @param username of User
     * @return <i>HTTP 200 - OK</i> code with User Task entry. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/userTask", method = RequestMethod.GET)
    public ResponseEntity<?> getUserTasksForUsername(
            @RequestParam(value = "username") String username
    ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userTasksRepository.getAllByUserTaskKeyUsername(username));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion

    //region Assign Task to User POST
    /**
     * Assigns a task to the user
     *
     * EXAMPLE:
     *  http://{host}:6969/api/userTask
     *
     *  Body:
     *  {
     * 	    "taskId": 11,
     * 	    "username": "David"
     *  }
     *
     * @param userTaskKey UserTaskKey object in JSON format
     * @return <i>HTTP 201 - CREATED</i> code with created row if User Task is created. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/userTask", method = RequestMethod.POST)
    public ResponseEntity<?> assignTaskToUser(
            @RequestBody UserTaskKey userTaskKey
    ){
        try{
            UserTasks savedUserTasks = userTasksRepository.save(new UserTasks(userTaskKey));
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUserTasks);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion
}
