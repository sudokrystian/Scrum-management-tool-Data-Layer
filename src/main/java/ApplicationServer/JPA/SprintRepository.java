package ApplicationServer.JPA;

import ApplicationServer.Model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {
    List<Sprint> findAllByProjectId(int id);
    List<Sprint> findBySprintId(int id);
}
