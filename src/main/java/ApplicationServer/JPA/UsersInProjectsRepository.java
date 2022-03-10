package ApplicationServer.JPA;

import ApplicationServer.Model.CompositeKeys.UserProjectKey;
import ApplicationServer.Model.UsersInProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersInProjectsRepository extends JpaRepository<UsersInProjects, UserProjectKey> {
    List<UsersInProjects> findByUserProjectKeyUsername(String username);
    List<UsersInProjects> findByUserProjectKeyProjectId(int id);
    List<UsersInProjects> findByUserProjectKeyProjectIdNot(int id);
    UsersInProjects findByUserProjectKey(UserProjectKey key);
}
