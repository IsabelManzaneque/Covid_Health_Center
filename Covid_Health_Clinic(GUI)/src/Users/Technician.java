package Users;
import java.util.*;
/**
 * Subclass that extends the class Staff with those variables and methods that are 
 * unique to the members of the staff that are Technicians
 *
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class Technician extends Staff{

	
    private ArrayList<Integer> weeksOfYear;           
    
    /**
     *Constructor for objects of class Technician
     */
    public Technician(String name, String id, String phoneNum, String birthDate)
    {   
        
        super(name, id, phoneNum, birthDate);
        setPosition("Technician");
        weeksOfYear = new ArrayList<Integer>();
    }
    
    /**
     * Returns the integers of the the weeks of the year in which the appointments were scheduled
     
     * @return weeksOfYear
     */
    public ArrayList<Integer> getWeeksOfYear()
    {
        return weeksOfYear;
    }
    
          
    
    /**
     * Calculates the weekly appointments carried away by a technician iterating through weeksOfYear. 
     * If the frequency of a values equals 4, the maximum of appointments has been booked
     * for that particular week
     * 
     * @return false if reached max of appointments booked for a week, true if not
     */
    public boolean weeklyAppointments(int weekOfYear)
    {
      
       for(int i: weeksOfYear){
         if( i == weekOfYear && Collections.frequency(weeksOfYear,i) == 4){
             return false; 
         }
       }
       return true;
    }

}