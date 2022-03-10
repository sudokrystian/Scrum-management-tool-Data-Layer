package ApplicationServer.Model;

import ApplicationServer.Model.CompositeKeys.UserTaskKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class UserTasks {
    @EmbeddedId
    private UserTaskKey userTaskKey;

    public UserTasks() {
    }

    public UserTasks(UserTaskKey userTaskKey) {
        this.userTaskKey = userTaskKey;
    }

    public UserTaskKey getUserTaskKey() {
        return userTaskKey;
    }

    public void setUserTaskKey(UserTaskKey userTaskKey) {
        this.userTaskKey = userTaskKey;
    }
}
