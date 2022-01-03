/*
 * Program: Main.java
 * Author: Alex Gool
 * Description: Main class used to run the app.
 */

import Utilities.DatabaseConnection;
import Utilities.DatabaseQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class creates app displaying the Login View.
 *
 * @author Alex Gool
 */
public class Main extends Application {

    /**
     * Login View is loaded and displayed.
     * LoginView is set as primary stage.
     *
     * @param primaryStage Primary window of app.
     * @throws IOException If an I/O exception occurred.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/Login.fxml"));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Create connection to database.
     *
     * @param args Command-line arguments.
     * @throws SQLException If SQL exception occurred.
     */
    public static void main(String[] args) throws SQLException {
        //create connection
        Connection conn = DatabaseConnection.openConnection();

        //create statement object
        DatabaseQuery.setStatement(conn);
        launch(args);

        //close connection
        DatabaseConnection.closeConnection();
    }
}