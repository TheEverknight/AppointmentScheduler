package appointmentscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * this is the class is used to connect to the database......
 *
 * @author
 */
public class DatabaseConnection {

    /**
     * Singleton pattern is used here to prevent to create multiple instant of the class
     */
    private DatabaseConnection() {

    }
    static Connection connection = null;

    /**
     * this method is used singleton design pattern. and create only one connection instance to prevent multiple
     * multiple instance
     * @return
     */
    public static Connection CreateConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver Does not exist");
            }

            try {
                // database data........
                String URL = "jdbc:mysql://localhost:3306/client_schedule";
                // user name for the database
                String username = "sqlUser";
                // database password 
                String password = "Passw0rd!";
                connection = DriverManager.getConnection(URL, username, password);

            } catch (SQLException e) {
            }
        }
        return connection;
    }
}
