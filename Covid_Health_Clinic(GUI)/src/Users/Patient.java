package Users;
import java.util.*;
/**
 * Subclass that extends the class Person with those variables and methods that are 
 * unique to the persons that are patients of the clinic
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class Patient extends Person {
	
	
    private boolean vaccinated;
    private boolean isolating;
    private String quarantineStartDate;
    private int quarantineDays;
    private LinkedHashMap<String, Object[]> medicalHistory;

    /**
     * Constructor for objects of class Patient
     *
     */
    public Patient(String name, String id, String phoneNum, String birthDate)
    {   
     
        super(name, id, phoneNum, birthDate);
        medicalHistory = new LinkedHashMap<>();
        vaccinated = false;
        isolating = false;
        quarantineDays = 0;
        quarantineStartDate = "None";
    }
    
    
    /**
     * Returns a LinkedHashMap of the patient's medical history
     *
     * @return: medicalHistory
     */
    
    public LinkedHashMap<String,Object[]> getMedicalHistory()
    {
        return medicalHistory;
    }
    
   
    /**
     * Returns if a patient has been or not vaccinated
     *
     * @return  vaccinated: false if not vaccinated, true otherwise
     */
    public boolean getVacunado()
    {   
          return vaccinated;
    }
    
   
    /**
     * Updates if a patient has been or not vaccinated
     *
     * @param: vaccinated
     */
    public void setVaccinated(boolean vaccinated)
    {   
          this.vaccinated = vaccinated;
    }
    
    
    /**
     * Returns if a patient is or not self isolating
     *
     * @return isolatin:, false if not and true otherwise
     */
    public boolean getIsolating()
    {   
          return isolating;
    }
    
    
    /**
     * Updates if a patient is or not self isolating
     *
     * @param isolating
     */
    public void setConfinado(boolean isolating)
    {   
          this.isolating = isolating;
    }
    
    
    /**
     * Returns a String with a patient's quarantine start date
     *
     * @return quarantineStartDate
     */
    public String getQuarantineStartDate()
    {   
          return quarantineStartDate;
    }
    
    
    /**
     * Updates a patient's quarantine start date
     *
     * @param: date 
     */
    public void setFechaConfinado(String date)
    {   
    	quarantineStartDate = date;
    }
    
   
    /**
     * Returns an integer of the number of days a patient has been self isolating
     *
     * @return: diasConfinado
     */
    public int getQuarantineDays()
    {   
          return quarantineDays;
    }
    
   
    /**
     * Increments the number of days a patient has been in quarantine by 1
     */
    public void quarantineDaysUp()
    {   
    	quarantineDays ++;               
    }
    
    
    /**
     * Assignates a vaccine group to a patient based on its age
     *
     * @return group 1 if over 65 years old, group 2 otherwise
     */
    public int getVaccineGroup()
    {   
          if(getAge()>65){
              return 1;
          }else{
              return 2;
          }
    }
    
   
    /**
     * Adds a new vaccination appointment to the patient's medical history
     *
     */
    public void addToHistory(String appointmentID,Object[] object)
    {
        if(object != null) {
            medicalHistory.put(appointmentID, object);
        }
    }
    
    
    /**
     * Removes a vaccination appointment from the patient's medical history
     *
     * @param key of the entry to erase
     */
    public void removeFromHistory(String key)
    {
        if(medicalHistory.containsKey(key)) {
            medicalHistory.remove(key);
        }
    }
       
    //ordenar con un treeMap con la ordenacion invertida????
    /**
     * finds all the appointments of a certain type iterating through the patient's medical history
     * and stores them in the "appointments" ArrayList. Then returns the last position of the Array,
     * which is the last appointment booked of a certain type
     *
     * @param type of appointment
     * @return appointment
     */
    public Object[] getLastTest(String type)
    { 
        Object[] appointment = new Object[4];
        ArrayList<Object[]> appointments =  new ArrayList<>();
        if(!medicalHistory.isEmpty()){            
            medicalHistory.forEach((key, value) -> 
            {
              if(value[0].toString().contains(type)){ 
                appointments.add(value);
              }
            });
            for(int i = 0; i < appointments.size(); i++){
            	appointment = appointments.get(i);
            }  
        }        
        return appointment;
    }
    
    
    /**
     * Returns a string with the patient's details
     * 
     * @return a String with the patient's ID, phone number, birth date and quarantine start date.
     */
    public String toString()    
    {
        return String.format("%-11s  %-17s  %-11s  %-11s  %-11s",getID(), getName(), getNumber(), getBirthDate(),quarantineStartDate);
        
    }

}