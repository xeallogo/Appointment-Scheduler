# Appointment-Scheduler
-------------------------------- General Info -----------------------------------

Title:
Scheduling Application

Purpose:
This is an application for scheduling appointments. It is used to store users, customers and appointment records in a MySQL database. This application will allow users to create and modify customer records and appointment records. The app will also display notifications of upcoming appointments after the user logs in, and allow users to view appointments in different ways and generate reports based on customers, contacts, and appointment records.

Author:
Alexander Gool

Contact Information:
agool1@wgu.edu

Student Application Version:
QAM2

Date:
12/06/2021

-------------------------------- Requirements -----------------------------------

IDE Version:
IntelliJ Community 2021.1.3

JDK Version:
Java SE 11.0.12

MySQL Connector Driver Version:
mysql-connector-java-8.0.23

JavaFX Version:
JavaFX-SDK-11.0.2

-------------------------------- Running the Program -----------------------------------

Listed above are the requirements necessary to run the application.
The instructions for running this program are based on the IntelliJ Community IDE.

A JavaFX SDK and a MySQL Connector Driver are needed to run the application. Most likely the Driver and SDK should automatically be in your external library, but if they are not you will need to add them manually.

Adding to External Library:
- Go to: 'Project Structure' (COMMAND + ;) > 'Libraries'
- Click on '+' to add Java SDK
- Click 'Java'
- Go to your java-sdk/lib directory and click 'OK'
    example: /Users/alexgool/Desktop/WGU/C195/openjfx-11.0.2_windows-x64_bin-sdk/javafx-sdk-11.0.2/lib
- Click on '+' to add MYSQL Connector Driver
- Click 'Java'
- Navigate to your mysql-connector-java.jar file and click 'OK'
    example: /Users/alexgool/.m2/repository/mysql/mysql-connector-java/8.0.23/mysql-connector-java-8.0.23.jar

Your project must also have a path pointing to your JavaFX SDK set.

Adding path for JavaFX:
- Go to 'Run' > 'Edit Configurations...'
- Click 'Modify Options' > 'Add VM Options'
- Under VM Options, add the path to your JavaFX SDK lib:
    --module-path "<path>\javafx-sdk-11.0.2\lib" --add-modules javafx.fxml,javafx.controls,javafx.graphics
        ex: --module-path "C:\Users\LabUser\Desktop\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib" --add-modules javafx.fxml,javafx.controls,javafx.graphics
- Click 'OK'

Click the green triangle to run the application. Ensure the file being run is 'Main.java'

There are 2 ways to log in to the Scheduling Application:
username: test
password: test

username: admin
password: admin

It does not matter which one you use, but the application tracks which type of login in login_Activity.txt

-------------------------------- Reports Description -----------------------------------

ReportMonth
    Tracks the total number of appointments for each month by type, including the month, appointment type, and the total number of appointment types by month.

ReportContact
    Displays all contacts' schedules including the contact's name, appointment ID, title, type, description, date, start and end time, and customer ID.

ReportCMTotal
    Tracks the number of appointments for each contact by month, including the contact's name, the month, and the total number for appointments the contact may have for each month if applicable.

-------------------------------- Lambda Expressions -----------------------------------

In the Formatter.java class in directory 'Resources'.
Line 31 - numberValidationFormatter
Line 52 - hourTimeFilter
Line 66 - minuteTimeFilter

In the MainController.java class in directory 'Controller'.
Line 460 - deleteAppointmentBTNOA
Line 526 - deleteCustomerBTNOA
