package Project;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
/**
* This class defines the variables and methods that manage the time events
* 
* @author Isabel Manzaneque
* @version 0.1
*/
public class Calendar {
	
  private LocalDate todaysDate;
  
  /**
  Constructor for objects of class Calendar
   * 
   */
  public Calendar()
  {
     todaysDate = LocalDate.now(); 
  }
    
  /**
   * Returns today's local date
   *  
   * @return: todaysDate
   */
  public LocalDate getTodaysDate()
  {
      return todaysDate;
  }
  
  /**
   * Makes the system stop fos two seconds
   */
  public void standBy()
  {
      try{
          Thread.sleep(2000);
      }catch(InterruptedException ex){
          Thread.currentThread().interrupt();
      }
  }
  
  /**
   * Adds one day to today's date to allow time dependant events
   *  
   */
  public void addOneDay()
  {       
     todaysDate = getTodaysDate().plusDays(1);
  }
  
  /**
   * Receives a String variable of format "yyyy/MM/dd" and turns it
   * into a LocalDate variable of the same format
   * 
   * @param  fecha, String of format "yyyy/MM/dd"
   * @return date, LocalDate variable
   */
  public LocalDate stringToDate(String fecha)
  {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      LocalDate date = LocalDate.parse(fecha, formatter);
      return date;        
  }
  
  /**
   * Receives a LocalDate variable and turns it into a String variable 
   * of format "yyyy/MM/dd" 
   * into a LocalDate variable of the same format
   * 
   * @param:  fecha, LocalDate variable 
   * @return: date, String of format "yyyy/MM/dd"
   */
  public String dateToString(LocalDate fecha)
  {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      String date = fecha.format(formatter);   
      return date;
  }
         
  /**
   * Checks if the inserted date is before today's date and if so, returns true.
   * Otherwise, return false
   *
   * @param:  fecha, variable de tipo String
   * @return  date.isBefore(fechaHoy), expresion booleana 
   */
  public boolean isBefore(String fecha)
  {
      LocalDate date = stringToDate(fecha); 
      if (date.isBefore(todaysDate)){
          System.out.println("Insert a future date");
      }
      return date.isBefore(todaysDate);          
  }
         
  /**
   * Receives a String date variable and returns the integer corresponding to the 
   * week of the year
   * 
   * @param:  fecha 
   * @return: weekOfYear 
   */
  public int getWeekOfYear(String fecha)
  {
      WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
      LocalDate date = stringToDate(fecha);
      int weekOfYear = date.get(weekFields.weekOfWeekBasedYear());
      return weekOfYear;
  } 
  
  /**
   * Calculates the number of days between two dates
   * 
   * @param  fecha1, fecha2, String variables
   * @return  ChronoUnit.DAYS.between(date1, date2), days passed between two days
   */
  public long getDaysBetweenDates(String fecha1, String fecha2)
  {        
      LocalDate date1 = stringToDate(fecha1);
      LocalDate date2 = stringToDate(fecha2);
      return ChronoUnit.DAYS.between(date1, date2); 
  }
  
  /**
   * Checks that the inserted variable is of format "yyyy/MM/dd". If the format is correct,
   * returns true and otherwise false
   * 
   * @param:  input
   * @return  checkFormat
   */
  public boolean checkFormat(String input)
  {
      boolean checkFormat = true;
      if (input.matches("([0-9]{4})/([0-9]{2})/([0-9]{2})")){
          checkFormat=true;
      }else{  
         checkFormat=false;
         System.out.println("Invalid inserted format, try again");
      }
      return checkFormat;
  } 
}