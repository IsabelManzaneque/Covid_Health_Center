package Project;

import Vaccines.*;
import java.util.*;
import java.time.*;

/**
 * Class that manages the features of the clinic that are related to vaccines:
 * stock and appoinments
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class VaccineManager {
	
    private UserManager userM; 
    private Calendar calendar;
    private HashMap<String, Object[]> vAgenda;        //Programmed vaccination appointments
    private ArrayList<Vaccine> vStock;                //Available stock
    private Vaccine vaccine;
        
   /**
     * Constructor for objects of class VaccineManager. Creates a new HashMap to store the scheduled
     * vaccination appointments, an ArrayList to store the available stock, a Caldendar variable
     * and a UserManager variable.
     * Calls the addRecurrentStock() method.
     */
     public VaccineManager(UserManager userManager, Calendar calendar)
    {
        userM = userManager;     
        vAgenda = new HashMap<>();
        vStock = new ArrayList<>();
        this.calendar = calendar;
        addRecurrentStock();
    }  
   
   /**
    * Return an ArrayList with the clinic's stock
    * 
    * @return vStock
    */
    public ArrayList<Vaccine> getVstock()
    {
        return vStock;
    }
    
   /**
    * Returns a HashMap with the vaccination scheduled appointments
    * 
    * @return vAgenda
    */
    public HashMap<String, Object[]> getVagenda()
    {
        return vAgenda;  
    }
    
   /**
    * Adds the details of a new vaccine to the system
    *
    * @param vaccine
    */
    public void addVaccine(Vaccine vaccine)
    {
        if(vaccine != null) {
            vStock.add(vaccine);            
        }
    }    
    
   /**
    * Receives an int with the number of vaccines of each type that the user
    * wishes to order. If the format is correct, these will be loaded into the systmem.
    * 
    * @param units  string of the desired amount of vaccines of each type
    * @return aux   true if format is valid, false otherwise
    */
    public boolean newStock(String units)
    {   
        try{
           Double.parseDouble(units);  
           int unidades = Integer.parseInt(units.trim());
           for(int i = 0; i<unidades; i++){
               addVaccine(new Moderna());
               addVaccine(new Pfizer());
               addVaccine(new Johnson());
           }  
           return true;
        }catch(NumberFormatException e){  
           return false;                 
        }          
    }
        
   /**
    * If today's day of the week is Monday, orders 5 units of each vaccine type
    * 
    */
    private void addRecurrentStock()
    { 
       if(calendar.getTodaysDate().getDayOfWeek().equals(DayOfWeek.MONDAY)){
           newStock("5");         
       }
    }
       
   /**
    * Returns a random vaccine from the stock ArrayList and then deletes it from
    * the available stock
    *
    * @return: vaccine
    */
    public Vaccine anyVaccine()
    {
        Random generator = new Random();
        int index = generator.nextInt(vStock.size());
        Vaccine vaccine = vStock.get(index);
        vStock.remove(index);
        return vaccine;
    }
    
   /**
    * Checks on the non vaccinated patients and assigns a vaccine based on their
    * priority and the clinic's available stock 
    * 
    */
    
    @SuppressWarnings("rawtypes")
	public ArrayList<Object> getPlanificacion()
    {
        Iterator <? extends Object> it = userM.getSortedPatients().entrySet().iterator();
        ArrayList<Object> holder = new ArrayList<>();
        
        int index = 0;
        while((index < vStock.size()) && (index < userM.getSortedPatients().size())){
            Map.Entry entry = (Map.Entry) it.next();
            holder.add(entry.getValue());
            holder.add("Assignated vaccine -> " + getVstock().get(index) + "\n");            
            index ++;
        } 
        return holder;
    } 
    
   /**
     * Calculates the vaccination appointment ID, which is unique to each appointment
     * 
     * @return string that combines #V and a random number between 0 and 10000
     */
    private String getVappointmentID()
    {
        Random generator = new Random();
        return  "#V" + generator.nextInt(10000);  
    }
       
   /**
    * Calculates the vaccination priority of the clinic's patients. If a patient in 
    * group 2 is selected while there is a patient in group 1 that hasnt been vaccinated
    * yet, the appointment wont go through.
    * 
    * @return aux  false if there is any patient in group 1 that hasnt been vaccinated
    * @param id
    */
   public boolean checkPriority (String id)
   {
      boolean[] aux = new boolean [1];
      aux[0] = true;
      if(userM.getPatientMap().get(id).getVaccineGroup() == 2){
          userM.getPatientMap().forEach((key, value) -> 
          {
             if(value.getVaccineGroup() == 1 && !value.getVacunado()){
                  aux[0] = false;
              }              
          });
      }
      return aux[0];      
   }
   
   /**
     * Adds a vaccination to the scheduled appointments agenda, the nurse's agenda and the 
     * patients medical history. 
     *  
     * @param patient's ID, nurse's ID and the appointment date
     */
    public int addVaccination(String id, String nurseId, String date1)
    {   
                        
        vaccine = anyVaccine(); //hacer la vacuna variable global? cada vez que se llama a anyVaccine deberia cambiar igualmente
        vaccine.setJabID(getVappointmentID());
        vaccine.setPatientID(id);
        vaccine.setNurseID(nurseId);
        vaccine.setDate(date1);
        userM.getPatientMap().get(id).setVaccinated(true);
        
        Object[] infoVappointment = new Object[]{vaccine.getDate()+ "  ", userM.getPatientMap().get(id).getName(), userM.getNurseMap().get(nurseId).getName(), vaccine};
        userM.getPatientMap().get(id).addToHistory(vaccine.getJabID(),infoVappointment);
        userM.getNurseMap().get(nurseId).addJabAppointment(vaccine.getJabID(),infoVappointment);
        userM.getNurseMap().get(nurseId).addAssignedPatients(userM.getPatientMap().get(id));
        vAgenda.put(vaccine.getJabID(),infoVappointment);
        
        return vaccine.getDoses();
        
    }
   
    /**
     * If the selected vaccine needs 2 doses, a date for the second appointment is requested 
     * automatically. If the gap between appointments is less than 21 days, a later date is requested.
     *  
     * @param patient's ID, nurse's ID and the second appointment date
     */
    public boolean addSecondJab(String patientID, String nurseID, String date)
    { 
    	
	      if(calendar.getDaysBetweenDates(vaccine.getDate(), date) > 21){                            
	  	
			  String vaccinationID2 = getVappointmentID();
			  Object[] infoVappointment2 = new Object[]{date+ "  ", userM.getPatientMap().get(patientID).getName(), userM.getNurseMap().get(nurseID).getName(), vaccine};
			  userM.getPatientMap().get(patientID).addToHistory(vaccinationID2,infoVappointment2);
			  userM.getNurseMap().get(nurseID).addAppointment(vaccinationID2,infoVappointment2);
			  vAgenda.put(vaccinationID2,infoVappointment2);
			 
			  return true;
	      }
	      return false;
	      
    }
    
   /**
    * Deletes an appointment from the vaccinations agenda, the patient's medical
    * history and the nurse's agenda. The patient is set to not vaccinated.
    * 
    * @param   key, ID of the appointment
    */
   public void removeVaccination(String key)    {      
       
           Object[] appointment = vAgenda.get(key); 
           Vaccine vaccine = (Vaccine)appointment[3]; // la vacuna es la ultima posicion?
           System.out.println(vaccine);
           userM.getPatientMap().get(vaccine.getPatientID()).setVaccinated(false);
           userM.getPatientMap().get(vaccine.getPatientID()).removeFromHistory(key);
           userM.getNurseMap().get(vaccine.getNurseID()).removeAppointment(key);
           vAgenda.remove(key);
         
    }
}