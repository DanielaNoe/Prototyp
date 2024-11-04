import jakarta.persistence.*;

import java.util.List;

@Entity
public class ReportingPerson extends Person {

    @OneToMany(mappedBy = "reportedBy", fetch = FetchType.EAGER)
    private List<GhostNet> reportedGhostNets;

    public ReportingPerson() {
    }

    public ReportingPerson(String name, String phoneNumber) {
        super(name, phoneNumber);
    }

    public List<GhostNet> getReportedGhostNets() {
        return reportedGhostNets;
    }

    public void setReportedGhostNets(List<GhostNet> reportedGhostNets) {
        this.reportedGhostNets = reportedGhostNets;
    }
}
