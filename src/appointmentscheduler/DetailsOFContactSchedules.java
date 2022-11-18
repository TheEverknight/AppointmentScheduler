package appointmentscheduler;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

public class DetailsOFContactSchedules extends Stage{



    /**
     * Constructor to create the instance of Stage whenever initialized and
     * display the requested data
     */
    DetailsOFContactSchedules() {

        //Need to be able to modify the table columns directly, should possibly set these to
        //public or make global references to them.
      
        // used to present data in the table format
        TableView TableFormat = new TableView();
        

        // table column is used to display each column data in the table view
        TableColumn<Contacts, String> ContactID  = new TableColumn<>("Contact ID");
        ContactID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Contacts, String> ContactName = new TableColumn<>("Name");
        ContactName.setCellValueFactory(new PropertyValueFactory<>("ContactName"));

        TableColumn<Contacts, String> AppointmentID = new TableColumn<>("Appointment ID");
        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("AppID"));

        TableColumn<Contacts, String> Title = new TableColumn<>("Title");
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));

        TableColumn<Contacts, String> AppointmentDescription = new TableColumn<>("Description");
        AppointmentDescription.setCellValueFactory(new PropertyValueFactory<>("AppointmentDescription"));

        TableColumn<Contacts, String> AppointmentStartTime = new TableColumn<>("Start");
        AppointmentStartTime.setCellValueFactory(new PropertyValueFactory<>("AppointmentStart"));

        TableColumn<Contacts, String> AppointmentEndTime = new TableColumn<>("End");
        AppointmentEndTime.setCellValueFactory(new PropertyValueFactory<>("AppEnd"));

        TableColumn<Contacts, String> Availability = new TableColumn<>("Availability");
        Availability.setCellValueFactory(new PropertyValueFactory<>("Availability"));




        // Adding the column into the table format
        TableFormat.getColumns().add(ContactID );
        TableFormat.getColumns().add(ContactName);
        TableFormat.getColumns().add(AppointmentID);
        TableFormat.getColumns().add(Title);
        TableFormat.getColumns().add(AppointmentDescription);
        TableFormat.getColumns().add(AppointmentStartTime);
        TableFormat.getColumns().add(AppointmentEndTime);
       //System.out.println(AppointmentStartTime.toString().replace('T', ' ').substring(0, 16) + ":00");
       //System.out.println(AppointmentEndTime.toString().replace('T', ' ').substring(0, 16) + ":00");
       TableFormat.getColumns().add(Availability);


        // get all the appointment data through getAppointement function
        ArrayList<Contacts> ContactList = getAllContactDetails();

        // declaration of back button
        Button BackButton = new Button("Back");   

        // defining back button action....
        BackButton.setOnAction((event) -> {
            // hide current form when button is pressed
            this.hide();
            // Appointement menu intialization.....
            FormReport ContactMenu = new FormReport();
            ContactMenu.show();
        });
        // inserting appointement Details into table view
        for (int i = 0; i < ContactList.size(); i++) {
            TableFormat.getItems().add(ContactList.get(i));
        }

        VBox Box = new VBox();
         Box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        Box.getChildren().addAll(TableFormat, BackButton);
        Scene DataScene = new Scene(Box, 700, 500);
        this.setScene(DataScene);
        this.setTitle("Report");
        this.setResizable(false);
        this.show();

    }

    /**
     * this method is used to retrieve all the appointment from the database
     *
     * @return list of the appointment array list
     */
    public ArrayList<Contacts> getAllContactDetails() {
        ArrayList<Contacts> contactsArrayList = null;
        try {
            contactsArrayList = new ArrayList<>();
            Connection connection = DatabaseConnection.CreateConnection();
            Statement statment = connection.createStatement();
            ResultSet resultSet = null;

             
                     //Add another nested While loop to iterate through appointments with the given
                    //contact assigned to it, and add each appointment to the list Listing it by contact
                    // Perhaps sorting the result set with a sql command would be the easiest route
                
          
                resultSet = statment.executeQuery("SELECT *\n" +
                        "FROM appointments \n" +
                        "INNER JOIN client_schedule.contacts ON client_schedule.contacts.Contact_ID=appointments.Contact_ID\n" +
                        "Order by appointments.Contact_ID;");

                while(resultSet.next()){

                        Contacts contact = new Contacts();

                        contact.setID(resultSet.getInt("Contact_ID"));
                        contact.setContactName(resultSet.getString("Contact_Name"));
                        contact.SetTitle(resultSet.getString("Title"));
                        contact.SetAppID(resultSet.getInt("Appointment_ID"));
                        contact.SetAppointmentDescription(resultSet.getString("Description"));
                    try {
                        SimpleDateFormat NewFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

                        contact.SetAppointmentStart(TimeConversion.fromUtcToLocalzone(resultSet.getString("Start")).substring(0, TimeConversion.fromUtcToLocalzone(resultSet.getString("Start")).length()-22).replace('T', ' '));
                        contact.SetAppEnd(TimeConversion.fromUtcToLocalzone(resultSet.getString("End")).substring(0, TimeConversion.fromUtcToLocalzone(resultSet.getString("End")).length()-22).replace('T', ' '));
                    }catch (Exception e){e.printStackTrace();};

                        contact.setContactEmail(resultSet.getString("Email"));
                        contact.SetAvailability("Sunday - Saturday, 8a - 10p");


                        contactsArrayList.add(contact);




                
            }
           
        } catch (SQLException ex) {

            return contactsArrayList;
        }
        return contactsArrayList;
    }
    
    


}
