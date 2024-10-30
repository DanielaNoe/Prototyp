import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "reportedBy")
    private List<GhostNet> reportedGhostNets;

    @OneToMany(mappedBy = "recoveringAnnouncedBy")
    private List<GhostNet> recoveringAnnouncedGhostNets;


    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GhostNet> getReportedGhostNets() {
        return reportedGhostNets;
    }

    public void setReportedGhostNets(List<GhostNet> reportedGhostNets) {
        this.reportedGhostNets = reportedGhostNets;
    }

    public List<GhostNet> getRecoveringAnnouncedGhostNets() {
        return recoveringAnnouncedGhostNets;
    }

    public void setRecoveringAnnouncedGhostNets(List<GhostNet> recoveringAnnouncedGhostNets) {
        this.recoveringAnnouncedGhostNets = recoveringAnnouncedGhostNets;
    }
}
