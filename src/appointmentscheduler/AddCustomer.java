package appointmentscheduler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javafx.collections.FXCollections;
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
 * this class is used to add new customer or update existing customer.....
 */
public class AddCustomer extends Stage {

    String language = Locale.getDefault().getLanguage();
    ComboBox Divisions;
    TextField ID;

    /**
     * this constructor display the field of add customer in the adding new customer scenario...
     * and display the existing customer details in the fields while updating existing customer.
     *Lambda expression are used in this Constructor....
     * @param customer this is customer object in case of existing customer... 
     *  customer parameter is null in case of adding new customer
     */
    AddCustomer(Customer customer) {
        // VBox set up elements in form UI
        VBox box = new VBox();

        box.setSpacing(8);
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setPadding(new Insets(10, 10, 10, 10));
        box.setBackground(new Background(new BackgroundFill(Color.rgb(0,255,255), CornerRadii.EMPTY, Insets.EMPTY)));
        // degining all required labels ......
        Label Namelabel, addressLabel, postalLabel, phoneLabel, countryLabel, devisionLabel;
        // defining all required text fields......

        TextField nametextFields, addressTextfield, phoneTextfield, postalTextfield;

        Namelabel = new Label("Name");
        nametextFields = new TextField(customer.getCustomerName());
        addressLabel = new Label("Address i.e 345 CDE Street, black cana");
        addressTextfield = new TextField(customer.getCustomerAddress());
        postalLabel = new Label("Postal Code");
        postalTextfield = new TextField(customer.getCustomerpostalCode());
        phoneLabel = new Label("Phone No");
        phoneTextfield = new TextField(customer.getCustomerphone());
        countryLabel = new Label("Country");
        devisionLabel = new Label("State/Province");

        // Adding countries to the countriesList combo....
        ComboBox countriesList = new ComboBox();
        countriesList.getItems().add("Canada");
        countriesList.getItems().add("UK");
        countriesList.getItems().add("USA");
        
        countriesList.setValue(customer.getCustomerCountry());

        // Adding devisions to the USADivisions combo...
        ComboBox USADivisions = new ComboBox();
        USADivisions.getItems().add("Washington");
        USADivisions.getItems().add("Arizona");
        USADivisions.getItems().add("Ohio");
        USADivisions.getItems().add("Georgia");
        USADivisions.getItems().add("Colorado");
        USADivisions.getItems().add("California");
        USADivisions.getItems().add("Texas");
        USADivisions.getItems().add("Florida");
        USADivisions.getItems().add("Alaska");
        USADivisions.getItems().add("Virginia");
        

        // adding the division to the UKDivision combo box...
        ComboBox UKDivisions = new ComboBox();
        UKDivisions.getItems().add("Scotland");
        UKDivisions.getItems().add("Wales");
        UKDivisions.getItems().add("England");
        UKDivisions.getItems().add("Northern Ireland");
        

        // Inserting Divisions to the CanadaDivisions.....
        ComboBox CanadaDivisions = new ComboBox();
        CanadaDivisions.getItems().add("Newfoundland and Labrador");
        CanadaDivisions.getItems().add("British Columbia");
        CanadaDivisions.getItems().add("Manitoba");
        CanadaDivisions.getItems().add("Nunavut");
        CanadaDivisions.getItems().add("Alberta");
        CanadaDivisions.getItems().add("New Brunswich");
        CanadaDivisions.getItems().add("Nova Scotia");
        CanadaDivisions.getItems().add("Northwest territories");
        
       
        
        
       

        // display the divisions of the customer country
        Divisions = new ComboBox();

        Divisions.setValue(customer.getCustomerDivision());

        // Implementation of event listener while selecting country from countriesList como box....
        EventHandler<ActionEvent> event
                = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // if the selected countries is USA then insert the USADivision in the Division comobox...
                if (countriesList.getValue().equals("USA")) {
                    Divisions.setItems(FXCollections.observableArrayList(USADivisions.getItems()));
                    Divisions.setValue("");
                } // if the selected countries is UK then insert the UKDivision in the Division comobox...
                else if (countriesList.getValue().equals("UK")) {
                    Divisions.setItems(FXCollections.observableArrayList(UKDivisions.getItems()));
                    Divisions.setValue("");

                } // if the selected countries is Canada then insert the CanadaDivision in the Division comobox...
                else if (countriesList.getValue().equals("Canada")) {
                    Divisions.setItems(FXCollections.observableArrayList(CanadaDivisions.getItems()));
                    Divisions.setValue("");

                }
            }
        };

        // adding above event on countries box
        countriesList.setOnAction(event);
        // Defining button to perform different actions....
        Button AddButton = new Button("Add");
        Button updatButton = new Button("Update Existing Customer");
        Button backButton = new Button("Back");
        
        // defining properties of button
        AddButton.setFont(new Font("Times New Roman",15));
        AddButton.setStyle("-fx-background-color: Green ");
        
         updatButton.setFont(new Font("Times New Roman",15));
        updatButton.setStyle("-fx-background-color: Green ");
        
         backButton.setFont(new Font("Times New Roman",15));
        backButton.setStyle("-fx-background-color: Green ");
        // if the customer received as paramet is null then display All UI elemet except ID

        if (customer.ID == 0) {
            box.getChildren().addAll(Namelabel,
                    nametextFields,
                    addressLabel,
                    addressTextfield,
                    countryLabel,
                    countriesList,
                    devisionLabel,
                    Divisions,
                    postalLabel,
                    postalTextfield,
                    phoneLabel,
                    phoneTextfield,
                    AddButton,
                    backButton);

            this.setTitle("Add Customers");

        } // if received customer instance is not null then create all UI element along with the ID...
        else {
            ID = new TextField(customer.getID() + "");
            // make disable ID feild
            ID.setEditable(false);
            box.getChildren().addAll(new Label("Id"),
                    ID,
                    Namelabel,
                    nametextFields,
                    addressLabel,
                    addressTextfield,
                    countryLabel,
                    countriesList,
                    devisionLabel,
                    Divisions,
                    postalLabel,
                    postalTextfield,
                    phoneLabel,
                    phoneTextfield,
                    updatButton,
                    backButton);

            this.setTitle("Update Customers");
        }
        // defining event action for add button using lamda expression
        AddButton.setOnAction((e) -> {
            System.out.println(nametextFields.getText());
            System.out.println(addressTextfield.getText());
            System.out.println(countriesList.getValue());
            System.out.println(Divisions.getValue());
            System.out.println(postalTextfield.getText());
            System.out.println(phoneTextfield.getText());
            // when add button is pressed if there is any empty field then show the error..
            if (nametextFields.getText() == null || nametextFields.getText().isEmpty() || addressTextfield.getText() == null || addressTextfield.getText().isEmpty() || countriesList.getValue() == null || countriesList.getValue().toString().isEmpty() || Divisions.getValue() == null || Divisions.getValue().toString().isEmpty() || postalTextfield.getText().isEmpty() || phoneLabel.getText().isEmpty()) {
                Dialog<String> emptyyfeildMessageDailog = new Dialog<>();
                //Defining the title
                emptyyfeildMessageDailog.setTitle("Dialog");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Defining the content of the message dailog...
                emptyyfeildMessageDailog.setContentText("please filled all the feilds..");
                //Adding buttons to the message dailog pane
                emptyyfeildMessageDailog.getDialogPane().getButtonTypes().add(type);
                emptyyfeildMessageDailog.showAndWait();
            } // if all the required fields are filled then add the customer to the database...
            else {
                Customer customerObject = new Customer();
                customerObject.setCustomerAddress(addressTextfield.getText());
                customerObject.setCustomerCountry(countriesList.getValue().toString());
                customerObject.setCustomerDivision(Divisions.getValue().toString());
                customerObject.setCustomerName(nametextFields.getText());
                customerObject.setCustomerpostalCode(postalTextfield.getText());
                customerObject.setCustomerphone(phoneTextfield.getText());

                // Check if appointment is successfully added to the database
                boolean b = addCustomerTotheDatabase(customerObject);
                if (b) {
                    Dialog<String> messageDialog = new Dialog<String>();
                    //Setting the title
                    messageDialog.setTitle("Message");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //Setting the content of the dialog
                    messageDialog.setContentText("Customer Successfully added");
                    //Adding buttons to the Message dialog pane
                    messageDialog.getDialogPane().getButtonTypes().add(type);
                    messageDialog.showAndWait();
                    nametextFields.setText("");
                    addressTextfield.setText("");
                    postalTextfield.setText("");
                    phoneTextfield.setText("");
                    ID.setText("");
                    countriesList.setValue("Select any country");
                    Divisions.setValue("");
                }
                else{
                      Dialog<String> dialog = new Dialog<String>();
                    //Setting the title
                    dialog.setTitle("Dialog");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //Setting the content of the dialog
                    dialog.setContentText("Some problem occured while adding customer, check input data and try again.");
                    //Adding buttons to the dialog pane
                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();
                }
            }

        });
        // defining back button functionality to navigate to the back menu....
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (language.equals("en")) {
                    navigateToCustomerMenu();

                } else if (language.equals("fr")) {

                    

                }
            }
        });
        // defining action for the update button....
        updatButton.setOnAction((event2) -> {
            // after pressing the update button if there is empty field then prompt the user to filled that field....

            if (nametextFields.getText() == null || nametextFields.getText().isEmpty() || addressTextfield.getText() == null || addressTextfield.getText().isEmpty() || countriesList.getValue() == null || countriesList.getValue().toString().isEmpty() || Divisions.getValue() == null || Divisions.getValue().toString().isEmpty() || postalTextfield.getText().isEmpty() || phoneLabel.getText().isEmpty()) {
                Dialog<String> messageDailog = new Dialog<>();
                //Defining the title
                messageDailog.setTitle("Message");
                ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                //Defining the content of the dialog
                messageDailog.setContentText("Please fill all the fields...");
                //add button to message dailog button......
                messageDailog.getDialogPane().getButtonTypes().add(type);
                messageDailog.showAndWait();
            } // if all the fields are filled then add th customer to the database...
            else {
                Customer customerObject = new Customer();
                customerObject.setID(Integer.parseInt(ID.getText()));
                customerObject.setCustomerAddress(addressTextfield.getText());
                customerObject.setCustomerCountry(countriesList.getValue().toString());
                customerObject.setCustomerDivision(Divisions.getValue().toString());
                customerObject.setCustomerName(nametextFields.getText());
                customerObject.setCustomerpostalCode(postalTextfield.getText());
                customerObject.setCustomerphone(phoneTextfield.getText());
                boolean b = updateCustomerRecord(customerObject);
                // if the customer is updated successfully then display successfull message...
                if (b) {
                    Dialog<String> messageDailog = new Dialog<>();
                    //Defining the title
                    messageDailog.setTitle("Message");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //Defining the content of the message dialog
                    messageDailog.setContentText("customer is successfuly updated.");
                    //Adding buttons to the message dialog pane
                    messageDailog.getDialogPane().getButtonTypes().add(type);
                    messageDailog.showAndWait();
                    nametextFields.setText("");
                    addressTextfield.setText("");
                    postalTextfield.setText("");
                    phoneTextfield.setText("");

                    countriesList.setValue("Select any country");
                    Divisions.setValue("");
                    ID.setText("");
                }// if there is any problem while updating
                else{
                     Dialog<String> messageDialog = new Dialog<>();
                    //Defining the title
                    messageDialog.setTitle("Error");
                    ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    //defining the content of the Error dialog
                    messageDialog.setContentText("Some problem accure during updating customer data. please check input data and try again.");
                    //Adding buttons to the Error dialog pane
                    messageDialog.getDialogPane().getButtonTypes().add(type);
                    messageDialog.showAndWait();
                  
                }
            }

        });
        Scene scene = new Scene(box, 500, 500);
        this.setScene(scene);
        this.setTitle("Customer Main operations");
        this.setResizable(false);

    }

    /**
     * this method navigate the user to the customer to main menu.... 
     */
    public void navigateToCustomerMenu() {

        CustomerMenu m = new CustomerMenu();
        m.show();
        this.hide();

    }

    /**
     * this method is used to add customer to the database
     *
     * @param customerinstance refer to the customer instance that need to be to store...
     * @return boolean true if the customer is added successful else false
     */
    public boolean addCustomerTotheDatabase(Customer customerinstance) {
        Connection connection = DatabaseConnection.CreateConnection();
        Statement statment;

        try {
            statment = connection.createStatement();
            ResultSet rs =statment.executeQuery("Select * from first_level_divisions where Division='"+customerinstance.getCustomerDivision()+"'");
            rs.next();
            int div_id = rs.getInt("Division_ID");
            String query = "insert into customers(CUSTOMER_Name,Address,Postal_Code,Phone,Division_ID) values('" + customerinstance.getCustomerName() + "','" + customerinstance.getCustomerAddress() + "','" + customerinstance.getCustomerpostalCode() + "','" + customerinstance.getCustomerphone() + "','" + div_id+"');";
            int a = statment.executeUpdate(query);
            if (a == 0) {
                return false;
            }

        } catch (SQLException ex) {
             System.out.print(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * this method update the existing the customer.....
     *
     * @param customerInstance refer to the customer instance that need to be updated and
     * needs to be stored in database.
     * @return boolean true if the customer is added successful else return null...
     */
    public boolean updateCustomerRecord(Customer customerInstance) {
        Connection connection = DatabaseConnection.CreateConnection();
        Statement statment;

        try {
            statment = connection.createStatement();
            
            ResultSet rs =statment.executeQuery("Select * from first_level_divisions where Division='"+customerInstance.getCustomerDivision()+"'");
            rs.next();
            int div_id = rs.getInt("Division_ID");
            String query = "update customers set Customer_Name='" + customerInstance.getCustomerName() + "',address='" + customerInstance.getCustomerAddress() + "',postal_code='" + customerInstance.getCustomerpostalCode() + "',phone='" + customerInstance.getCustomerphone() + "',Division_ID="+div_id+" where Customer_ID=" + customerInstance.getID();
            int a = statment.executeUpdate(query);
            if (a == 0) {
                return false;
            }

        } catch (SQLException ex) {
             System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

}
