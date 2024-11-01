import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class LocationDAO {

    private EntityManager entityManager;

    public LocationDAO() {
        try {
            this.entityManager = Persistence.createEntityManagerFactory("ghost-net-fishing").createEntityManager();
        } catch (Exception e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Location addLocation(Location location) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(location);
            transaction.commit();
            return location;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
