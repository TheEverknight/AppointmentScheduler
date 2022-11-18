/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentscheduler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * this class display all the appointment details in table format
 *
 * @author
 */
public class ShowAppointmentRecords extends Stage {

    /**
     * Constructor of the frame....
     */
    ShowAppointmentRecords() {
        // TableView representing the table in form
        TableView tableFormat = new TableView();
      

        // defining the column for the table view.....
        TableColumn<Customer, String> AppointmentID = new TableColumn<>("Appointment_ID");
        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Customer, String> Appointmenttitle = new TableColumn<>("Title");
        Appointmenttitle.setCellValueFactory(new PropertyValueFactory<>("Title"));

        TableColumn<Customer, String> AppointmentDescription = new TableColumn<>("Description");
        AppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));

        TableColumn<Customer, String> Location = new TableColumn<>("Location");
        Location.setCellValueFactory(new PropertyValueFactory<>("AppLocation"));

        TableColumn<Customer, String> AppointmentContact = new TableColumn<>("Contact");
        AppointmentContact.setCellValueFactory(new PropertyValueFactory<>("appContact"));

        TableColumn<Customer, String> ApointmentType = new TableColumn<>("Type");
        ApointmentType.setCellValueFactory(new PropertyValueFactory<>("appType"));

        TableColumn<Customer, String> AppointmentStartdate = new TableColumn<>("Start date & time");
        AppointmentStartdate.setCellValueFactory(new PropertyValueFactory<>("startDataAndTime"));

        TableColumn<Customer, String> EndDate = new TableColumn<>("End date & time");
        EndDate.setCellValueFactory(new PropertyValueFactory<>("AppEndDate"));

        TableColumn<Customer, String> CustomerID = new TableColumn<>("Customer_Id");
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        // adding above columns in the table format
        tableFormat.getColumns().add(AppointmentID);
        tableFormat.getColumns().add(Appointmenttitle);
        tableFormat.getColumns().add(AppointmentDescription);
        tableFormat.getColumns().add(Location);
        tableFormat.getColumns().add(AppointmentContact);
        tableFormat.getColumns().add(ApointmentType);
        tableFormat.getColumns().add(AppointmentStartdate);
        tableFormat.getColumns().add(EndDate);
        tableFormat.getColumns().add(CustomerID);

        // Getting all the appointments data from the database using getAllAppointments method
        ArrayList<Appointment> appointmentsList = getAllAppointments();

        // Back button initialization
        Button back = new Button("Back");
        back.setStyle("-fx-background-color: Green; ");
        // defining up action on back button
        back.setOnAction((event) -> {
            // Hiding current form
            this.hide();
            // Appointment menu initailization..
            AppointmentsMenu AppointmentMenu = new AppointmentsMenu();
            AppointmentMenu.show();
        });
        // inserting apointment data into table that are reterieve from the database....
        for (int i = 0; i < appointmentsList.size(); i++) {
            tableFormat.getItems().add(appointmentsList.get(i));
        }

        VBox box = new VBox();
        box.getChildren().addAll(tableFormat, back);
        box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(box, 500, 500);
        scene.setFill(Color.web("#81c483"));
        this.setScene(scene);
        this.setTitle("View appointments");
        this.show();
        
        this.setResizable(false);

    }

    /**
     * this method fetch all the appointment records from the database...
     *
     * @return array list of appointments
     */
    public ArrayList<Appointment> getAllAppointments() {
        ArrayList<Appointment> AllAppointments = null;
        try {
            AllAppointments = new ArrayList<>();
            Connection connection = DatabaseConnection.CreateConnection();
            Statement statment = connection.createStatement();
            Statement statment1 = connection.createStatement();
            Statement statment2 = connection.createStatement();
            ResultSet resultSet = statment.executeQuery("select * from appointments");

            while (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setAppID(resultSet.getInt("Appointment_ID"));
                Appointment.setAppContact(resultSet.getString("Contact_ID"));
                Appointment.setAppointmentDescription(resultSet.getString("description"));
                Appointment.setTitle(resultSet.getString("title"));
                Appointment.setAppType(resultSet.getString("type"));
                Appointment.setAppLocation(resultSet.getString("location"));
                Appointment.setStartDataAndTime(TimeConversion.fromUtcToLocalzone(resultSet.getString("start")));
                Appointment.setAppEndDate(TimeConversion.fromUtcToLocalzone(resultSet.getString("end")));
                Appointment.setCustomerID(resultSet.getInt("Customer_id"));
                Appointment.setUserID(resultSet.getInt("User_id"));
                ResultSet resultset2 = statment1.executeQuery("select Contact_Name from contacts where Contact_ID="+resultSet.getInt("Contact_ID"));
                resultset2.next();
                Appointment.setAppContact(resultset2.getString("Contact_Name"));
                AllAppointments.add(Appointment);
            }
        } catch (SQLException ex) {
            return AllAppointments;
        }
        return AllAppointments;
    }

}
