import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class GhostNet {

    @Id
    @GeneratedValue
    private UUID ghostNetId;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false)
    private GhostNetStatus status;

    @ManyToOne
    @JoinColumn(name = "locationId", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "reportingUserId")
    private ReportingPerson reportedBy;

    @ManyToOne
    @JoinColumn(name = "recovererUserId")
    private RecoveringPerson recoveringPerson;

    @ManyToOne
    @JoinColumn(name = "announcedLostUserId")
    private Person announcedLostBy;


    public GhostNet() {
    }

    public GhostNet(Location location, int size, GhostNetStatus status) {
        this.location = location;
        this.size = size;
        this.status = status;
    }

    public GhostNet(Location location, int size, GhostNetStatus status, ReportingPerson reportedBy) {
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public ReportingPerson getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(ReportingPerson reportedBy) {
        this.reportedBy = reportedBy;
    }

    public RecoveringPerson getRecoveringPerson() {
        return recoveringPerson;
    }

    public void setRecoveringPerson(RecoveringPerson recoveringPerson) {
        this.recoveringPerson = recoveringPerson;
    }

    public Person getAnnouncedLostBy() {
        return announcedLostBy;
    }

    public void setAnnouncedLostBy(Person announcedLostBy) {
        this.announcedLostBy = announcedLostBy;
    }
}
