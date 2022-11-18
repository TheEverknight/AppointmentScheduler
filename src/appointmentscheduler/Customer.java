package appointmentscheduler;

/**
 * this class show the customer details which will be stored in the database and different operation will be perform on this...
 *
 * @author
 */
public class Customer {

    
    String customerCountry;
    int ID;
    String customerName;
    String customerAddress;
    String CustomerpostalCode;
    String Customerphone;
    String customerDivision;

    public String getCustomerDivision() {
        return customerDivision;
    }

    public void setCustomerDivision(String customerDivision) {
        this.customerDivision = customerDivision;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    Customer() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerpostalCode() {
        return CustomerpostalCode;
    }

    public void setCustomerpostalCode(String CustomerpostalCode) {
        this.CustomerpostalCode = CustomerpostalCode;
    }

    public String getCustomerphone() {
        return Customerphone;
    }

    public void setCustomerphone(String Customerphone) {
        this.Customerphone = Customerphone;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + ID + ", name=" + customerName + ", address=" + customerAddress + ", postalCode=" + CustomerpostalCode + ", phone=" + Customerphone + '}';
    }

}
