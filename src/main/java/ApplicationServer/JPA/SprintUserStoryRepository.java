package ApplicationServer.JPA;

import ApplicationServer.Model.SprintUserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintUserStoryRepository extends JpaRepository<SprintUserStory, Integer> {
    List<SprintUserStory> findAllBySprintBacklogId(int sprintBacklogId);
}