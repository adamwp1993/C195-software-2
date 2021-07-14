package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class customerViewController implements Initializable {
    @FXML
    Button addButton;
    @FXML
    Button editButton;
    @FXML
    Button deleteButton;
    @FXML
    Button backButton;
    @FXML
    TableView<Customer> customerTable;
    @FXML
    TableColumn<Customer, Integer> customerIDColumn;
    @FXML
    TableColumn<Customer, String> customerNameColumn;
    @FXML
    TableColumn<Customer, String> countryColumn;
    @FXML
    TableColumn<Customer, String> divisionColumn;
    @FXML
    TableColumn<Customer, String> addressColumn;
    @FXML
    TableColumn<Customer, String> postalCodeColumn;
    @FXML
    TableColumn<Customer, String> phoneNumberColumn;

    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void populateCustomers(ObservableList<Customer> inputList) {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));

    }

    public void pressBackButton(ActionEvent event) throws IOException {

        switchScreen(event, "/view_controller/appointmentView.fxml");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
