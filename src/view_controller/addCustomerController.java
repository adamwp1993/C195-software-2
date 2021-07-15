package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.CustomerDB;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addCustomerController implements Initializable {
    @FXML
    TextField customerIDTextBox;
    @FXML
    ComboBox<String> countryComboBox;
    @FXML
    ComboBox<String> divisionComboBox;
    @FXML
    TextField customerNameTextBox;
    @FXML
    TextField addressTextBox;
    @FXML
    TextField postalCodeTextBox;
    @FXML
    TextField phoneNumberTextBox;
    @FXML
    Button saveButton;
    @FXML
    Button clearButton;
    @FXML
    Button backButton;


    public void pressSaveButton(ActionEvent event) {

    }

    public void pressClearButton(ActionEvent event) {

    }

    public void pressBackButton(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO - research change listeners
        // TODO - start here
        // when the Country code is selected it must update division to only show related values.
        // if division is selected first, should automatically pick the corresponding country.
        // Listeners seems like the way to go. need to look into that 
        try {
            countryComboBox.setItems(CustomerDB.getAllCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
