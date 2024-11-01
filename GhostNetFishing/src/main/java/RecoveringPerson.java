import jakarta.persistence.*;

import java.util.List;

@Entity
public class RecoveringPerson extends Person {

    @OneToMany(mappedBy = "recoveringAnnouncedBy", fetch = FetchType.EAGER)
    private List<GhostNet> recoveringAnnouncedGhostNets;

    @OneToMany(mappedBy = "recoveredBy", fetch = FetchType.EAGER)
    private List<GhostNet> recoveredGhostNets;

    public RecoveringPerson() {
    }

    public RecoveringPerson(String name, String phoneNumber, String password) {
        super(name, phoneNumber, password);
    }

    public List<GhostNet> getRecoveringAnnouncedGhostNets() {
        return recoveringAnnouncedGhostNets;
    }

    public void setRecoveringAnnouncedGhostNets(List<GhostNet> recoveringAnnouncedGhostNets) {
        this.recoveringAnnouncedGhostNets = recoveringAnnouncedGhostNets;
    }

    public List<GhostNet> getRecoveredGhostNets() {
        return recoveredGhostNets;
    }

    public void setRecoveredGhostNets(List<GhostNet> recoveredGhostNets) {
        this.recoveredGhostNets = recoveredGhostNets;
    }
}
