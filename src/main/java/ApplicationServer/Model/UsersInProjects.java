package ApplicationServer.Model;

import ApplicationServer.Model.CompositeKeys.UserProjectKey;

import javax.persistence.*;

@Entity
public class UsersInProjects{
    @EmbeddedId
    private UserProjectKey userProjectKey;

    public UsersInProjects() {
    }

    public UsersInProjects(UserProjectKey userProjectKey) {
        this.userProjectKey = userProjectKey;
    }

    public UserProjectKey getUserProjectKey() {
        return userProjectKey;
    }

    public void setUserProjectKey(UserProjectKey userProjectKey) {
        this.userProjectKey = userProjectKey;
    }
}
