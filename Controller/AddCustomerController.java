/*
 * Program: AddCustomerController.java
 * Author: Alexander Gool
 * Description: Class used to control Add Customer screen
 */


package Controller;

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
 * This class controls Add Customer screen.
 *
 * @author Alexander Gool
 */
public class AddCustomerController implements Initializable {
    //references to view
    @FXML
    private TextField nameTF, addressTF, postalTF, phoneTF;
    @FXML
    private ComboBox stPvCB, countryCB;
    @FXML
    private Button addBTN, cancelBTN;
    @FXML
    private Label headerLBL, customerIDLBL, customerDBLBL, nameLBL,
            addressLBL, stPvLBL, countryLBL, postalLBL, phoneLBL, errorLBL;

    //variables are instantiated
    String autoIncrementIDvalue = "";
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
        // if country is US, set the stPvCB to US cities and the label to state
        if (countryCB.getValue().equals("U.S")) {
            stPvCB.setItems(List.usCities);
            stPvLBL.setText("State");
        }
        //if UK is selected country, set the stPvCB to uk cities and the label to state
        if (countryCB.getValue().equals("UK")) {
            stPvCB.setItems(List.ukCities);
            stPvLBL.setText("State");
        }
        //if Canada is selected country, set the stPvCB to Canada cities and the label to province
        if (countryCB.getValue().equals("Canada")) {
            stPvCB.setItems(List.canadaProvinces);
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
     * Adds new customer to DB and ChangeScene is called to load and display Main screen.
     *
     * @param actionEvent Action event.
     * @throws IOException If an I/O exception occurred.
     * @throws SQLException If SQL exception occurred.
     */
    public void addBTNOA(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        statement = conn.createStatement();

        // checking if the values are null/empty, an error message is returned
        if (nameTF.getText().equals("") || addressTF.getText().equals("") || stPvCB.getValue() == null ||
                countryCB.getValue() == null || postalTF.getText().equals("") || phoneTF.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing fields");
            alert.setContentText("All fields must be filled");

            alert.showAndWait();
        }
        // checking if country value is UK and phone.length is not 12, an error message is returned
        else if (countryCB.getValue() == "UK" && phoneTF.getText().length() != 12) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect number of digits");
            alert.setContentText("UK phone numbers contain 12 digits" +
                    "\nCurrent number of digits: " + phoneTF.getText().length() +
                    "\nPhone number Input: " + phoneTF.getText());

            alert.showAndWait();
        }
        // checking if country value is not UK and phone.length is not 10, an error message is returned
        else if (countryCB.getValue() != "UK" && phoneTF.getText().length() != 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Incorrect number of phone digits");
            alert.setContentText("US and Canadian phone numbers contain 10 digits" +
                    "\nCurrent number of digits: " + phoneTF.getText().length() +
                    "\nPhone number input: " + phoneTF.getText());

            alert.showAndWait();
        } else {
            // formatting phone for DB insert
            if (countryCB.getValue() == "UK") {
                phoneNumberFormat = phoneTF.getText().replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3-$4");
            } else {
                phoneNumberFormat = phoneTF.getText().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
            }

            // SQL query for changing countryCCB to Division_ID
            String divisionIDQuery = "SELECT Division_ID from first_level_divisions " +
                    "WHERE Division='" + stPvCB.getValue() + "';";
            resultSet = statement.executeQuery(divisionIDQuery);
            while (resultSet.next()) {
                divisionIDvalue = resultSet.getInt("Division_ID");
            }

            // get user login name
            uservalue = Login.getUsername();

            // This is the SQL insert statement
            String SQLInsert = "INSERT INTO customers (Customer_Name, Address, Postal_Code, " +
                    "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                    "VALUES('" + nameTF.getText() + "', " +
                    "'" + addressTF.getText() + "', " +
                    "'" + postalTF.getText() + "', " +
                    "'" + phoneNumberFormat + "', " +
                    "NOW(), " +
                    "'" + uservalue + "', " +
                    "NOW(), " +
                    "'" + uservalue + "', " +
                    "'" + divisionIDvalue + "')";

            statement.executeUpdate(SQLInsert);

            ChangeScene.getMainScene(actionEvent);
        }
    }

    /**
     * AddCustomerController class is initialized.
     * Sets all combobox variables, auto incrementing appointment ID, and textformatter for spinners.
     *
     * @param url Location used to resolve relative paths for the root object, or null if location is not known.
     * @param rb Resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            statement = conn.createStatement();

            // SQL query to get next autoIncrement id
            String autoIncrementQuery = "SELECT AUTO_INCREMENT " +
                    "FROM information_schema.TABLES " +
                    "WHERE (TABLE_NAME = 'customers')";
            resultSet = statement.executeQuery(autoIncrementQuery);
            while (resultSet.next()) {
                autoIncrementIDvalue = resultSet.getString("AUTO_INCREMENT");
            }

            // set customerDBLBL to autoIncrementIDvalue
            customerDBLBL.setText(autoIncrementIDvalue);

            // set countryCB with country array list 
            countryCB.setItems(List.countries);

            // phone formatter is set
            phoneTF.setTextFormatter(new TextFormatter<Object>(numberValidationFormatter));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}