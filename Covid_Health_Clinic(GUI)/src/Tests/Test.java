package Tests;

/**
 * This class defines those variables and methods that are common to all the tests 
 * performed at the clinic 
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public abstract class Test {
	
    private String testType;
    private String date;
    private String results;
    private String testID;
    private String nurseID;
    private String tecID;
    private String patientID;
    private int minGap;    
    
    /**
     *Constructor for objects of class Test
     *
     */
    public Test(String date)
    {
        this.date = date;
        results = "Unknown";
    }
    
    
    /**
     * Sets the minimum of days in between two appointments of the same type
     *@param minGap
     */
    public void setMinGap(int minGap)
    {
       this.minGap = minGap; 
    }
    
    
    /**
     * Sets an ID for a test
     * @param testID
     *
     */
    public void setTestID(String testID)
    {
       this.testID = testID; 
    }
    
    
    /**
     * Returns a test's ID
     * @return testID
     *
     */
    public String getTestID()
    {
       return testID; 
    }
    
   
    /**
     * Returns a test's date
     * @return date
     */
    public String getDate()
    {
        return date;  
    }    
    
   
    /**
     * Returns the minimum of days in between two appointments of the same type
     * @return minGap
     *
     */
    public int getMinGap()
    {
        return minGap;       
    }
    
   
    /**
     * Returns a test's type
     * @return type
     *
     */
    public String getType()
    {  
       return testType;
    }
    
  
    /**
     * Sets the test's type
     * @param type
     */
    protected void setType(String type)
    {  
       testType = type;
    }
    
   
    /**
     * Abstract method that sets a test's result, which has a diferent format for 
     * PCR/antigens tests and antibodies test
     * 
     * @param result
     * @return false if the result format isn't valid, true otherwise
     */
    public abstract boolean changeResult(String result);
    
   
    /**
     * 
     * sets a test's result
     *@param result
     */
    public void setResult(String result)
    {  
       results = result;
    }
        
    
    /**
     * Returns a test's result
     * @return results
     *
     */
    public String getResult()
    {  
       return results;
    } 
    
    /**
     * Asigna el DNI del técnico que analiza la prueba
     *
     */
    public void setTecID(String idTecnico)
    {  
       tecID = idTecnico;
    }
        
    
    /**
     * Devuelve el DNI del técnico que analiza la prueba
     *
     */
    public String getTecID()
    {  
       return tecID;
    } 
    
    /**
     * Asigna el DNI del enfermero que analiza la prueba
     *
     */
    public void setNurseID(String idEnfermero)
    {  
       nurseID = idEnfermero;
    }
        
     /**
     * Devuelve el DNI del enfermero que analiza la prueba
     *
     */
    public String getNurseID()
    {  
       return nurseID;
    } 
    
   
 
    
    /**
     * sets the patient that will have the test done
     *
     */
    public void setPacienteID(String patientID)
    {  
       this.patientID = patientID;
    }
        
    /**
     * Returns the ID of the patient that will have the test done
     *
     */
    public String getPacienteID()
    {  
       return patientID;
    } 
    
   
    /**
     * Returns a String with the details of a test
     * 
     * @return String with the test's type, date and results
     */
    public String toString()
    {        
        return String.format("%-12s %-18s %-10s ", date, testType, results);
    }
}