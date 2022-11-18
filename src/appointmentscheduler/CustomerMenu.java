/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentscheduler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
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
 * this class is used to represent the customer menu to perform different type of opertions
 * @author
 */
public class CustomerMenu extends Stage {

    Optional<String> Result;
    int ID;

    /**
     * Constructor of customer Menu....
     */
    CustomerMenu() {
        // Defining button for the form....
        Button AddCustomer = null, updateCustomer = null, deleteCustomer = null, ViewCustomer = null, Back = null;

        String language = Locale.getDefault().getLanguage();

        Scene scene;

        // defining properties of above button....
        ViewCustomer = new Button("View Customers List");
        ViewCustomer.setMinWidth(200);
        
        ViewCustomer.setFont(new Font("Times New Roman",20));
        ViewCustomer.setStyle("-fx-background-color:Green ");
        ViewCustomer.setTextFill(Color.WHITE);
        
        
        AddCustomer = new Button("Add Customer");
        AddCustomer.setMinWidth(200);
        
        
        AddCustomer.setFont(new Font("Times New Roman",20));
        AddCustomer.setStyle("-fx-background-color:Green ");
        AddCustomer.setTextFill(Color.WHITE);

        updateCustomer = new Button("Update existing customers");
        updateCustomer.setMinWidth(200);
        
        updateCustomer.setFont(new Font("Times New Roman",20));
        updateCustomer.setStyle("-fx-background-color:Green ");
        updateCustomer.setTextFill(Color.WHITE);

        deleteCustomer = new Button("Delete Customers");
        deleteCustomer.setMinWidth(200);
        
        deleteCustomer.setFont(new Font("Times New Roman",20));
        deleteCustomer.setStyle("-fx-background-color:Green ");
        deleteCustomer.setTextFill(Color.WHITE);

        Back = new Button("Back");
        Back.setMinWidth(200);
        
        Back.setFont(new Font("Times New Roman",20));
        Back.setStyle("-fx-background-color: Green ");
        Back.setTextFill(Color.WHITE);
        
        

        // Defining action for back button
        Back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // hide this frame and nevigate to the back menu
                MainMenu();

            }
        });
        // defining action for view customers button
        ViewCustomer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                // hide the current frame and go to the showCustomerRecord frame
                ShowCustomerRecords showcustomerRecord = new ShowCustomerRecords();
                showcustomerRecord.show();
                hideCurrentFrame();

            }
        });

        // defining action for Add customers button
        AddCustomer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // hiding current frame and initializing the add customer frame
                AddCustomer c = new AddCustomer(new Customer());
                c.show();
                hideCurrentFrame();

            }
        });

        // defining action for Update customers button
        updateCustomer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // defining dialog to take the customer id input to update customer record..
                TextInputDialog idInputDialog = new TextInputDialog();
                idInputDialog.setHeaderText("Enter customer ID: ");

                Result = idInputDialog.showAndWait();

                // tale id input and save it.....
                Result.ifPresent(input -> ID = Integer.parseInt(input));
                // check whether the customer with the given id exist...
                Customer customer = checkCustomerRecordFotID(ID);

                // if customer with given id does not exist show dialog message...
                if (customer == null) {
                    Dialog<String> ErrorDailog = new Dialog<String>();
                    //Defining the title
                    ErrorDailog.setTitle("Message");
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    //Defining the message dialog content....
                    ErrorDailog.setContentText("customer with the ID " + ID + " does not exist, please try with a valid customer ID.");
                    //Adding buttons to the message dialog 
                    ErrorDailog.getDialogPane().getButtonTypes().add(type);
                    ErrorDailog.showAndWait();
                } // if the customer with the requested id exist then fetch the customer details and display it in the add customer frame
                else {
                    AddCustomer addcustomer = new AddCustomer(customer);
                    addcustomer.show();
                    hideCurrentFrame();
                }

            }
        });

        // Defining action for delete customers button
        deleteCustomer.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {

                // defining input dialog to take customer id input....
                TextInputDialog td = new TextInputDialog();
                td.setHeaderText("Enter customer ID: ");

                Result = td.showAndWait();

                // store the input id in the ID variables
                Result.ifPresent(input -> ID = Integer.parseInt(input));
                // check whether the customer with the given id exist or not....
                Customer customer = checkCustomerRecordFotID(ID);
                // if the customer with the given id does not exist show the error message
                if (customer == null) {
                    Dialog<String> ErrorDialog = new Dialog<String>();
                    //Defining title
                    ErrorDialog.setTitle("Error Message");
                    
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    //defining the content of the error message dailog...
                    ErrorDialog.setContentText("Customer with the given input ID :" + ID + " does not exist, please try with a valid ID.");
                    //Adding buttons to the Error dialog 
                    ErrorDialog.getDialogPane().getButtonTypes().add(type);
                    ErrorDialog.showAndWait();
                }
                // if customer with the requested input id exist then delete that customer and display successfull message.....
                if (deleteCustomer(ID)) {

                    Dialog<String> MessageDialog = new Dialog<String>();
                    //Difining the title
                    MessageDialog.setTitle("Message");
                    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
                    //defining the content of the given message dailog....
                    MessageDialog.setContentText("Customer with the given input id " + ID + " deleted successfully.");
                    //Adding buttons to the message dailog.....
                    MessageDialog.getDialogPane().getButtonTypes().add(type);
                    MessageDialog.showAndWait();

                }
                else{
                    Dialog<String> dialog = new Dialog<>();
                        //Defining title
                        dialog.setTitle("Error Dialog");
                        ButtonType btype = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        //defining the content of the error dailog
                        dialog.setContentText("Some problem encounter during deletion. please try again later");
                        //Adding buttons to the error dailog...
                        dialog.getDialogPane().getButtonTypes().add(btype);
                        dialog.showAndWait();
                
                }
            }
        });
        VBox box = new VBox();

        box.setSpacing(5);
        box.setAlignment(Pos.CENTER);
        VBox.setMargin(ViewCustomer, new Insets(20, 20, 20, 20));
        VBox.setMargin(AddCustomer, new Insets(20, 20, 20, 20));
        VBox.setMargin(updateCustomer, new Insets(20, 20, 20, 20));
        VBox.setMargin(deleteCustomer, new Insets(20, 20, 20, 20));
        VBox.setMargin(Back, new Insets(20, 20, 20, 20));
         box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        //get the observable list of the Vbox 
        ObservableList boxList = box.getChildren();

        //Adding all the nodes to the observable list 
        boxList.addAll(ViewCustomer, AddCustomer, updateCustomer, deleteCustomer, Back);

        scene = new Scene(box, 500, 500);
        this.setScene(scene);
        this.setTitle("Customer Main Menu");
        
        this.setResizable(false);

    }

    /**
     * this method navigate the to the main menu....
     */
    public void MainMenu() {

        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
        hideCurrentFrame();

    }

    /**
     * method used to hide current form...
     */
    void hideCurrentFrame() {
        this.hide();
    }

    /**
     * this method check that whether the customer with specific id exist or not in the database....
     *
     * @param id represent  the customer ID
     * @return customer details of the given id if exist otherwise null..
     */
    public Customer checkCustomerRecordFotID(int id) {
        Connection connection = DatabaseConnection.CreateConnection();
        Statement statment;
        try {
            statment = connection.createStatement();

            ResultSet resultSet = statment.executeQuery(" select * from customers where Customer_ID = " + id);
            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setID(Integer.parseInt(resultSet.getString("Customer_Id")));
                customer.setCustomerName(resultSet.getString("Customer_Name"));
                customer.setCustomerAddress(resultSet.getString("Address"));
                customer.setCustomerphone(resultSet.getString("Phone"));
                customer.setCustomerpostalCode(resultSet.getString("Postal_Code"));
                ResultSet resultstatment2 =statment.executeQuery("Select * from first_level_divisions where Division_ID="+resultSet.getInt("Division_ID"));
                resultstatment2.next();
                int country_id = resultstatment2.getInt("Country_ID");
                customer.setCustomerDivision(resultstatment2.getString("Division"));
                
                resultstatment2 =statment.executeQuery("Select Country from countries where Country_ID="+country_id);
                resultstatment2.next();
                
            
                customer.setCustomerCountry(resultstatment2.getString("country"));
                return customer;

            }

        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    /**
     * this method is used to delete the customer of specific id
     *
     * @param id represent customer id
     * @return true when customer is successfully deleted else return false...
     * 
     */
    public boolean deleteCustomer(int id) {
        Connection connection = DatabaseConnection.CreateConnection();
        Statement statment;

        try {
            statment = connection.createStatement();
            statment.executeUpdate("delete from appointments where Customer_ID = " + id);
            int checkbol = statment.executeUpdate(" delete from customers where Customer_ID = " + id);
            if (checkbol == 0) {
                return false;
            }

        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

}
