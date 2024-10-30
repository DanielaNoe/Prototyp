import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class GhostNetDAO {

    private EntityManager entityManager;

    public GhostNetDAO() {
        try {
            this.entityManager = Persistence.createEntityManagerFactory("ghost-net-fishing").createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
