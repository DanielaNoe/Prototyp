import jakarta.persistence.*;

import java.util.List;

@Entity
public class RecoveringPerson extends Person {

    private String password;

    @OneToMany(mappedBy = "recoveringPerson", fetch = FetchType.EAGER)
    private List<GhostNet> assignedGhostNets;

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

    public List<GhostNet> getAssignedGhostNets() {
        return assignedGhostNets;
    }

    public void setAssignedGhostNets(List<GhostNet> assignedGhostNets) {
        this.assignedGhostNets = assignedGhostNets;
    }
}
