/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentscheduler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *this class is used to convert time into UTC zone before storing into database...
 * @author ADMIN
 */
public class TimeConversion {
    
    /**
     * this method is used to convert Appointment time into UTC zone...... 
     * @param dateTime this parameter is data time variable which need to convert into UTC zone...
     * @return return UTC zone date time string........................
     */
    public static String fromLocalToUTCzone(String dateTime) {
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        LocalDateTime localdatetime = timestamp.toLocalDateTime();
        ZonedDateTime zonedatetime = localdatetime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczonedatetime = zonedatetime.withZoneSameInstant(ZoneId.of("UTC"));

        return utczonedatetime.toString();
    }
     /**
     * this method work opposite to the above method. it convert from UTC zone to local time zone...
     * @param dateTime this parameter is data time parameter which need to be convert to local zone From UTC time zone
     * @return date time string in local zone
     */
    public static String fromUtcToLocalzone(String dateTime) {
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        LocalDateTime localdatetime = timestamp.toLocalDateTime();
        ZonedDateTime zonedateTime = localdatetime.atZone(ZoneId.of("UTC"));
        ZonedDateTime localzonedatetime = zonedateTime.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        return localzonedatetime.toString();

    }
}
