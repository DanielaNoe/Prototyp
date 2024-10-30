import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class ReportingPersonDAO {

    private EntityManager entityManager;

    public ReportingPersonDAO() {
        try {
            this.entityManager = Persistence.createEntityManagerFactory("ghost-net-fishing").createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ReportingPerson addReportingPerson(ReportingPerson person) {
        try {
            EntityTransaction transaction = this.entityManager.getTransaction();
            transaction.begin();
            this.entityManager.persist(person);
            transaction.commit();
            return person;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
