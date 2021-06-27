package Project;

/**
 * The clinic's data base. Automatically loads a number of patients, staff, stock
 * and scheduled appointments.
 * 
 * @author  Isabel Manzaneque
 * @version 0.1
 */
public class DataBase {
	    
    UserManager userManager;  
    VaccineManager vaccineManager;
    TestManager testManager;
    Calendar calendar;
    
    /**
     * Constructor for objects of class DataBase
     * 
     */
    public DataBase()
    {
        userManager = new UserManager();
        calendar = new Calendar();
        vaccineManager = new VaccineManager(userManager, calendar);
        testManager = new TestManager(userManager, calendar);
        vaccineManager.newStock("3");
        addPeople();        
        addCitas();        
    }

    /**
     *  Adds 4 patients, 2 nurses and 2 technicians to the clinic's system
     *   
     */
    public void addPeople()
    {
    	userManager.newPerson("patient", "Carla Dominguez","87539389F","656950334","1967/12/10");
    	userManager.newPerson("patient", "Alba Perales","40159367P" , "916800340", "1980/03/16");
    	userManager.newPerson("patient", "Luis Fernando","46439511H" , "954150334", "1945/12/10");
    	userManager.newPerson("patient", "Tyke Pillay","81739541T" , "954150334", "1937/07/07");  
    	userManager.newPerson("nurse", "Louise Fletcher","17452786X" , "916173999", "1962/04/14");
    	userManager.newPerson("nurse", "Alejandro Arribas","97552381G" , "756911335", "1993/02/14");
    	userManager.newPerson("technician", "Hernan Cortés","13391319F" , "619950894", "1978/11/11");
    	userManager.newPerson("technician", "Isabel Manzaneque","53902577F" , "07762347351", "1993/02/14");              
    }
       
    /**
     *  Adds a PCR, an antibodies test and two antigens tests (one quick, one classic) to the
     *  system, as well as the integer of the week of the year to the staff's "weeksOfYear" 
     *  ArrayList    
     * 
     */
    public void addCitas()
    {
         
         testManager.addTest("81739541T","97552381G", "53902577F", "2021/04/26", "PCR test");
         userManager.getNurseMap().get("97552381G").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/26"));
         userManager.getTechnicianMap().get("53902577F").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/26"));
         
         testManager.addTest("40159367P","17452786X", "53902577F", "2021/04/27", "Antigens test: quick");
         userManager.getNurseMap().get("17452786X").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/27"));
         userManager.getTechnicianMap().get("53902577F").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/27"));
         
         testManager.addTest("81739541T","97552381G", "13391319F", "2021/04/28", "Antigens test: classic");
         userManager.getNurseMap().get("97552381G").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/28"));
         userManager.getTechnicianMap().get("13391319F").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/28"));
         
         testManager.addTest("40159367P","17452786X", "13391319F", "2021/04/29", "Antibodies test");
         userManager.getNurseMap().get("17452786X").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/29"));
         userManager.getTechnicianMap().get("13391319F").getWeeksOfYear().add(calendar.getWeekOfYear("2021/04/29"));
    }
}
