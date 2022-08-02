package Users;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Subclass that extends the class Person with those variables and methods that are 
 * unique to the persons that are members of the staff
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public abstract class Staff extends Person {
  
    private String position;      
    private HashMap<String, Object[]> appAgenda;
    private ArrayList<Patient> assignedPatients;
    
    /**
     * Constructor for objects of class Staff
     *
     */
    public Staff(String name, String id, String phoneNum, String birthDate)
    {
        
        super(name, id, phoneNum, birthDate);  
        appAgenda = new HashMap<>();
        assignedPatients = new ArrayList<>();
        
    }
    
    
    /**
     * Returns the assigned patients ArrayList
     *
     * @return: assignedPatients
     */
    public ArrayList<Patient> getAssignedPatients()
    {   
        return assignedPatients;
    }
   
    /**
     * Add a new patient to the assigned patients ArrayList
     *
     * @return: assignedPatients
     */
    public void addAssignedPatients(Patient patient) 
    {
        if(!assignedPatients.contains(patient)){
            assignedPatients.add(patient); 
        }
    }      
    	
    
    /**
     * Adds a new appointment to the scheduled appointments HashMap
     *
     * @param appointmentID (key of a new appointment) object (value of a new appointment)
     */
    public void addAppointment(String appointmentID, Object[] object)
    {
        if(object != null) {
        	getAppAgenda().put(appointmentID, object);
        }
    }
    
    /**
     * Deletes an appointment from the system
     *
     * @param: key of the entry to remove
     */
    public void removeAppointment(String key)
    {
        if(getAppAgenda().containsKey(key)){
        	getAppAgenda().remove(key);
        }
    }     
    
    /**
     * Updates a member of the staff's position
     * @param position
     */
    public void setPosition(String position)
    {        
       this.position = position;     
        
    }
    
    
    /**
     * Returns a member of the staff's position
     * @return position
     */
    public String getPosition()
    {        
        return position;       
        
    }
    
    /**
     * Returns a HashMap with the scheduled appointments
     *
     * @return appAgenda
     */
    public HashMap<String, Object[]> getAppAgenda()
    {
        return appAgenda;
       
    }
    
    /**
     * Returns a string with the member of the staff's details
     * 
     * @return  a String with staff's ID, name and phone number 
     */
    public String toString()
  
    {        
        return String.format("%-11s   %-17s   %-11s   %-11s",getID(), getName(),  position, getNumber());
    } 
    
}