import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class GhostNetController implements Serializable {

    @Inject
    private ValidationService validationService;

    @Inject
    private NavigationService navigationService;

    @Inject
    private MessageService messageService;

    @Inject
    private CurrentApplicationUser currentApplicationUser;

    @Inject
    private GhostNetDAO ghostNetDAO;

    @Inject
    private ReportingPersonController reportingPersonController;

    private String latitude;
    private String longitude;
    private int size;

    private String name;
    private String phoneNumber;

    private List<GhostNet> reportedGhostNets = new ArrayList<>();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }


    public List<GhostNet> getReportedGhostNets() {
        return reportedGhostNets = this.ghostNetDAO.getReportedGhostNets();
    }

    public String addGhostNetAsRecoveringPerson() {
        if (!this.validateInputFieldsGhostNet()) {
            return this.navigationService.stayOnPage();
        }

        this.ghostNetDAO.addGhostNet(
                new GhostNet(this.latitude, this.longitude, this.size, GhostNetStatus.REPORTED, this.currentApplicationUser.getRecoveringPerson())
        );

        this.messageService.addMessage(new Message("Ghost net reported successfully!", MessageType.SUCCESS));
        return this.navigationService.getReportingPage();
    }

    public String addGhostNetAsReportingPerson() {
        if (!this.validateInputFieldsGhostNet() || !this.validateInputFieldsReportingPerson()) {
            return this.navigationService.stayOnPage();
        }

        this.reportingPersonController.setName(this.name);
        this.reportingPersonController.setPhoneNumber(this.phoneNumber);
        ReportingPerson person = this.reportingPersonController.addReportingPerson();

        this.ghostNetDAO.addGhostNet(
                new GhostNet(this.latitude, this.longitude, this.size, GhostNetStatus.REPORTED, person)
        );

        this.messageService.addMessage(new Message("Ghost net reported successfully!", MessageType.SUCCESS));
        return this.navigationService.getReportingPage();
    }

    public void announceRecovering() {
        // TODO: implement
    }

    public String goToGhostNetReporting() {
        return this.navigationService.getReportingPage();
    }

    private boolean validateInputFieldsGhostNet() {
        boolean valid = true;

        ValidationResult latitudeResult = this.validationService.validateLatitude(this.latitude, true);
        ValidationResult longitudeResult = this.validationService.validateLongitude(this.longitude, true);
        ValidationResult sizeResult = this.validationService.validateSize(this.size, true);

        if (!latitudeResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(latitudeResult.getMessage(), MessageType.FAILURE));
        }
        if (!longitudeResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(longitudeResult.getMessage(), MessageType.FAILURE));
        }
        if (!sizeResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(sizeResult.getMessage(), MessageType.FAILURE));
        }

        return valid;
    }

    private boolean validateInputFieldsReportingPerson() {
        boolean valid = true;

        ValidationResult nameResult = this.validationService.validateName(this.name, true);
        ValidationResult phoneNumberResult = this.validationService.validatePhoneNumber(this.phoneNumber, false);

        if (!nameResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(nameResult.getMessage(), MessageType.FAILURE));
        }
        if (!phoneNumberResult.isValid()) {
            valid = false;
            this.messageService.addMessage(new Message(phoneNumberResult.getMessage(), MessageType.FAILURE));
        }

        return valid;
    }
}
