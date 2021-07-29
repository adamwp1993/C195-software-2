package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {


    public static ObservableList<String> getContactAppts(String contactID) throws SQLException {
        ObservableList<String> apptStr = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement(
                "SELECT * FROM appointments WHERE Contact_ID = ?");

        sqlCommand.setString(1, contactID);

        ResultSet results = sqlCommand.executeQuery();

        while ( results.next()) {
            String apptID = results.getString("Appointment_ID");
            String title = results.getString("Title");
            String type = results.getString("Type");
            String start = results.getString("Start");
            String end = results.getString("End");
            String customerID = results.getString("Customer_ID");

            String newLine = "  AppointmentID: " + apptID + "\n";
            newLine += "        Title: " + title + "\n";
            newLine += "        Type: " + type + "\n";
            newLine += "        Start date/time: " + start + " UTC\n";
            newLine += "        End date/time: " + end + " UTC\n";
            newLine += "        CustomerID: " + customerID + "\n";

            apptStr.add(newLine);

        }

        sqlCommand.close();
        return apptStr;

    }



    public static ObservableList<String> getAllContactName() throws SQLException {
        ObservableList<String> allContactName = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT DISTINCT Contact_Name" +
                " FROM contacts;");
        ResultSet results = sqlCommand.executeQuery();

        while ( results.next() ) {
            allContactName.add(results.getString("Contact_Name"));
        }
        sqlCommand.close();
        return allContactName;
    }

    public static Integer findContactID(String contactName) throws SQLException {
        // take user selected name and find the FK so we can add to appointments table.
        Integer contactID = -1;
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT Contact_ID, Contact_Name " +
        "FROM contacts WHERE Contact_Name = ?");
        sqlCommand.setString(1, contactName);
        ResultSet results = sqlCommand.executeQuery();

        while (results.next()) {
            // should always return one value, since the Contact_name was retrieved from DB as well.
            contactID = results.getInt("Contact_ID");
        }
        sqlCommand.close();
        return contactID;


    }
}
