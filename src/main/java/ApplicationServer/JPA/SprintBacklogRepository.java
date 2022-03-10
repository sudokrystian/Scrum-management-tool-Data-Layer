package ApplicationServer.JPA;

import ApplicationServer.Model.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintBacklogRepository extends JpaRepository<SprintBacklog, Integer> {
    SprintBacklog findBySprintId(int sprintId);
}
