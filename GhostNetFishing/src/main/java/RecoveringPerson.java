import jakarta.persistence.*;

import java.util.List;

@Entity
public class RecoveringPerson extends Person {

    private String password;

    @OneToMany(mappedBy = "recoveringPerson", fetch = FetchType.EAGER)
    private List<GhostNet> assignedAnnouncedGhostNets;

    public RecoveringPerson() {
    }

    public RecoveringPerson(String name, String phoneNumber, String password) {
        super(name, phoneNumber);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GhostNet> getAssignedAnnouncedGhostNets() {
        return assignedAnnouncedGhostNets;
    }

    public void setAssignedAnnouncedGhostNets(List<GhostNet> assignedAnnouncedGhostNets) {
        this.assignedAnnouncedGhostNets = assignedAnnouncedGhostNets;
    }
}
