import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class GhostNet {

    @Id
    @GeneratedValue
    private UUID ghostNetId;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private GhostNetStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reportingUserId", nullable = false)
    private Person reportedBy;


    public GhostNet() {
    }

    public GhostNet(String latitude, String longitude, int size, GhostNetStatus status, Person reportedBy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.size = size;
        this.status = status;
        this.reportedBy = reportedBy;
    }

    public UUID getGhostNetId() {
        return ghostNetId;
    }

    public void setGhostNetId(UUID ghostNetId) {
        this.ghostNetId = ghostNetId;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GhostNetStatus getStatus() {
        return status;
    }

    public void setStatus(GhostNetStatus status) {
        this.status = status;
    }

    public Person getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Person reportedBy) {
        this.reportedBy = reportedBy;
    }
}
