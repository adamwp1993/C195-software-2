package model;

import utility.SqlDatabase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * LogonSession
 *
 * @author Adam Petersen
 */
public class LogonSession {

    private static User loggedOnUser;
    private static Locale userLocale;
    private static ZoneId userTimeZone;

    /**
     * LogonSessionConstructor
     */
    public LogonSession() {}

    /**
     * attemptLogon
     * Takes input username and password and checks them against DB to logon
     *
     * @param userNameInput user input username
     * @param userPassword user input password
     *
     * @return Boolean indicating a successful logon
     * @throws SQLException
     */
    public static boolean attemptLogon(String userNameInput, String userPassword) throws SQLException{
        Connection conn = SqlDatabase.dbCursor();
        PreparedStatement sqlCommand = conn.prepareStatement("SELECT * FROM users WHERE " +
                "User_Name = ? AND Password = ?");
        sqlCommand.setString(1, userNameInput);
        sqlCommand.setString(2, userPassword);
        System.out.println("Executing query...");
        ResultSet result = sqlCommand.executeQuery();
        if (!result.next()) {
            sqlCommand.close();
            return false;
            //Log failed login attempt

        }
        else {
            loggedOnUser = new User(result.getString("User_Name"), result.getInt("User_ID"));
            userLocale = Locale.getDefault();
            userTimeZone = ZoneId.systemDefault();
            sqlCommand.close();
            return true;

        }


    }

    /**
     * Getter - user Object
     * @return logged on user object
     */
    public static User getLoggedOnUser() {
        return loggedOnUser;
    }

    /**
     * Getter - user Locale
     * @return locale of logged on user
     */
    public static Locale getUserLocale() {
        return userLocale;

    }

    /**
     * Getter - user Time Zone
     * @return logged on user time zone
     */
    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }

    /**
     * logOff
     * Logs off user
     */
    public static void logOff() {
        loggedOnUser = null;
        userLocale = null;
        userTimeZone = null;
    }
}
