package ApplicationServer.Model;

import ApplicationServer.Model.CompositeKeys.AdministratorProjectKey;
import ApplicationServer.Model.CompositeKeys.UserProjectKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class AdministratorsInProjects {
    @EmbeddedId
    private AdministratorProjectKey administratorProjectKey;

    public AdministratorsInProjects() {
    }

    public AdministratorsInProjects(AdministratorProjectKey administratorProjectKey) {
        this.administratorProjectKey = administratorProjectKey;
    }

    public AdministratorProjectKey getAdministratorProjectKey() {
        return administratorProjectKey;
    }

    public void setAdministratorProjectKey(AdministratorProjectKey administratorProjectKey) {
        this.administratorProjectKey = administratorProjectKey;
    }
}
