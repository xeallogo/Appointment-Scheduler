/*
 * Program: ReportCMTotal.java
 * Author: Alex Gool
 * Description: ReportCMTotal class used to model all appointments for each contact by month.
 */

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class models all appointments for each contact by month.
 *
 * @author Alex Gool
 */
public class ReportCMTotal {
    //variables are instantiated
    private SimpleStringProperty contactName, month;
    private SimpleIntegerProperty total;

    //default constructor
    public ReportCMTotal() {
    }

    /**
     * Report is created containing all appointments by month for each contact.
     *
     * @param contactName The contact's name.
     * @param month The month.
     * @param total All appointments.
     */
    public ReportCMTotal(String contactName, String month, int total) {
        this.contactName = new SimpleStringProperty(contactName);
        this.month = new SimpleStringProperty(month);
        this.total = new SimpleIntegerProperty(total);
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
     * Set month.
     * @param month Month as a string.
     */
    public void setMonth(String month) {
        this.month = new SimpleStringProperty(month);
    }
    /**
     * Get month.
     * @return Month as a string.
     */
    public String getMonth() {
        return month.get();
    }

    /**
     * Set all appointments.
     * @param total All appointments as an integer.
     */
    public void setTotal(int total) {
        this.total = new SimpleIntegerProperty(total);
    }
    /**
     * Get all appointments.
     * @return All appointments as an integer.
     */
    public int getTotal() {
        return total.get();
    }
}