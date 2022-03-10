package ApplicationServer.JPA;

import ApplicationServer.Model.CompositeKeys.UserTaskKey;
import ApplicationServer.Model.UserTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTasksRepository extends JpaRepository<UserTasks, UserTaskKey> {
    List<UserTasks> getAllByUserTaskKeyUsername(String username);
}
