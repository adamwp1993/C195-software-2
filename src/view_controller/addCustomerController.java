package view_controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.CustomerDB;


import java.io.IOException;
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


    public void switchScreen(ActionEvent event, String switchPath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(switchPath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void pressSaveButton(ActionEvent event) {
        // INPUT VALIDATION - check for nulls
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();
        String name = customerNameTextBox.getText();
        String address = addressTextBox.getText();
        String postalCode = postalCodeTextBox.getText();
        String phone = phoneNumberTextBox.getText();

        if (country.isBlank() || division.isBlank() || name.isBlank() || address.isBlank() || postalCode.isBlank() ||
        phone.isBlank()) {

            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert emptyVal = new Alert(Alert.AlertType.WARNING, "Please ensure all fields are completed.",
                    clickOkay);
            emptyVal.showAndWait();
            return;

        }
        else {
            // TODO - insert
        }

    }

    public void pressClearButton(ActionEvent event) {
        countryComboBox.getItems().clear();
        divisionComboBox.getItems().clear();
        customerNameTextBox.clear();
        addressTextBox.clear();
        postalCodeTextBox.clear();
        phoneNumberTextBox.clear();

    }

    public void pressBackButton(ActionEvent event) throws IOException {
        switchScreen(event, "/view_controller/customerView.fxml");

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

        // Listener for combo box change
        countryComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                divisionComboBox.getItems().clear();
                divisionComboBox.setDisable(true);

            }
            else {
                divisionComboBox.setDisable(false);
                try {
                    divisionComboBox.setItems(CustomerDB.getFilteredDivisions(countryComboBox.getValue()));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });

    }
}
