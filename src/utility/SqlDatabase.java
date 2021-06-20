package utility;

import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * MySQL database connectivity class
 * Database credentials stored separately SqlDatabase.properties, for security reasons.
 * Ideally we would use a secrets manager of some kind here, hard coded credentials are BAD!
 */
public class SqlDatabase {

    private static String serverUrl = "wgudb.ucertify.com";
    private static int port = 3306;
    // DB credentials stored separately in sqlDatabases.properties, for security reasons.
    private static String dbName;
    private static String dbUserName;
    private static String dbPassword;
    private static String jdbcUrl;
    private static String jdbcDriver = "com.mysql.jdbc.Driver";
    private static Connection cursor;


    public SqlDatabase() { };

    public static void setJdbcUrl(String jdbcUrlInput) {
        jdbcUrl = jdbcUrlInput;
    }

    public static void setDbName(String dbNameInput) {
        dbName = dbNameInput;
    }

    public static void setDbUserName(String dbUserNameInput) {
        dbUserName = dbUserNameInput;
    }

    public static void setDbPassword(String dbPasswordInput) {
        dbPassword = dbPasswordInput;
    }

    public static void connectDB() {
        try {
            Class.forName(jdbcDriver);
            cursor = DriverManager.getConnection(jdbcUrl, dbUserName, dbPassword);

        }
        catch (SQLException error) {
            System.out.println(error.toString() + error.getSQLState());
        }
        catch (ClassNotFoundException error) {
            System.out.println(error.getMessage());
        }

    }

    public static Connection dbCursor() {
        return cursor;
    }
}
