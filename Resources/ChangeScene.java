/*
 * Program: ChangeScene.java
 * Author: Alex Gool
 * Description: Class used to call different scenes for the application.
 */

package Resources;

import Controller.ModifyAppointmentController;
import Controller.ModifyCustomerController;
import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

/**
 * FXML views are loaded and scenes are changed in this class.
 *
 * @author Alex Gool
 */
public class ChangeScene {
    private static Parent root;
    private static Stage stage;
    private static FXMLLoader loader;

    /**
     * MainView FXML is loaded and displayed.
     * Set new scene MainView. Current stage window is grabbed and closed.
     * New stage is set with new scene and displayed.
     *
     * @param actionEvent Instance of ActionEvent.
     * @throws IOException If an I/O exception occurred.
     */
    public static void getMainScene(ActionEvent actionEvent) throws IOException {
        //load the main screen
        root = FXMLLoader.load(ChangeScene.class.getResource("/View/Main.fxml"));
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //close the current stage and open the new stage
        stage.close();
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * AddAppointmentView FXML is loaded and displayed.
     * Set new scene AddAppointmentView. Current stage window is grabbed and closed.
     * New stage is set with new scene and displayed.
     *
     * @param actionEvent Instance of ActionEvent.
     * @throws IOException If an I/O exception occurred.
     */
    public static void getAddAppointmentScene(javafx.event.ActionEvent actionEvent) throws IOException {
        //load Add Appointment Screen
        root = FXMLLoader.load(ChangeScene.class.getResource("/View/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //close the current stage and open the new stage
        stage.close();
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * ModifyAppointmentView FXML is loaded and displayed.
     * Set new scene ModifyAppointmentView. Current stage window is grabbed and closed.
     * New stage is set with new scene, pass customer model, and displayed.
     *
     * @param actionEvent Instance of ActionEvent.
     * @param appointment Model of appointment object.
     * @throws IOException If an I/O exception occurred.
     * @throws SQLException If SQL exception occurred.
     */
    public static void getModifyAppointmentScene(javafx.event.ActionEvent actionEvent, Appointment appointment) throws IOException, SQLException {
        //load Modify Appointment screen
        loader = new FXMLLoader(ChangeScene.class.getResource("/View/ModifyAppointment.fxml"));
        Parent root = loader.load();

        //Appointment Model is retrieved and stored
        ModifyAppointmentController ModifyAppointmentController = loader.getController();
        ModifyAppointmentController.setAppointment(appointment);

        //change the current stage to new scene
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * AddCustomerView FXML is loaded and displayed.
     * Set new scene AddCustomerView. Current stage window is grabbed and closed.
     * New stage is set with new scene and displayed.
     *
     * @param actionEvent Instance of ActionEvent.
     * @throws IOException If an I/O exception occurred.
     */
    public static void getAddCustomerScene(javafx.event.ActionEvent actionEvent) throws IOException {
        //load Add Customer screen
        root = FXMLLoader.load(ChangeScene.class.getResource("/View/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //close the current stage and open the new stage
        stage.close();
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * ModifyCustomerView FXML is loaded and displayed.
     * Set new scene ModifyCustomerView. Current stage window is grabbed and closed.
     * New stage is set with new scene, pass customer model, and displayed.
     *
     * @param actionEvent Instance of ActionEvent.
     * @param customer Model of customer object.
     * @throws IOException If an I/O exception occurred.
     * @throws SQLException If SQL exception occurred.
     */
    public static void getModifyCustomerScene(javafx.event.ActionEvent actionEvent, Customer customer) throws IOException, SQLException {
        //load Modify Customer screen
        loader = new FXMLLoader(ChangeScene.class.getResource("/View/ModifyCustomer.fxml"));
        Parent root = loader.load();

        //Customer Model is retrieved and stored
        ModifyCustomerController ModifyCustomerController = loader.getController();
        ModifyCustomerController.setCustomer(customer);

        //change the current stage to new scene
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}