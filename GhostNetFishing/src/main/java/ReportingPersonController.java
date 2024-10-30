import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class ReportingPersonController implements Serializable {

    @Inject
    private ReportingPersonDAO reportingPersonDAO;

    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ReportingPerson addReportingPerson() {
        if (this.phoneNumber != null && !this.phoneNumber.isEmpty()) {
            return reportingPersonDAO.addReportingPerson(new ReportingPerson(this.name, this.phoneNumber));
        }

        return this.reportingPersonDAO.addReportingPerson(new ReportingPerson(this.name));
    }
}
