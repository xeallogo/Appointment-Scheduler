/*
 * Program: ReportContact.java
 * Author: Alex Gool
 * Description: ReportContact class used to model a contact's schedule.
 */

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class models a contact's schedule.
 *
 * @author Alex Gool
 */
public class ReportContact {
    //variables are instantiated
    private SimpleStringProperty contactName, title, type, description, date, startTime, endTime;
    private SimpleIntegerProperty appointmentID, customerID;

    //default constructor
    public ReportContact() {
    }

    /**
     * Contact's schedule is created.
     *
     * @param contactName The contact's name.
     * @param appointmentID The appointment's ID.
     * @param title The appointment's title.
     * @param type The appointment's type.
     * @param description The appointment's description.
     * @param date The appointment's date.
     * @param startTime The appointment's start time.
     * @param endTime The appointment's end time.
     * @param customerID The customer's ID.
     */
    public ReportContact(String contactName, int appointmentID, String title, String type, String description,
                          String date, String startTime, String endTime, int customerID) {
        this.contactName = new SimpleStringProperty(contactName);
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.date = new SimpleStringProperty(date);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
        this.customerID = new SimpleIntegerProperty(customerID);
    }

    /* --------------------- Setters and getters --------------------- */

    /**
     * Set contact's name.
     * @param contactName Contact's name as a string.
     */
    public void setContactName(String contactName) {
        this.contactName = new SimpleStringProperty(contactName);
    }
    /**
     * Get contact's name.
     * @return Contact's name as a string.
     */
    public String getContactName() {
        return contactName.get();
    }

    /**
     * Set appointment's ID.
     * @param appointmentID Appointment's ID as an integer.
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
    }
    /**
     * Get appointment's ID.
     * @return Appointment's ID as an integer.
     */
    public int getAppointmentID() {
        return appointmentID.get();
    }

    /**
     * Set appointment's title.
     * @param title Appointment's title as a string.
     */
    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }
    /**
     * Get appointment's title.
     * @return Appointment's title as a string.
     */
    public String getTitle() {
        return title.get();
    }

    /**
     * Set appointment's type.
     * @param type Appointment's type as a string.
     */
    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }
    /**
     * Get appointment's type.
     * @return Appointment's type as a string.
     */
    public String getType() {
        return type.get();
    }

    /**
     * Set appointment's description.
     * @param description Appointment's description as a string.
     */
    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }
    /**
     * Get appointment's description.
     * @return Appointment's description as a string.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Set appointment's date.
     * @param date Appointment's date as a string.
     */
    public void setDate(String date) {
        this.date = new SimpleStringProperty(date);
    }
    /**
     * Get appointment's date.
     * @return Appointment's date as a string.
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Set appointment's start time.
     * @param startTime Appointment's start time as a string.
     */
    public void setStartTime(String startTime) {
        this.startTime = new SimpleStringProperty(startTime);
    }
    /**
     * Get appointment's start time.
     * @return Appointment's start time as a string.
     */
    public String getStartTime() {
        return startTime.get();
    }

    /**
     * Set appointment's end time.
     * @param endTime Appointment's end time as a string.
     */
    public void setEndTime(String endTime) {
        this.endTime = new SimpleStringProperty(endTime);
    }
    /**
     * Get appointment's end time.
     * @return Appointment's end time as a string.
     */
    public String getEndTime() {
        return endTime.get();
    }

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
}