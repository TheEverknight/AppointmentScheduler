package appointmentscheduler;

/**
 * this is the appointment class which show the details attribute of the appointment class
 *
 * @author
 */
public class Appointment{

    int AppID;
    String Title;
    String AppointmentDescription;
    String AppLocation;
    String appContact;
    String appType;
    String startDataAndTime;
    String AppEndDate;
    int customerID;
    int UserID;

    public int getAppID() {
        return AppID;
    }

    public void setAppID(int appID) {
        this.AppID = appID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getAppointmentDescription() {
        return AppointmentDescription;
    }

    public void setAppointmentDescription(String AppointmentDescription) {
        this.AppointmentDescription = AppointmentDescription;
    }

    public String getAppLocation() {
        return AppLocation;
    }

    public void setAppLocation(String AppLocation) {
        this.AppLocation = AppLocation;
    }

    public String getAppContact() {
        return appContact;
    }

    public void setAppContact(String appContact) {
        this.appContact = appContact;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getStartDataAndTime() {
        return startDataAndTime;
    }

    public void setStartDataAndTime(String startDataAndTime) {
        this.startDataAndTime = startDataAndTime;
    }

    public String getAppEndDate() {
        return AppEndDate;
    }

    public void setAppEndDate(String AppEndDate) {
        this.AppEndDate = AppEndDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + AppID + ", title=" + Title + ", description=" + AppointmentDescription + ", location=" + AppLocation + ", contact=" + appContact + ", type=" + appType + ", startDateTime=" + startDataAndTime + ", endDateTime=" + AppEndDate + ", customerId=" + customerID + ", userId=" + UserID + '}';
    }

}
