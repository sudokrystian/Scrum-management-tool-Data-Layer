package ApplicationServer.JPA;

import ApplicationServer.Model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Integer> {
    List<Tasks> getAllBySprintUserStoryId(int sprintUserStoryId);
}
