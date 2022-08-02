package Users;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * This class defines those variables and methods that are common to users 
 * registered in the system 
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public abstract class Person 
{

	private String name;
    private String id;
    private String phoneNum;
    private String birthDate; 
   	
	
	/**
	 * Constructor for objects of class Person 
     *
     * @param name, id, num, birthDate
     */
    public Person (String name, String id, String phoneNum, String birthDate)
    {
     this.name = name;
     this.id = id;
     this.phoneNum = phoneNum;
     this.birthDate = birthDate;
          
    }
	
	/**
     * Returns a person's ID
     *
     *@return id
     */
    public String getID()
    {  
        return id;
    }
    
    
    /**
     * Returns a persons's birthDate
     * 
     * @return birthDate
     *
     */
    protected String getBirthDate()
    {  
        return birthDate;
    }
    
  
    /**
     * Returns a persons's phone number
     *
     *@return phoneNum
     */
    protected String getNumber()
    {  
        return phoneNum;
    }
    
    
    /**
     * Returns a persons's name
     *
     *@return name
     */
    public String getName()
    {  
        return name;
    }
    
  
    /**
     * Abstract method that returns a string with a person's info, which is
     * different for a patient and a member of the staff
     * 
     * @return: String with a person's info
     */
     public abstract String toString();
    
   
    /**
     * Calculates a person's age based on it's date of birth 
     *
     * @return: periodo.getYears(), years passed since birth date
     */
    public int getAge()
    {   
        LocalDate todaysDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(birthDate, formatter);
        Period period = Period.between(date, todaysDate);
        return period.getYears();      
                
    }
}
