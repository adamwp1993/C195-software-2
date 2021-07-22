package view_controller;


import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import model.Appointment;
import model.LogonSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import utility.Logger;

import java.net.URL;



//TODO - log all logon attempts. create logger utility


public class loginPageController implements Initializable {
    @ FXML
    private TextField passwordTextBox;
    @ FXML
    private TextField userTextBox;
    @ FXML
    private Label titleLabel;
    @ FXML
    private Label userNameLabel;
    @ FXML
    private Label passwordLabel;
    @ FXML
    private Button loginButton;
    @ FXML
    private Button clearButton;
    @ FXML
    private Button exitButton;
    @ FXML
    private Label zoneLabel;

    public void switchScreen(ActionEvent event, String switchPath) throws IOException {


        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    public void pressLogonButton(ActionEvent event) throws IOException, SQLException {
        String userName = userTextBox.getText();
        String password = passwordTextBox.getText();

        // Attempt Login
        boolean logon = LogonSession.attemptLogon(userName, password);

        // Log Login attempt
        Logger.auditLogin(userName, logon);

        if (logon) {


            // Get appointments in 15 minutes and display notification if there is.
            ObservableList<Appointment> upcomingAppts = model.AppointmentDB.getAppointmentsIn15Mins();

            if (!upcomingAppts.isEmpty()) {
                for (Appointment upcoming : upcomingAppts ) {

                    String message = "Upcoming appointmentID: " + upcoming.getAppointmentID() + " Start: " +
                            upcoming.getStartDateTime().toString();
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert invalidInput = new Alert(Alert.AlertType.WARNING, message, clickOkay);
                    invalidInput.showAndWait();

                }

            }
            // If no appointments in 15 minutes, display notification that no upcoming appointments.
            else {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert invalidInput = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming Appointments", clickOkay);
                invalidInput.showAndWait();
            }

            switchScreen(event, "/view_controller/appointmentView.fxml");

        }
        else {
            Locale userLocale = Locale.getDefault();
            ResourceBundle resources = ResourceBundle.getBundle("language_property/loginPage");
            ButtonType clickOkay = new ButtonType(resources.getString("okayButton"), ButtonBar.ButtonData.OK_DONE);
            Alert failedLogon = new Alert(Alert.AlertType.WARNING, resources.getString("logonFailedButton"),
                    clickOkay);
            failedLogon.showAndWait();
        }

    }


    public void pressClearButton(ActionEvent event) throws IOException {
        userTextBox.clear();
        passwordTextBox.clear();

    }

    public void pressExitButton(ActionEvent event) throws IOException {
        LogonSession.logOff();
        System.exit(0);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Locale userLocale = Locale.getDefault();
        zoneLabel.setText(ZoneId.systemDefault().toString());
        resources = ResourceBundle.getBundle("language_property/loginPage");
        titleLabel.setText(resources.getString("titleLabel"));
        userNameLabel.setText(resources.getString("userNameLabel"));
        passwordLabel.setText(resources.getString("passwordLabel"));
        loginButton.setText(resources.getString("loginButton"));
        clearButton.setText(resources.getString("clearButton"));
        exitButton.setText(resources.getString("exitButton"));


    }

}
