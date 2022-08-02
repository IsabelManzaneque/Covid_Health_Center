package Vaccines;

/**
 * Subclass that extends the class Vaccine with those variables and methods that are 
 * unique to the Pfizer vaccines
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class Pfizer extends Vaccine {
	
    /**
     * Constructor for objects of class Pfizer
     */
    public Pfizer()
    {
        super();
        setType("Pfizer");
        setDoses(2);  
    }
}
