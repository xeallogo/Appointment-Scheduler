/*
 * Program: List.java
 * Author: Alex Gool
 * Description: List class for storing ObservableLists.
 */

package Resources;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.DecimalFormat;

/**
 * This class is used for storing all ObservableList.
 *
 * @author Alex Gool
 */
public class List {

    /** Country List. */
    public static ObservableList<String> countries = FXCollections.observableArrayList(
            "U.S", "UK", "Canada");

    /** US city List. */
    public static ObservableList<String> usCities = FXCollections.observableArrayList(
            "Alabama", "Arizona", "Arkansas", "California", "Colorado",
            "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia",
            "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
            "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey",
            "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
            "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
            "Washington", "West Virginia", "Wisconsin", "Wyoming", "Hawaii", "Alaska");

    /** UK city List. */
    public static ObservableList<String> ukCities = FXCollections.observableArrayList(
            "England", "Wales", "Scotland", "Northern Ireland");

    /** Canada province List. */
    public static ObservableList<String> canadaProvinces = FXCollections.observableArrayList(
            "Northwest Territories", "Alberta", "British Columbia",
            "Manitoba", "New Brunswick", "Nova Scotia", "Prince Edward Island",
            "Ontario", "Qu├®bec", "Saskatchewan", "Nunavut", "Yukon",
            "Newfoundland and Labrador");

    /** Appointment type List. */
    public static ObservableList<String> types = FXCollections.observableArrayList(
            "Planning Session", "De-Briefing");

    /** Hours List. */
    public static ObservableList<String> hours = FXCollections.observableArrayList(hours());

    /** Minutes List. */
    public static ObservableList<String> minutes = FXCollections.observableArrayList(minutes());

    /**
     * ObservableList of hours.d is created
     *
     * @return String containing hours listed 0 to 23.
     */
    public static ObservableList<String> hours(){
        DecimalFormat df = new DecimalFormat("00");
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i=0; i <24; i++){
            list.add(df.format(i));
        }
        return list;
    }

    /**
     * ObservableList of minutes is created
     *
     * @return String containing minutes listed 0 to 59.
     */
    public static ObservableList<String> minutes(){
        DecimalFormat df = new DecimalFormat("00");
        ObservableList<String> list = FXCollections.observableArrayList();
        for(int i=0; i <60; i++){
            list.add(df.format(i));
        }
        return list;
    }
}