package model;

import java.util.Locale;
import java.util.TimeZone;

/**
 * User
 *
 * @author Adam Petersen
 */
public class User {
    private String userName;
    private Integer userID;

    /**
     * User Constructor
     *
     * @param inputUserName user name
     * @param inputUserId user ID
     */
    public User(String inputUserName,Integer inputUserId) {
        userName = inputUserName;
        userID = inputUserId;

    }

    /**
     * Getter - user name
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter - user ID 
     * @return user ID
     */
    public Integer getUserID() {
        return userID;
    }

}
