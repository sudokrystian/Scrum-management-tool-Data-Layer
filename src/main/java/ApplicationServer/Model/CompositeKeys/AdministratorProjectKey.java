package ApplicationServer.Model.CompositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AdministratorProjectKey implements Serializable {
    @Column(name = "administrator_username")
    private String username;

    @Column(name = "project_id")
    private int projectId;

    public AdministratorProjectKey() {
    }


    public AdministratorProjectKey(String username, int projectId) {
        this.username = username;
        this.projectId = projectId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdministratorProjectKey)) return false;
        AdministratorProjectKey that = (AdministratorProjectKey) o;
        return getProjectId() == that.getProjectId() &&
                getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getProjectId());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
