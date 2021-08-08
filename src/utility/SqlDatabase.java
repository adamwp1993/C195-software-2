package utility;

import java.util.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * SqlDatabase
 *
 * @author Adam Petersen
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

    /**
     * SqlDatabase Constructor
     */
    public SqlDatabase() { };

    /**
     * Setter - JDBC url
     *
     * @param jdbcUrlInput JDBC url for connectivity
     */
    public static void setJdbcUrl(String jdbcUrlInput) {
        jdbcUrl = jdbcUrlInput;
    }

    /**
     * Setter- DbName
     *
     * @param dbNameInput DB name for connectivity
     */
    public static void setDbName(String dbNameInput) {
        dbName = dbNameInput;
    }

    /**
     * Setter - DB username
     *
     * @param dbUserNameInput DB username for DB login
     */
    public static void setDbUserName(String dbUserNameInput) {
        dbUserName = dbUserNameInput;
    }

    /**
     * Setter - DB password
     *
     * @param dbPasswordInput password for login
     */
    public static void setDbPassword(String dbPasswordInput) {
        dbPassword = dbPasswordInput;
    }

    /**
     * connectDB
     * Connect to the database
     */
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

    /**
     * dbCursor
     *
     * @return DB connection for use
     */
    public static Connection dbCursor() {
        return cursor;
    }
}
