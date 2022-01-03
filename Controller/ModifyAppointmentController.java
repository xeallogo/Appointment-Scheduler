/*
 * Program: ModifyAppointmentController.java
 * Author: Alexander Gool
 * Description: ModifyAppointmentController class used to control ModifyAppointmentView.fxml
 */

package Controller;

import Model.Appointment;
import Resources.ChangeScene;
import Resources.Formatter;
import Resources.List;
import Utilities.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This class controls Modify Appointment screen.
 *
 * @author Alexander Gool
 */
public class ModifyAppointmentController implements Initializable {
    //references to view
    @FXML
    private TextField titleTF, descriptionTF, locationTF;
    @FXML
    private ComboBox customerCB, userCB, contactCB, typeCB;
    @FXML
    private Button updateBTN, cancelBTN;
    @FXML
    private Label headerLBL, customerLBL, userLBL, appointmentIDLBL, appointmentDBLBL,
            titleLBL, descriptionLBL, locationLBL, contactLBL, typeLBL,
            dateLBL, startLBL, endLBL;
    @FXML
    private DatePicker dateDP;
    @FXML
    private Spinner<String> startHourSP, startMinuteSP,
            endHourSP, endMinuteSP;

    // values for spinner are stored
    SpinnerValueFactory<String> startHoursList = new SpinnerValueFactory.ListSpinnerValueFactory<String>(List.hours);
    SpinnerValueFactory<String> startMinutesList = new SpinnerValueFactory.ListSpinnerValueFactory<String>(List.minutes);
    SpinnerValueFactory<String> endHoursList = new SpinnerValueFactory.ListSpinnerValueFactory<String>(List.hours);
    SpinnerValueFactory<String> endMinutesList = new SpinnerValueFactory.ListSpinnerValueFactory<String>(List.minutes);

    //variables are instantiated
    private Appointment appointment;
    private String customerValue, userValue;
    private Timestamp startDateValue, endDateValue;
    int customerIDvalue = 0;
    int contactIDvalue = 0;
    int dateCount = 0;

    // datetimeformatter is set for DB
    LocalDate startLD, endLD;
    LocalTime startLT, endLT;
    LocalDateTime startLDT, endLDT, startDBLDT, endDBLDT;
    ZoneId myZoneID = ZoneId.systemDefault();
    ZonedDateTime startZDT, startUTC, startEST, endZDT, endUTC, endEST, startDBUTC, startDBZDT, endDBUTC, endDBZDT;

    //Below are the statements for DB connection.
    private Connection conn = DatabaseConnection.openConnection();
    private Statement statement;
    ResultSet resultSet = null;

    /**
     * ChangeScene is called to load and display Main screen.
     *
     * @param actionEvent Action event.
     * @throws IOException If an I/O exception occurred.
     */
    public void cancelBTNOA(javafx.event.ActionEvent actionEvent) throws IOException {
        ChangeScene.getMainScene(actionEvent);
    }

    /**
     * Updated appointment is added to DB and ChangeScene is called to load and display Main screen.
     *
     * @param actionEvent Action event.
     * @throws IOException If an I/O exception occurred.
     * @throws SQLException If SQL exception occurred.
     */
    public void updateBTNOA(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        statement = conn.createStatement();

        //start and end timezone is set to EST
        startEST = ZonedDateTime.ofInstant(ZonedDateTime.of(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(Integer.parseInt(startHourSP.getValue()),
                        Integer.parseInt(startMinuteSP.getValue()), 00)), myZoneID).toInstant(),
                ZoneId.of("America/New_York"));
        endEST = ZonedDateTime.ofInstant(ZonedDateTime.of(LocalDateTime.of(LocalDate.now(),
                LocalTime.of(Integer.parseInt(endHourSP.getValue()),
                        Integer.parseInt(endMinuteSP.getValue()), 00)), myZoneID).toInstant(),
                ZoneId.of("America/New_York"));

        //checking if the values are null/empty, an error message is returned
        if (customerCB.getValue() == null || titleTF.getText().equals("") || descriptionTF.getText().equals("") ||
                locationTF.getText().equals("") || contactCB.getValue() == null || typeCB.getValue() == null ||
                dateDP.getValue() == null || startHourSP.getValue() == null || startMinuteSP.getValue() == null ||
                endHourSP.getValue() == null || endMinuteSP.getValue() == null || userCB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing fields");
            alert.setContentText("All fields must be filled");

            alert.showAndWait();
        }
        //checking if start hour time is > end hour time, an error message is returned
        else if (Integer.parseInt(startHourSP.getValue()) > Integer.parseInt(endHourSP.getValue())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Start and End Time Hours");
            alert.setContentText("Start time: " + startHourSP.getValue() + ":" + startMinuteSP.getValue() +
                    "\nmust be before" +
                    "\nEnd time: " + endHourSP.getValue() + ":" + endMinuteSP.getValue());

            alert.showAndWait();
        }
        //checking if start hour time = end hour time, and if start minute time is > end minute time, an error message is returned
        else if (Integer.parseInt(startHourSP.getValue()) == Integer.parseInt(endHourSP.getValue()) &&
                Integer.parseInt(startMinuteSP.getValue()) >= Integer.parseInt(endMinuteSP.getValue())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Start and End Time Minutes");
            alert.setContentText("Start time: " + startHourSP.getValue() + ":" + startMinuteSP.getValue() +
                    "\nmust be before" +
                    "\nEnd time: " + endHourSP.getValue() + ":" + endMinuteSP.getValue());

            alert.showAndWait();
        } else if (dateDP.getValue().getDayOfWeek() == DayOfWeek.SATURDAY ||
                dateDP.getValue().getDayOfWeek() == DayOfWeek.SUNDAY) {

            System.out.println("Day: " + dateDP.getValue().getDayOfWeek());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Outside Business Hours");
            alert.setContentText("Appointments may not be scheduled on a weekend" +
                    "\nCurrent day of the Week: " + dateDP.getValue().getDayOfWeek());

            alert.showAndWait();
        }
        //checking if the start time is within business hours (8am to 10pm)
        else if (startEST.getHour() < 8 || startEST.getHour() > 22) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Outside Business Hours");
            alert.setContentText("Appointments must be schedule between 8:00 to 22:00 EST" +
                    "\nCurrent Start time: " + startHourSP.getValue() + ":" + startMinuteSP.getValue() +
                    "\nEST Start time: " + Formatter.timeFormat(startEST.getHour()) + ":" + Formatter.timeFormat(startEST.getMinute()));

            alert.showAndWait();
        }
        //checking if the end time is within business hours (8am to 10pm)
        else if (endEST.getHour() < 8 || endEST.getHour() > 22) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Outside Business Hours");
            alert.setContentText("Appointments must be schedule between 8:00 to 22:00 EST" +
                    "\nCurrent End time: " + endHourSP.getValue() + ":" + endMinuteSP.getValue() +
                    "\nEST End time: " + Formatter.timeFormat(endEST.getHour()) + ":" + Formatter.timeFormat(endEST.getMinute()));

            alert.showAndWait();
        } else {
            //SQL query to 'count' date selected
            //dateCount 0 = no matching date in DB
            //dateCount 1 = matching date in DB
            String dateCountQuery = "SELECT COUNT(CAST(Start AS DATE)) AS Date FROM appointments " +
                    "WHERE CAST(Start AS DATE) = '" + dateDP.getValue() + "';";
            resultSet = statement.executeQuery(dateCountQuery);
            while (resultSet.next()) {
                dateCount = resultSet.getInt("Date");
            }

            // get start date/time from form
            startLD = LocalDate.of(dateDP.getValue().getYear(), dateDP.getValue().getMonthValue(), dateDP.getValue().getDayOfMonth());
            startLT = LocalTime.of(Integer.parseInt(startHourSP.getValue()), Integer.parseInt(startMinuteSP.getValue()), 00);
            startLDT = LocalDateTime.of(startLD, startLT);

            // start timezone id is set to UTC
            startZDT = ZonedDateTime.of(startLDT, myZoneID);
            startUTC = ZonedDateTime.ofInstant(startZDT.toInstant(), ZoneId.of("UTC"));

            // get end date/time
            endLD = LocalDate.of(dateDP.getValue().getYear(), dateDP.getValue().getMonthValue(), dateDP.getValue().getDayOfMonth());
            endLT = LocalTime.of(Integer.parseInt(endHourSP.getValue()), Integer.parseInt(endMinuteSP.getValue()), 00);
            endLDT = LocalDateTime.of(endLD, endLT);

            // end timezone id is set to UTC
            endZDT = ZonedDateTime.of(endLDT, myZoneID);
            endUTC = ZonedDateTime.ofInstant(endZDT.toInstant(), ZoneId.of("UTC"));

            //get start date/time from DB
            startDBLDT = startDateValue.toLocalDateTime();

            // start timezone id is set to UTC
            startDBZDT = ZonedDateTime.of(startDBLDT, myZoneID);
            startDBUTC = ZonedDateTime.ofInstant(startDBZDT.toInstant(), ZoneId.of("UTC"));

            // get end date/time
            endDBLDT = endDateValue.toLocalDateTime();

            // end timezone id is set to UTC
            endDBZDT = ZonedDateTime.of(endDBLDT, myZoneID);
            endDBUTC = ZonedDateTime.ofInstant(endDBZDT.toInstant(), ZoneId.of("UTC"));

            //if dateCount is > 0, date selected matches date in DB
            if (dateCount > 0) {

                //datetimecount used checking if time matches any time slots in DB where (0 = no match) and (1 = match)
                int datetimecount = 0;

                //loop iterates through time slots of the given date
                for (int i = 1; i < dateCount + 1; i++) {
                    int limit = i - 1;

                    //SQL query used to return the datetimecount if time selected overlaps DB time.
                    //If selected start time <= DB end time and selected end time >= DB start time, then
                    //a match has occurred, meaning selected time can not be used as there is a time in used in DB.
                    //Loop doesn't include selected appt as that appt time can be changed and updated.
                    //Limit is used to iterate through different times in DB based on given date.
                    String datetimecountquery = "SELECT COUNT(Start) AS Start FROM appointments " +
                            "WHERE (CAST(Start as DATE) = '" + dateDP.getValue() + "' " +
                            "AND ('"
                            + Formatter.dateTimeFormatter(startUTC) + "' <= End AND " +
                            "'" + Formatter.dateTimeFormatter(endUTC) + "' >= Start))" +
                            " AND " +
                            "Appointment_ID NOT IN (" +
                            "SELECT Appointment_ID " +
                            "FROM appointments " +
                            "WHERE ('" + Formatter.dateTimeFormatter(startDBUTC) + "' = Start AND " +
                            "'" + Formatter.dateTimeFormatter(endDBUTC) + "' = End))" +
                            "LIMIT " + limit + ",1;";
                    resultSet = statement.executeQuery(datetimecountquery);
                    while (resultSet.next()) {
                        datetimecount = resultSet.getInt("Start");
                    }
                }

                //if the datetimecount is > 0, selected time overlaps DB time
                if (datetimecount > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Overlapping Date/Time");
                    alert.setContentText("Existing appointment");

                    alert.showAndWait();
                }
                //if no overlap has occured, data is added to DB
                else {
                    //SQL query for changing customerCB to Customer_ID
                    String customerIDQuery = "SELECT Customer_ID from customers " +
                            "WHERE Customer_Name='" + customerCB.getValue() + "';";
                    resultSet = statement.executeQuery(customerIDQuery);
                    while (resultSet.next()) {
                        customerIDvalue = resultSet.getInt("Customer_ID");
                    }

                    //SQL query for changing contactCB to Contact_ID
                    String contactIDQuery = "SELECT Contact_ID from contacts " +
                            "WHERE Contact_Name='" + contactCB.getValue() + "';";
                    resultSet = statement.executeQuery(contactIDQuery);
                    while (resultSet.next()) {
                        contactIDvalue = resultSet.getInt("Contact_ID");
                    }

                    // get user login name
                    // Note: uservalue = Login.getUsername();

                    // This is the SQL insert statement
                    String SQLUpdate = "UPDATE appointments SET " +
                            "Title = '" + titleTF.getText() + "', " +
                            "Description = '" + descriptionTF.getText() + "', " +
                            "Location = '" + locationTF.getText() + "', " +
                            "Type = '" + typeCB.getValue() + "', " +
                            "Start = '" + Formatter.dateTimeFormatter(startUTC) + "', " +
                            "End = '" + Formatter.dateTimeFormatter(endUTC) + "', " +
                            "Last_Update = NOW(), " +
                            "Last_Updated_By = '" + userCB.getValue() + "', " +
                            "Customer_ID = '" + customerIDvalue + "', " +
                            "User_ID = 1, " +
                            "Contact_ID = '" + contactIDvalue + "'" +
                            "WHERE Appointment_ID = '" + appointmentDBLBL.getText() + "';";
                    statement.executeUpdate(SQLUpdate);

                    ChangeScene.getMainScene(actionEvent);
                }
            }
            // data is added to DB
            else {
                //SQL query for changing customerCB to Customer_ID
                String customerIDQuery = "SELECT Customer_ID from customers " +
                        "WHERE Customer_Name='" + customerCB.getValue() + "';";
                resultSet = statement.executeQuery(customerIDQuery);
                while (resultSet.next()) {
                    customerIDvalue = resultSet.getInt("Customer_ID");
                }

                //SQL query for changing contactCB to Contact_ID
                String contactIDQuery = "SELECT Contact_ID from contacts " +
                        "WHERE Contact_Name='" + contactCB.getValue() + "';";
                resultSet = statement.executeQuery(contactIDQuery);
                while (resultSet.next()) {
                    contactIDvalue = resultSet.getInt("Contact_ID");
                }

                // get user login name
                // Note: uservalue = Login.getUsername();

                //This is the SQL insert statement
                String SQLUpdate = "UPDATE appointments SET " +
                        "Title = '" + titleTF.getText() + "', " +
                        "Description = '" + descriptionTF.getText() + "', " +
                        "Location = '" + locationTF.getText() + "', " +
                        "Type = '" + typeCB.getValue() + "', " +
                        "Start = '" + Formatter.dateTimeFormatter(startUTC) + "', " +
                        "End = '" + Formatter.dateTimeFormatter(endUTC) + "', " +
                        "Last_Update = NOW(), " +
                        "Last_Updated_By = '" + userCB.getValue() + "', " +
                        "Customer_ID = '" + customerIDvalue + "', " +
                        "User_ID = 1, " +
                        "Contact_ID = '" + contactIDvalue + "'" +
                        "WHERE Appointment_ID = '" + appointmentDBLBL.getText() + "';";
                statement.executeUpdate(SQLUpdate);

                ChangeScene.getMainScene(actionEvent);
            }
        }
    }

    /**
     * Selected appointment values are set.
     *
     * @param value The selected appointment
     * @throws SQLException If SQL exception occurred.
     */
    public void setAppointment(Appointment value) throws SQLException {
        appointment = value;

        statement = conn.createStatement();

        //SQL query to get customer name from customer ID
        String customerQuery = "SELECT Customer_Name from customers " +
                "WHERE Customer_ID='" + appointment.getCustomerID() + "';";
        resultSet = statement.executeQuery(customerQuery);
        while (resultSet.next()) {
            customerValue = resultSet.getString("Customer_Name");
        }

        //SQL query to get username from customer ID
        String userQuery = "SELECT User_Name from users " +
                "WHERE User_Name='" + appointment.getLastUpdatedBy() + "';";
        resultSet = statement.executeQuery(userQuery);
        while (resultSet.next()) {
            userValue = resultSet.getString("User_Name");
        }

        //SQL query to get date/time from start date
        String startDateQuery = "SELECT Start from appointments " +
                "WHERE Appointment_ID='" + appointment.getAppointmentID() + "';";
        resultSet = statement.executeQuery(startDateQuery);
        while (resultSet.next()) {
            startDateValue = resultSet.getTimestamp("Start");
        }

        //SQL query to get date/time from end date
        String endDateQuery = "SELECT End from appointments " +
                "WHERE Appointment_ID='" + appointment.getAppointmentID() + "';";
        resultSet = statement.executeQuery(endDateQuery);
        while (resultSet.next()) {
            endDateValue = resultSet.getTimestamp("End");
        }

        // all selected database variables are set in textfields, label, and comboboxes
        customerCB.setValue(customerValue);
        userCB.setValue(userValue);
        appointmentDBLBL.setText(String.valueOf(appointment.getAppointmentID()));
        titleTF.setText(appointment.getTitle());
        descriptionTF.setText(appointment.getDescription());
        locationTF.setText(appointment.getLocation());
        contactCB.setValue(appointment.getContactName());
        typeCB.setValue(appointment.getType());
        dateDP.setValue(LocalDate.parse(startDateValue.toString().substring(0, 10)));
        startHourSP.getValueFactory().setValue(startDateValue.toString().substring(11, 13));
        startMinuteSP.getValueFactory().setValue(startDateValue.toString().substring(14, 16));
        endHourSP.getValueFactory().setValue(endDateValue.toString().substring(11, 13));
        endMinuteSP.getValueFactory().setValue(endDateValue.toString().substring(14, 16));
    }

    /**
     * ModifyAppointmentController class is initialized.
     * Sets datepicker editor, all combobox variables, and textformatter for spinners.
     *
     * @param url location used to resolve relative paths for the root object, or null if location is not known.
     * @param rb Resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // this prevents users from editing the dateDP textfield
        dateDP.setEditable(false);

        // spinners are set
        startHourSP.setValueFactory(startHoursList);
        startHourSP.setEditable(true);
        startHourSP.getEditor().setTextFormatter(new TextFormatter<Object>(Formatter.hourTimeFilter));
        startMinuteSP.setValueFactory(startMinutesList);
        startMinuteSP.setEditable(true);
        startMinuteSP.getEditor().setTextFormatter(new TextFormatter<Object>(Formatter.minuteTimeFilter));
        endHourSP.setValueFactory(endHoursList);
        endHourSP.setEditable(true);
        endHourSP.getEditor().setTextFormatter(new TextFormatter<Object>(Formatter.hourTimeFilter));
        endMinuteSP.setValueFactory(endMinutesList);
        endMinuteSP.setEditable(true);
        endMinuteSP.getEditor().setTextFormatter(new TextFormatter<Object>(Formatter.minuteTimeFilter));

        try {
            statement = conn.createStatement();

            // SQL query to get customer name from DB
            String customerNameCB = "SELECT Customer_Name FROM customers";
            resultSet = statement.executeQuery(customerNameCB);
            while (resultSet.next()) {
                customerCB.getItems().addAll(resultSet.getString("Customer_Name"));
            }

            // SQL query to get user name from DB
            String userNameCB = "SELECT User_Name FROM users";
            resultSet = statement.executeQuery(userNameCB);
            while (resultSet.next()) {
                userCB.getItems().addAll(resultSet.getString("User_Name"));
            }

            // SQL query to get contact name from DB
            String contactNameCB = "SELECT Contact_Name FROM contacts";
            resultSet = statement.executeQuery(contactNameCB);
            while (resultSet.next()) {
                contactCB.getItems().addAll(resultSet.getString("Contact_Name"));
            }

            // typeCB is set with type array list
            typeCB.setItems(List.types);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}