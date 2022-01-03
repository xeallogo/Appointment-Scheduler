/*
 * Program: MainController.java
 * Author: Alexander Gool
 * Description: Class used to control Main View screen
 */

package Controller;

import Model.*;
import Resources.ChangeScene;
import Resources.Formatter;
import Utilities.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * This class controls Main View screen.
 *
 * @author Alexander Gool
 */
public class MainController implements Initializable {

    // references to view
    @FXML
    private Button addCustomerBTN, ModifyCustomerBTN, addAppointmentBTN, ModifyAppointmentBTN;
    @FXML
    private TabPane tabPane;

    // references to appt table
    @FXML
    private TableView<Appointment> appointmentTV;
    @FXML
    private TableColumn<Appointment, Integer> ATappointmentIDCOL, ATcustomerIDCOL, ATcontactCOL;
    @FXML
    private TableColumn<Appointment, String> ATtitleCOL, ATdescriptionCOL, ATlocationCOL, ATtypeCOL;
    @FXML
    private TableColumn<Appointment, Date> ATstartCOL, ATendCOL;

    // references to customer table
    @FXML
    private TableView<Customer> customerTV;
    @FXML
    private TableColumn<Customer, Integer> CTcustomerIDCOL, CTcountryIDCOL;
    @FXML
    private TableColumn<Customer, String> CTnameCOL, CTaddressCOL, CTpostalCodeCOL, CTphoneCOL, CTcreatedByCOL, CTupdatedByCOL, CTdivisionCOL;
    @FXML
    private TableColumn<Customer, Date> CTcreateDateCOL;
    @FXML
    private TableColumn<Customer, Timestamp> CTupdateCOL;

    // references to reports table
    @FXML
    private TableView reportsTV;

    // table columns for appt type by month
    private TableColumn month = new TableColumn("Month");
    private TableColumn type = new TableColumn("Type");
    private TableColumn monthTypeTotal = new TableColumn("Total Type");

    // table columns for appt schedules by customer
    private TableColumn contactName = new TableColumn("Contact Name");
    private TableColumn appointmentID = new TableColumn("Appointment ID");
    private TableColumn title = new TableColumn("Title");
    private TableColumn description = new TableColumn("Description");
    private TableColumn date = new TableColumn("Date");
    private TableColumn startTime = new TableColumn("Start Time");
    private TableColumn endTime = new TableColumn("End Time");
    private TableColumn customerID = new TableColumn("Customer ID");

    // table columns for total appts for contact by month
    private TableColumn appTotal = new TableColumn("Total Number of appts");

    // observable list for storing model data
    private ObservableList<Appointment> appointments;
    private ObservableList<Customer> customers;
    private ObservableList<ReportMonth> reportsMonth;
    private ObservableList<ReportContact> reportsContact;
    private ObservableList<ReportCMTotal> reportsCMTotal;

    // item selected by user
    Customer customerSelectedItem;
    Appointment appointmentSelectedItem;

    // Below are the statements for DB connection.
    private Connection conn = DatabaseConnection.openConnection();
    private Statement statement;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /* ------------------ Table Views ------------------ */

    /**
     * Gets appt data from DB.
     *
     * @param conn Connection session with the DB.
     * @return Appointment model
     * @throws SQLException If SQL exception occurred.
     */
    private ObservableList<Appointment> fetchAppointment(Connection conn) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        statement = conn.createStatement();
        // executing SQL statement, getting appts
        resultSet = statement.executeQuery("SELECT *, contacts.Contact_name FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "ORDER BY Appointment_ID");
        // adding results to new appts
        while (resultSet.next()) {
            appointments.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getTimestamp("Create_Date").toLocalDateTime(),
                    resultSet.getString("Created_By"),
                    resultSet.getTimestamp("Last_Update"),
                    resultSet.getString("Last_Updated_By"),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID"),
                    resultSet.getString("Contact_Name")
            ));
        }
        //return appt model
        return appointments;
    }

    /**
     * Appointment model is set into TableView
     */
    private void setAppointmentTV() {
        ATappointmentIDCOL.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentID"));
        ATtitleCOL.setCellValueFactory(new PropertyValueFactory("title"));
        ATdescriptionCOL.setCellValueFactory(new PropertyValueFactory("description"));
        ATlocationCOL.setCellValueFactory(new PropertyValueFactory("location"));
        ATcontactCOL.setCellValueFactory(new PropertyValueFactory("contactName"));
        ATtypeCOL.setCellValueFactory(new PropertyValueFactory("type"));
        ATstartCOL.setCellValueFactory(new PropertyValueFactory("start"));
        ATstartCOL.setCellFactory(new Formatter.DateColumnFormatter<>());
        ATendCOL.setCellValueFactory(new PropertyValueFactory("end"));
        ATendCOL.setCellFactory(new Formatter.DateColumnFormatter<>());
        ATcustomerIDCOL.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerID"));
    }

    /**
     * Gets customer data from DB.
     *
     * @param conn Connection session with the DB.
     * @return Customer model
     * @throws SQLException If SQL exception occurred.
     */
    private ObservableList<Customer> fetchCustomer(Connection conn) throws SQLException {
        customers = FXCollections.observableArrayList();

        statement = conn.createStatement();
        // executing SQL statement, getting customers
        resultSet = statement.executeQuery("SELECT *, first_level_divisions.Division, first_level_divisions.COUNTRY_ID FROM customers " +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                "ORDER BY Customer_ID");
        // adding results to new customers
        while (resultSet.next()) {
            customers.add(new Customer(
                    resultSet.getInt("Customer_ID"),
                    resultSet.getString("Customer_Name"),
                    resultSet.getString("Address"),
                    resultSet.getString("Postal_Code"),
                    resultSet.getString("Phone"),
                    resultSet.getTimestamp("Create_Date").toLocalDateTime(),
                    resultSet.getString("Created_By"),
                    resultSet.getTimestamp("Last_Update"),
                    resultSet.getString("Last_Updated_By"),
                    resultSet.getInt("Division_ID"),
                    resultSet.getString("Division"),
                    resultSet.getInt("COUNTRY_ID")
            ));
        }
        // return customer model
        return customers;
    }

    /**
     * Customer model is set into TableView
     */
    private void setCustomerTV() {
        CTcustomerIDCOL.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        CTnameCOL.setCellValueFactory(new PropertyValueFactory("customerName"));
        CTaddressCOL.setCellValueFactory(new PropertyValueFactory("address"));
        CTpostalCodeCOL.setCellValueFactory(new PropertyValueFactory("postal"));
        CTphoneCOL.setCellValueFactory(new PropertyValueFactory("phone"));
        CTcreateDateCOL.setCellValueFactory(new PropertyValueFactory("createDate"));
        CTcreateDateCOL.setCellFactory(new Formatter.DateColumnFormatter<>());
        CTcreatedByCOL.setCellValueFactory(new PropertyValueFactory("createdBy"));
        CTupdateCOL.setCellValueFactory(new PropertyValueFactory("lastUpdate"));
        CTupdatedByCOL.setCellValueFactory(new PropertyValueFactory("lastUpdatedBy"));
        CTdivisionCOL.setCellValueFactory(new PropertyValueFactory("division"));
        CTcountryIDCOL.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("countryID"));
    }

    /* ------------- Appointment Radio Buttons ------------- */

    /**
     * All appointments are displayed.
     *
     * @param actionEvent Action event.
     */
    public void allRBTNOA(javafx.event.ActionEvent actionEvent) {
        try {
            // fetchAppointment is called and all appointments are stored/set
            appointments = fetchAppointment(conn);
            appointmentTV.setItems(appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Appointments are displayed for current month.
     *
     * @param actionEvent Action event.
     */
    public void monthRBTNOA(javafx.event.ActionEvent actionEvent) {
        try {
            // fetchAppointment is called and all appointments are stored/set within current month
            appointments = fetchAppointmentMonth(conn);
            appointmentTV.setItems(appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Appointments are displayed for current week.
     *
     * @param actionEvent Action event.
     */
    public void weekRBTNOA(javafx.event.ActionEvent actionEvent) {
        try {
            // fetchAppointment is called and all appointments are stored/set within current week
            appointments = fetchAppointmentWeek(conn);
            appointmentTV.setItems(appointments);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Gets appt data from DB for current month.
     *
     * @param conn Connection session with the DB.
     * @return Appointment model
     * @throws SQLException If SQL exception occurred.
     */
    private ObservableList<Appointment> fetchAppointmentMonth(Connection conn) throws SQLException {
        appointments = FXCollections.observableArrayList();

        // get current month and start of next month
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate startOfNextMonth = startOfMonth.plusMonths(1);

        // SQL statement to get appointments for current month
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT *, contacts.Contact_name FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE Start >= ? AND Start < ?  " +
                "ORDER BY Start");
        preparedStatement.setString(1, String.valueOf(startOfMonth) + " 00:00:00");
        preparedStatement.setString(2, String.valueOf(startOfNextMonth) + " 00:00:00");

        ResultSet resultSet = preparedStatement.executeQuery();

        // results are added to new appointments
        while (resultSet.next()) {
            appointments.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getTimestamp("Create_Date").toLocalDateTime(),
                    resultSet.getString("Created_By"),
                    resultSet.getTimestamp("Last_Update"),
                    resultSet.getString("Last_Updated_By"),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID"),
                    resultSet.getString("Contact_Name")
            ));
        }
        // return Appointment model
        return appointments;
    }



    /**
     * Gets appt data from DB for current week.
     *
     * @param conn Connection session with the DB.
     * @return Appointment model
     * @throws SQLException If SQL exception occurred.
     */
    private ObservableList<Appointment> fetchAppointmentWeek(Connection conn) throws SQLException {
        appointments = FXCollections.observableArrayList();

        // get current week and start of next week
        LocalDate startOfWeek = LocalDate.now();
        LocalDate startOfNextWeek = startOfWeek.plusWeeks(1);

        // SQL statement to get appts for current week
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT *, contacts.Contact_name FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE Start >= ? AND Start < ?  " +
                "ORDER BY Start");
        preparedStatement.setString(1, String.valueOf(startOfWeek) + " 00:00:00");
        preparedStatement.setString(2, String.valueOf(startOfNextWeek) + " 00:00:00");

        ResultSet resultSet = preparedStatement.executeQuery();

        // results are added to new appointments
        while (resultSet.next()) {
            appointments.add(new Appointment(
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Description"),
                    resultSet.getString("Location"),
                    resultSet.getString("Type"),
                    resultSet.getTimestamp("Start").toLocalDateTime(),
                    resultSet.getTimestamp("End").toLocalDateTime(),
                    resultSet.getTimestamp("Create_Date").toLocalDateTime(),
                    resultSet.getString("Created_By"),
                    resultSet.getTimestamp("Last_Update"),
                    resultSet.getString("Last_Updated_By"),
                    resultSet.getInt("Customer_ID"),
                    resultSet.getInt("User_ID"),
                    resultSet.getInt("Contact_ID"),
                    resultSet.getString("Contact_Name")
            ));
        }
        // return Appointment model
        return appointments;
    }

    /* ---------------- Buttons ---------------- */

    /**
     * ChangeScene is called to load and display Add Appointment screen.
     *
     * @param actionEvent Action event.
     * @throws IOException If an I/O exception occurred.
     */
    public void addAppointmentBTNOA(javafx.event.ActionEvent actionEvent) throws IOException {
        ChangeScene.getAddAppointmentScene(actionEvent);
    }

    /**
     * ChangeScene is called to load and display Modify Appointment screen.
     *
     * @param actionEvent Action event.
     */
    public void ModifyAppointmentBTNOA(javafx.event.ActionEvent actionEvent) {
        // get user appt table row selection
        appointmentSelectedItem = appointmentTV.getSelectionModel().getSelectedItem();

        try {
            // if no user appt table row is selected, an error message is returned
            if (appointmentSelectedItem == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Appointment Selected");
                alert.setContentText("Please select an appointment");
                alert.showAndWait();
            }
            //else, ChangeScene is called and pass appt table row selected
            else {
                ChangeScene.getModifyAppointmentScene(actionEvent, appointmentTV.getSelectionModel().getSelectedItem());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ChangeScene is called to load and display Add Customer screen.
     *
     * @param actionEvent Action event.
     * @throws IOException  If an I/O exception occurred.
     */
    public void addCustomerBTNOA(javafx.event.ActionEvent actionEvent) throws IOException {
        ChangeScene.getAddCustomerScene(actionEvent);
    }

    /**
     * ChangeScene is called to load and display Modify Customer screen.
     *
     * @param actionEvent Action event.
     */
    public void ModifyCustomerBTNOA(javafx.event.ActionEvent actionEvent) {
        // get user's customer table row selection
        customerSelectedItem = customerTV.getSelectionModel().getSelectedItem();
        try {
            // if no user's customer table row is selected, an error message is returned
            if (customerSelectedItem == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Customer Selected");
                alert.setContentText("Please select a customer");
                alert.showAndWait();
            }
            // else, ChangeScene is called and pass customer table row selected
            else {
                ChangeScene.getModifyCustomerScene(actionEvent, customerTV.getSelectionModel().getSelectedItem());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Selected appointment object is removed from DB.
     *
     * @param appointmentID Selected appointment.
     * @throws SQLException If SQL exception occurred.
     */
    public void removeAppointment(int appointmentID) throws SQLException {
        try {
            // Execute SQL statement for deleting selected appt from appts DB.
            preparedStatement = conn.prepareStatement("DELETE FROM appointments where Appointment_ID = ?");
            preparedStatement.setInt(1, appointmentID);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * Selected appointment object is removed from TableView.
     * Lambda expression used to execute user selected response and delete appointment object from DB and TableView.
     *
     * @param actionEvent Action event.
     */
    public void deleteAppointmentBTNOA(javafx.event.ActionEvent actionEvent) {
        // get user selection
        appointmentSelectedItem = appointmentTV.getSelectionModel().getSelectedItem();

        // if no selection was made on customer table, an error message is returned
        if (appointmentSelectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an appointment");
            alert.showAndWait();
        }
        // This is confirmation dialog for user selection deletion, confirmation message
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete \"" + appointmentSelectedItem.getTitle() + "\"?");
            alert.setContentText("Appointment ID: " + appointmentSelectedItem.getAppointmentID() +
                    "\nAppointment Type: " + appointmentSelectedItem.getType() +
                    "\nAre you sure you want to delete this appointment?");
            alert.showAndWait().ifPresent((response -> {
                // if OK button is selected by user, row is deleted from tableview and from DB
                if (response == ButtonType.OK) {
                    try {
                        statement = conn.createStatement();

                        appointmentTV.getItems().remove(appointmentSelectedItem);
                        removeAppointment(appointmentSelectedItem.getAppointmentID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
    }

    /**
     * Selected customer object is removed from DB.
     *
     * @param customerID Selected customer.
     * @throws SQLException If SQL exception occurred.
     */
    public void removeCustomer(int customerID) throws SQLException {
        try {
            // execute SQL statement to delete selected customer from appts and customers database
            preparedStatement = conn.prepareStatement("DELETE FROM appointments where Customer_ID = ?");
            preparedStatement.setInt(1, customerID);
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement("DELETE FROM customers where Customer_ID = ?");
            preparedStatement.setInt(1, customerID);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    /**
     * Selected customer object is removed from TableView.
     * Lambda expression used to execute user selected response and delete customer object from DB and TableView.
     *
     * @param actionEvent Action event.
     */
    public void deleteCustomerBTNOA(javafx.event.ActionEvent actionEvent) {
        // get user's customer table row selection
        customerSelectedItem = customerTV.getSelectionModel().getSelectedItem();

        // if no selection was made on customer table, an error message is returned
        if (customerSelectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
        }
        // confirmation dialog for user selection deletion, confirmation message
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete \"" + customerSelectedItem.getCustomerName() + "\"?");
            alert.setContentText("Are you sure you want to delete this customer?");
            alert.showAndWait().ifPresent((response -> {
                // if OK button is selected by user, row is deleted from tableview and from DB
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        statement = conn.createStatement();

                        customerTV.getItems().remove(customerSelectedItem);
                        removeCustomer(customerSelectedItem.getCustomerID());

                        appointments = fetchAppointment(conn);
                        setAppointmentTV();
                        appointmentTV.setItems(appointments);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
    }

    /* ---------------- Reports radio buttons ---------------- */

    /**
     * Total number of appts is displayed for each month by type.
     *
     * @param actionEvent Action event.
     * @throws SQLException If SQL exception occurred.
     */
    public void reportsMonthRBTNOA(javafx.event.ActionEvent actionEvent) throws SQLException {
        statement = conn.createStatement();

        // current tableview is cleared and new table columns are added
        reportsTV.getColumns().clear();
        reportsTV.getItems().clear();
        reportsTV.getColumns().addAll(month, type, monthTypeTotal);

        reportsMonth = FXCollections.observableArrayList();

        // SQL query to get all appts for each month by type
        String reportsMonthQuery = "SELECT MONTHNAME(Start) AS Month, Type, COUNT(*) AS 'Total Type' " +
                "FROM appointments " +
                "GROUP BY MONTHNAME(Start), Type;";
        resultSet = statement.executeQuery(reportsMonthQuery);

        // results are added to new reportsMonth
        while (resultSet.next()) {
            reportsMonth.add(new ReportMonth(
                    resultSet.getString("Month"),
                    resultSet.getString("Type"),
                    resultSet.getInt("Total Type")
            ));
        }

        // reportsMonth is set into TableView
        month.setCellValueFactory(new PropertyValueFactory<>("month"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        monthTypeTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        // reportsMonth TableView is displayed
        reportsTV.setItems(reportsMonth);
    }

    /**
     * Contact's schedule is displayed.
     *
     * @param actionEvent Action event.
     * @throws SQLException If SQL exception occurred.
     */
    public void reportsContactRBTNOA(javafx.event.ActionEvent actionEvent) throws SQLException {
        statement = conn.createStatement();

        // current tableview is cleared and new table columns are added
        reportsTV.getColumns().clear();
        reportsTV.getItems().clear();
        reportsTV.getColumns().addAll(contactName, appointmentID, title, type, description,
                date, startTime, endTime, customerID);

        reportsContact = FXCollections.observableArrayList();

        // SQL query to get contact's schedule
        String reportsCustomerQuery = "SELECT Contact_Name, Appointment_ID, Title, Type, Description, " +
                "DATE(Start) AS Date, TIME(Start) AS 'Start Time', TIME(End) AS 'End Time', Customer_ID " +
                "FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID " +
                "ORDER BY Contact_Name, Date;";
        resultSet = statement.executeQuery(reportsCustomerQuery);

        // results are added to new reportsContact
        while (resultSet.next()) {
            reportsContact.add(new ReportContact(
                    resultSet.getString("Contact_Name"),
                    resultSet.getInt("Appointment_ID"),
                    resultSet.getString("Title"),
                    resultSet.getString("Type"),
                    resultSet.getString("Description"),
                    resultSet.getString("Date"),
                    resultSet.getString("Start Time"),
                    resultSet.getString("End Time"),
                    resultSet.getInt("Customer_ID")
            ));
        }

        // reportsContact is set into TableView
        contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // reportsContact TableView is displayed
        reportsTV.setItems(reportsContact);
    }

    /**
     * Total number of appts is displayed for each contacts by month.
     *
     * @param actionEvent Action event.
     * @throws SQLException If SQL exception occurred.
     */
    public void reportsCMTotalRBTNOA(javafx.event.ActionEvent actionEvent) throws SQLException {
        statement = conn.createStatement();

        // current tableview is cleared and new table columns are added
        reportsTV.getColumns().clear();
        reportsTV.getItems().clear();
        reportsTV.getColumns().addAll(contactName, month, appTotal);

        reportsCMTotal = FXCollections.observableArrayList();

        // SQL query to get the total number of appts for each contacts by month
        String reportsCMTotalQuery = "SELECT Contact_Name, MONTHNAME(Start) AS Month, COUNT(*) AS 'Total Number of Appointments' " +
                "FROM appointments JOIN contacts ON contacts.Contact_ID = appointments.Contact_ID " +
                "GROUP BY Contact_Name, Month;";
        resultSet = statement.executeQuery(reportsCMTotalQuery);

        // results are added to new reportsCMTotal
        while (resultSet.next()) {
            reportsCMTotal.add(new ReportCMTotal(
                    resultSet.getString("Contact_Name"),
                    resultSet.getString("Month"),
                    resultSet.getInt("Total Number of Appointments")
            ));
        }

        //set reportsCMTotal into TableView
        contactName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        month.setCellValueFactory(new PropertyValueFactory<>("month"));
        appTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        //display reportsCMTotal TableView
        reportsTV.setItems(reportsCMTotal);
    }

    /**
     * MainController class is initialized.
     * TableViews are set.
     *
     * @param url location used to resolve relative paths for the root object, or null if location is not known.
     * @param rb Resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // get appointments and customers from DB
            appointments = fetchAppointment(conn);
            customers = fetchCustomer(conn);

            // appointments and customers TableView are set
            setAppointmentTV();
            setCustomerTV();

            // appointments and customers TableView are displayed
            appointmentTV.setItems(appointments);
            customerTV.setItems(customers);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}