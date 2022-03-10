package ApplicationServer.Controllers;

import ApplicationServer.JPA.SprintBacklogRepository;
import ApplicationServer.JPA.SprintUserStoryRepository;
import ApplicationServer.JPA.TaskRepository;
import ApplicationServer.Model.SprintUserStory;
import ApplicationServer.Model.Tasks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private TaskRepository taskRepository;
    private SprintBacklogRepository sprintBacklogRepository;
    private SprintUserStoryRepository sprintUserStoryRepository;

    public TaskController(TaskRepository taskRepository, SprintBacklogRepository sprintBacklogRepository, SprintUserStoryRepository sprintUserStoryRepository) {
        this.taskRepository = taskRepository;
        this.sprintBacklogRepository = sprintBacklogRepository;
        this.sprintUserStoryRepository = sprintUserStoryRepository;
    }

    //region Create Task POST
    /**
     * Creates a task for chosen Sprint User Story
     * <b>EXAMPLE</b>:
     *  http://{host}:6969/api/task
     *
     *  <b>BODY</b>:
     *  {
     * 	    "sprintUserStoryId": 8,
     * 	    "description": "Create Task in Rest",
     * 	    "status": "COMPLETED"
     * }
     *
     * @param task Task object in JSON format
     * @return <i>HTTP 201 - CREATED</i> code with created row if Task is created. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(
            @RequestBody Tasks task
    ){
        try {
            System.out.println(task);
            Tasks savedTask = taskRepository.save(task);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion

    //region Get Tasks for Sprint User Story
    /**
     * Returns list of tasks for Sprint User Story with sprint id
     *
     * <p>
     *     <b>EXAMPLE</b>:
     *          http://{host}:6969/api/task?sprintUserStoryId=5
     *
     * </p>
     *
     * @param sprintUserStoryId Id of the Sprint User Story
     * @return <i>HTTP 200 - OK</i> code with all tasks for Sprint User Story. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTasks(
            @RequestParam(value = "sprintUserStoryId") Integer sprintUserStoryId
    ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(taskRepository.getAllBySprintUserStoryId(sprintUserStoryId));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion

    //region Get Tasks for SprintId

    /**
     * Returns list of tasks for Sprint with sprint id
     *
     * <p>
     *     <b>EXAMPLE</b>:
     *          http://{host}:6969/api/task?sprintId=5
     *
     * </p>
     * @param sprintId ID of the Sprint
     * @return <i>HTTP 200 - OK</i> code with all tasks for Sprint. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/sprintTasks", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTasksForSprint(
            @RequestParam(value = "sprintId") Integer sprintId
    ){
        try {
            int sprintBacklogId = sprintBacklogRepository.findBySprintId(sprintId).getSprintBacklogId();
            List<SprintUserStory> sprintUserStories = sprintUserStoryRepository.findAllBySprintBacklogId(sprintBacklogId);

            List<Tasks> allTasksForSprint = new ArrayList<>();

            for (SprintUserStory SUS : sprintUserStories){
                List<Tasks> SUSTasks = taskRepository.getAllBySprintUserStoryId(SUS.getSprintUserStoryId());
                allTasksForSprint.addAll(SUSTasks);
            }

            return ResponseEntity.status(HttpStatus.OK).body(allTasksForSprint);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + e.getMessage());
        }
    }
    //endregion


}
