package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AppointmentDB;
import model.ContactDB;
import model.CustomerDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportsPageController implements Initializable {

    @FXML
    Button ApptByReportButton;
    @FXML
    Button contactScheduleReportButton;
    @FXML
    Button minsPerContactButton;
    @FXML
    TextArea reportTextField;
    @FXML
    Button backButton;

    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void pressBackButton(ActionEvent event) throws IOException {
        switchScreen(event, "/view_controller/appointmentView.fxml");

    }

    public void pressApptByReportButton(ActionEvent event) throws SQLException {

        ObservableList<String> reportStrings = AppointmentDB.reportTotalsByTypeAndMonth();

        for (String str : reportStrings) {
            reportTextField.appendText(str);
        }

    }

    public void pressContactSchedule(ActionEvent event) throws SQLException {
        ObservableList<String> contacts = ContactDB.getAllContactName();
        for (String contact : contacts) {
            String contactID = ContactDB.findContactID(contact).toString();
            reportTextField.appendText("Contact Name: " + contact + " ID: " + contactID);


        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}
