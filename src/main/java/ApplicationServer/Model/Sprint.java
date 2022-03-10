package ApplicationServer.Model;

import javax.persistence.*;

@Entity
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sprint_id")
    private int sprintId;

    @Column(name = "project_id")
    private int projectId;

    @Column(name = "sprint_number")
    private int sprintNumber;

    @Column(name = "date_started")
    private String dateStarted;

    @Column(name = "date_finished")
    private String dateFinished;

    @Column(name = "product_owner_username")
    private String productOwnerUsername = "fg";

    @Column(name = "scrum_master_username")
    private String scrumMasterUsername = "asdas";

    @Column(name = "status")
    private String status;

    public Sprint() {
    }

    public Sprint(int projectId, int sprintNumber) {
        this.projectId = projectId;
        this.sprintNumber = sprintNumber;
    }

    public Sprint(int projectId, int sprintNumber, String dateStarted, String dateFinished, String productOwnerUsername, String scrumMasterUsername, String status) {
        this.projectId = projectId;
        this.sprintNumber = sprintNumber;
        this.dateStarted = dateStarted;
        this.dateFinished = dateFinished;
        this.productOwnerUsername = productOwnerUsername;
        this.scrumMasterUsername = scrumMasterUsername;
        this.status = status;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSprintNumber() {
        return sprintNumber;
    }

    public void setSprintNumber(int sprintNumber) {
        this.sprintNumber = sprintNumber;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateFinished() {
        return dateFinished;
    }

    public void setDateFinished(String dateFinished) {
        this.dateFinished = dateFinished;
    }

    public String getProductOwnerUsername() {
        return productOwnerUsername;
    }

    public void setProductOwnerUsername(String productOwnerUsername) {
        this.productOwnerUsername = productOwnerUsername;
    }

    public String getScrumMasterUsername() {
        return scrumMasterUsername;
    }

    public void setScrumMasterUsername(String scrumMasterUsername) {
        this.scrumMasterUsername = scrumMasterUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "sprintId=" + sprintId +
                ", projectId=" + projectId +
                ", sprintNumber=" + sprintNumber +
                ", dateStarted='" + dateStarted + '\'' +
                ", dateFinished='" + dateFinished + '\'' +
                ", productOwnerUsername=" + productOwnerUsername +
                ", scrumMasterUsername=" + scrumMasterUsername +
                ", status='" + status + '\'' +
                '}';
    }
}
