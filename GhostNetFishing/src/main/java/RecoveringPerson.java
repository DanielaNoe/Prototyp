import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class RecoveringPerson extends Person {

    private String phoneNumber;

    private String password;

    @OneToMany(mappedBy = "recoveringAnnouncedBy", fetch = FetchType.EAGER)
    private List<GhostNet> recoveringAnnouncedGhostNets;

    @OneToMany(mappedBy = "recoveredBy", fetch = FetchType.EAGER)
    private List<GhostNet> recoveredGhostNets;

    @OneToMany(mappedBy = "announcesLostBy", fetch = FetchType.EAGER)
    private List<GhostNet> lostGhostNets;

    public RecoveringPerson() {
    }

    public RecoveringPerson(String name, String phoneNumber, String password) {
        super(name);
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<GhostNet> getLostGhostNets() {
        return lostGhostNets;
    }

    public void setLostGhostNets(List<GhostNet> lostGhostNets) {
        this.lostGhostNets = lostGhostNets;
    }
}
