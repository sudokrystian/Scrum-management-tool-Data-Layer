package ApplicationServer.Model;

import javax.persistence.*;

@Entity(name = "user_story")
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_story_id")
    private int userStoryId;

    @Column(name = "product_backlog_id")
    private int productBacklogId;

    @Column(name = "priority")
    private String priority;

    @Column(name = "description")
    private String description;

    @Column(name = "difficulty")
    private int difficulty;

    @Column(name = "status")
    private String status;

    public UserStory() {
    }

    public UserStory(int productBacklogId, String priority, String description, int difficulty, String status) {
        this.productBacklogId = productBacklogId;
        this.priority = priority;
        this.description = description;
        this.difficulty = difficulty;
        this.status = status;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getProductBacklogId() {
        return productBacklogId;
    }

    public void setProductBacklogId(int productBacklogId) {
        this.productBacklogId = productBacklogId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
