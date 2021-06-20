package view_controller;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import model.LogonSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
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
        boolean logon = LogonSession.attemptLogon(userTextBox.getText(), passwordTextBox.getText());
        if (logon) {
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
        // TODO - create label that displays users login location (zone ID) and timezone
        //Test your Localization
        //Locale.setDefault(new Locale("FR"));

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
