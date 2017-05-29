package ${package}.util.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Delsael Kenji <kenji@delsael.com>, Original Author
 */
public class DateHelper {

  /**
   * Remove time from date.
   * 
   * @param Date date
   * @return Date
   */
  public static Date removeTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    return cal.getTime();
  }

  /**
   * Convert date to localDate.
   * 
   * @param Date date
   * @return LocalDate
   */
  public static LocalDate toLocalDate(Date date) {
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate;
  }

  /**
   * Get the date of the day before a certain date
   * 
   * @param Date date
   * @return Date date
   */
  public static Date getDayBeforeDate(Date date){
    Calendar cal = Calendar.getInstance(); 
    cal.setTime(date);
    cal.add(Calendar.DATE, -1);

    return cal.getTime();
  }

}
