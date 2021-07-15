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

    public static ObservableList<String> getAllCountries() throws SQLException {

        ObservableList<String> allCountries = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT DISTINCT Country FROM countries");
        ResultSet results = sqlCommand.executeQuery();

        while (results.next()) {
            allCountries.add(results.getString("Country"));
        }
        sqlCommand.close();
        return allCountries;
    }

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        // Prepare SQL and execute query
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement(
                "SELECT cx.Customer_ID, cx.Customer_Name, cx.Address, cx.Postal_Code, cx.Phone, cx.Division_ID, " +
                        "f.Division, f.COUNTRY_ID, co.Country FROM customers as cx INNER JOIN first_level_divisions " +
                        "as f on cx.Division_ID = f.Division_ID INNER JOIN countries as co ON f.COUNTRY_ID = co.Country_ID");
        ResultSet results = sqlCommand.executeQuery();


        while( results.next() ) {
            // get data from the returned rows
            Integer custID = results.getInt("Customer_ID");
            String custName = results.getString("Customer_Name");
            String custAddress = results.getString("Address");
            String custPostalCode = results.getString("Postal_Code");
            String custPhoneNum = results.getString("Phone");
            String custDivision = results.getString("Division");
            String custCountry = results.getString("Country");

            // populate into an customer object
            Customer newCust = new Customer(custID, custName, custAddress, custPostalCode, custPhoneNum, custDivision, custCountry);


            // Add to the observablelist
            allCustomers.add(newCust);

        }
        sqlCommand.close();
        return allCustomers;

    }
}
