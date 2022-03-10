package ApplicationServer.Model.Remote;

public class ScrumRole {
    private String username;
    private int sprintId;

    public ScrumRole() {
    }

    public ScrumRole(String username, int sprintId) {
        this.username = username;
        this.sprintId = sprintId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }
}
