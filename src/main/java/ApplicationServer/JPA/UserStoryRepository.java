package ApplicationServer.JPA;

import ApplicationServer.Model.Project;
import ApplicationServer.Model.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepository extends JpaRepository<UserStory, Integer> {
    List<UserStory> findAllByProductBacklogId(int id);
    List<UserStory> findByUserStoryId(int id);
}



