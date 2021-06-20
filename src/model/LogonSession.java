package model;

import utility.SqlDatabase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class LogonSession {
    private static User loggedOnUser;
    private static Locale userLocale;
    private static ZoneId userTimeZone;


    public LogonSession() {}

    public static boolean attemptLogon(String userNameInput, String userPassword) throws SQLException{
        //TODO - Add logging here
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

    public static Locale getUserLocale() {
        return userLocale;

    }

    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }

    public static void logOff() {
        loggedOnUser = null;
        userLocale = null;
        userTimeZone = null;
    }
}
