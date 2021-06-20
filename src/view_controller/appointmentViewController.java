package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.AppointmentDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
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


    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }



    public void pressNewButton(ActionEvent event) throws IOException {
        switchScreen(event, "/view_controller/addAppointmentPage.fxml");

    }

    public void populateAllAppointments(ObservableList<Appointment> inputList) {
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



    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        // TODO - Handle new appointment, edit, delete
        // TODO - Throw notification if there is an appointment within 15 mins of login time
        // TODO - Handle dateTime/ Timezone
        // TODO - Month/ week filtering
        //

        try {
            populateAllAppointments(AppointmentDB.getAllAppointments());
        }
        catch (SQLException error){
            error.printStackTrace();
        }


    }

}
