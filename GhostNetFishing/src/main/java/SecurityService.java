import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@Named
@ApplicationScoped
public class SecurityService {

    @ConfigProperty(name = "securityService.pepper")
    private String pepper;

    @ConfigProperty(name = "securityService.salt")
    private String salt;

    private EntityManager entityManager;

    public SecurityService() {
        try {
            this.entityManager = Persistence.createEntityManagerFactory("ghost-net-fishing").createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digester.digest((this.pepper + password + this.salt)
                    .getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(hashBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String toPortalPage() {
        return "portal.xhtml?faces-redirect=true";
    }

    public static void main(String[] args) {
        new SecurityService();
    }
}
