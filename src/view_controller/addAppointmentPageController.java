package view_controller;

//import com.mysql.cj.result.LocalDateTimeValueFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class addAppointmentPageController implements Initializable {

    @FXML
    TextField titleTextBox;
    @FXML
    TextArea descriptionTextBox;
    @FXML
    TextField locationTextBox;
    @ FXML
    ComboBox<Integer> contactComboBox;
    @ FXML
    TextField typeTextBox;
    @ FXML
    ComboBox<Integer> customerComboBox;
    @ FXML
    ComboBox<Integer> userComboBox;
    @ FXML
    Label timeZoneLabel;
    @ FXML
    DatePicker apptDatePicker;
    @ FXML
    TextField startTimeTextBox;
    @ FXML
    TextField endTimeTextBox;
    @ FXML
    Button saveButton;
    @ FXML
    Button clearButton;
    @ FXML
    Button backButton;

    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void pressSaveButton(ActionEvent event) throws SQLException {

        // Input validation

        // no overlapping appointments for customers
        // exception handling
        // insert into DB
        // notify
        // Clear

        Boolean validStartDateTime = null;
        Boolean validEndDateTime = null;
        Boolean validOverlap = null;
        Boolean validBusinessHours = null;


        String title = titleTextBox.getText();
        String description = descriptionTextBox.getText();
        String location = locationTextBox.getText();
        Integer contact = contactComboBox.getValue();
        String type = typeTextBox.getText();
        Integer customerID = customerComboBox.getValue();
        Integer userID = userComboBox.getValue();
        LocalDate apptDate = apptDatePicker.getValue();
        LocalDateTime endDateTime = null;
        LocalDateTime startDateTime = null;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // INPUT VALIDATION - check input time
        try {
            startDateTime = LocalDateTime.of(apptDatePicker.getValue(),
                    LocalTime.parse(startTimeTextBox.getText(), formatter));
            validStartDateTime = true;
        }
        catch(DateTimeParseException error) {
            validEndDateTime = false;
            System.out.println("startDateTime parsing error");
        }

        try {
            endDateTime = LocalDateTime.of(apptDatePicker.getValue(),
                    LocalTime.parse(endTimeTextBox.getText(), formatter));
            validEndDateTime = true;
        }
        catch(DateTimeParseException error) {
            validEndDateTime = false;
            System.out.println("endDateTime parsing error");
        }

        // INPUT VALIDATION - check business hours and overlap
        validBusinessHours = validateBusinessHours(startDateTime, endDateTime, apptDate);
        validOverlap = validateCustomerOverlap(customerID, startDateTime, endDateTime, apptDate);



        // Check all Booleans and if any are false, present user with corresponding errors of what they screwed up.
        // if any of these are wrong, that means what was inserted cannot be inserted to DB.
        String errorMessage = null;
        if (!validStartDateTime) {
            errorMessage += "Invalid Start Date or time. Please ensure proper format HH:MM, including leading 0's.\n";
        }
        if (!validEndDateTime) {
            errorMessage += "Invalid End Date or time. Please ensure proper format HH:MM, including leading 0's.\n";
        }
        if (!validBusinessHours) {
            errorMessage += "Invalid Business Hours.(8am to 10pm EST, Not including weekends)\n";
        }
        if (!validOverlap) {
            errorMessage += "Invalid Customer Overlap. Cannot double book customers.\n";
        }

        // if input is valid, we insert the appt into the DB and display success notification.



        /*
        ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        Alert invalidInput = new Alert(Alert.AlertType.WARNING, "Invalid Start Date or Time. Ensure time is " +
                "input in HH:MM format.", clickOkay);
        invalidInput.showAndWait();
        */


    }

    public void pressClearButton() {
        titleTextBox.clear();
        descriptionTextBox.clear();
        locationTextBox.clear();
        typeTextBox.clear();
        startTimeTextBox.clear();
        endTimeTextBox.clear();
        contactComboBox.getSelectionModel().clearSelection();
        customerComboBox.getSelectionModel().clearSelection();
        userComboBox.getSelectionModel().clearSelection();
        apptDatePicker.getEditor().clear();


    }

    public void pressBackButton(ActionEvent event) throws IOException {
        switchScreen(event, "/view_controller/appointmentView.fxml");

    }



    public Boolean validateBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate apptDate) {
        // (8am to 10pm EST, Not including weekends)
        // Turn into zonedDateTimeObject, so we can evaluate whatever time was entered in user time zone against EST

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, LogonSession.getUserTimeZone());
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, LogonSession.getUserTimeZone());

        ZonedDateTime startBusinessHours = ZonedDateTime.of(apptDate, LocalTime.of(8,0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(apptDate, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        // If startTime is before or after business hours
        // If end time is before or after business hours
        // if startTime is after endTime - these should cover all possible times entered and validate input.
        if (startZonedDateTime.isBefore(startBusinessHours) | startZonedDateTime.isAfter(endBusinessHours) |
                endZonedDateTime.isBefore(startBusinessHours) | endZonedDateTime.isAfter(endBusinessHours) |
                startZonedDateTime.isAfter(endZonedDateTime)) {
            return false;

        }
        else {
            return true;
        }

    }


    public Boolean validateCustomerOverlap(Integer inputCustomerID, LocalDateTime startDateTime,
                                           LocalDateTime endDateTime, LocalDate apptDate) throws SQLException {
        //TODO - test this

        // Get list of appointments that might have conflicts
        ObservableList<Appointment> possibleConflicts = AppointmentDB.getCustomerFilteredAppointments(apptDate,
                inputCustomerID);
        // for each possible conflict, evaluate:
        // if conflictApptStart is before newApptstart and conflictApptEnd is after newApptStart(starts before ends after)
        // if conflictApptStart is before newApptEnd & conflictApptStart after newApptStart (startime anywhere in appt)
        // if endtime is before end and endtime is after start (endtime falls anywhere in appt)
        if (possibleConflicts.isEmpty()) {
            System.out.println("No possible conflicts on same day");
            return true;
        }
        else {
            for (Appointment conflictAppt : possibleConflicts) {
                System.out.println("Appts in same day, starting evaluation loop");
                LocalDateTime conflictStart = conflictAppt.getStartDateTime().toLocalDateTime();
                LocalDateTime conflictEnd = conflictAppt.getEndDateTime().toLocalDateTime();

                // Conflict starts before and Conflict ends any time after new appt ends - overlap
                if( conflictStart.isBefore(startDateTime) & conflictEnd.isAfter(endDateTime)) {
                    System.out.println("Conflict case 1");
                    return false;
                }
                // ConflictAppt start time falls anywhere in the new appt
                if (conflictStart.isBefore(endDateTime) & conflictStart.isAfter(startDateTime)) {
                    System.out.println("Conflict case 2");
                    return false;
                }
                // ConflictAppt end time falls anywhere in the new appt
                if (conflictEnd.isBefore(endDateTime) & conflictEnd.isAfter(startDateTime)) {
                    System.out.println("Conflict case 3");
                    return false;
                }
                else {
                    System.out.println("No conflicts");
                    return true;
                }

            }
        }
    return true;

    }

    public void initialize(URL location, ResourceBundle resources) {
        // TODO - populate comboBoxes
        // TODO - exception handling if values are left empty
        // TODO - enter appt into DB
        // TODO - throw errors
        timeZoneLabel.setText("Your Time Zone:" + LogonSession.getUserTimeZone());

        // Disable users from picking dates in the past or weekend.
        apptDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate apptDatePicker, boolean empty) {
                super.updateItem(apptDatePicker, empty);
                setDisable(
                        empty || apptDatePicker.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        apptDatePicker.getDayOfWeek() == DayOfWeek.SUNDAY || apptDatePicker.isBefore(LocalDate.now()));
            }
        });

        // Populate ComboBoxes
        try {
            customerComboBox.setItems(CustomerDB.getAllCustomerID());
            userComboBox.setItems(UserDB.getAllUserID());
        } catch (SQLException throwables) {
            //TODO - log possible errors
            throwables.printStackTrace();
        }


    }


}
