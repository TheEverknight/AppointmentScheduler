package appointmentscheduler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * this class add the new appointment to the database and update the appointment....
 *
 * @author
 */
public class AddAppointment extends Stage {

    TextField IDtextField;

    /**
     * this constructor load the appointment details to update the existing the appointment in case of update appointment
     * or load the add appointment required fields......
     *@Lambda expression are used in this Constructor....
     * @param appointment refer to the appointment instance in case of update appointment 
     * and empty Appointment instance in case add appointment scenario
     */
    AddAppointment(Appointment appointment) {
        // vbox for setting ui element in the frame....
        VBox box = new VBox();

        box.setSpacing(8);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        // defining all required label used in the UI form...
        Label titleLabel, descriptionlabel, locationLabel, contactLabel, typeLabel, startDateTimeLabel, endDateTimeLabel, customerIdLabel, userIdLabel;
        // defining all the textfields that are required in the UI....
        TextField titleTextfield, descriptionTextfield, locationTextfield, typeTextfield, startDateTimeTextfield, endDateTimeTextfield, customerIdTextfield, userIdTextfield;

        titleLabel = new Label("Appointment  Title");
        titleTextfield = new TextField(appointment.getTitle());
        descriptionlabel = new Label("Description");
        descriptionTextfield = new TextField(appointment.getAppointmentDescription());
        locationLabel = new Label("Location");
        locationTextfield = new TextField(appointment.getAppLocation());
        contactLabel = new Label("Contact");
        typeLabel = new Label("Type");
        typeTextfield = new TextField(appointment.getAppType());
        startDateTimeLabel = new Label("Start date & time i.e 2021-12-31 10:00:00 ");
        // if the appointement startdate and time is empty...
        if (appointment.getStartDataAndTime() == null) {
            startDateTimeTextfield = new TextField(appointment.getStartDataAndTime());
        } // if the appointment instance is not null then convert date and time from UTC to local zone..
        else {
            startDateTimeTextfield = new TextField(TimeConversion.fromUtcToLocalzone(appointment.getStartDataAndTime()).replace('T', ' ').substring(0, 16) + ":00");
        }
        endDateTimeLabel = new Label("End date & time i.e 2021-12-31 11:00:00");
        // if appointment is new set date fields empty
        if (appointment.getAppEndDate() == null) {
            endDateTimeTextfield = new TextField(appointment.getAppEndDate());
        } // if the appointment is existing then convert the UTC time extracted from the datbase into existing time
        else {
            endDateTimeTextfield = new TextField(TimeConversion.fromUtcToLocalzone(appointment.getAppEndDate()).replace('T', ' ').substring(0, 16) + ":00");
        }
        customerIdLabel = new Label("Customer ID");
        customerIdTextfield = new TextField(appointment.getCustomerID() + "");
        userIdLabel = new Label("User ID");
        userIdTextfield = new TextField(appointment.getUserID() + "");

        // define comobox that show all the contacts available in the database...
        ComboBox contactsList = new ComboBox();
        // getting appointment contact list from the database using getAllContacts from the db..
        ArrayList ContactNameList = getAllContacts();
        // inserting the contacts in the database that are retrieve from the getAllContacts method..
        for (int i = 0; i < ContactNameList.size(); i++) {
            contactsList.getItems().add(ContactNameList.get(i));
        }
        // setting the contactappointment value from the previus appointment
        contactsList.setValue(appointment.getAppContact());
        // Button used for adding new appointment operation
        Button addAppointment = new Button("Add");
        // update button to update appointment 
        Button updateAppointment = new Button("Update");
        // back button navigate the user to go back to the main menue 
        Button back = new Button("Back");
        // setting properties for the button
         addAppointment.setFont(new Font("Times New Roman",15));
         addAppointment.setStyle("-fx-background-color: Green ");
         updateAppointment.setFont(new Font("Times New Roman",15));
         updateAppointment.setStyle("-fx-background-color: Green ");
         back.setFont(new Font("Times New Roman",15));
         back.setStyle("-fx-background-color: Green ");
         
        // if the received appointment instance is null then add UI element except ID
        if (appointment.AppID == 0) {

            // UI element are added to the Vbox....
            box.getChildren().addAll(
                    titleLabel, titleTextfield, descriptionlabel, descriptionTextfield, locationLabel, locationTextfield,
                    contactLabel, contactsList, typeLabel, typeTextfield, startDateTimeLabel, startDateTimeTextfield, endDateTimeLabel,
                    endDateTimeTextfield, customerIdLabel, customerIdTextfield, userIdLabel, userIdTextfield, addAppointment, back);
 
            this.setTitle("Add New Appointment");

        } // if the received appointment instance is not null then add all the required UI element and also add the ID ..
        else {
            IDtextField = new TextField(appointment.getAppID() + "");
            // disable ID text fields
            IDtextField.setEditable(false);
            // Adding the UI element to vbox
            box.getChildren().addAll(new Label("ID"),
                    IDtextField, titleLabel, titleTextfield, descriptionlabel, descriptionTextfield, locationLabel, locationTextfield,
                    contactLabel, contactsList, typeLabel, typeTextfield, startDateTimeLabel, startDateTimeTextfield, endDateTimeLabel,
                    endDateTimeTextfield, customerIdLabel, customerIdTextfield, userIdLabel, userIdTextfield,
                    updateAppointment,
                    back);

            this.setTitle("Update existing Appointments");
        }
        // defining action for add button using  lamda expression
        addAppointment.setOnAction((e) -> {
            // after pressing the add button if there is any empty field promt the user to fill it..
            if (titleTextfield.getText() == null || descriptionTextfield.getText().isEmpty() || locationTextfield.getText() == null || contactsList.getValue().toString().equals("") || typeTextfield.getText().isEmpty() || startDateTimeTextfield.getText().isEmpty() || endDateTimeTextfield.getText().isEmpty() || customerIdTextfield.getText().isEmpty() || userIdTextfield.getText().isEmpty()) {
                Dialog<String> MessageDialog = new Dialog<>();
                
                //Defining the Title
                MessageDialog.setTitle("Alert Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //defining the content 
                MessageDialog.setContentText("please fill all the feilds..");
                //Adding buttons to the message dialog pane
                MessageDialog.getDialogPane().getButtonTypes().add(btype);
                MessageDialog.showAndWait();
            } else if(isOutOfRange(startDateTimeTextfield.getText(),  endDateTimeTextfield.getText())){
                Dialog<String> messaging = new Dialog<>();
                //defining the Title
                messaging.setTitle("Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //defining the content of the message dialog
                messaging.setContentText("please try with another time. Current appointment time is outside of operating hours");
                //Adding buttons to the dialog pane
                messaging.getDialogPane().getButtonTypes().add(btype);
                messaging.showAndWait();
            }
            // check whether the time set for new appointment overlap with the existing appointment or not....
            else if (isOverlap(startDateTimeTextfield.getText(), endDateTimeTextfield.getText())) {
                Dialog<String> Messagedialog = new Dialog<>();

                Messagedialog.setTitle("Alert Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                // defining custom message
                Messagedialog.setContentText("please try another time. current time overlaps with another appointment time");

                Messagedialog.getDialogPane().getButtonTypes().add(btype);
                Messagedialog.showAndWait();
            } // if all the required fields are filled and appointment time not overlaping with any other appointment time then add the appointment detalis to the datbase
            
            else {
                Appointment appointmentobject = new Appointment();
                appointmentobject.setTitle(titleTextfield.getText());
                appointmentobject.setAppointmentDescription(descriptionTextfield.getText());
                appointmentobject.setAppLocation(locationTextfield.getText());
                appointmentobject.setAppContact(contactsList.getValue().toString());
                appointmentobject.setAppType(typeTextfield.getText());
                // convert start date and time from local zone into UTC zone.....
                appointmentobject.setStartDataAndTime(TimeConversion.fromLocalToUTCzone(startDateTimeTextfield.getText()).substring(0, TimeConversion.fromLocalToUTCzone(startDateTimeTextfield.getText()).length() - 6));

                // after converting the date and time to UTC zone then store it into database
                appointmentobject.setAppEndDate(TimeConversion.fromLocalToUTCzone(endDateTimeTextfield.getText()).substring(0, TimeConversion.fromLocalToUTCzone(endDateTimeTextfield.getText()).length() - 6));
                appointmentobject.setCustomerID(Integer.parseInt(customerIdTextfield.getText()));
                appointmentobject.setUserID(Integer.parseInt(userIdTextfield.getText()));

                // check whether the appointment is added successfully into database or not...
                boolean isadded = addNewAppointment(appointmentobject);
                if (isadded) {
                    Dialog<String> messageDailog = new Dialog<>();

                    messageDailog.setTitle("Message");
                    ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);

                    messageDailog.setContentText("Appointment Details are successfully added.");

                    messageDailog.getDialogPane().getButtonTypes().add(btype);
                    messageDailog.showAndWait();
                    titleTextfield.setText("");
                    locationTextfield.setText("");
                    descriptionTextfield.setText("");
                    contactsList.setValue("");
                    typeTextfield.setText("");
                    startDateTimeTextfield.setText("");
                    endDateTimeTextfield.setText("");
                    customerIdTextfield.setText("");
                    userIdTextfield.setText("");

                }// if there is any problem while adding appointment details into database then show error message
                else {
                    Dialog<String> errorDialogue = new Dialog<>();
                    //Defining the Title
                    errorDialogue.setTitle("Error");
                    ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //defining the error dialogue content...
                    errorDialogue.setContentText("problem accure during adding. please try again with the valid data.");
                    //Adding buttons to the error dialog pane
                    errorDialogue.getDialogPane().getButtonTypes().add(btype);
                    errorDialogue.showAndWait();
                }
            }

        });
        // defining functionality for back button to navigate to the Appointment menu
        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
               
                    navigateTotheAppointmentMenu();

              
            }
        });
        // defining action for update button....
        updateAppointment.setOnAction((event) -> {

            //when update button is clicked if there is any ampty feild then prompt the user...
            if (titleTextfield.getText() == null || descriptionTextfield.getText().isEmpty() || locationTextfield.getText() == null || contactsList.getValue().toString().equals("") || typeTextfield.getText().isEmpty() || startDateTimeTextfield.getText().isEmpty() || endDateTimeTextfield.getText().isEmpty() || customerIdTextfield.getText().isEmpty() || userIdTextfield.getText().isEmpty()) {
                Dialog<String> messageDailog = new Dialog<>();
                //Defining the Title
                messageDailog.setTitle("Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //defining the message dialogue content...
                messageDailog.setContentText("No field should be empty. please enter all the feilds.");
                //Adding buttons to the message dialog pane
                messageDailog.getDialogPane().getButtonTypes().add(btype);
                messageDailog.showAndWait();
            }
            if (isOverlap(startDateTimeTextfield.getText(), endDateTimeTextfield.getText())) {
                Dialog<String> Messagedialog = new Dialog<>();

                Messagedialog.setTitle("Alert Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                // defining custom message
                Messagedialog.setContentText("please try another time. current time overlaps with another appointment time");

                Messagedialog.getDialogPane().getButtonTypes().add(btype);
                Messagedialog.showAndWait();
            } // if all the required fields are filled and appointment time not overlaping with any other appointment time then add the appointment detalis to the datbase

            else if(isOutOfRange(startDateTimeTextfield.getText(),  endDateTimeTextfield.getText())){
                Dialog<String> messagedialogue = new Dialog<>();
                //defining the Title
                messagedialogue.setTitle("Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //defining the content of the message dialog
                messagedialogue.setContentText("please try with another time. Current appointment time is outside of operating hours");
                //Adding buttons to the dialog pane
                messagedialogue.getDialogPane().getButtonTypes().add(btype);
                messagedialogue.showAndWait();
            }
            // check whether the given appointment time overlap with the existing appointment time..
            else if (isOverlap(startDateTimeTextfield.getText(), endDateTimeTextfield.getText())) {
                Dialog<String> messagedialogue = new Dialog<>();
                //defining the Title
                messagedialogue.setTitle("Message");
                ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //defining th content of the message dialog
                messagedialogue.setContentText("pleas try with another time. Current appointment time overlap with the existing appointement time");
                //Adding buttons to the dialog pane
                messagedialogue.getDialogPane().getButtonTypes().add(btype);
                messagedialogue.showAndWait();
            }

             // if all the appointment details are filled and there is no time overlaping then add appiontment details into the database..
            else {
                try {
                    Appointment appointmentobject = new Appointment();
                    appointmentobject.setTitle(titleTextfield.getText());
                    appointmentobject.setAppointmentDescription(descriptionTextfield.getText());
                    appointmentobject.setAppLocation(locationTextfield.getText());
                    appointmentobject.setAppContact(contactsList.getValue().toString());
                    appointmentobject.setAppType(typeTextfield.getText());

                    // start and end date and time convert from local zone into UTC before storing into database...
                    appointmentobject.setStartDataAndTime(TimeConversion.fromLocalToUTCzone(startDateTimeTextfield.getText()).substring(0, TimeConversion.fromLocalToUTCzone(startDateTimeTextfield.getText()).length() - 6));
                    appointmentobject.setAppEndDate(TimeConversion.fromLocalToUTCzone(endDateTimeTextfield.getText()).substring(0, TimeConversion.fromLocalToUTCzone(endDateTimeTextfield.getText()).length() - 6));
                    appointmentobject.setCustomerID(Integer.parseInt(customerIdTextfield.getText()));
                    appointmentobject.setUserID(Integer.parseInt(userIdTextfield.getText()));
                    appointmentobject.setAppID(Integer.parseInt(IDtextField.getText()));
                    // Check if appointment is succesfully added to the database with updated values
                    boolean isupdate = updateExistingAppointment(appointmentobject);
                    if (isupdate) {
                        Dialog<String> messageDailog = new Dialog<>();
                        //Defining the Title
                        messageDailog.setTitle("Dialog");
                        ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //defning the content of the dialog
                        messageDailog.setContentText("Appointment details successfully updated.");
                        //Adding buttons to the Message dialog pane
                        messageDailog.getDialogPane().getButtonTypes().add(btype);
                        messageDailog.showAndWait();
                        titleTextfield.setText("");
                        locationTextfield.setText("");
                        descriptionTextfield.setText("");
                        contactsList.setValue("");
                        typeTextfield.setText("");
                        startDateTimeTextfield.setText("");
                        endDateTimeTextfield.setText("");
                        customerIdTextfield.setText("");
                        userIdTextfield.setText("");
                        IDtextField.setText("");

                    }// if any problem accur during adding into the database then show error message.... 
                    else
                    {
                        Dialog<String> errorDailog = new Dialog<>();
                        //Definign the Title
                        errorDailog.setTitle("Error");
                        ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //Defining the content of the error dialog
                        errorDailog.setContentText("problem accur during updating appointment  details. please try with valid data. ");
                        //Adding buttons to the error  dialog pane
                        errorDailog.getDialogPane().getButtonTypes().add(btype);
                        errorDailog.showAndWait();

                    }
                } catch (Exception ex) {

                }
            }

        });
        Scene scene = new Scene(box, 500, 650);
        this.setScene(scene);
        this.setTitle("Appointment Main operations");
        this.setResizable(false);

    }

    /**
     *this method navigates to the appointment Menu.....
     */
    public void navigateTotheAppointmentMenu() {

        // intialize appointment frame,,
        AppointmentsMenu appointmentmenu = new AppointmentsMenu();
        appointmentmenu.show();
        // Hide current frame....
        this.hide();

    }

    /**
     * to add the new appointment to the database....
     *
     * @param appointmet refer to the new appointment instance that need to be added into database
     * 
     * @return true if the new appointment addition is successful else false
     */
    public boolean addNewAppointment(Appointment appointmet) {
        Connection connection = DatabaseConnection.CreateConnection();
        Statement statment;

        try {
            statment = connection.createStatement();
            ResultSet resultSet = statment.executeQuery("select contact_id from contacts where contact_name='"+appointmet.getAppContact()+"'");
            resultSet.next();
            String query = "insert into appointments(title,description,location,Contact_ID,type,start,end,Customer_Id,User_ID) values('" + appointmet.getTitle() + "','" + appointmet.getAppointmentDescription() + "','" + appointmet.getAppLocation() + "',"+resultSet.getInt("Contact_Id")+",'"+ appointmet.getAppType() + "','" + appointmet.getStartDataAndTime() + "','" + appointmet.getAppEndDate() + "','" + appointmet.getCustomerID() + "','" + appointmet.getUserID() + "');";
           
            int checkAdd = statment.executeUpdate(query);
            if (checkAdd == 0) {
                return false;
            }

        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * used to update the current appointment into the database....
     *
     * @param  appointment refer to the appointment instance that need to be updated and
     * needs to be stored in database.
     * @return true if the appointment is successfully updated otherwise false
     */
    public boolean updateExistingAppointment(Appointment  appointment) {
        Connection  connection = DatabaseConnection.CreateConnection();
        Statement statement;

        try {
            statement =  connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select contact_id from contacts where contact_name='"+ appointment.getAppContact()+"'");
            resultSet.next();
            String query = "update appointments set title='" +  appointment.getTitle() + "',description='" +  appointment.getAppointmentDescription() + "',location='" +  appointment.getAppLocation() + "',Contact_ID='" + resultSet.getInt("Contact_ID") + "',type='" +  appointment.getAppType() + "',start='" +  appointment.getStartDataAndTime() + "',end='" +  appointment.getAppEndDate() + "',Customer_ID=" +  appointment.getCustomerID() + ",User_ID=" +  appointment.getUserID() + " where Appointment_ID=" +  appointment.getAppID() + ";";

           
            int checkupdate = statement.executeUpdate(query);
            if (checkupdate == 0) {
                return false;
            }

        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * this method check that whether the date and time of the current appointment overlape with other 
     * existing appointment time
     * @param start refer to the start date of new appointment 
     * to be added
     * @param end refer to the end date of the new appointment to be added
     * @return true if the overlapping  exist otherwise return false
     */
    public static boolean isOverlap(String start, String end) {
        try {
            Connection connection = DatabaseConnection.CreateConnection();
            Statement statment = connection.createStatement();
            ResultSet resultSet = statment.executeQuery("select start,end from appointments");

            SimpleDateFormat simpledataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpledataformat.parse(TimeConversion.fromLocalToUTCzone(start).substring(0, TimeConversion.fromLocalToUTCzone(start).length() - 6).replace('T', ' ') + ":00"));
            Calendar calender2 = Calendar.getInstance();
            calender2.setTime(simpledataformat.parse(TimeConversion.fromLocalToUTCzone(end).substring(0, TimeConversion.fromLocalToUTCzone(end).length() - 6).replace('T', ' ') + ":00"));
            while (resultSet.next()) {
                Calendar cal1 = Calendar.getInstance();

                cal1.setTime(simpledataformat.parse(resultSet.getString("start")));
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(simpledataformat.parse(resultSet.getString("end")));

                if (calendar.after(cal1) && calendar.before(cal2)){

                    return true;
                } else if( calender2.after(cal1) && calender2.before(cal2)) {
                    return true;
                }
                  else if( calendar.equals(cal1)) {
                    return true;
                }
                  else if( calender2.equals(cal2)) {
                    return true;
                }
                  else if( calender2.before(cal1) && calender2.after(cal2)) {
                    return true;
                }

            }
        } catch (SQLException ex) {
            System.out.println("A1");
            return false;
        } catch (ParseException ex) {
            System.out.println("A2");
            return false;
        }
        System.out.println("A3");
        return false;
    }

    //set this up to detect if the appointment is outside of the available hours
    public static boolean isOutOfRange(String start, String end) {

        /*
        try {
            System.out.println("OutOfRangeCheck A");

            SimpleDateFormat simpledataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            System.out.println("OutOfRangeCheck B");
            calendar.setTime(simpledataformat.parse(TimeConversion.fromLocalToUTCzone(start).substring(0, TimeConversion.fromLocalToUTCzone(start).length() - 6).replace('T', ' ') + ":00"));
            Calendar calender2 = Calendar.getInstance();
            System.out.println("OutOfRangeCheck C");
            calender2.setTime(simpledataformat.parse(TimeConversion.fromLocalToUTCzone(end).substring(0, TimeConversion.fromLocalToUTCzone(end).length() - 6).replace('T', ' ') + ":00"));





                int StartCheckTime;
            System.out.println(calendar.getTime().toString());
            System.out.println(Integer.parseInt(calendar.getTime().toString().substring(11, 13)));

            try {
                     StartCheckTime = Integer.parseInt(calendar.getTime().toString().substring(11, 13));
                     System.out.println(" A " + StartCheckTime);
                }
                catch (Exception e){
                    try{
                         StartCheckTime = Integer.parseInt(calendar.getTime().toString().substring(12, 13));
                         System.out.println(" B " + StartCheckTime);
                    }
                    catch (Exception f){
                        f.printStackTrace();
                        StartCheckTime = 0;
                        return false;
                    }
                }
                int EndCheckTime = Integer.parseInt(calender2.getTime().toString().substring(11,13));
                */


        int StartCheckTime, EndCheckTime;

        System.out.println(start.substring(10, 12));
        System.out.println(start.charAt(11));

        StartCheckTime = 0;
        EndCheckTime = 0;

        try {
            StartCheckTime = Integer.parseInt(start.substring(10, 12));
            System.out.println(" A " + StartCheckTime);
            int test = 1 / StartCheckTime;

        }
        catch (Exception e){
            try{
                StartCheckTime = Integer.parseInt(start.substring(11, 13).replace(':', ' '));
                System.out.println(" B " + StartCheckTime);
                int test = 1 / StartCheckTime;
            }
            catch (Exception f){
                try{
                    StartCheckTime = Integer.parseInt(start.substring(11, 12));
                    System.out.println(" C " + StartCheckTime);
                }
                catch(Exception g) {
                    f.printStackTrace();
                    EndCheckTime = 0;
                    return true;
                }
            }
        }
        try {
            EndCheckTime = Integer.parseInt(end.substring(10, 12));
            System.out.println(" A " + EndCheckTime);
            int test = 1 / EndCheckTime;
        }
        catch (Exception e){
            try{
                EndCheckTime = Integer.parseInt(end.substring(11, 13).replace(':', ' '));
                System.out.println(" B " + EndCheckTime);
                int test = 1 / EndCheckTime;
            }
            catch (Exception f){

                try{
                    EndCheckTime = Integer.parseInt(start.substring(11, 12));
                    System.out.println(" C " + EndCheckTime);
                }
                catch(Exception g) {
                    f.printStackTrace();
                    EndCheckTime = 0;
                    return true;
                }
            }
        }

                System.out.println(start);
                System.out.println(end);


                int startTime = 8;
                int endTime = 22;

            if (StartCheckTime >= startTime && StartCheckTime <= endTime) {
                if (EndCheckTime >= startTime && EndCheckTime <= endTime) {
                    System.out.println("Not out of bounds");
                    return false;
                }
            }



        return true;
    }

    /**
     *to get appointment contact from the database that are stored in it...
     *
     * @return ArrayList of appointmentContact name from the database...
     */
    public static ArrayList getAllContacts() {
        ArrayList ContactList = new ArrayList();
        try {
            Connection connection = DatabaseConnection.CreateConnection();
            Statement statment = connection.createStatement();
            ResultSet resultset = statment.executeQuery("Select Contact_Name from contacts");
            while (resultset.next()) {
                ContactList.add(resultset.getString("Contact_Name"));

            }

        } catch (SQLException ex) {
            return ContactList;
        }
        return ContactList;
    }
}
