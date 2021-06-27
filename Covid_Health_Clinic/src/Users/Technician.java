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

	
    private HashMap<String, Object[]> appAgenda;      //Agenda of scheduled appointments
    private ArrayList<Integer> weeksOfYear;           
    
    /**
     *Constructor for objects of class Technician
     */
    public Technician(String name, String id, String phoneNum, String birthDate)
    {   
        
        super(name, id, phoneNum, birthDate);
        setPosition("Technician");
        appAgenda = new HashMap<>();
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
     * Returns a HashMap with the appointments scheduled for the technician
     *
     * @return appAgenda
     */
    public HashMap<String, Object[]> getAppAgenda()
    {
        return appAgenda;
       
    }
    
    /**
     * Iterates through the appointments agenda and adds position [1] of each value (the
     * patient) to the ArrayList assignedPatients. Then it returns the assigned patients ArrayList
     *
     * @return: assignedPatients
     */
    public ArrayList<Object> getAssignedPatients()
    {   
       ArrayList<Object> assignedPatients = new ArrayList<>();
       getAppAgenda().forEach((key, value) -> 
      {
        Object[] valor = (Object[]) value;
        if(!assignedPatients.contains(valor[1])){
          assignedPatients.add(valor[1]); 
        }                          
      });
      return assignedPatients;
    }
    
   
    /**
     * Adds a new appointment to the scheduled appointments HashMap
     *
     * @param appointmentID (key of a new appointment) object (value of a new appointment)
     */
    public void addAppointment(String appointmentID, Object[] object)
    {
        if(object != null) {
            appAgenda.put(appointmentID, object);
        }
    }
    
    /**
     * Deletes an appointment from the system
     *
     * @param: key of the entry to remove
     */
    public void removeAppointment(String key)
    {
        if(appAgenda.containsKey(key)){
            appAgenda.remove(key);
        }
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
       boolean aux = true;
       for(int i: weeksOfYear){
         if( i == weekOfYear && Collections.frequency(weeksOfYear,i) == 4){
           aux = false;  
         }
       }
       return aux;
    }

}