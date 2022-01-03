/*
 * Program: ReportMonth.java
 * Author: Alex Gool
 * Description: Class used to model all appointments for each month by type.
 */

package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class models all appointments for each month by type.
 *
 * @author Alex Gool
 */
public class ReportMonth {
    // variables are instantiated
    private SimpleStringProperty month, type;
    private SimpleIntegerProperty total;

    //default constructor
    public ReportMonth(){}

    /**
     * Report of all appointments for each month by type is created.
     *
     * @param month The month.
     * @param type The type of appointment.
     * @param total All appointments.
     */
    public ReportMonth(String month, String type, int total){
        this.month = new SimpleStringProperty(month);
        this.type = new SimpleStringProperty(type);
        this.total = new SimpleIntegerProperty(total);
    }

    /* --------------------- Setters and getters --------------------- */

    /**
     * Sets month.
     * @param month The month as a string.
     */
    public void setMonth(String month) {
        this.month = new SimpleStringProperty(month);
    }
    /**
     * Gets month.
     * @return The month as a string.
     */
    public String getMonth() {
        return  month.get();
    }

    /**
     * Sets appointment type.
     * @param type The type of appointment as a string.
     */
    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }
    /**
     * Get appointment type.
     * @return The type of appointment as a string.
     */
    public String getType() {
        return  type.get();
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
        return  total.get();
    }
}