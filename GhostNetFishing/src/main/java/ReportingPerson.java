import jakarta.persistence.Entity;

@Entity
public class ReportingPerson extends Person {

    private String phoneNumber;

    public ReportingPerson() {
    }

    public ReportingPerson(String name) {
        super(name);
    }

    public ReportingPerson(String name, String phoneNumber) {
        super(name);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
