package Vaccines;
import java.util.*;
/**
 * This class defines those variables and methods that are common to the three 
 * different types of vaccine: Pfizer, Moderna and Johnson
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public abstract class Vaccine {
	private String type;
    private String serial;
    private int doses;    
    private String nurseID;
    private String patientID;
    private String jabID;
    private String date;
    
    
    /**
     * Constructor for objects of class Vaccine
     * 
     */
    public Vaccine()
    {      
        setSerial();
    }
      
    /**
     * Returns a jabs's date
     * @return date
     */
    public String getDate()
    {
        return date;  
    }
    
    public void setDate(String date)
    {
        this.date = date;  
    }
    
    /**
     * Updates the ID of the nurse that administrates de vaccine
     *
     *@param nurseID  
     */
    public void setNurseID(String nurseID)
    {  
       this.nurseID = nurseID;
    }
        
    /**
     * Returns de Id of the nurse that administrates de vaccine
     *
     *@return nurseID
     */
    public String getNurseID()
    {  
       return nurseID;
    }
    
    /**
     * Updates the ID of the patient that receives de vaccine
     *
     *@param patientID
     */
    public void setPatientID(String patientID)
    {  
       this.patientID = patientID;
    }
        
    /**
     * Returns the ID of the patient that receives de vaccine
     *
     * @return patientID
     */
    public String getPatientID()
    {  
       return patientID;
    } 
    
    /**
     * Updates the number of doses needed to achieve full immunity 
     * 
     * @param: doses
     */
    public void setDoses(int doses)
    {
      this.doses = doses;  
    }
    
    /**
     * Returns the number of doses needed to achieve full immunity 
     * 
     * @return: doses
     */
    public int getDoses()
    {
      return doses;  
    }
    
    /**
     * Updates the vaccine's type
     * 
     * @param type     
     */
    public void setType(String type)
    {
      this.type = type;  
    }  
    
    /**
     * Return the vaccine's type
     * 
     * @return: type
     */
    public String getType()
    {
      return type;  
    }   
    
    /**
     * Calculates the vaccine's serial number, which is unique to each vial 
     * 
     */
    private void setSerial()
    {
        Random generator = new Random();
        serial =  "S-" + generator.nextInt(10000);  
    }
    
    /**
     * Return the vaccine's serial number
     * 
     * @return: serial
     */
    protected String getSerial()
    {
        return serial;
    }
    
    /**
     * Sets an ID for a test
     * @param testID
     *
     */
    public void setJabID(String jabID)
    {
       this.jabID = jabID; 
    }
    
    
    /**
     * Returns a test's ID
     * @return testID
     *
     */
    public String getJabID()
    {
       return jabID; 
    }
    
    /**
     * Returns a string with the vaccine's info
     * 
     * @return String with the vaccine's type and required doses
     */
    public String toString()
    {
        return String.format(" %-10s %-13s %-3s ",serial, type, doses);
        
    }
}