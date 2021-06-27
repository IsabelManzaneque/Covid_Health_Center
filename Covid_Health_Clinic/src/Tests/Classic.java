package Tests;
/**
 * Subclass that extends the class Antigens with those variables and methods that are 
 * unique to the Classic Antigens Tests
 * 
 * @author Isabel Manzaneque
 * @vercion 0.1
 */
public class Classic extends Antigens{
	/**
     * Constructor for objects of class Classic
     */
    public Classic(String date)
    {
        super(date);
        setType("Antigens test: classic");
      
    }
}
