package appointmentscheduler;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * this Main class from where the application flow start....
 * 
 * @author 
 */
public class MAINCLASS extends Application {

    /**
     * On application start initialize and display SignIn frame
     *
     * @param InitailSart initial start of the application
     */
    @Override
    public void start(Stage InitailSart) {

        SIGNIN start = new SIGNIN();
        start.show(); 
      
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
