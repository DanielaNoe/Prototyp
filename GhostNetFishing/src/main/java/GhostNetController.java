import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private RecoveringPersonDAO recoveringPersonDAO;

    @Inject
    private ReportingPersonController reportingPersonController;

    private String latitude;
    private String longitude;
    private int size;

    private String name;
    private String phoneNumber;

    private List<GhostNet> reportedGhostNets = new ArrayList<>();

    private List<GhostNet> recoveringAnnouncedGhostNets = new ArrayList<>();
    private List<GhostNet> recoveredGhostNets = new ArrayList<>();
    private List<GhostNet> lostGhostNets = new ArrayList<>();

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
        return reportedGhostNets = this.ghostNetDAO.getAllReportedGhostNets();
    }

    public List<GhostNet> getRecoveringAnnouncedGhostNets() {
        return recoveringAnnouncedGhostNets = this.ghostNetDAO.getGhostNetsByStatusAndUserId(GhostNetStatus.RECOVERING_ANNOUNCED, this.currentApplicationUser.getRecoveringPerson().getUserId());
    }

    public List<GhostNet> getRecoveredGhostNets() {
        return recoveredGhostNets = this.ghostNetDAO.getGhostNetsByStatusAndUserId(GhostNetStatus.RECOVERED, this.currentApplicationUser.getRecoveringPerson().getUserId());
    }

    public List<GhostNet> getLostGhostNets() {
        return lostGhostNets = this.ghostNetDAO.getGhostNetsByStatusAndUserId(GhostNetStatus.LOST, this.currentApplicationUser.getRecoveringPerson().getUserId());
    }

    public String addGhostNetAsRecoveringPerson() {
        if (this.validateInputFieldsGhostNet()) {
            this.ghostNetDAO.addGhostNet(
                    new GhostNet(this.latitude, this.longitude, this.size, GhostNetStatus.REPORTED, this.currentApplicationUser.getRecoveringPerson())
            );
            return this.navigationService.getPortalPage();
        }
        return this.navigationService.stayOnPage();
    }

    public void addGhostNetAsReportingPerson() {
        if (this.validateInputFieldsGhostNet() && this.validateInputFieldsReportingPerson()) {
            this.reportingPersonController.setName(this.name);
            this.reportingPersonController.setPhoneNumber(this.phoneNumber);
            ReportingPerson person = this.reportingPersonController.addReportingPerson();

            this.ghostNetDAO.addGhostNet(
                    new GhostNet(this.latitude, this.longitude, this.size, GhostNetStatus.REPORTED, person)
            );

            this.resetInputFields();
        }
    }

    public String announceRecovering(UUID ghostNetId) {
        this.ghostNetDAO.changeGhostNetStatus(ghostNetId, GhostNetStatus.RECOVERING_ANNOUNCED);
        return this.navigationService.getPortalPage();
    }

    public String announceRecovered(UUID ghostNetId) {
        this.ghostNetDAO.changeGhostNetStatus(ghostNetId, GhostNetStatus.RECOVERED);
        return this.navigationService.getPortalPage();
    }

    public String announceLost(UUID ghostNetId) {
        this.ghostNetDAO.changeGhostNetStatus(ghostNetId, GhostNetStatus.LOST);
        return this.navigationService.getPortalPage();
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

    private void resetInputFields() {
        this.latitude = null;
        this.longitude = null;
        this.size = 0;
        this.name = null;
        this.phoneNumber = null;
    }
}
