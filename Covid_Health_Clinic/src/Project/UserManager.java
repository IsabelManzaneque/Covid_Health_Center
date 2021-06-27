package Project;

import Users.*;
import java.util.*;
/**
 * This class allows to manage those tasks related to user registration, updating their 
 * personal details or deleting them from the system  
 * 
 * @author Isabel Manzaneque
 * @version 1.0
 */
public class UserManager {
	
	// HashMap variables to store the different types of users registered in the system
    private HashMap<String, Patient> patientMap;
    private HashMap<String, Nurse> nurseMap;
    private HashMap<String, Technician> technicianMap;
    
    private IsolatingManager isoManager;
   /**
    * Constructor for objects of class UserManager
    */
    public UserManager()
    {
    	patientMap = new HashMap<>();
    	nurseMap = new HashMap<>();
    	technicianMap = new HashMap<>();
    	isoManager = new IsolatingManager();
    }    
   
   /**
    * Returns a IsolatingManager thats manages those patients that are self isolating
    * 
    * @return isoManager
    */
   public IsolatingManager getIsoManager()
   {
       return isoManager;
   }
    
   /**
    * Returns a HashMap that stores the patients registered in the system
    * 
    * @return patientMap
    */
    public HashMap<String, Patient> getPatientMap()
    {   
        return patientMap;
    }
    
   /**
    * Returns a HashMap that stores the nurses registered in the system
    * 
    * @return nurseMap
    */
    public HashMap<String, Nurse> getNurseMap()
    {           
        return nurseMap;
    }
    
   /**
   * Returns a HashMap that stores the technicians registered in the system
   * 
   * @return technicianMap
   */
    public HashMap<String, Technician> getTechnicianMap()
    {           
        return technicianMap;
    }
   
    
   /**
    * Returns a HashMap with the patients registered in the system sorted as per vaccination 
    * priority. To do so, patients are added into a reversed order TreeMap with their age as key
    * 
    * 
    * @return: sortedPatients
    */
   public Map<Integer, Patient> getSortedPatients()
    { 
      Map<Integer, Patient> sortedPatients = new TreeMap<>(Collections.reverseOrder());
 
      for(String id : getPatientMap().keySet()){
          Patient p = getPatientMap().get(id);
          int edad = p.getAge();
          if(!p.getVacunado()){
             sortedPatients.put(edad, p);   
          }
      }
      
      return sortedPatients;
    }
   
   /**
    * Adds those patients marked as isolating to the collection of self isolating patients
    *  
    */
    public void setConfinados()
    {
      isoManager.updateCuarentena();
      getPatientMap().forEach((key, value) -> 
      {
        if(value.getIsolating()){ 
          isoManager.addPatient(value);
        }else{
          isoManager.removeConfinado(value.getID());
        }
      });
    }
    
   /**
    * Add a new user to the system
    * 
    */
    public void newPerson(String type, String name,String id,String phoneNum,String birthDate)
    {  
        if(type.equals("patient")){
            addPerson(new Patient(name, id, phoneNum, birthDate));
        }else if(type.equals("nurse")){
            addPerson(new Nurse(name, id, phoneNum, birthDate));
        }else if(type.equals("technician")){
            addPerson(new Technician(name, id, phoneNum, birthDate));
        }
    }
             
   /**
    * Stores the details of a new person in the system 
    *  
    * @param: person
    */
    private void addPerson(Person person)
    {
        if(person != null) {
            if(person instanceof Patient){
                patientMap.put(person.getID(), (Patient) person);
            }else if(person instanceof Nurse){
                nurseMap.put(person.getID(), (Nurse) person);
            }else if(person instanceof Technician){
                technicianMap.put(person.getID(), (Technician) person);
            }
        }
    }
    
   /**
    * Deletes from one of the users HashMap the entry corresponding to the inserted key
    * 
    * @param   key of the entry to delete
    * @return  aux, false if cant find the inserted key
    */
    public boolean removePersona(String key)
    {
        boolean aux = true;
        if(patientMap.containsKey(key)) {
        	patientMap.remove(key);
        }else if(technicianMap.containsKey(key)) {
        	technicianMap.remove(key);
        }else if(nurseMap.containsKey(key)) {
        	nurseMap.remove(key);
        }else{
            aux = false;
            System.out.println("Unable to find an user with the inserted ID, try again");
        }
        return aux;
    }    
}