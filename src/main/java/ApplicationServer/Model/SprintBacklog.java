package ApplicationServer.Model;

import ApplicationServer.Model.Statuses.BacklogStatus;

import javax.persistence.*;

@Entity(name = "sprint_backlog")
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sprint_backlog_id")
    private int sprintBacklogId;

    @Column(name = "sprint_id")
    private int sprintId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BacklogStatus status;

    public SprintBacklog() {
    }

    public SprintBacklog(int sprintId, BacklogStatus status) {
        this.sprintId = sprintId;
        this.status = status;
    }

    public int getSprintBacklogId() {
        return sprintBacklogId;
    }

    public void setSprintBacklogId(int sprintBacklogId) {
        this.sprintBacklogId = sprintBacklogId;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public BacklogStatus getStatus() {
        return status;
    }

    public void setStatus(BacklogStatus status) {
        this.status = status;
    }
}
