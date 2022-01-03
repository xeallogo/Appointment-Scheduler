/*
 * Program: ModifyCustomerController.java
 * Author: Alexander Gool
 * Description: ModifyCustomerController class used to control Modify Customer screen
 */

package Controller;

import Model.Customer;
import Model.Login;
import Resources.ChangeScene;
import Resources.List;
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
import java.util.ResourceBundle;

import static Resources.Formatter.numberValidationFormatter;

/**
 * This class controls Modify Customer screen.
 *
 * @author Alexander Gool
 */
public class ModifyCustomerController implements Initializable {
    //references to view
    @FXML
    private TextField nameTF, addressTF, postalTF, phoneTF;
    @FXML
    private ComboBox stPvCBB, countryCBB;
    @FXML
    private Button cancelBTN, updateBTN;
    @FXML
    private Label headerLBL, customerIDLBL, customerDBLBL, nameLBL,
            addressLBL, stPvLBL, countryLBL, postalLBL, phoneLBL, errorLBL;

    // variables are instantiated
    private Customer customer;
    private String countryValue;
    String phoneNumber;
    String phoneNumberFormat = "";
    int divisionIDvalue = 0;
    String uservalue = "";

    //Below are the statements for DB connection.
    private Connection conn = DatabaseConnection.openConnection();
    private Statement statement;
    ResultSet resultSet = null;

    /**
     * State/province spinner is set based on country spinner selection.
     * cityCCB is set once user selects countryCCB.
     *
     * @param actionEvent Action event.
     */
    public void countryOA(javafx.event.ActionEvent actionEvent) {
        //if US is selected country, set the stPvCB to US cities and the label to state
        if (countryCBB.getValue().equals("U.S")) {
            stPvCBB.setItems(List.usCities);
            stPvLBL.setText("State");
        }
        //if UK is selected country, set the stPvCB to uk cities and the label to state
        if (countryCBB.getValue().equals("UK")) {
            stPvCBB.setItems(List.ukCities);
            stPvLBL.setText("State");
        }
        //if Canada is selected country, set the stPvCB to Canada cities and the label to province
        if (countryCBB.getValue().equals("Canada")) {
            stPvCBB.setItems(List.canadaProvinces);
            stPvLBL.setText("Province");
        }
    }

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
     * Customer is updated to DB and ChangeScene is called to load and display Main screen.
     *
     * @param actionEvent Action event.
     * @throws IOException If an I/O exception occurred.
     * @throws SQLException If SQL exception occurred.
     */
    public void updateBTNOA(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
        statement = conn.createStatement();

        //input values are checked for being empty/null, an error message is returned
        if (nameTF.getText().equals("") || addressTF.getText().equals("") || stPvCBB.getValue() == null ||
                countryCBB.getValue() == null || postalTF.getText().equals("") || phoneTF.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing fields");
            alert.setContentText("All fields must be filled");

            alert.showAndWait();
        }
        //checking if country is UK and phone.length is not 12, an error message is returned
        else if (countryCBB.getValue() == "UK" && phoneTF.getText().length() != 12) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Phone Length");
            alert.setContentText("UK phone numbers contain 12 digits" +
                    "\nCurrent number of digits: " + phoneTF.getText().length() +
                    "\nCurrent Input: " + phoneTF.getText());

            alert.showAndWait();
        }
        //checking if country is not UK and phone.length is not 10, an error message is returned
        else if (countryCBB.getValue() != "UK" && phoneTF.getText().length() != 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Phone Length");
            alert.setContentText("US and Canadian phone numbers contain 10 digits" +
                    "\nCurrent number of digits: " + phoneTF.getText().length() +
                    "\nCurrent Input: " + phoneTF.getText());

            alert.showAndWait();
        } else {
            // phone number is formatted for DB insert
            if (countryCBB.getValue() == "UK") {
                phoneNumberFormat = phoneTF.getText().replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3-$4");
            } else {
                phoneNumberFormat = phoneTF.getText().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
            }

            //This is a SQL query for changing countryCCB to Division_ID
            String divisionIDQuery = "SELECT Division_ID from first_level_divisions " +
                    "WHERE Division='" + stPvCBB.getValue() + "';";
            resultSet = statement.executeQuery(divisionIDQuery);
            while (resultSet.next()) {
                divisionIDvalue = resultSet.getInt("Division_ID");
            }

            //get user login name
            uservalue = Login.getUsername();

            //This is the SQL insert statement
            String SQLUpdate = "UPDATE customers SET " +
                    "Customer_Name = '" + nameTF.getText() + "', " +
                    "Address = '" + addressTF.getText() + "', " +
                    "Postal_Code = '" + postalTF.getText() + "', " +
                    "Phone = '" + phoneNumberFormat + "', " +
                    "Last_Update = NOW(), " +
                    "Last_Updated_By = '" + uservalue + "', " +
                    "Division_ID = '" + divisionIDvalue + "' " +
                    "WHERE Customer_ID = '" + customerDBLBL.getText() + "';";

            statement.executeUpdate(SQLUpdate);

            ChangeScene.getMainScene(actionEvent);
        }
    }

    /**
     * Selected customer values are set.
     *
     * @param value The selected customer
     * @throws SQLException If SQL exception occurred.
     */
    public void setCustomer(Customer value) throws SQLException {
        // selected value is stored into customer
        customer = value;

        statement = conn.createStatement();

        //This is a SQL query to get country from country ID
        String countryQuery = "SELECT Country from countries " +
                "WHERE Country_ID='" + customer.getCountryID() + "';";
        resultSet = statement.executeQuery(countryQuery);

        while (resultSet.next()) {
            countryValue = resultSet.getString("Country");
        }

        //if country is US, set the stPvCB to US cities and the label to state
        if (countryValue.equals("U.S")) {
            stPvCBB.setItems(List.usCities);
            stPvLBL.setText("State");
        }
        //if country is UK, set the stPvCB to uk cities and the label to state
        if (countryValue.equals("UK")) {
            stPvCBB.setItems(List.ukCities);
            stPvLBL.setText("State");
        }
        //if country is Canada, set the stPvCB to Canada cities and the label to province
        if (countryValue.equals("Canada")) {
            stPvCBB.setItems(List.canadaProvinces);
            stPvLBL.setText("Province");
        }

        //remove all phone number dashes
        phoneNumber = customer.getPhone().replaceAll("-", "");

        //all selected database variables are set in textfields, label, and comboboxes
        customerDBLBL.setText(String.valueOf(customer.getCustomerID()));
        nameTF.setText(customer.getCustomerName());
        addressTF.setText(customer.getAddress());
        stPvCBB.setValue(customer.getDivision());
        countryCBB.setValue(countryValue);
        postalTF.setText(customer.getPostal());
        phoneTF.setText(phoneNumber);
    }

    /**
     * ModifyCustomerController class is initialized.
     * Sets all combobox variables, auto-incrementing appointment ID, and textformatter for spinners.
     *
     * @param url Location used to resolve relative paths for the root object or null if location is not known.
     * @param rb Resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set countryCB with country array list
        countryCBB.setItems(List.countries);

        //phone formatter is set
        phoneTF.setTextFormatter(new TextFormatter<Object>(numberValidationFormatter));
    }
}