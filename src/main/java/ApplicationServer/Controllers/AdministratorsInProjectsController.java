package ApplicationServer.Controllers;

import ApplicationServer.JPA.AdministratorsInProjectsRepository;
import ApplicationServer.Model.AdministratorsInProjects;
import ApplicationServer.Model.CompositeKeys.AdministratorProjectKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AdministratorsInProjectsController {
    private AdministratorsInProjectsRepository administratorsInProjectsRepository;

    public AdministratorsInProjectsController(AdministratorsInProjectsRepository administratorsInProjectsRepository) {
        this.administratorsInProjectsRepository = administratorsInProjectsRepository;
    }

    //region Assign Admin POST
    /**
     * Post method assigning administrator privileges to the project by passing AdministratorProjectKey object body as JSON.
     *
     * <p>
     *  <b>EXAMPLE</b>:
     *  http://{host}:6969/api/assignAdmin
     *
     *  <b>BODY</b>:
     *  {
     *      "username" : "David",
     *      "projectId" : 4
     *  }
     * </p>
     *
     *
     * @param adminEntry AdministratorProjectKey object containing username and projectId
     * @return <i>HTTP 201 - CREATED</i> code if administrator is added. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/assignAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> assignAdmin(@RequestBody AdministratorProjectKey adminEntry) {
        try {
            if (administratorsInProjectsRepository.findByAdministratorProjectKey(adminEntry) != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(adminEntry.getUsername() + " is already assigned as administrator in project id " + adminEntry.getProjectId());
            }
            var databaseResponse = administratorsInProjectsRepository.save(new AdministratorsInProjects(adminEntry));
            return ResponseEntity.status(HttpStatus.CREATED).body(databaseResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //endregion

    //region Remove Admin POST
    /**
     * Removes admin privileges from user. Deletes entry in database.
     *
     * <p>
     *  <b>EXAMPLE</b>:
     *  http://{host}:6969/api/removeAdmin
     *
     *  <b>BODY</b>:
     *  {
     *      "username" : "David",
     *      "projectId" : 4
     *  }
     *  </p>
     *
     * @param entryToDelete AdministratorProjectKey object containing username and projectId
     * @return <i>HTTP 200 - OK</i> code if administrator is removed. Returns <i>HTTP 400 - BAD_REQUEST</i> if error occurred.
     */
    @RequestMapping(value = "/removeAdmin", method = RequestMethod.POST)
    public ResponseEntity<?> removeAdmin(@RequestBody AdministratorProjectKey entryToDelete) {
        try{
            AdministratorsInProjects rowToDelete = administratorsInProjectsRepository.findByAdministratorProjectKey(entryToDelete);
            if (rowToDelete == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No entry found");
            }

            administratorsInProjectsRepository.delete(rowToDelete);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted " + entryToDelete.getUsername() + " from project id " + entryToDelete.getProjectId() + ".");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //endregion
}
