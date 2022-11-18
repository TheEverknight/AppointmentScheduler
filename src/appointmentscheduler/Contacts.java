package appointmentscheduler;

/**
 * this class show the Contact details which will be stored in the database and different operation will be perform on this...
 *
 * @author
 */
public class Contacts {



    int ID;
    String ContactName;
    String Contactphone;
    String ContactEmail;
    String ContactAppTypes;
    String Availability;

    //Appointment Info
    int AppID;
    String Title;
    String AppointmentDescription;
    String AppointmentStart;
    String AppEnd;

    Contacts() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        this.ContactName = contactName;
    }

    public String getContactphone() {
        return Contactphone;
    }

    public void setContactphone(String Contactphone) {
        this.Contactphone = Contactphone;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String ContactEmail) {
        this.ContactEmail = ContactEmail;
    }

    public void SetContactAppTypes(String Type){ this.ContactAppTypes = Type; }

    public String getContactAppTypes(){ return this.ContactAppTypes; }

    public void SetAvailability(String Availabiliity){ this.Availability = Availabiliity; }

    public String getAvailability(){ return this.Availability; }

    public void SetAppID(int _AppID){

        this.AppID =_AppID;
    }

    public int getAppID(){

        return this.AppID;
    }

    public void SetTitle(String _AppointmentTitle){

        this.Title = _AppointmentTitle;
    }

    public String getTitle(){

        return this.Title;
    }

    public void SetAppointmentDescription(String _AppointmentDescription){

        this.AppointmentDescription = _AppointmentDescription;
    }

    public String getAppointmentDescription(){

        return this.AppointmentDescription;
    }

    public void SetAppointmentStart(String _AppStart){

        this.AppointmentStart = _AppStart;
    }

    public String getAppointmentStart(){

        return this.AppointmentStart;
    }

    public void SetAppEnd(String _AppEnd){

        this.AppEnd = _AppEnd;
    }

    public String getAppEnd(){

        return this.AppEnd;
    }




    @Override
    public String toString() {
        return "Customer{" + "id=" + ID + ", name=" + ContactName + ", phone=" + Contactphone + ", Email=" + ContactEmail + ", Types=" + ContactAppTypes + '}';
    }

}
