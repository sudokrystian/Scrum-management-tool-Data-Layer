package ApplicationServer.JPA;

import ApplicationServer.Model.AdministratorsInProjects;
import ApplicationServer.Model.CompositeKeys.AdministratorProjectKey;
import ApplicationServer.Model.CompositeKeys.UserProjectKey;
import ApplicationServer.Model.UsersInProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministratorsInProjectsRepository extends JpaRepository<AdministratorsInProjects, AdministratorProjectKey> {
    List<AdministratorsInProjects> findByAdministratorProjectKeyProjectId(int projectId);
    AdministratorsInProjects findByAdministratorProjectKey(AdministratorProjectKey key);
}
