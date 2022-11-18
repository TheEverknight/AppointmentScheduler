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
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * this class provide the menu of all operations that we can perform on the appointment
 *
 * @author
 */
public class AppointmentsMenu extends Stage {

    Optional<String> Result;
    int ID;

    /**
     *Constructor of the class that create different UI element for the current class operation
     */
    AppointmentsMenu() {
        // iniliazation of button..........
        Button AddAppointment = null, updateAppointment = null, deleteAppointment = null, ViewAppointment = null, BackButton = null;

        Scene scene;
        

        // defining above declared button functionalities...
        ViewAppointment = new Button("Check all Appointments");
        ViewAppointment.setMinWidth(200);
        
        ViewAppointment.setFont(new Font("Times New Roman",20));
        ViewAppointment.setStyle("-fx-background-color: Green ");
        ViewAppointment.setTextFill(Color.WHITE);
        
        
        AddAppointment = new Button("Add Appointment");
        AddAppointment.setMinWidth(200);
        
        AddAppointment.setFont(new Font("Times New Roman",20));
        AddAppointment.setStyle("-fx-background-color: Green ");
        AddAppointment.setTextFill(Color.WHITE);
        

        updateAppointment = new Button("Update Appointment");
        updateAppointment.setMinWidth(200);
        
        updateAppointment.setFont(new Font("Times New Roman",20));
        updateAppointment.setStyle("-fx-background-color: Green ");
        updateAppointment.setTextFill(Color.WHITE);

        deleteAppointment = new Button("Delete  Appointment");
        deleteAppointment.setMinWidth(200);
        
        deleteAppointment.setFont(new Font("Times New Roman",20));
        deleteAppointment.setStyle("-fx-background-color: Green ");
        deleteAppointment.setTextFill(Color.WHITE);

        BackButton = new Button("Back");
        BackButton.setMinWidth(200);
        
        BackButton.setFont(new Font("Times New Roman",20));
        BackButton.setStyle("-fx-background-color: Green ");
        BackButton.setTextFill(Color.WHITE);
        
        
        // defining  action of back button...
        BackButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // Return to main Menu
                gotoBackMenu();

            }
        });

        // defining action on ViewAppointment button.....
        ViewAppointment.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // hide the current frame and initlialize the showAppointmentRecords.....
                ShowAppointmentRecords showappointments= new ShowAppointmentRecords();
                showappointments.show();
                hideCurrentFrame();
            }
        });

        //defining action for Add appointment button
        AddAppointment.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // hiding current frame and initilaztion of appointment class object and intiliazation 
                //of Add appointment frame 
                Appointment appointment = new Appointment();
                AddAppointment addappointment = new AddAppointment(appointment);
                addappointment.show();
                hideCurrentFrame();

            }
        });

        //defining action for update appointment button....
        updateAppointment.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // taking input of appointment that need to be uodated....
                TextInputDialog textinputdailog = new TextInputDialog();
 
                textinputdailog.setHeaderText("Enter Appointment ID");

                Result = textinputdailog.showAndWait();

                // save the input id ....
                Result.ifPresent(input -> ID = Integer.parseInt(input));
                // check that whether tha given appointment id exist if yes then load the appointment details...
                Appointment AppointmentDetails = checkAppointDetails(ID);

                // if the Appointment is wrong.....
                if (AppointmentDetails == null) {

                    Dialog<String> dialog = new Dialog<String>();
                    //defining  the title
                    dialog.setTitle("Error");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //defining the dialog content....
                    dialog.setContentText("Appointment with the given ID " + ID + " not found, please try with valid ID.");
                    //adding button to the dailoge
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                } // If appointment details exist with the given id then display the appointment details
                else {
                    // display the appoint details in the addappointment frame....
                    AddAppointment updateAppointment  = new AddAppointment(AppointmentDetails);
                    updateAppointment .show();
                    hideCurrentFrame();
                }

            }
        });

        // defining action for delete button
        deleteAppointment.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // defining dialog to take appointment id input.....
                TextInputDialog inputDialog = new TextInputDialog();
                inputDialog.setHeaderText("Enter appointment ID: ");

                Result = inputDialog.showAndWait();
                //taking ID input..... 
                Result.ifPresent(input -> ID = Integer.parseInt(input));
                // check if the appointment with given ID exist....
                Appointment AppointmentDetails = checkAppointDetails(ID);

                // show error dialog if the given appointment does not exist....
                if (AppointmentDetails == null) {
                    Dialog<String> errorDialog = new Dialog<String>();
                    //Defining the title
                    errorDialog.setTitle("Error");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //defining  the content of the dialog
                    errorDialog.setContentText("Appointment with given ID :" + ID + " not found , please try with a valid ID.");
                    //Adding buttons to the dailog.....
                    errorDialog.getDialogPane().getButtonTypes().add(type);
                    errorDialog.showAndWait();
                } // if the details with the given ID found then delete the appointment....
                if (DeletAppointment(ID)) {

                    Dialog<String> showDeleteMessage = new Dialog<String>();
                    //Defining the title
                    showDeleteMessage.setTitle("Message");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //defning the content of the message dialog
                    showDeleteMessage.setContentText("Deletion of the " + AppointmentDetails.appType + " appointement with id: " + ID + " successful.");
                    //Adding buttons to the message dialog 
                    showDeleteMessage.getDialogPane().getButtonTypes().add(type);
                    showDeleteMessage.showAndWait();

                } else {
                    Dialog<String> problemDialog = new Dialog<>();
                    //Defining the title
                    problemDialog.setTitle("Error Dialog");
                    ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //Defining the content of the  error dialog
                    problemDialog.setContentText("Problem occurred while deleting the appointment ,please try again.");
                    //Adding buttons to the error dialog pane
                    problemDialog.getDialogPane().getButtonTypes().add(btype);
                    problemDialog.showAndWait();
                }

            }

        });

        
        VBox box = new VBox();

        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        VBox.setMargin(ViewAppointment, new Insets(40, 40, 40, 40));
        VBox.setMargin(AddAppointment, new Insets(40, 40, 40, 40));
        VBox.setMargin(updateAppointment, new Insets(40, 40, 40, 40));
        VBox.setMargin(deleteAppointment, new Insets(40, 40, 40, 40));
        VBox.setMargin(BackButton, new Insets(40, 40, 40, 40));
        
        box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        //fetch the observable list of the VBox 
        ObservableList observableList = box.getChildren();

        //Adding all the nodes to the observable list 
        observableList.addAll(ViewAppointment, AddAppointment, updateAppointment, deleteAppointment, BackButton);

        scene = new Scene(box, 600, 600);
        this.setScene(scene);
        this.setTitle("Appointments Main Menu");
        scene.setFill(Color.web("#81c483"));
        
        this.setResizable(false);

    }

    /**
     * to go back to the main menu
     */
    public void gotoBackMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        hideCurrentFrame();

    }

    /**
     * used to hide current frame
     */
    void hideCurrentFrame() {
        this.hide();
    }

    /**
     * this method is used to check that whether the appointment with given id exist in the database
     *
     * @param ID is appointment id...
     * @return the requested appointment details otherwise return null..
     */
    public Appointment checkAppointDetails(int ID) {
        Connection connection = DatabaseConnection.CreateConnection();
        Statement statement;
        try {
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(" select * from appointments where Appointment_ID = " + ID);
            // If appointment exists, populate an Appointment object and return it
            if (resultSet.next()) {
                Appointment Appointment = new Appointment();
                Appointment.setAppID(resultSet.getInt("Appointment_ID"));
                Appointment.setAppointmentDescription(resultSet.getString("description"));
                Appointment.setTitle(resultSet.getString("title"));
                Appointment.setAppType(resultSet.getString("type"));
                Appointment.setAppLocation(resultSet.getString("location"));
                Appointment.setStartDataAndTime(resultSet.getString("start"));
                Appointment.setAppEndDate(resultSet.getString("end"));
                Appointment.setCustomerID(resultSet.getInt("Customer_ID"));
                Appointment.setUserID(resultSet.getInt("User_ID"));
                ResultSet rs2 = statement.executeQuery("select * from contacts where Contact_ID = "+resultSet.getInt("Contact_ID"));
                rs2.next();
                Appointment.setAppContact(rs2.getString("Contact_Name"));

                return Appointment;

            }

        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    /**
     * used to delete the appointment with given id From the database
     *
     * @param id represent the appointment id...
     * @return true if the appointment is successfully deleted else return null
     */
    public boolean DeletAppointment(int id) {
        Connection connection = DatabaseConnection.CreateConnection();

        Statement statment;
        try {
            statment = connection.createStatement();

            int bol = statment.executeUpdate(" delete from appointments where Appointment_ID = " + id);
            if (bol == 0) {
                return false;
            }

        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

}
