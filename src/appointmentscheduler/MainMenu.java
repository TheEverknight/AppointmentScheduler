package appointmentscheduler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *this class provides the Main menu from which user can navigate to the different menu to perform different operations
 *
 * @author
 */
public class MainMenu extends Stage {

    /**
     * Constructor to initialize all necessary element of UI
     */
    public MainMenu() {
        // detect if there is any appointment in coming 15 minute
        Appointment recentAppointment = checkAppointmentincoming15minute();

        // if there is any recent appointment show the appointment id and appointment date...
        if (recentAppointment != null) {
            Dialog<String> AppointmentDailog = new Dialog<String>();
            //Setting the title
            AppointmentDailog.setTitle("Upcoming Appointment");
            ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            //Defining the content of the dailog
            AppointmentDailog.setContentText("Reminder! there is an appointment in 15 minutes! appointment ID : " + recentAppointment.getAppID() + ", Date:" + recentAppointment.getStartDataAndTime().replace('T', ' ').substring(0, 16) + ".");
            //Adding button to Appointment Dailog....
            AppointmentDailog.getDialogPane().getButtonTypes().add(btype);
            AppointmentDailog.showAndWait();

        } // if there is no recent appoint then show that there is no recent appointment ...
        else {
            Dialog<String> MessageDailog = new Dialog<>();
            //Defining dailog title
            MessageDailog.setTitle("Message Dailog");
            ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            //Defining the content of the dailog...
            MessageDailog.setContentText("there is no Upcoming appointment..");
            //button is added to the message dailog....
            MessageDailog.getDialogPane().getButtonTypes().add(btype);
            MessageDailog.showAndWait();
        }

        // defining button that are required in the Form....
        Button customerManagment = null, AppointmentManagement = null, exitbutton = null,checkReportButton=null;

        Scene scene = null;

       
        
        
        AppointmentManagement = new Button("Manage Appointments");
        AppointmentManagement.setMinWidth(200);
        AppointmentManagement.setFont(new Font("Times New Roman",20));
        AppointmentManagement.setTextFill(Color.WHITE);
        AppointmentManagement.setStyle("-fx-background-color:Green ");
        
        checkReportButton = new Button("Show Reports");
        checkReportButton.setMinWidth(200);
        checkReportButton.setTextFill(Color.WHITE);
        checkReportButton.setFont(new Font("Times New Roman",20));
        checkReportButton.setStyle("-fx-background-color:Green ");
        
         customerManagment = new Button("Manage Customer");
        customerManagment.setMinWidth(200);
        customerManagment.setFont(new Font("Times New Roman",20));
        customerManagment.setStyle("-fx-background-color: Green ");
        customerManagment.setTextFill(Color.WHITE);

        
        exitbutton = new Button("Close ");
        exitbutton.setMinWidth(200);
        exitbutton.setTextFill(Color.WHITE);
        exitbutton.setFont(new Font("Times New Roman",20));
        exitbutton.setStyle("-fx-background-color:Green ");
        

        // Defining action for cutomerManagement button 
        customerManagment.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent e) {
                // navigate to the main menu of the customer....
                naviagateTocustomerMainMenu();

            }
        });

        // Defining action for Appointment management button
        /**
         * Lambda expression for quick switchting of application environments, this one allows for a quick
         * switch to the Appointment management meny
         */
        AppointmentManagement.setOnAction((event) -> {
            // navigate to the appointment main menu
            navigateTotheMainMenuofAppointment();

        });
       // defining action for the checkReport button...

        /**
         * Lambda expression for quick switchting of application environments, this one allows for a quick
         * switch to the Report retrieval meny
         */
        checkReportButton.setOnAction((event) -> {
            navigateTotheReportMenu();
        });

        // defining action for exit button.....\
        /**
         * Lambda expression for quick switchting of application environments, this one allows for a quick
         * switch to the Login Menu environment.
         */
        exitbutton.setOnAction((event) -> {
            //signout and go to the login frame...
            navigateToLogin();
        });
        VBox box = new VBox();

        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        VBox.setMargin(checkReportButton, new Insets(20, 20, 20, 20));
        VBox.setMargin(AppointmentManagement, new Insets(20, 20, 20, 20));
        
         VBox.setMargin(customerManagment, new Insets(20, 20, 20, 20));
        VBox.setMargin(exitbutton, new Insets(20, 20, 20, 20));
        //geting the observable list of the vbox....
        ObservableList observableList = box.getChildren();

        //add nodes to the observableList.. 
        observableList.addAll(customerManagment, AppointmentManagement,checkReportButton, exitbutton);
        box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        scene = new Scene(box, 500, 350);
        this.setTitle("Main Menu");
        this.setScene(scene);
        
        this.setResizable(false);

    }

    /**
     * this method is used to navigate to the customer Menu....
     */
    public void naviagateTocustomerMainMenu() {
        CustomerMenu customerMenu = new CustomerMenu();
        customerMenu.show();
        this.hide();

    }

    /**
     * this method is used to navigate to the Appointment main menu....
     */
    public void navigateTotheMainMenuofAppointment() {

        AppointmentsMenu appointmentMenu = new AppointmentsMenu();
        appointmentMenu.show();
        this.hide();

    }
     /**
     * this method is to navigate to the FormReport from the current Menu and hide the 
     * current Frame..........
     */
    
    public void navigateTotheReportMenu(){
        FormReport formReport = new FormReport();
        formReport.show();
        this.hide();
    }

    /**
     * this method navigate the user from Main menu to the login frame and 
     * hide the current frame...
     */
    public void navigateToLogin() {
        SIGNIN l = new SIGNIN();
        l.show();
        this.hide();
    }

    /**
     *this method check that whether there is any appointment in coming 15 minute..
     *
     * @return appointment details if there is any appointment in coming 15 minute if there is no any upcoming appointment
     * then return null...
     */
    private Appointment checkAppointmentincoming15minute() {
        try {
            
            Connection connection = DatabaseConnection.CreateConnection();
            Statement statment = connection.createStatement();
            ResultSet resultset = statment.executeQuery("select * from appointments where User_ID="+SIGNIN.ID);
          
            while (resultset.next()) {
                Calendar startTime = Calendar.getInstance();
                SimpleDateFormat simpledataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String str=resultset.getString("start");
                System.out.println(str);
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(simpledataformat.parse(TimeConversion.fromUtcToLocalzone(resultset.getString("start")).replace('T', ' ').substring(0, 16) + ":00"));

                Calendar newStartTime = Calendar.getInstance();

                newStartTime.add(Calendar.MINUTE, 15); // adding 15 minutes to the start_time
                System.out.println(newStartTime.toString());
                if (newStartTime.after(endTime) && startTime.before(endTime)) {
                    Appointment appointment = new Appointment();
                    appointment.setAppID(resultset.getInt("Appointment_ID"));
                    //appointment.setAppContact(resultset.getString("contact"));
                    appointment.setAppointmentDescription(resultset.getString("description"));
                    appointment.setTitle(resultset.getString("title"));
                    appointment.setAppType(resultset.getString("type"));
                    appointment.setAppLocation(resultset.getString("location"));
                    appointment.setStartDataAndTime(TimeConversion.fromUtcToLocalzone(resultset.getString("start")));
                    appointment.setAppEndDate(TimeConversion.fromUtcToLocalzone(resultset.getString("end")));
                    appointment.setCustomerID(resultset.getInt("Customer_ID"));
                    appointment.setUserID(resultset.getInt("User_ID"));
                    return appointment;
                }
            }
        } catch (SQLException | ParseException ex ) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }

}
