/*
 * Program: Customer.java
 * Author: Alex Gool
 * Description: Class used to model a customer object.
 */

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class models a customer.
 *
 * @author Alex Gool
 */
public class Customer {
    // variables are instantiated
    private SimpleIntegerProperty customerID, divisionID, countryID;
    private SimpleStringProperty customerName, address, postal, phone, createdBy, lastUpdatedBy;
    private SimpleStringProperty division;
    private LocalDateTime createDate;
    private Timestamp lastUpdate;

    //default constructor
    public Customer() {
    }

    /**
     * Customer is created.
     *
     * @param customerID Customer's ID.
     * @param customerName Customer's name.
     * @param address Customer's address.
     * @param postal Customer's postal code.
     * @param phone Customer's phone number
     * @param createDate Customer's creation date and time.
     * @param createdBy User who created the customer.
     * @param lastUpdate User who last updated the customer.
     * @param lastUpdatedBy User who last updated the customer.
     * @param divisionID Division's ID.
     * @param division Division's name.
     * @param countryID Country's ID.
     */
    public Customer(int customerID, String customerName, String address, String postal,
                    String phone, LocalDateTime createDate, String createdBy, Timestamp lastUpdate,
                    String lastUpdatedBy, int divisionID, String division, int countryID) {
        this.customerID = new SimpleIntegerProperty(customerID);
        this.divisionID = new SimpleIntegerProperty(divisionID);
        this.customerName = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.postal = new SimpleStringProperty(postal);
        this.phone = new SimpleStringProperty(phone);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.division = new SimpleStringProperty(division);
        this.countryID = new SimpleIntegerProperty(countryID);
    }

    /* --------------------- Setters and getters --------------------- */

    /**
     * Set customer's ID.
     * @param customerID Customer's ID as an integer.
     */
    public void setCustomerID(int customerID) {
        this.customerID = new SimpleIntegerProperty(customerID);
    }
    /**
     * Get customer's ID.
     * @return Customer's ID as an integer.
     */
    public int getCustomerID() {
        return customerID.get();
    }

    /**
     * Set division's ID.
     * @param divisionID Division's ID as an integer.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = new SimpleIntegerProperty(divisionID);
    }
    /**
     * Get division's ID.
     * @return  Division's ID as an integer.
     */
    public int getDivisionID() {
        return divisionID.get();
    }

    /**
     * Set customer's name.
     * @param customerName Customer's name as a string.
     */
    public void setCustomerName(String customerName) {
        this.customerName = new SimpleStringProperty(customerName);
    }
    /**
     * Get customer's name.
     * @return  Customer's name as a string.
     */
    public String getCustomerName() {
        return customerName.get();
    }

    /**
     * Set customer's address.
     * @param address Customer's address as a string.
     */
    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }
    /**
     * Get customer's address.
     * @return  Customer's address as a string.
     */
    public String getAddress() {
        return address.get();
    }

    /**
     * Set customer's postal code.
     * @param postal Customer's postal code as a string.
     */
    public void setPostal(String postal) {
        this.postal = new SimpleStringProperty(postal);
    }
    /**
     * Get customer's postal code.
     * @return Customer's postal code as a string.
     */
    public String getPostal() {
        return postal.get();
    }

    /**
     * Set customer's phone number.
     * @param phone Customer's phone number as a string.
     */
    public void setPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }
    /**
     * Get customer's phone number.
     * @return Customer's phone number as a string.
     */
    public String getPhone() {
        return phone.get();
    }

    /**
     * Set user who created the customer.
     * @param createdBy User who created the customer as a string.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = new SimpleStringProperty(createdBy);
    }
    /**
     * Get user who created the customer.
     * @return User who created the customer as a string.
     */
    public String getCreatedBy() {
        return createdBy.get();
    }

    /**
     * Set user who last updated the customer.
     * @param lastUpdatedBy User who last updated the customer as a string.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
    }
    /**
     * Get user who last updated the customer.
     * @return User who last updated the customer as a string.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy.get();
    }

    /**
     * Set date and time of when the customer was created.
     * @param createDate Customer's creation date and time as a localdatetime.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * Get date and time of when the customer was created.
     * @return Customer's creation date and time as a localdatetime.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Set date and time of when the customer was last updated.
     * @param lastUpdate Customer's last updated time as a timestamp.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * Get date and time of when the customer was last updated.
     * @return Customer's last updated time as a timestamp.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Set division's ID.
     * @param division Division's ID as a string.
     */
    public void setDivision(String division) {
        this.division = new SimpleStringProperty(division);
    }
    /**
     * Get division's ID.
     * @return Division's ID as a string.
     */
    public String getDivision() {
        return division.get();
    }

    /**
     * Set country's ID.
     * @param countryID Country's ID as an integer.
     */
    public void setCountryID(int countryID) {
        this.countryID = new SimpleIntegerProperty(countryID);
    }
    /**
     * Get country's ID.
     * @return Country's ID as an integer.
     */
    public int getCountryID() {
        return countryID.get();
    }
}