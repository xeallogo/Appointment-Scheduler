/*
 * Program: Formatter.java
 * Author: Alex Gool
 * Description: Class used for formatting text and numbers.
 */

package Resources;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextFormatter;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

/**
 * This class is used for formatting.
 *
 * @author Alex Gool
 */
public class Formatter {

    /**
     * TextField is formatted and filtered.
     * Lambda expression used to only allow numbers.
     */
    public static UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
        if (change.getText().matches("\\d{0,11}")) {
            //if change is number, return number
            return change;
        } else {
            //if change is not number, set text to empty string
            change.setText("");
            //remove selected text
            change.setRange(
                    change.getRangeStart(),
                    change.getRangeStart()
            );
            //return nothing
            return change;
        }
    };

    /**
     * Filter and format spinner.
     * Lambda expression used to only permit 2 digit numbers up to 23.
     */
    public static UnaryOperator<TextFormatter.Change> hourTimeFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d{0,2}") && Integer.parseInt(newText) < 24) {
            //if change is a number, return number
            return change;
        }
        //if change is not a number, return nothing
        return null;
    };

    /**
     * Spinner is filtered and formatted.
     * Lambda expression used to only permit 2 digit numbers up to 59.
     */
    public static UnaryOperator<TextFormatter.Change> minuteTimeFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d{0,2}") && Integer.parseInt(newText) < 60) {
            //if change is number, return number
            return change;
        }
        //if change is not number, return nothing
        return null;
    };

    /**
     * Time is formatted to specified pattern "yyyy-MM-dd HH:mm:ss".
     *
     * @param zonedDateTime A ZonedDateTime containing the time.
     * @return Formatted string.
     */
    public static String dateTimeFormatter(ZonedDateTime zonedDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(zonedDateTime);
    }

    /**
     * Time is formatted to 2 digit pattern "00".
     *
     * @param time The time as an integer.
     * @return Formatted string.
     */
    public static String timeFormat(int time){
        DecimalFormat df = new DecimalFormat("00");
        return df.format(time);
    }

    /** Within this class, dates in Tableview column are displayed as specified pattern "yyyy-MM-dd HH:mm:ss". */
    public static class DateColumnFormatter<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Override
        public TableCell<S, T> call(TableColumn<S, T> arg0) {
            return new TableCell<S, T>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);

                    //if there aren't items in the tablecell, set text to null
                    if (item == null || empty) {
                        setText(null);
                    }
                    //else, format the date
                    else {
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime ldt = (LocalDateTime) item;
                        String val = ldt.format(dateTimeFormatter);
                        setText(val);
                    }
                }
            };
        }
    }
}