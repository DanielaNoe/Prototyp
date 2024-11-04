import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private UUID userId;

    private String name;

    private String phoneNumber;

    @OneToMany(mappedBy = "announcedLostBy", fetch = FetchType.EAGER)
    private List<GhostNet> lostGhostNets;

    public Person() {
    }

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<GhostNet> getLostGhostNets() {
        return lostGhostNets;
    }

    public void setLostGhostNets(List<GhostNet> lostGhostNets) {
        this.lostGhostNets = lostGhostNets;
    }
}
