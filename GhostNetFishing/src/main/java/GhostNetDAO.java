import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Named
@ApplicationScoped
public class GhostNetDAO {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public GhostNetDAO() {
        try {
            this.entityManager = Persistence.createEntityManagerFactory("ghost-net-fishing").createEntityManager();
            this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void addGhostNet(GhostNet ghostNet) {
        try {
            EntityTransaction transaction = this.entityManager.getTransaction();
            transaction.begin();
            this.entityManager.persist(ghostNet);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<GhostNet> getReportedGhostNets() {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        query.select(root).where(criteriaBuilder.equal(root.get("status"), GhostNetStatus.REPORTED))
                .orderBy(criteriaBuilder.desc(root.get("size")));

        try {
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
