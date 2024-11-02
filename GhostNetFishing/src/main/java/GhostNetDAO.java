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
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(ghostNet);
            transaction.commit();

            this.messageService.addMessage(new Message("Meldung erfolgreich!", MessageType.SUCCESS));
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void changeGhostNetStatus(UUID ghostNetId, GhostNetStatus status) {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        query.select(root).where(criteriaBuilder.equal(root.get("ghostNetId"), ghostNetId));

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            GhostNet ghostNet = entityManager.createQuery(query).setMaxResults(1).getSingleResult();

            ghostNet.setStatus(status);

            switch(status) {
                case RECOVERING_ANNOUNCED:
                    ghostNet.setRecoveringPerson(this.currentApplicationUser.getRecoveringPerson());
                    break;
                case LOST:
                    ghostNet.setAnnouncedLostBy(this.currentApplicationUser.getRecoveringPerson());
                    break;
            }

            transaction.commit();

            this.messageService.addMessage(new Message("Status erfolgreich geändert!", MessageType.SUCCESS));
        } catch (NoResultException e) {
            this.messageService.addMessage(new Message("Geisternetz nicht gefunden!", MessageType.FAILURE));
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void announceLostAsReportingPerson(UUID ghostNetId, ReportingPerson person) {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        query.select(root).where(criteriaBuilder.equal(root.get("ghostNetId"), ghostNetId));

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            GhostNet ghostNet = entityManager.createQuery(query).setMaxResults(1).getSingleResult();

            ghostNet.setStatus(GhostNetStatus.LOST);
            ghostNet.setAnnouncedLostBy(person);

            transaction.commit();

            this.messageService.addMessage(new Message("Status erfolreich geändert!", MessageType.SUCCESS));
        } catch (NoResultException e) {
            this.messageService.addMessage(new Message("Geisternetz nicht gefunden!", MessageType.FAILURE));
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<GhostNet> getAllReportedGhostNets() {
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

    public List<GhostNet> getAllLostGhostNets() {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        query.select(root).where(criteriaBuilder.equal(root.get("status"), GhostNetStatus.LOST))
                .orderBy(criteriaBuilder.desc(root.get("size")));

        try {
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<GhostNet> getGhostNetsByStatusAndUserId(GhostNetStatus status, UUID userId) {
        CriteriaQuery<GhostNet> query = criteriaBuilder.createQuery(GhostNet.class);
        Root<GhostNet> root = query.from(GhostNet.class);

        switch(status) {
            case RECOVERING_ANNOUNCED:
                query.select(root).where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("status"), status),
                                criteriaBuilder.equal(root.get("recoveringAnnouncedBy").get("userId"), userId)))
                        .orderBy(criteriaBuilder.desc(root.get("size")));
                break;
            case RECOVERED:
                query.select(root).where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("status"), status),
                                criteriaBuilder.equal(root.get("recoveredBy").get("userId"), userId)))
                        .orderBy(criteriaBuilder.desc(root.get("size")));
                break;
            case LOST:
                query.select(root).where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get("status"), status),
                                criteriaBuilder.equal(root.get("announcedLostBy").get("userId"), userId)))
                        .orderBy(criteriaBuilder.desc(root.get("size")));
                break;
        }

        try {
            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
