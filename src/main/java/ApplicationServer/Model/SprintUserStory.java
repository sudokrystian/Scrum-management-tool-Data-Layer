package ApplicationServer.Model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "sprint_user_story")
public class SprintUserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sprint_user_story_id")
    private int sprintUserStoryId;

    @Column(name = "user_story_id")
    private int userStoryId;

    @Column(name = "sprint_backlog_id")
    private int sprintBacklogId;

    public SprintUserStory() {
    }

    public SprintUserStory(int userStoryId, int sprintBacklogId) {
        this.userStoryId = userStoryId;
        this.sprintBacklogId = sprintBacklogId;
    }

    public int getSprintUserStoryId() {
        return sprintUserStoryId;
    }

    public void setSprintUserStoryId(int sprintUserStoryId) {
        this.sprintUserStoryId = sprintUserStoryId;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getSprintBacklogId() {
        return sprintBacklogId;
    }

    public void setSprintBacklogId(int sprintBacklogId) {
        this.sprintBacklogId = sprintBacklogId;
    }
}
