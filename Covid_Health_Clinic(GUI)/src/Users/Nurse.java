package Users;

import java.util.*;
/**
 * Subclass that extends the class Staff with those variables and methods that are 
 * unique to the members of the staff that are nurses
 *
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class Nurse extends Staff {
	 
	 private HashMap<String, Object[]> jabsAgenda;
	 private ArrayList<Integer> weeksOfYear;    
    
    /**
     * Constructor for objects of class Nurse
     */
    public Nurse(String name, String id, String phoneNum, String birthDate)
    {
    	super(name, id, phoneNum, birthDate);
        setPosition("Nurse");
        weeksOfYear = new ArrayList<Integer>();
        jabsAgenda = new HashMap<>();

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
     * Returns a HashMap with the scheduled vaccination appointments
     *
     * @return appAgenda
     */
    public HashMap<String, Object[]> getJabsAgenda()
    {
        return jabsAgenda;
       
    }
    
    /**
     * Adds a new vaccination appointment to the scheduled vaccinations HashMap
     *
     * @param appointmentID (key of a new appointment) object (value of a new appointment)
     */
    public void addJabAppointment(String appointmentID, Object[] object)
    {
        if(object != null) {
        	jabsAgenda.put(appointmentID, object);
        }
    }
    
      
    
    /**
     * Calculates the weekly appointments carried away by a nurse iterating through weeksOfYear. 
     * If the frequency of a values equals 5, the maximum of appointments has been booked
     * for that particular week
     * 
     * @return false if reached max of appointments booked for a week, true if not
     */
    public boolean weeklyAppointments(int weekOfYear)
    {
      
       for(int i: weeksOfYear){
         if( i == weekOfYear && Collections.frequency(weeksOfYear,i) == 5){
           return false;  
         }
       }
       return true;
    }

}