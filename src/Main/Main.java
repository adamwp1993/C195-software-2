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

public class Main extends Application{

    @Override
    public void start(Stage loginStage) throws Exception {
        loginStage.setTitle("Software II - Advanced Java Concepts - C195");
        Parent root = FXMLLoader.load(getClass().getResource("/view_controller/loginPage.fxml"));
        loginStage.setScene(new Scene(root));
        loginStage.show();
    }


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
