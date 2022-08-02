package Project;

import Tests.*;
import java.util.*;
/**
 * This class manages those tasks that are related to the tests: schedule a new test, update
 * its results or delete it's details from the system
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class TestManager {
	
    private UserManager userM;
    private Calendar calendar;
    private Test test;
    private HashMap<String, Object[]> testAgenda;    //scheduled test appointments
    Scanner key; 
    
    /**
     * Constructor for objects of class TestManager. Creates a new HashMap to store the scheduled
     * appointments, a Caldendar variable and a UserManager variable.
     * 
     */
    public TestManager(UserManager userManager, Calendar calendar)
    {
      testAgenda = new HashMap<>();  
      userM = userManager;
      key = new Scanner(System.in); 
      this.calendar = calendar;
    }
    
    /**
    * Returns a HashMap with the scheduled test appointments
    * 
    * @return testAgenda
    */
    public HashMap<String, Object[]> getTagenda()
    {
      return testAgenda;
    }
    
    /**
     * Calculates the test appointment ID, which is unique to each appointment
     * 
     * @return: string that combines #P and a random number between 0 and 10000
     */
    public String getTappointmentID()
    {
        Random generator = new Random();
        return  "#P" + generator.nextInt(10000);  
    }
    
    /**
     * Add a new test to the scheduled appointments. If there is a minimun time gap in between two appointments
     * or if the staff involved has reached the max of appointments for that particular week, it will be 
     * notified to the user
     *  
     * @param The patient's ID (id), the nurse's ID (nurseId), the technician's ID (tecId), the appointment's
     *        date (date) and the test's type (type)
     */
    public void addTest(String id, String nurseId, String tecId, String date, String type)
    {        
        setTest(date, type, id);
        test.setPacienteID(id);
        test.setNurseID(nurseId);
        test.setTecID(tecId);
        test.setTestID(getTappointmentID());
        
        Object[]infoAppointment= new Object[]{test, userM.getPatientMap().get(id).getName(), userM.getNurseMap().get(nurseId).getName(), userM.getTechnicianMap().get(tecId).getName()};
        userM.getPatientMap().get(id).addToHistory(test.getTestID(), infoAppointment);
        userM.getNurseMap().get(nurseId).addAppointment(test.getTestID(),infoAppointment);
        userM.getNurseMap().get(nurseId).addAssignedPatients(userM.getPatientMap().get(id));
        userM.getTechnicianMap().get(tecId).addAppointment(test.getTestID(),infoAppointment);
        userM.getTechnicianMap().get(tecId).addAssignedPatients(userM.getPatientMap().get(id));
        testAgenda.put(test.getTestID(),infoAppointment);        
    }
       
    /**
     * Deletes a test from the scheduled appointments, from the patient's medical history and
     * from the staff agenda
     * 
     * @param   key of the entry to erase
     */
    public void removeTest(String key)
    {      
        
        boolean aux = false;
        int i = 0;
        
        Object[] cita = testAgenda.get(key); 
        Test test = (Test)cita[0];
           
           // Deteles integer corresponding to the week of the year from nurse's appointments
           do{
               if(userM.getTechnicianMap().get(test.getTecID()).getWeeksOfYear().get(i) == calendar.getWeekOfYear(test.getDate())){
                   aux = true;
                   userM.getTechnicianMap().get(test.getTecID()).getWeeksOfYear().remove(i);
               }
               i++;
           }while(aux == false && i < userM.getTechnicianMap().get(test.getTecID()).getWeeksOfYear().size());
           aux = false;
           i= 0;
           // Deteles integer corresponding to the week of the year from technician's appointments
           do{
               if(userM.getNurseMap().get(test.getNurseID()).getWeeksOfYear().get(i) == calendar.getWeekOfYear(test.getDate())){
                   aux = true;
                   userM.getNurseMap().get(test.getNurseID()).getWeeksOfYear().remove(i);
               }
               i++;
           }while(aux == false && i < userM.getNurseMap().get(test.getNurseID()).getWeeksOfYear().size());
           
           userM.getPatientMap().get(test.getPacienteID()).removeFromHistory(key);
           userM.getNurseMap().get(test.getNurseID()).removeAppointment(key);
           userM.getTechnicianMap().get(test.getTecID()).removeAppointment(key);           
           testAgenda.remove(key);
                   
    }
        
   
    /**
     * Updates the variable global variable "test". If there is a minimun time gap in between two appointments
     * of the same type, calculates it and updates aux variable
     * 
     * @return aux, false if not enough days have passed  since the last test of the same type
     * @param: The patient's ID (id), the test's date (date) and the test's type (type)
     */
    public boolean setTest(String date, String type, String id)
    {               
        
        Test test = (Test)userM.getPatientMap().get(id).getLastTest(type)[0];
        boolean aux = true;
        if(type.equals("PCR test")){
        	this.test = new PCR(date);
        }else if(type.equals("Antibodies test")){
            this.test = new Antibodies(date);
        }else if(type.equals("Antigens: quick")){
            this.test = new Quick(date);
        }else if(type.equals("Antigens: classic")){
            this.test = new Classic(date);
        }else{
            aux = false;
            test = null;
            System.out.println("Choose a valid test type");
        }
    
        if(test != null && calendar.getDaysBetweenDates(test.getDate(), date) <= this.test.getMinGap()){
          aux = false;
          System.out.println("\nNot enough days have passed since the last " + test.getType());
          System.out.println("Choose a different one: \n");
        }
        return aux;
    }
    
    
    /**
     * Checks if the nurse has reached the max weekly appointments
     * 
     * @param: The nurse's ID(nurseId) and test's date(date) 
     * @return: aux, returns false if weekly max of appointments was reached
     * 
     */
     public boolean setNurse(String nurseId, String date){          
        
        
        if(!userM.getNurseMap().get(nurseId).weeklyAppointments(calendar.getWeekOfYear(date))){
            return false;
        }else{
        	userM.getNurseMap().get(nurseId).getWeeksOfYear().add(calendar.getWeekOfYear(date));
        }
        return true;        
      }
    
     /**
     * Checks if the technician has reached the max weekly appointments
     * 
     * @param: The technician's ID(tecId) and test's date(date) 
     * @return: aux, returns false if weekly max of appointments was reached
     * 
     */
    public boolean setTechnician(String tecId, String date){                   
        
        if(!userM.getTechnicianMap().get(tecId).weeklyAppointments(calendar.getWeekOfYear(date))){
           return false;
        }else{
        	userM.getTechnicianMap().get(tecId).getWeeksOfYear().add(calendar.getWeekOfYear(date));
        }
        return true;  
    }
    
    /**
     * Updates a test's result: positive/negative for PCR and antigens tests and a number between 0 and 
     * 10 for antibodies tests. If a PCR or antigens test comes back positive, the patient
     * is automatically marked at self isolating.
     * 
     * @param the appointments ID and the test result
     */
    public void setResult(String appID, String result)
    {   
       
       if(testAgenda.containsKey(appID)) {
           Object[] cita = testAgenda.get(appID); 
           Test test = (Test)cita[0];
           test.changeResult(result);
           if(test instanceof PCR ||test instanceof Classic||test instanceof Quick){             
            
	               if(test.getResult().equals("Positive")){
	            	   userM.getPatientMap().get(test.getPacienteID()).setConfinado(true);
	            	   userM.getPatientMap().get(test.getPacienteID()).setFechaConfinado(calendar.dateToString(calendar.getTodaysDate()));
	               }else if(test.getResult().equals("Negative")){
	            	   userM.getPatientMap().get(test.getPacienteID()).setConfinado(false);
	                }
	               userM.setConfinados();
            }           
         }
      }
     
}