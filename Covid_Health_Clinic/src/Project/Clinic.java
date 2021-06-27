package Project;

/**
 * Class that contains the main method and loads all the functionalities
 * of the clinic
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class Clinic {

    
    /**
     * Constructor for objects of class Clinic
     */
    public Clinic(){
        new TextInterface();         
    }
     
    /**
     * Main method. Runs the clinic's system 
     */
    public static void main(String[] args) {
         new Clinic();         
    }
}