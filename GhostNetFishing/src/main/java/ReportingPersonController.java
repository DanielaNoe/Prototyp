import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ReportingPersonController implements Serializable {

    @Inject
    private ReportingPersonDAO reportingPersonDAO;

    public ReportingPerson addReportingPerson(ReportingPerson reportingPerson) {
        return reportingPersonDAO.addReportingPerson(reportingPerson);
    }
}
