import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class CurrentApplicationUser implements Serializable {

    private RecoveringPerson recoveringPerson;
    private boolean isLoggedIn = false;

    public void login(RecoveringPerson recoveringPerson) {
        this.isLoggedIn = true;
        this.recoveringPerson = recoveringPerson;
    }

    public void logout() {
        this.isLoggedIn = false;
        this.recoveringPerson = null;
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public RecoveringPerson getRecoveringPerson() {
        return this.recoveringPerson;
    }
}
