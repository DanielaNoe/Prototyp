import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginController implements Serializable {

    @Inject
    private ValidationService validationService;

    @Inject
    private NavigationService navigationService;

    @Inject
    private MessageService messageService;

    @Inject
    private Portal portal;

    @Inject
    private RecoveringPersonDAO recoveringPersonDAO;

    @Inject
    private CurrentApplicationUser currentApplicationUser;

    private String phoneNumber;
    private String password;

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

    public String validateLogin() {
        this.currentApplicationUser.logout();

        if (!this.validateInputFields()) {
            return this.navigationService.stayOnPage();
        }

        RecoveringPerson person = this.recoveringPersonDAO.getRecoveringPersonPersonByPhoneNumber(this.phoneNumber);

        if (person == null) {
            this.messageService.addMessage(new Message("Phone number not found!", MessageType.FAILURE));
            return this.navigationService.stayOnPage();
        }

        String hashedPassword = this.portal.hashPassword(this.password);

        if (!person.getPassword().equals(hashedPassword)) {
            this.messageService.addMessage(new Message("Password is wrong!", MessageType.FAILURE));
            return this.navigationService.stayOnPage();
        }

        this.currentApplicationUser.login(person);
        this.messageService.addMessage(new Message("Login successful!", MessageType.SUCCESS));
        return this.navigationService.getPortalPage();
    }

    public boolean isLoggedIn() {
        return this.currentApplicationUser.isLoggedIn();
    }

    public String logout() {
        this.currentApplicationUser.logout();
        return this.navigationService.getLoginPage();
    }

    public String toLoginPage() {
        return this.navigationService.getLoginPage();
    }

    public void redirectToLoginPage() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.navigationService.getLoginPage());
        } catch (IOException e) {
            System.err.println("Fehler: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean validateInputFields() {
        boolean valid = true;

        ValidationResult passwordResult = this.validationService.validatePassword(this.password, true);
        ValidationResult phoneNumberResult = this.validationService.validatePhoneNumber(this.phoneNumber, true);

        if (!passwordResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(passwordResult.getMessage(), MessageType.FAILURE));
        }
        if (!phoneNumberResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(phoneNumberResult.getMessage(), MessageType.FAILURE));
        }

        return valid;
    }
}
