package Tests;


/**
 * Subclass that extends the class Test with those variables and methods that are 
 * unique to the Antigens Tests
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public abstract class Antigens extends Test {
	/**
     Constructor for objects of class Antigens
     */
    public Antigens(String date)
    {       
        super(date);
        setMinGap(0);        
    }
    
    /**
     * Sets the result of an antibodies test which either "positive" or "negative"
     * 
     * @param String with the test result
     * @return false if the format is not valid, true if it is
     */
    public boolean changeResult(String result)
    {  
       
       if(result.equals("Positive") || result.equals("Negative")){
           setResult(result); 
           return true;
        }
        return false;     
    }
}