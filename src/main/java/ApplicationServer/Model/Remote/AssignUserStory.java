package ApplicationServer.Model.Remote;

public class AssignUserStory {
    private int userStoryId;
    private int sprintId;

    public AssignUserStory() {
    }

    public AssignUserStory(int userStoryId, int sprintId) {
        this.userStoryId = userStoryId;
        this.sprintId = sprintId;
    }

    public int getUserStoryId() {
        return userStoryId;
    }

    public void setUserStoryId(int userStoryId) {
        this.userStoryId = userStoryId;
    }

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }
}
