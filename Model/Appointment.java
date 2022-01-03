/*
 * Program: Appointment.java
 * Author: Alex Gool
 * Description: Class used to model an appointment object.
 */

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * This class models an appointment.
 *
 * @author Alex Gool
 */
public class Appointment {

    // variables are instantiated
    private SimpleIntegerProperty appointmentID, customerID, userID, contactID;
    private SimpleStringProperty title, description, location, type, createdBy, lastUpdatedBy;
    private SimpleStringProperty contactName;
    private LocalDateTime start, end, createDate;
    private Timestamp lastUpdate;

    //default constructor
    public Appointment(){}

    /**
     * Creates an appointment.
     *
     * @param appointmentID Appointment's ID.
     * @param title Appointment's title.
     * @param description Appointment's description.
     * @param location Appointment's location.
     * @param type Appointment's type.
     * @param start Appointment's start date and time.
     * @param end Appointment's end date and time.
     * @param createDate Appointment's creation date and time.
     * @param createdBy User who created the appointment.
     * @param lastUpdate Date and time of when the appointment was last updated.
     * @param lastUpdatedBy User who last updated the appointment.
     * @param customerID Customer's ID.
     * @param userID User's ID.
     * @param contactID Contact's ID.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, Timestamp lastUpdate,
                       String lastUpdatedBy, int customerID, int userID, int contactID){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contactID = new SimpleIntegerProperty(contactID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    /**
     * Creates an appointment.
     *
     * @param appointmentID Appointment's ID.
     * @param title Appointment's title.
     * @param description Appointment's description.
     * @param location Appointment's location.
     * @param type Appointment's type.
     * @param start Appointment's start date and time.
     * @param end Appointment's end date and time.
     * @param createDate Appointment's creation date and time.
     * @param createdBy User who created the appointment.
     * @param lastUpdate Date and time of when the appointment was last updated.
     * @param lastUpdatedBy User who last updated the appointment.
     * @param customerID Customer's ID.
     * @param userID User's ID.
     * @param contactID Contact's ID.
     * @param contactName Contact's name.
     */
    public Appointment(int appointmentID, String title, String description, String location, String type,
                       LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, Timestamp lastUpdate,
                       String lastUpdatedBy, int customerID, int userID, int contactID, String contactName){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.userID = new SimpleIntegerProperty(userID);
        this.contactID = new SimpleIntegerProperty(contactID);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.contactName = new SimpleStringProperty(contactName);
    }

    /* --------------------- Setters and getters --------------------- */

    /**
     * Set appointment's ID.
     * @param appointmentID Appointment's ID as an integer.
     */
    public void setAppointmentID(int appointmentID){
        this.appointmentID = new SimpleIntegerProperty(appointmentID);
    }
    /**
     * Get appointment's ID.
     * @return Appointment's ID as an integer.
     */
    public int getAppointmentID(){
        return appointmentID.get();
    }

    /**
     * Set customer's ID.
     * @param customerID Customer's ID as an integer.
     */
    public void setCustomerID(int customerID){
        this.customerID = new SimpleIntegerProperty(customerID);
    }
    /**
     * Get customer's ID.
     * @return Customer's ID as an integer.
     */
    public int getCustomerID(){
        return customerID.get();
    }

    /**
     * Set user's ID.
     * @param userID User's ID as an integer.
     */
    public void setUserID(int userID){
        this.userID = new SimpleIntegerProperty(userID);
    }
    /**
     * Get user's ID.
     * @return User's ID as an integer.
     */
    public int getUserID(){
        return userID.get();
    }

    /**
     * Set contact's ID.
     * @param contactID Contact's ID as an integer.
     */
    public void setContactID(int contactID){
        this.contactID = new SimpleIntegerProperty(contactID);
    }
    /**
     * Get contact's ID.
     * @return Contact's ID as an integer.
     */
    public int getContactID(){
        return contactID.get();
    }

    /**
     * Set appointment's title.
     * @param title Appointment's title as a string.
     */
    public void setTitle(String title){
        this.title = new SimpleStringProperty(title);
    }
    /**
     * Get appointment's title.
     * @return Appointment's title as a string.
     */
    public String getTitle(){
        return title.get();
    }

    /**
     * Set appointment's description.
     * @param description Appointment's description as a string.
     */
    public void setDescription(String description){
        this.description = new SimpleStringProperty(description);
    }
    /**
     * Get appointment's description.
     * @return Appointment's description as a string.
     */
    public String getDescription(){
        return description.get();
    }

    /**
     * Set appointment's location.
     * @param location Appointment's location as a string.
     */
    public void setLocation(String location){
        this.location = new SimpleStringProperty(location);
    }
    /**
     * Get appointment's location.
     * @return Appointment's location as a string.
     */
    public String getLocation(){
        return location.get();
    }

    /**
     * Set appointment's type.
     * @param type Appointment's type as a string.
     */
    public void setType(String type){
        this.type = new SimpleStringProperty(type);
    }
    /**
     * Get appointment's type.
     * @return Appointment's type as a string.
     */
    public String getType(){
        return type.get();
    }

    /**
     * Set user who created the appointment.
     * @param createdBy User who created the appointment as a string.
     */
    public void setCreatedBy(String createdBy){
        this.createdBy = new SimpleStringProperty(createdBy);
    }
    /**
     * Get user who created the appointment.
     * @return User who created the appointment as a string.
     */
    public String getCreatedBy(){
        return createdBy.get();
    }

    /**
     * Set user who last updated the appointment.
     * @param lastUpdatedBy User who last updated the appointment as a string.
     */
    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
    }
    /**
     * Get user who last updated the appointment.
     * @return User who last updated the appointment as a string.
     */
    public String getLastUpdatedBy(){
        return lastUpdatedBy.get();
    }

    /**
     * Set appointment's start date and time.
     * @param start Appointment's start date and time as a localdatetime.
     */
    public void setStart(LocalDateTime start){
        this.start = start;
    }
    /**
     * Get appointment's start date and time.
     * @return Appointment's start date and time as a localdatetime.
     */
    public LocalDateTime getStart(){
        return start;
    }

    /**
     * Set appointment's end date and time.
     * @param end Appointment's end date and time as a localdatetime.
     */
    public void setEnd(LocalDateTime end){
        this.end = end;
    }
    /**
     * Get appointment's end date and time.
     * @return Appointment's end date and time as a localdatetime.
     */
    public LocalDateTime getEnd(){
        return end;
    }

    /**
     * Set date and time of when the appointment was created.
     * @param createDate Appointment's creation date and time as a localdatetime.
     */
    public void setCreateDate(LocalDateTime createDate){
        this.createDate = createDate;
    }
    /**
     * Get date and time of when the appointment was created.
     * @return Appointment's creation date and time as a localdatetime.
     */
    public LocalDateTime getCreateDate(){
        return createDate;
    }

    /**
     * Set date and time of when the appointment was last updated.
     * @param lastUpdate Appointment's last updated time as a timestamp.
     */
    public void setLastUpdate(Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }
    /**
     * Get date and time of when the appointment was last updated.
     * @return Appointment's last updated time as a timestamp.
     */
    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     * Set contact's name.
     * @param contactName Contact's name as a string.
     */
    public void setContactName(String contactName){
        this.contactName = new SimpleStringProperty(contactName);
    }
    /**
     * Get contact's name.
     * @return Contact's name as a string.
     */
    public String getContactName(){
        return contactName.get();
    }
}