import jakarta.persistence.Entity;

@Entity
public class RecoveringPerson extends Person {

    private String phoneNumber;

    private String password;

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
}
