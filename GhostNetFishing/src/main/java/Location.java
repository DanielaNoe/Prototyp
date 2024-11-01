import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private UUID locationId;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    private List<GhostNet> ghostNets;

    public Location() {
    }

    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<GhostNet> getGhostNets() {
        return ghostNets;
    }

    public void setGhostNets(List<GhostNet> ghostNets) {
        this.ghostNets = ghostNets;
    }
}
