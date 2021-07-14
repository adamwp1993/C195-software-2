package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentDB;
import model.LogonSession;
import utility.SqlDatabase;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class appointmentViewController implements Initializable {
    @FXML
    Button newAppointmentButton;
    @FXML
    Button editAppointmentButton;
    @FXML
    Button deleteButton;
    @FXML
    Button customersViewButton;
    @FXML
    Button reportsButton;
    @FXML
    Button logOutButton;
    @FXML
    Button nextTimeButton;
    @FXML
    Button previousTimeButton;
    @FXML
    RadioButton monthFilterButton;
    @FXML
    RadioButton weekFilterButton;
    @FXML
    RadioButton noFilterButton;
    @FXML
    TableView<Appointment> appointmentTable;
    @FXML
    TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    TableColumn<Appointment, String> titleColumn;
    @FXML
    TableColumn<Appointment, String> descriptionColumn;
    @FXML
    TableColumn<Appointment, String> locationColumn;
    @FXML
    TableColumn<Appointment, String> contactColumn;
    @FXML
    TableColumn<Appointment, String> typeColumn;
    @FXML
    TableColumn<Appointment, ZonedDateTime> startDateTimeColumn;
    @FXML
    TableColumn<Appointment, ZonedDateTime> endDateTimeColumn;
    @FXML
    TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    ToggleGroup filterToggle;
    @FXML
    Label selectedTimeLabel;

    // Variable that holds the range we are filtering on since we move back and forth.
    ZonedDateTime startRangeMarker;
    ZonedDateTime endRangeMarker;


    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    public void initToggleGroup() {

        // TODO - fix toggle groups
        filterToggle = new ToggleGroup();

        noFilterButton.setToggleGroup(filterToggle);
        weekFilterButton.setToggleGroup(filterToggle);
        monthFilterButton.setToggleGroup(filterToggle);



    }


    public void pressNoFilterButton(ActionEvent event) {
        // only one selection at a time! 
        monthFilterButton.setSelected(false);
        weekFilterButton.setSelected(false);
        
        ObservableList<Appointment> allAppts;
        try {
            allAppts = AppointmentDB.getAllAppointments();
        }
        catch (SQLException error){
            // Sometimes the connection to DB breaks here.(not sure why) If it does, re-connnect and try again.
            error.printStackTrace();
            SqlDatabase.connectDB();
            try {
                allAppts = AppointmentDB.getAllAppointments();
            } catch (SQLException anotherError) {
                anotherError.printStackTrace();
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.WARNING, "DB connection failed. please restart", clickOkay);
                invalidInput.showAndWait();
                return;
            }

        }
        populateAppointments(allAppts);
        selectedTimeLabel.setText("All Appointments");
        startRangeMarker = null;


    }

    public void pressWeekFilterButton(ActionEvent event) throws SQLException {
        // Only one selection at a time! 
        monthFilterButton.setSelected(false);
        noFilterButton.setSelected(false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        startRangeMarker = ZonedDateTime.now(LogonSession.getUserTimeZone());
        endRangeMarker = startRangeMarker.plusWeeks(1);

        // Convert to UTC
        ZonedDateTime startRange = startRangeMarker.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endRange = endRangeMarker.withZoneSameInstant(ZoneOffset.UTC);

        // query DB for time frame
        filteredAppts = AppointmentDB.getDateFilteredAppointments(startRange, endRange);
        // populate
        populateAppointments(filteredAppts);
        // update label
        selectedTimeLabel.setText(startRangeMarker.format(formatter) + " - " + endRangeMarker.format(formatter) + " " +
                LogonSession.getUserTimeZone());
        // update filterRangeMarker to next week.




    }

    public void pressMonthFilterButton(ActionEvent event) throws SQLException {
        weekFilterButton.setSelected(false);
        noFilterButton.setSelected(false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        startRangeMarker = ZonedDateTime.now(LogonSession.getUserTimeZone());
        endRangeMarker = startRangeMarker.plusMonths(1);

        // Convert to UTC
        ZonedDateTime startRange = startRangeMarker.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime endRange = endRangeMarker.withZoneSameInstant(ZoneOffset.UTC);

        // query DB for time frame
        filteredAppts = AppointmentDB.getDateFilteredAppointments(startRange, endRange);
        // populate
        populateAppointments(filteredAppts);
        // update label
        selectedTimeLabel.setText(startRangeMarker.format(formatter) + " - " + endRangeMarker.format(formatter) + " " +
                LogonSession.getUserTimeZone());



    }

    public void pressNextButton(ActionEvent event) throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();

        if (filterToggle.getSelectedToggle() == weekFilterButton) {

            ZonedDateTime startRange = startRangeMarker.plusWeeks(1);
            ZonedDateTime endRange = endRangeMarker.plusWeeks(1);

            // update markers
            startRangeMarker = startRange;
            endRangeMarker = endRange;

            // convert to UTC for the DB
            startRange = startRange.withZoneSameInstant(ZoneOffset.UTC);
            endRange = endRange.withZoneSameInstant(ZoneOffset.UTC);

            filteredAppts = AppointmentDB.getDateFilteredAppointments(startRange, endRange);

            populateAppointments(filteredAppts);

            // update label
            selectedTimeLabel.setText(startRangeMarker.format(formatter) + " - " + endRangeMarker.format(formatter) + " " +
                    LogonSession.getUserTimeZone());

        }
        if (filterToggle.getSelectedToggle() == monthFilterButton) {

            ZonedDateTime startRange = startRangeMarker.plusMonths(1);
            ZonedDateTime endRange = endRangeMarker.plusMonths(1);

            // update markers
            startRangeMarker = startRange;
            endRangeMarker = endRange;

            // convert to UTC for the DB
            startRange = startRange.withZoneSameInstant(ZoneOffset.UTC);
            endRange = endRange.withZoneSameInstant(ZoneOffset.UTC);

            filteredAppts = AppointmentDB.getDateFilteredAppointments(startRange, endRange);

            populateAppointments(filteredAppts);

            // update label
            selectedTimeLabel.setText(startRangeMarker.format(formatter) + " - " + endRangeMarker.format(formatter) + " " +
                    LogonSession.getUserTimeZone());

        }

    }

    public void pressBackButton(ActionEvent event) throws SQLException {

        // TODO - fix this - the values are backward so its messing up the SQL query. they need to be flipped

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();

        if (filterToggle.getSelectedToggle() == weekFilterButton) {

            ZonedDateTime startRange = startRangeMarker.minusWeeks(1);
            ZonedDateTime endRange = endRangeMarker.minusWeeks(1);

            // update markers
            startRangeMarker = startRange;
            endRangeMarker = endRange;

            // convert to UTC for the DB
            startRange = startRange.withZoneSameInstant(ZoneOffset.UTC);
            endRange = endRange.withZoneSameInstant(ZoneOffset.UTC);

            filteredAppts = AppointmentDB.getDateFilteredAppointments(startRange, endRange);

            populateAppointments(filteredAppts);

            // update label
            selectedTimeLabel.setText(startRangeMarker.format(formatter) + " - " + endRangeMarker.format(formatter) + " " +
                    LogonSession.getUserTimeZone());

        }
        if (filterToggle.getSelectedToggle() == monthFilterButton) {

            ZonedDateTime startRange = startRangeMarker.minusMonths(1);
            ZonedDateTime endRange = endRangeMarker.minusMonths(1);

            // update markers
            startRangeMarker = startRange;
            endRangeMarker = endRange;

            // convert to UTC for the DB
            startRange = startRange.withZoneSameInstant(ZoneOffset.UTC);
            endRange = endRange.withZoneSameInstant(ZoneOffset.UTC);

            filteredAppts = AppointmentDB.getDateFilteredAppointments(startRange, endRange);

            populateAppointments(filteredAppts);

            // update label
            selectedTimeLabel.setText(startRangeMarker.format(formatter) + " - " + endRangeMarker.format(formatter) + " " +
                    LogonSession.getUserTimeZone());
        }

    }

    public void pressDeleteButton(ActionEvent event) throws IOException, SQLException {

        Appointment selectedAppt = appointmentTable.getSelectionModel().getSelectedItem();

        // check that user selected an appointment in the table
        if (selectedAppt == null) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.WARNING, "No selected Appointment", clickOkay);
            invalidInput.showAndWait();
            return;
        }
        else {
            // show alert and ensure user wants to delete
            ButtonType clickYes = ButtonType.YES;
            ButtonType clickNo = ButtonType.NO;
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete Appointment: "
                    + selectedAppt.getAppointmentID() + " ?", clickYes, clickNo);
            Optional<ButtonType> result = deleteAlert.showAndWait();

            // if user confirms, delete appointment
            if (result.get() == ButtonType.YES) {
                Boolean success = AppointmentDB.deleteAppointment(selectedAppt.getAppointmentID());

                // if successful notify, if not show user error.
                if (success) {
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert deletedAppt = new Alert(Alert.AlertType.CONFIRMATION, "Appointment deleted", clickOkay);
                    deletedAppt.showAndWait();

                }
                else {
                    //TODO - log error if it occurs
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert deleteAppt = new Alert(Alert.AlertType.WARNING, "Failed to delete Appointment", clickOkay);
                    deleteAppt.showAndWait();

                }

                // Re-load appointments on screen
                try {
                    populateAppointments(AppointmentDB.getAllAppointments());
                }
                catch (SQLException error){
                    //TODO - log error
                    error.printStackTrace();
                }

            }
            else {
                return;
            }
        }
    }



    public void pressNewButton(ActionEvent event) throws IOException {
        switchScreen(event, "/view_controller/addAppointmentPage.fxml");

    }

    public void pressLogoutButton(ActionEvent event) throws IOException {
        ButtonType clickYes = ButtonType.YES;
        ButtonType clickNo = ButtonType.NO;
        Alert logOff = new Alert(Alert.AlertType.WARNING, "Are you sure you want to Log Off?", clickYes, clickNo);
        Optional<ButtonType> result = logOff.showAndWait();

        if (result.get() == ButtonType.YES) {
            LogonSession.logOff();
            switchScreen(event, "/view_controller/loginPage.fxml");
        }
        else {
            return;
        }


    }


    public void pressEditButton(ActionEvent event) throws IOException, SQLException {

        Appointment selectedAppt = appointmentTable.getSelectionModel().getSelectedItem();
        // throw error if no selection
        if (selectedAppt == null) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.WARNING, "No selected Appointment", clickOkay);
            invalidInput.showAndWait();
            return;

        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view_controller/editAppointmentPage.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        // get the controller and load our selected appointment into it
        editAppointmentPageController controller = loader.getController();
        controller.initData(selectedAppt);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);

    }

    public void populateAppointments(ObservableList<Appointment> inputList) {
        // Takes an observable list of appointments and populates them on screen.

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactName"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, ZonedDateTime>("startDateTime"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, ZonedDateTime>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
        appointmentTable.setItems(inputList);

    }

    public void checkCanceled(ObservableList<Appointment> inputList) {

        inputList.forEach((appt) -> {
            if (appt.getType().equalsIgnoreCase("canceled")) {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.WARNING, "Appointment " + appt.getAppointmentID() +
                        " is canceled.", clickOkay);
                invalidInput.showAndWait();
            }
        });

    }




    @Override
    public void initialize(URL location, ResourceBundle resources)   {

        // TODO - start here
        // TODO - finish week filtering, month filtering, back and forward buttons

        noFilterButton.setSelected(true);
        initToggleGroup();

        // populate table view, handle DB connection breakage by retry.
        ObservableList<Appointment> allAppts = null;
        try {
            allAppts = AppointmentDB.getAllAppointments();
        }
        catch (SQLException error){
            // Sometimes the connection to DB breaks here.(not sure why) If it does, re-connnect and try again.
            error.printStackTrace();
            SqlDatabase.connectDB();
            try {
                allAppts = AppointmentDB.getAllAppointments();
            } catch (SQLException anotherError) {
                anotherError.printStackTrace();
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.WARNING, "DB connection failed. please restart", clickOkay);
                invalidInput.showAndWait();
                return;
            }

        }
        populateAppointments(allAppts);
        checkCanceled(allAppts);


    }

}
