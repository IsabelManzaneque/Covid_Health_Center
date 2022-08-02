package Vaccines;
/**
 * Subclass that extends the class Vaccine with those variables and methods that are 
 * unique to the Moderna vaccines
 * 
 * @author Isabel Manzaneque 
 * @version 0.1
 */
public class Moderna extends Vaccine {
	
	/**
     * Constructor for objects of class Moderna
     */
    public Moderna()
    {
        super();
        setType("Moderna");
        setDoses(2); 
    }
}