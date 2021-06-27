package Users;

/**
 * Subclass that extends the class Staff with those variables and methods that are 
 * unique to the cinic admin
 *
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class SysAdmin extends Staff{
	
	/**
     * Constructor for objects of class SysAdmin
     */
    public SysAdmin(String name, String id, String phoneNum, String birthDate)
    {
        super(name, id, phoneNum, birthDate);
        setPosition("Admin");
        
    } 
}
