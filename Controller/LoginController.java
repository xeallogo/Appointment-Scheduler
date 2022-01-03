/*
 * Program: LoginController.java
 * Author: Alexander Gool
 * Description: Class used to control Login screen.
 */

package Controller;

import Resources.ChangeScene;
import Resources.Formatter;
import Resources.LoginActivity;
import Utilities.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class controls Login screen.
 *
 * @author Alexander Gool
 */
public class LoginController implements Initializable {
    //references to view
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private Button loginBTN;
    @FXML
    private Label headerLBL, usernameLBL, passwordLBL, zoneIdLBL, userLocationLBL;

    // here we are setting region and language
    private final ZoneId zoneId = ZoneId.systemDefault();
    private Locale locale = Locale.getDefault();
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("Resources/Nat", locale);

    //Below are the statements for DB connection.
    private Connection conn = DatabaseConnection.openConnection();
    private Statement statement;
    ResultSet resultSet = null;

    ZonedDateTime currentDateTime;
    int appointmentID;
    LocalDateTime dateTime;

    /**
     * ChangeScene is called to load and display Main screen.
     *
     * @param actionEvent Action event.
     * @throws IOException If an I/O exception occurred.
     * @throws SQLException If SQL exception occurred.
     */
    public void loginBTNOA(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        // Get user input for username/password
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        // Checking if username/password are empty
        if (username.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorTitle"));
            alert.setHeaderText(resourceBundle.getString("errorHeaderBlank"));
            alert.setContentText(resourceBundle.getString("errorContentBlank"));
            alert.showAndWait();
        }
        // Checking if username/password are valid. If invalid, throw error and erase passwordTF
        else if (validLogin(username, password) == false) {
            LoginActivity.track(username, "Fail");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("errorTitle"));
            alert.setHeaderText(resourceBundle.getString("errorHeaderInvalid"));
            alert.setContentText(resourceBundle.getString("errorContentInvalid"));
            alert.showAndWait();

            passwordTF.setText("");
        }

        //if username/password are valid, store username and change to main scene
        else {
            // storing username value
            LoginActivity.track(username, "Pass");
            // Login.setUsername(username);

            ChangeScene.getMainScene(actionEvent);

            // get current time
            currentDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));

            // SQL query to get appointments, if any, within 15min of the user's successful log-in
            String appointmentQuery = "SELECT Appointment_ID, Start FROM appointments " +
                    "WHERE CAST(Start as DATE) = '" + currentDateTime.toLocalDate() + "' AND ('"
                    + Formatter.dateTimeFormatter(currentDateTime) + "' <= Start AND '" +
                    Formatter.dateTimeFormatter(currentDateTime.plusMinutes(15)) + "' >= Start);";

            // execute SQL query
            resultSet = statement.executeQuery(appointmentQuery);
            //if an appointment exists, get the appt ID and time
            if(resultSet.next()) {
                appointmentID = resultSet.getInt("Appointment_ID");
                dateTime = resultSet.getTimestamp("Start").toLocalDateTime();

                // user is alerted of appt
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment");
                alert.setHeaderText("Upcoming Appointment");
                alert.setContentText("Appointment ID: " + appointmentID +
                        "\nDate: " + dateTime.toString().substring(0, 10) +
                        "\nTime: " + dateTime.toString().substring(11,16));
                alert.showAndWait();
            }
            // user is alerted that there is no appt
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Appointment");
                alert.setHeaderText("Upcoming Appointment");
                alert.setContentText("No upcoming appointments");
                alert.showAndWait();
            }
        }
    }

    /**
     * Validates if a password matches with the DB, based on the username.
     *
     * @param username Username as a string.
     * @param password Password as a string.
     * @return <code>true</code> If password matches with DB.
     *         <code>false</code> Otherwise.
     * @throws SQLException If SQL exception occurred.
     */
    private boolean validLogin(String username, String password) throws SQLException {
        statement = conn.createStatement();

        // SQL query to get username/password if it matches the DB
        String userPassQuery = "SELECT * FROM users WHERE User_Name ='" + username + "' AND " +
                "Password = '" + password + "';";
        resultSet = statement.executeQuery(userPassQuery);

        //if results match, return true, else return false
        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * LoginController class is initialized.
     * All labels are set based on user's computer's language (English or French).
     *
     * @param url location used to resolve relative paths for the root object, or null if location is not known.
     * @param rb Resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userLocationLBL.setText(String.valueOf(zoneId));
        headerLBL.setText(resourceBundle.getString("titleLBL"));
        usernameLBL.setText(resourceBundle.getString("usernameLBL"));
        passwordLBL.setText(resourceBundle.getString("passwordLBL"));
        loginBTN.setText(resourceBundle.getString("loginBTN"));
        zoneIdLBL.setText(resourceBundle.getString("zoneLBL"));
    }
}