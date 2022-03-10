package ApplicationServer.Model;

import ApplicationServer.Model.Statuses.BacklogStatus;

import javax.persistence.*;

@Entity(name = "product_backlog")
public class ProductBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_backlog_id")
    private int productBacklogId;

    @Column(name = "project_id")
    private int projectId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BacklogStatus status;

    public ProductBacklog() {
    }

    public ProductBacklog(int projectId, BacklogStatus status) {
        this.projectId = projectId;
        this.status = status;
    }


    public int getProductBacklogId() {
        return productBacklogId;
    }

    public void setProductBacklogId(int productBacklogId) {
        this.productBacklogId = productBacklogId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public BacklogStatus getStatus() {
        return status;
    }

    public void setStatus(BacklogStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ProductBacklog{" +
                "productBacklogId=" + productBacklogId +
                ", projectId=" + projectId +
                ", status=" + status +
                '}';
    }
}
