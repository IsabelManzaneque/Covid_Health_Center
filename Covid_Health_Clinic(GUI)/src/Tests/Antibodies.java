package Tests;

/**
 * Subclass that extends the class Test with those variables and methods that are 
 * unique to the Antibodies Tests
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */

public class Antibodies extends Test{
private int antibodies;
    
    /**
     * Constructor for objects of class Antibodies     
     */
    public Antibodies(String date)
    {
        
        super(date);
        setType("Antibodies test");
        setMinGap(183);
    }
    
    /**
     * Sets the result of an antibodies test which is a number between 0 and 10
     * 
     * @param level, the antibodies level
     * @return false if the format is not valid, true if it is
     */
    public boolean changeResult(String level)
    {  
         
      try{  
        Double.parseDouble(level);  
        antibodies = Integer.parseInt(level.trim());
        if(antibodies >= 0 && antibodies <= 10){       
            setResult("A.level: " + level);
            return true;
        }
      }catch(NumberFormatException e){  
        System.out.println("Invalid format"); 
      }        
      return false;
    }
}