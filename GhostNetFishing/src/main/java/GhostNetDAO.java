import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.UUID;

@Named
@ApplicationScoped
public class GhostNetDAO {

    @Inject
    CurrentApplicationUser currentApplicationUser;

    @Inject
    MessageService messageService;

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

            this.messageService.addMessage(new Message("Ghost net reported successfully!", MessageType.SUCCESS));
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

    public List<GhostNet> getRecoveringAnnouncedGhostNets() {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        query.select(root).where(criteriaBuilder.equal(root.get("status"), GhostNetStatus.RECOVERING_ANNOUNCED))
                .orderBy(criteriaBuilder.desc(root.get("size")));

        try {
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void announceRecovering(UUID uuid) {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        query.select(root).where(criteriaBuilder.equal(root.get("status"), GhostNetStatus.REPORTED))
                .orderBy(criteriaBuilder.desc(root.get("size")));

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            GhostNet ghostNet = entityManager.createQuery(query).setMaxResults(1).getSingleResult();

            ghostNet.setStatus(GhostNetStatus.RECOVERING_ANNOUNCED);
            ghostNet.setRecoveringAnnouncedBy(this.currentApplicationUser.getRecoveringPerson());

            transaction.commit();

            this.messageService.addMessage(new Message("Ghost net recovery successfully announced!", MessageType.SUCCESS));
        } catch (NoResultException e) {
            this.messageService.addMessage(new Message("Ghost net not found!", MessageType.FAILURE));
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
