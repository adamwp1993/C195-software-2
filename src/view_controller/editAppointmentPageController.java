package view_controller;

import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class editAppointmentPageController implements Initializable {

    @FXML
    TextField appointmentIDTextBox;
    @FXML
    TextField titleTextBox;
    @FXML
    TextArea descriptionTextBox;
    @FXML
    TextField locationTextBox;
    @FXML
    ComboBox<String> contactComboBox;
    @FXML
    TextField typeTextBox;
    @FXML
    ComboBox<Integer> customerComboBox;
    @FXML
    ComboBox<Integer> userComboBox;
    @FXML
    DatePicker apptDatePicker;
    @FXML
    TextField startTimeTextBox;
    @FXML
    TextField endTimeTextBox;
    @FXML
    Button saveButton;
    @FXML
    Button clearButton;
    @FXML
    Button backButton;
    @FXML
    Label timeZoneLabel;



    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public void initData(Appointment selectedAppt) throws SQLException {


        // get the values to populate into the Date picker
        LocalDate apptDate = selectedAppt.getStartDateTime().toLocalDateTime().toLocalDate();
        ZonedDateTime startDateTimeUTC = selectedAppt.getStartDateTime().toInstant().atZone(ZoneOffset.UTC);
        ZonedDateTime endDateTimeUTC = selectedAppt.getEndDateTime().toInstant().atZone(ZoneOffset.UTC);

        ZonedDateTime localStartDateTime = startDateTimeUTC.withZoneSameInstant(LogonSession.getUserTimeZone());
        ZonedDateTime localEndDateTime = endDateTimeUTC.withZoneSameInstant(LogonSession.getUserTimeZone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String localStartString = localStartDateTime.format(formatter);
        String localEndString = localEndDateTime.format(formatter);





        appointmentIDTextBox.setText(selectedAppt.getAppointmentID().toString());
        titleTextBox.setText(selectedAppt.getTitle());
        descriptionTextBox.setText(selectedAppt.getDescription());
        locationTextBox.setText(selectedAppt.getLocation());
        contactComboBox.setItems(ContactDB.getAllContactName());
        contactComboBox.getSelectionModel().select(selectedAppt.getContactName());
        typeTextBox.setText(selectedAppt.getType());
        customerComboBox.setItems(CustomerDB.getAllCustomerID());
        customerComboBox.getSelectionModel().select(selectedAppt.getCustomerID());
        userComboBox.setItems(UserDB.getAllUserID());
        userComboBox.getSelectionModel().select(selectedAppt.getUserID());
        startTimeTextBox.setText(localStartString);
        endTimeTextBox.setText(localEndString);

        // Set date/ times - handle time zones

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timeZoneLabel.setText(LogonSession.getUserTimeZone().toString());

    }
}
