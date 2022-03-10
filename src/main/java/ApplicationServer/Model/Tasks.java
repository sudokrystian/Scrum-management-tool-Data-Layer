package ApplicationServer.Model;

import javax.persistence.*;

@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private int taskId;

    @Column(name = "sprint_user_story_id")
    private int sprintUserStoryId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    public Tasks() {
    }

    public Tasks(int sprintUserStoryId, String description, String status) {
        this.sprintUserStoryId = sprintUserStoryId;
        this.description = description;
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getSprintUserStoryId() {
        return sprintUserStoryId;
    }

    public void setSprintUserStoryId(int sprintUserStoryId) {
        this.sprintUserStoryId = sprintUserStoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "taskId=" + taskId +
                ", sprintUserStoryId=" + sprintUserStoryId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
