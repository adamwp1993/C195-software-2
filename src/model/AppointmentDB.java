package model;

import com.mysql.cj.log.Log;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.SqlDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * AppointmentDB
 *
 * @author Adam Petersen
 */
public class AppointmentDB {

    /**
     * GetDateFilteredAppointments
     * Queries DB for all appointments between a start and end range
     *
     * @param startRange start of range ZonedDateTime
     * @param endRange end of range ZonedDateTime
     * @return Filtered list of appointments based on an input start and end date.
     * @throws SQLException
     */
    public static ObservableList<Appointment> getDateFilteredAppointments(ZonedDateTime startRange, ZonedDateTime endRange)
            throws SQLException {
        
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement(
                "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE" +
                        " Start between ? AND ?"
        );

        String startRangeString = startRange.format(formatter);
        String endRangeString = endRange.format(formatter);

        sqlCommand.setString(1, startRangeString);
        sqlCommand.setString(2, endRangeString);

        ResultSet results = sqlCommand.executeQuery();

        while( results.next() ) {
            // get data from the returned rows
            Integer appointmentID = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp startDateTime = results.getTimestamp("Start");
            Timestamp endDateTime = results.getTimestamp("End");
            Timestamp createdDate = results.getTimestamp("Create_Date");
            String createdBy = results.getString("Created_by");
            Timestamp lastUpdateDateTime = results.getTimestamp("Last_Update");
            String lastUpdatedBy = results.getString("Last_Updated_By");
            Integer customerID = results.getInt("Customer_ID");
            Integer userID = results.getInt("User_ID");
            Integer contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");

            // populate into an appt object
            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );
            filteredAppts.add(newAppt);
        }

        sqlCommand.close();
        return filteredAppts;

    }

    /**
     * reportTotalsByTypeAndMonth
     * Queries DB and calculates sum of appointments by type and by month for use in reports page.
     *
     * @return List of strings to populate in the report.
     * @throws SQLException
     */
    public static ObservableList<String> reportTotalsByTypeAndMonth() throws SQLException {
        ObservableList<String> reportStrings = FXCollections.observableArrayList();

        reportStrings.add("Total Number of Appointments by type and month:\n");

        // Prepare SQL
        PreparedStatement typeSqlCommand = SqlDatabase.dbCursor().prepareStatement(
                "SELECT Type, COUNT(Type) as \"Total\" FROM appointments GROUP BY Type");

        PreparedStatement monthSqlCommand = SqlDatabase.dbCursor().prepareStatement(
                "SELECT MONTHNAME(Start) as \"Month\", COUNT(MONTH(Start)) as \"Total\" from appointments GROUP BY Month");

        ResultSet typeResults = typeSqlCommand.executeQuery();
        ResultSet monthResults = monthSqlCommand.executeQuery();

        while (typeResults.next()) {
            String typeStr = "Type: " + typeResults.getString("Type") + " Count: " +
                    typeResults.getString("Total") + "\n";
            reportStrings.add(typeStr);

        }

        while (monthResults.next()) {
            String monthStr = "Month: " + monthResults.getString("Month") + " Count: " +
                    monthResults.getString("Total") + "\n";
            reportStrings.add(monthStr);

        }

        monthSqlCommand.close();
        typeSqlCommand.close();

        return reportStrings;

    }

    /**
     * getCustomerFilteredAppointments
     * queries DB for all appointments for a specific customer on a specific date. used to find conflicts
     *
     * @param apptDate date we are seeing for any possible appointments
     * @param inputCustomerID customer ID we are checking for
     * @return List of appointments for a specific customer on the input apptDate
     * @throws SQLException
     */
    public static ObservableList<Appointment> getCustomerFilteredAppointments(
            LocalDate apptDate, Integer inputCustomerID) throws SQLException {
        // Prepare SQL statement
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement(
                        "SELECT * FROM appointments as a LEFT OUTER JOIN contacts as c " +
                                "ON a.Contact_ID = c.Contact_ID WHERE datediff(a.Start, ?) = 0 AND Customer_ID = ?;"
        );

        sqlCommand.setInt(2, inputCustomerID);

        sqlCommand.setString(1, apptDate.toString());

        ResultSet results = sqlCommand.executeQuery();

        while( results.next() ) {
            // get data from the returned rows
            Integer appointmentID = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp startDateTime = results.getTimestamp("Start");
            Timestamp endDateTime = results.getTimestamp("End");
            Timestamp createdDate = results.getTimestamp("Create_Date");
            String createdBy = results.getString("Created_by");
            Timestamp lastUpdateDateTime = results.getTimestamp("Last_Update");
            String lastUpdatedBy = results.getString("Last_Updated_By");
            Integer customerID = results.getInt("Customer_ID");
            Integer userID = results.getInt("User_ID");
            Integer contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");

            // populate into an appt object
            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );
            filteredAppts.add(newAppt);
        }

        sqlCommand.close();
        return filteredAppts;

    }

    /**
     * updateAppointment
     * takes the specific Appointment ID and updates it in the DB
     *
     * @param inputApptID Appointment ID
     * @param inputTitle Title of the appointment
     * @param inputDescription Description of appointment
     * @param inputLocation Location of appointment
     * @param inputType Type of appointment
     * @param inputStart Start of appointment
     * @param inputEnd end of appointment
     * @param inputLastUpdateBy date/time of update
     * @param inputCustomerID Customer ID
     * @param inputUserID User ID
     * @param inputContactID Contact ID
     * @return Boolean indicating if operation was successful
     * @throws SQLException
     */
    public static Boolean updateAppointment(Integer inputApptID, String inputTitle, String inputDescription,
                                            String inputLocation, String inputType, ZonedDateTime inputStart,
                                            ZonedDateTime inputEnd, String inputLastUpdateBy, Integer inputCustomerID,
                                            Integer inputUserID, Integer inputContactID) throws SQLException {

        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("UPDATE appointments "
        + "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?,Last_Updated_By=?, " +
                "Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = ?");

        // Format inputStart and inputEnd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String inputStartString = inputStart.format(formatter).toString();
        String inputEndString = inputEnd.format(formatter).toString();

        sqlCommand.setString(1,inputTitle);
        sqlCommand.setString(2, inputDescription);
        sqlCommand.setString(3, inputLocation);
        sqlCommand.setString(4, inputType);
        sqlCommand.setString(5, inputStartString);
        sqlCommand.setString(6, inputEndString);
        sqlCommand.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        sqlCommand.setString(8, inputLastUpdateBy);
        sqlCommand.setInt(9, inputCustomerID);
        sqlCommand.setInt(10, inputUserID);
        sqlCommand.setInt(11, inputContactID);
        sqlCommand.setInt(12, inputApptID);

        try {
            sqlCommand.executeUpdate();
            sqlCommand.close();
            return true;
        }
        catch (SQLException e) {
            //TODO- log error
            e.printStackTrace();
            sqlCommand.close();
            return false;
        }

    }

    /**
     * addAppointment
     * Takes values and creates a new appointment in the database
     *
     * @param inputTitle title of appointment
     * @param inputDescription description of appointment
     * @param inputLocation location of appointment
     * @param inputType type of appointment
     * @param inputStart start of appointment
     * @param inputEnd end of appointment
     * @param inputCreatedBy who created appointment
     * @param inputLastUpdateBy who updated appointment last
     * @param inputCustomerID customer ID
     * @param inputUserID user ID
     * @param inputContactID contact ID
     * @return Boolean indicating if operation was successful
     * @throws SQLException
     */
    public static Boolean addAppointment(String inputTitle, String inputDescription,
                                      String inputLocation, String inputType, ZonedDateTime inputStart,
                                      ZonedDateTime inputEnd, String inputCreatedBy,
                                       String inputLastUpdateBy, Integer inputCustomerID,
                                      Integer inputUserID, Integer inputContactID) throws SQLException {

        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("INSERT INTO appointments " +
                "(Title, Description, Location, Type, Start, End, Create_date, \n" +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Format inputStart and inputEnd
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String inputStartString = inputStart.format(formatter).toString();
        String inputEndString = inputEnd.format(formatter).toString();

        // Set Params
        sqlCommand.setString(1, inputTitle);
        sqlCommand.setString(2, inputDescription);
        sqlCommand.setString(3, inputLocation);
        sqlCommand.setString(4, inputType);
        sqlCommand.setString(5, inputStartString);
        sqlCommand.setString(6, inputEndString);
        sqlCommand.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        sqlCommand.setString(8, inputCreatedBy);
        sqlCommand.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        sqlCommand.setString(10, inputLastUpdateBy);
        sqlCommand.setInt(11, inputCustomerID);
        sqlCommand.setInt(12, inputUserID);
        sqlCommand.setInt(13, inputContactID);

        // Execute query
        try {
            sqlCommand.executeUpdate();
            sqlCommand.close();
            return true;
        }
        catch (SQLException e) {
            //TODO- log error
            e.printStackTrace();
            return false;
        }

    }

    /**
     * deleteAppointment
     *
     * @param inputApptID ID of appointment to be deleted
     * @return Boolean indicating if operation was successful
     * @throws SQLException
     */
    public static Boolean deleteAppointment(Integer inputApptID) throws SQLException {

            PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("DELETE FROM appointments " +
                    "WHERE Appointment_ID = ?");

            sqlCommand.setInt(1, inputApptID);

        try {
            sqlCommand.executeUpdate();
            sqlCommand.close();
            return true;
        }
        catch (SQLException e) {
            //TODO- log error
            e.printStackTrace();
            return false;
        }

    }

    /**
     * deleteCustomersAppointments
     * delete all appointments for a customer, to maintain referential integrity when we delete a customer
     *
     * @param customerID ID of customer to delete appointments for
     * @return Boolean indicating if operation was successful
     * @throws SQLException
     */
    public static Boolean deleteCustomersAppointments(Integer customerID) throws SQLException {

        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("DELETE FROM appointments " +
                "WHERE Customer_ID = ?");

        sqlCommand.setInt(1, customerID);

        try {
            sqlCommand.executeUpdate();
            sqlCommand.close();
            return true;
        }
        catch (SQLException e) {
            //TODO- log error
            e.printStackTrace();
            return false;
        }

    }

    /**
     * getAllAppointments
     * queries database for all appointments
     *
     * @return List of all appointments
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {

        // Prepare SQL and execute query
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT * FROM appointments as a\n" +
                "LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID;");
        ResultSet results = sqlCommand.executeQuery();


        while( results.next() ) {
            // get data from the returned rows
            Integer appointmentID = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp startDateTime = results.getTimestamp("Start");
            Timestamp endDateTime = results.getTimestamp("End");
            Timestamp createdDate = results.getTimestamp("Create_Date");
            String createdBy = results.getString("Created_by");
            Timestamp  lastUpdateDateTime = results.getTimestamp("Last_Update");
            String lastUpdatedBy = results.getString("Last_Updated_By");
            Integer customerID = results.getInt("Customer_ID");
            Integer userID = results.getInt("User_ID");
            Integer contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");

            // populate into an appt object
            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );

            // Add to the observablelist
            allAppointments.add(newAppt);

        }
        sqlCommand.close();
        return allAppointments;

    }

    /**
     * getAppointmentsIn15Mins
     * Queries DB to find appointments for logged in user starting within 15 minutes.
     *
     * @return list of appointments for user that start in 15 mins.
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsIn15Mins() throws SQLException{

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // prepare times and convert to UTC
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime userTZnow = now.atZone(LogonSession.getUserTimeZone());
        ZonedDateTime nowUTC = userTZnow.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime utcPlus15 = nowUTC.plusMinutes(15);

        // create input strings
        String rangeStart = nowUTC.format(formatter).toString();
        String rangeEnd = utcPlus15.format(formatter).toString();
        Integer logonUserID = LogonSession.getLoggedOnUser().getUserID();


        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT * FROM appointments as a " +
                "LEFT OUTER JOIN contacts as c ON a.Contact_ID = c.Contact_ID WHERE " +
                "Start BETWEEN ? AND ? AND User_ID = ? ");

        sqlCommand.setString(1, rangeStart);
        sqlCommand.setString(2, rangeEnd);
        sqlCommand.setInt(3, logonUserID);

        ResultSet results = sqlCommand.executeQuery();

        while( results.next() ) {
            // get data from the returned rows
            Integer appointmentID = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp startDateTime = results.getTimestamp("Start");
            Timestamp endDateTime = results.getTimestamp("End");
            Timestamp createdDate = results.getTimestamp("Create_Date");
            String createdBy = results.getString("Created_by");
            Timestamp lastUpdateDateTime = results.getTimestamp("Last_Update");
            String lastUpdatedBy = results.getString("Last_Updated_By");
            Integer customerID = results.getInt("Customer_ID");
            Integer userID = results.getInt("User_ID");
            Integer contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");

            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );

            // Add to the observablelist
            allAppointments.add(newAppt);

        }
        return allAppointments;

    }
}
