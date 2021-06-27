package Users;

/**
 * Subclass that extends the class Person with those variables and methods that are 
 * unique to the persons that are members of the staff
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public abstract class Staff extends Person {
  
    private String position;       
    
    /**
     * Constructor for objects of class Staff
     *
     */
    public Staff(String name, String id, String phoneNum, String birthDate)
    {
        
        super(name, id, phoneNum, birthDate);        
        
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
     * Returns a string with the member of the staff's details
     * 
     * @return  a String with staff's ID, name and phone number 
     */
    public String toString()
  
    {        
        return String.format("ID %-9s: %-10s Name: %-18s Phone number: %-10s",position, getID(), getName(), getNumber());
    } 
    
}