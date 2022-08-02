package Vaccines;
/**
 * Subclass that extends the class Vaccine with those variables and methods that are 
 * unique to the Moderna vaccinesampos distintivos de 
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class Johnson extends Vaccine {
	/**
     * Constructor for objects of class Johnson
     */
    public Johnson()
    {
        super();
        setType("Johnson");
        setDoses(1); 
    }
}