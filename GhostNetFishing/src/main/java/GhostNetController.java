import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
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
    private ReportingPersonController reportingPersonController;

    private String latitude;
    private String longitude;
    private int size;

    private boolean anonymous = false;
    private String name;
    private String phoneNumber;

    private String tempName;
    private String tempPhoneNumber;
    private GhostNet tempGhostNet;

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

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getTempPhoneNumber() {
        return tempPhoneNumber;
    }

    public void setTempPhoneNumber(String tempPhoneNumber) {
        this.tempPhoneNumber = tempPhoneNumber;
    }

    public GhostNet getTempGhostNet() {
        return tempGhostNet;
    }

    public void setTempGhostNet(GhostNet tempGhostNet) {
        this.tempGhostNet = tempGhostNet;
    }

    public List<GhostNet> getReportedGhostNetsOverview() {
        return this.ghostNetDAO.getAllReportedGhostNets();
    }

    public List<GhostNet> getReportedGhostNets() {
        return this.ghostNetDAO.getAllReportedGhostNets();
    }

    public List<GhostNet> getRecoveringAnnouncedGhostNets() {
        return this.ghostNetDAO.getGhostNetsByStatusAndUserId(GhostNetStatus.RECOVERING_ANNOUNCED, this.currentApplicationUser.getRecoveringPerson().getUserId());
    }

    public List<GhostNet> getRecoveredGhostNets() {
        return this.ghostNetDAO.getGhostNetsByStatusAndUserId(GhostNetStatus.RECOVERED, this.currentApplicationUser.getRecoveringPerson().getUserId());
    }

    public List<GhostNet> getLostGhostNets() {
        return this.ghostNetDAO.getGhostNetsByStatusAndUserId(GhostNetStatus.LOST, this.currentApplicationUser.getRecoveringPerson().getUserId());
    }

    public List<GhostNet> getAllLostGhostNets() {
        return this.ghostNetDAO.getAllLostGhostNets();
    }

    public void addGhostNetAsReportingPerson() {
        if (!anonymous) {
            if (this.validateInputFieldsGhostNet() && this.validateInputFieldsReportingPerson()) {
                ReportingPerson person = this.reportingPersonController.addReportingPerson(new ReportingPerson(this.name, this.phoneNumber));
                this.ghostNetDAO.addGhostNet(
                        new GhostNet(this.latitude, this.longitude, this.size, GhostNetStatus.REPORTED, person)
                );
            }
        }
        else {
            if (this.validateInputFieldsGhostNet()) {
                this.ghostNetDAO.addGhostNet(
                        new GhostNet(this.latitude, this.longitude, this.size, GhostNetStatus.REPORTED)
                );
            }
        }

        this.resetInputFields();
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

    public String announceLostAsReportingPerson() {
        if (this.validateInputFieldsAnnounceLostAsReportingPerson(this.tempName, this.tempPhoneNumber)) {
            ReportingPerson person = this.reportingPersonController.addReportingPerson(new ReportingPerson(this.tempName, this.tempPhoneNumber));
            this.ghostNetDAO.announceLostAsReportingPerson(this.tempGhostNet.getGhostNetId(), person);
        }

        this.resetTempVariables();
        return this.navigationService.getOverviewPage();
    }

    public String goToGhostNetReporting() {
        return this.navigationService.getReportingPage();
    }

    public String goToGhostNetOverview() {
        return this.navigationService.getOverviewPage();
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
        ValidationResult phoneNumberResult = this.validationService.validatePhoneNumber(this.phoneNumber, true);

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

    private boolean validateInputFieldsAnnounceLostAsReportingPerson(String name, String phoneNumber) {
        boolean valid = true;

        ValidationResult nameResult = this.validationService.validateName(name, true);
        ValidationResult phoneNumberResult = this.validationService.validatePhoneNumber(phoneNumber, true);

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
        this.anonymous = false;
    }

    private void resetTempVariables() {
        this.tempName = null;
        this.tempPhoneNumber = null;
        this.tempGhostNet = null;
    }
}
