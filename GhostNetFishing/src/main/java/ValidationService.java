import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@ApplicationScoped
public class ValidationService {

    private static final String NAME_PATTERN = "^[a-zA-ZäöüÄÖÜß-]{2,20}$";
    private static final String PASSWORD_PATTERN = "^.{5,15}$";
    private static final String PHONE_NUMBER_PATTERN = "^[0-9]{3,20}$";
    private static final String LATITUDE_PATTERN = "^(-?([0-8]?\\d(\\.\\d{1,4})?|90(\\.0{1,4})?))$";
    private static final String LONGITUDE_PATTERN = "^([-]?(180(\\.0{1,4})?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d{1,4})?))$";

    public ValidationResult validateName(String name, boolean required) {
        if (name == null || name.isEmpty()) {
            if (required) {
                return new ValidationResult("Name is required!", false);
            }
            return new ValidationResult("", true);
        }

        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        boolean valid = matcher.matches();

        return valid ? new ValidationResult("", true) : new ValidationResult("Name is invalid!", false);
    }

    public ValidationResult validatePassword(String password, boolean required) {
        if (password == null || password.isEmpty()) {
            if (required) {
                return new ValidationResult("Password is required!", false);
            }
            return new ValidationResult("", true);
        }

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        boolean valid = matcher.matches();

        return valid ? new ValidationResult("", true) : new ValidationResult("Password is invalid!", false);
    }

    public ValidationResult validatePhoneNumber(String phoneNumber, boolean required) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            if (required) {
                return new ValidationResult("Phone number is required!", false);
            }
            return new ValidationResult("", true);
        }

        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        boolean valid = matcher.matches();

        return valid ? new ValidationResult("", true) : new ValidationResult("Phone number is invalid!", false);
    }

    public ValidationResult validateLatitude(String latitude, boolean required) {
        if (latitude == null || latitude.isEmpty()) {
            if (required) {
                return new ValidationResult("Latitude is required!", false);
            }
            return new ValidationResult("", true);
        }

        Pattern pattern = Pattern.compile(LATITUDE_PATTERN);
        Matcher matcher = pattern.matcher(latitude);
        boolean valid = matcher.matches();

        return valid ? new ValidationResult("", true) : new ValidationResult("Latitude is invalid!", false);
    }

    public ValidationResult validateLongitude(String longitude, boolean required) {
        if (longitude == null || longitude.isEmpty()) {
            if (required) {
                return new ValidationResult("Longitude is required!", false);
            }
            return new ValidationResult("", true);
        }

        Pattern pattern = Pattern.compile(LONGITUDE_PATTERN);
        Matcher matcher = pattern.matcher(longitude);
        boolean valid = matcher.matches();

        return valid ? new ValidationResult("", true) : new ValidationResult("Longitude is invalid!", false);
    }

    public ValidationResult validateSize(int size, boolean required) {
        if (size == 0) {
            if (required) {
                return new ValidationResult("Size is required!", false);
            }
            return new ValidationResult("", true);
        }

        boolean valid = size > 0 && size <= 200000;

        return valid ? new ValidationResult("", true) : new ValidationResult("Size is invalid!", false);
    }
}
