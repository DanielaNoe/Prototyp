import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Named
@ApplicationScoped
public class RecoveringPersonDAO {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public RecoveringPersonDAO() {
        try {
            this.entityManager = Persistence.createEntityManagerFactory("ghost-net-fishing").createEntityManager();
            this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void addRecoveringPerson(RecoveringPerson person) {
        try {
            EntityTransaction transaction = this.entityManager.getTransaction();
            transaction.begin();
            this.entityManager.persist(person);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public RecoveringPerson getRecoveringPersonPersonByPhoneNumber(String phoneNumber) {
        CriteriaQuery<RecoveringPerson> query = this.criteriaBuilder.createQuery(RecoveringPerson.class);
        Root<RecoveringPerson> root = query.from(RecoveringPerson.class);

        query.select(root).where(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));

        try {
            return entityManager.createQuery(query).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
