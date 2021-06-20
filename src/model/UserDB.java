package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.SqlDatabase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {

    public static ObservableList<Integer> getAllUserID() throws SQLException {
        ObservableList<Integer> allUserID = FXCollections.observableArrayList();
        PreparedStatement sqlCommand = SqlDatabase.dbCursor().prepareStatement("SELECT DISTINCT User_ID" +
                " FROM users;");
        ResultSet results = sqlCommand.executeQuery();

        while ( results.next() ) {
            allUserID.add(results.getInt("User_ID"));
        }
        sqlCommand.close();
        return allUserID;
    }
}
