package appointmentscheduler;

/**
 *this class is used to stored the user data....
 *
 * @author ADMIN
 */
public class UserFrame {

    String username;
    String password;
    int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
  

    @Override
    public String toString() {
        return "User{" + "id=" + ID + ", name=" + username + ", pass=" + password + '}';
    }

}
