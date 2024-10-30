import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class RecoveringPersonController implements Serializable {

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

    private String name;
    private String password;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String addRecoveringPerson() {
        if (!this.validateInputFields()) {
            return this.navigationService.stayOnPage();
        }

        if (this.recoveringPersonDAO.getRecoveringPersonPersonByPhoneNumber(this.phoneNumber) != null) {
            this.messageService.addMessage(new Message("User with this phone number already exists!", MessageType.FAILURE));
            return this.navigationService.stayOnPage();
        }

        this.recoveringPersonDAO.addRecoveringPerson(
                new RecoveringPerson(this.name, this.phoneNumber, this.portal.hashPassword(this.password))
        );

        return this.navigationService.getLoginPage();
    }

    public String toRegistrationPage() {
        return this.navigationService.getRegistrationPage();
    }

    private boolean validateInputFields() {
        boolean valid = true;

        ValidationResult nameResult = this.validationService.validateName(this.name, true);
        ValidationResult passwordResult = this.validationService.validatePassword(this.password, true);
        ValidationResult phoneNumberResult = this.validationService.validatePhoneNumber(this.phoneNumber, true);

        if (!nameResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(nameResult.getMessage(), MessageType.FAILURE));
        }
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
