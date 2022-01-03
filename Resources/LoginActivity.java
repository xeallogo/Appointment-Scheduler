/*
 * Program: LoginActivity.java
 * Author: Alex Gool
 * Description: LoginActivity class tracks user logins and timestamps. These are then append to login_activity.txt.
 */

package Resources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Login attempts are tracked in this class.
 * Appends activity to login_activity.txt.
 *
 * @author Alex Gool
 */
public class LoginActivity {

    LoginActivity(){}

    /**
     * Timestamp is created and user login attempt/results are appended to login_activity.txt.
     *
     * @param user A string for the username.
     * @param attempt Database reference to determine if attempt was successful.
     * @throws IOException If an I/O exception occurred.
     */
    public static void track(String user, String attempt) throws IOException {
        // Assign file location and store the localdatetime
        String fileLocation = "./src/login_activity.txt";
        LocalDateTime localDateTime = LocalDateTime.now();

        //create and append login activity to login_activity.txt
        FileWriter fileWriter = new FileWriter(fileLocation, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(user + " - " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - Attempt: " +  attempt);
        printWriter.close();
    }
}