import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class NavigationService {

    private final String portalPage = "portal.xhtml?faces-redirect=true";
    private final String registrationPage = "registration.xhtml?faces-redirect=true";
    private final String loginPage = "login.xhtml?faces-redirect=true";
    private final String reportingPage = "index.xhtml?faces-redirect=true";
    private final String overviewPage = "overview.xhtml?faces-redirect=true";

    public String getPortalPage() {
        return portalPage;
    }

    public String getRegistrationPage() {
        return registrationPage;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public String getReportingPage() {
        return reportingPage;
    }

    public String getOverviewPage() {
        return overviewPage;
    }

    public String stayOnPage() {
        return null;
    }
}
