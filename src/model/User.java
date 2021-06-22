package model;

import java.util.Locale;
import java.util.TimeZone;

public class User {
    private String userName;
    private Integer userID;


    public User(String inputUserName,Integer inputUserId) {
        userName = inputUserName;
        userID = inputUserId;

    }

    public String getUserName() {
        return userName;
    }

    public Integer getUserID() {
        return userID;
    }


}
