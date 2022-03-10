package ApplicationServer.JPA;

import ApplicationServer.Model.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, Integer> {
    ProductBacklog findByProjectId(int projectId);

}
