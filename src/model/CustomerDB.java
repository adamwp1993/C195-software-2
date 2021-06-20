package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.SqlDatabase;

import java.sql.*;

public class CustomerDB {

    public static ObservableList<Integer> getAllCustomerID() throws SQLException {
        ObservableList<Integer> allCustomerID = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT DISTINCT Customer_ID" +
                " FROM customers;");
        ResultSet results = sqlCommand.executeQuery();

        while ( results.next() ) {
            allCustomerID.add(results.getInt("Customer_ID"));
        }
        sqlCommand.close();
        return allCustomerID;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {

        // Prepare SQL and execute query
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT * FROM customers;");
        ResultSet results = sqlCommand.executeQuery();


        while( results.next() ) {
            // get data from the returned rows
            Integer custID = results.getInt("Customer_ID");
            String custName = results.getString("Customer_Name");
            String custAddress = results.getString("Address");
            String custPostalCode = results.getString("Postal_Code");
            String custPhoneNum = results.getString("Phone");
            Timestamp custCreateDate = results.getTimestamp("Create_Date");
            String custCreatedBy = results.getString("Created_By");
            Timestamp custLastUpdate = results.getTimestamp("Last_Update");
            String custLastUpdateBy = results.getString("Last_Updated_By");
            Integer custDivisionID = results.getInt("Division_ID");

            // populate into an customer object
            Customer newCust = new Customer(custID, custName, custAddress, custPostalCode, custPhoneNum, custCreateDate,
                    custCreatedBy, custLastUpdate, custLastUpdateBy, custDivisionID);


            // Add to the observablelist
            allCustomers.add(newCust);

        }
        sqlCommand.close();
        return allCustomers;

    }
}
