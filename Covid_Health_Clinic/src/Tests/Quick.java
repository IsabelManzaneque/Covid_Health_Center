package Tests;
/**
 * Subclass that extends the class Antigens with those variables and methods that are 
 * unique to the Quick Antigens Tests
 * 
 * @author Isabel Manzaneque
 * @version 1.0
 */
public class Quick extends Antigens {
	/**
     * Constructor for objects of class Rapida
     */
    public Quick(String date)
    {
       
        super(date);
        setType("Antigens test: quick");
    }
}
