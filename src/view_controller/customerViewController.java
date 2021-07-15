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
import model.CustomerDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
        countryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));

        customerTable.setItems(inputList);

    }


    public void pressAddButton(ActionEvent event) {

    }

    public void pressEditButton(ActionEvent event) {

    }


    public void pressDeleteButton(ActionEvent event) {

    }

    public void pressBackButton(ActionEvent event) throws IOException {

        switchScreen(event, "/view_controller/appointmentView.fxml");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            populateCustomers(CustomerDB.getAllCustomers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
