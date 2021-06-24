package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

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
