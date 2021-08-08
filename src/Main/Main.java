package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import model.AppointmentDB;
import model.Customer;
import utility.SqlDatabase;

import java.io.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * The Main class of the application.
 *
 * @author Adam Petersen
 */
public class Main extends Application{

    /**
     *
     * Loads the Stage to display on screen.
     *
     * @param loginStage the path to the screen we will be loading.
      */

    @Override
    public void start(Stage loginStage) throws Exception {
        loginStage.setTitle("Software II - Advanced Java Concepts - C195");
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/loginPage.fxml"));
        loginStage.setScene(new Scene(root));
        loginStage.show();
    }

    /**
     *
     * Starting point of the application.
     * Loads the properties which stores the Database Credentials and opens a connection to the DB.
     *
     * @param args Command line arguments passed to the application.
     *
     * @throws SQLException If we fail to connect to the Database.
     */
    public static void main(String[] args) throws SQLException {
        // Retrieve Database credentials from properties file.
        Properties prop = new Properties();
        try {
            prop.load(SqlDatabase.class.getClassLoader().getResourceAsStream("utility/SqlDatabase.properties"));
        } catch (IOException error) {
            error.printStackTrace();
        }
        // Set DB credentials in the static SqlDatabase object
        SqlDatabase.setDbPassword(prop.getProperty("dbPassword"));
        SqlDatabase.setDbName(prop.getProperty("dbName"));
        SqlDatabase.setDbUserName(prop.getProperty("dbUserName"));
        SqlDatabase.setJdbcUrl(prop.getProperty("jdbcUrl"));


        SqlDatabase.connectDB();
        launch(args);


    }
}
