package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Logger
 *
 * @author Adam Petersen
 */
public class Logger {

    private static final String logPath = "login_activity.txt";

    /**
     * auditLogon
     * Log a logon activity in the text file
     *
     * @param userName userName of the logged on user
     * @param successBool Boolean to indicate successful logon
     * @throws IOException
     */
    public static void auditLogin(String userName, Boolean successBool) throws IOException {
        try {

            BufferedWriter logger = new BufferedWriter(new FileWriter(logPath, true));
            logger.append(ZonedDateTime.now(ZoneOffset.UTC).toString() + " UTC-LOGIN ATTEMPT-USERNAME: " + userName +
                    " LOGIN SUCCESSFUL: " + successBool.toString() + "\n");
            logger.flush();
            logger.close();
        }
        catch (IOException error) {
            error.printStackTrace();
        }

    }
}
