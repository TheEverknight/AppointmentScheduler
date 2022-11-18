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
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * 
 * this class contains the code that produces Reports Form of Different contact Schedules
 *
 * @author
 */
public class FormReport extends Stage {

    String type, month, year;
    Optional<String> result, result2;

    /**
     *FormReport constructor which display the the content of the this form when class it is 
     * instantiated.
     */
    FormReport() {
        Button Button1 = null, Button2 = null, Button3 = null, backToMain = null;
        // button for generating reports of appointments regarding contacts of organization
        Button2 = new Button("View contacts of organization's schedule");
        Button2.setMinWidth(500);
        Button2.setFont(new Font("Times New Roman", 20));
        Button2.setStyle("-fx-background-color: Green ");
        Button2.setTextFill(Color.WHITE);
         // button for generating reports of appointments handled in any particular year
        Button3 = new Button("View no of appointments for particular year");
        Button3.setMinWidth(500);
        Button3.setFont(new Font("Times New Roman", 20));
        Button3.setStyle("-fx-background-color: Green ");
        Button3.setTextFill(Color.WHITE);

        // button for generating reports of appointments regarding type and month
        Button1 = new Button("View no of appointments searched with type and month");
        Button1.setMinWidth(500);
        Button1.setFont(new Font("Times New Roman", 20));
        Button1.setStyle("-fx-background-color: Green ");
        Button1.setTextFill(Color.WHITE);

        

        
       

        // Button for naviagting back to menu
        backToMain = new Button("Back");
        backToMain.setMinWidth(500);
        backToMain.setFont(new Font("Times New Roman", 20));
        backToMain.setStyle("-fx-background-color: Green ");
        backToMain.setTextFill(Color.WHITE);

        Button1.setOnAction((event) -> {
            // Setting up dialog for taking appointment month as input
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Enter appointments month no i.e (01-12) for report details: ");

            result = td.showAndWait();

            // take the input and store in the month variable
            result.ifPresent(input -> month = input);

            // Setting up dialog for taking month input 
            TextInputDialog td2 = new TextInputDialog();
            td2.setHeaderText("Enter appointments type for report details: ");

            result2 = td2.showAndWait();

            // take the input and store in the type variable
            result2.ifPresent(input -> type = input);

            int numberOfAppointments = getAppointmentsNumber(month, type);

            Dialog<String> dialog = new Dialog<>();
            //Setting the title
            dialog.setTitle("Dialog");
            ButtonType type2 = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            //Setting the content of the dialog
            dialog.setContentText("There are in total " + numberOfAppointments + " appointments in month " + month + ", with type='" + type + "'.");
            //Adding buttons to the dialog pane
            dialog.getDialogPane().getButtonTypes().add(type2);
            dialog.showAndWait();
        });

        Button2.setOnAction((event) -> {
            DetailsOFContactSchedules sc = new DetailsOFContactSchedules();
            sc.show();
            tohide();
        });

        Button3.setOnAction((event) -> {
            // Setting up dialog for taking year as input 
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Enter year you want to check appointments for e.g. 2021:  ");

            result = td.showAndWait();

            // take the input and store in the year variable
            result.ifPresent(input -> year = input);

            int numberOfAppointments = getAppointmentsNumberByYear(year);

            Dialog<String> dialog = new Dialog<>();
            //Setting the title
            dialog.setTitle("Dialog");
            ButtonType type2 = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            //Setting the content of the dialog
            dialog.setContentText("There are in total " + numberOfAppointments + " appointments dealt with in year " + year + ".");
            //Adding buttons to the dialog pane
            dialog.getDialogPane().getButtonTypes().add(type2);
            dialog.showAndWait();
        });

        // setting up action on back button
        backToMain.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // Move back to main menu and hide this frame
                toMainMenu();

            }
        });
        VBox vBox = new VBox();

        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        VBox.setMargin(Button1, new Insets(20, 20, 20, 20));
        VBox.setMargin(Button2, new Insets(20, 20, 20, 20));
        VBox.setMargin(Button3, new Insets(20, 20, 20, 20));
        VBox.setMargin(backToMain, new Insets(20, 20, 20, 20));
         vBox.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        //retrieving the observable list of the VBox 
        ObservableList list = vBox.getChildren();

        //Adding all the nodes to the observable list 
        list.addAll(Button1, Button2, Button3, backToMain);

        this.setTitle("Reports Form");
        Scene scene = new Scene(vBox, 700, 400);
        this.setScene(scene);
        
        this.setResizable(false);

    }

    /**
     * This method is used to provide data of appointments filtered with particular month and type
     * @param month parameter denotes the month for which we are searching appointments
     * @param type parameter denotes the type for which we are searching appointments
     * @return 
     */
    public static int getAppointmentsNumber(String month, String type) {

        int count = 0;
        if (month.length() == 1) {
            month = "0" + month;
        }
        try {
            Connection con = DatabaseConnection.CreateConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from appointments where start like '_____" + month + "____________' and type ='" + type + "'");

            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    
    /**
     * This method is used to get number of total appointments dealt with in any particular year
     * @param year this parameter denotes the year for which we are getting statistics.
     * @return 
     */
    public static int getAppointmentsNumberByYear(String year) {

        int count = 0;
        try {
            Connection con = DatabaseConnection.CreateConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from appointments where start like '" + year + "_______________'");

            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    /**
     * This method is used to navigate back to MainMenuForm and hiding current
     * form
     */
    public void toMainMenu() {

        MainMenu m = new MainMenu();
        m.show();
        tohide();

    }

    /**
     * This method is used to hide current form
     */
    void tohide() {
        this.hide();
    }

}
