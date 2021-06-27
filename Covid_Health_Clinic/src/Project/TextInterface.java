package Project;

import java.util.*;
/**
 * Text interface of the clinic's system. Shows 4 different menus: Main menu, System admin menu,
 * nurse menu and technician menu which the options available to each of them.
 * Defines the input/output of those methods that require user interaction.
 * 
 * @author  Isabel Manzaneque
 * @version 0.1
 */
public class TextInterface {
	private DataBase base;
    private String option;
    private UserManager userManager;
    private VaccineManager vaccineManager;
    private TestManager testManager;
    private Calendar calendar;
    Scanner key; 
    
    /**
     * Constructor de objetos de la clase Interfaz. Crea una instancia de los gestores de personas, vacunas y 
     * pruebas, así como de la clase calendario y de la base de datos. Los gestores se igualan a los contenidos 
     * en la base de datos para que se inicialicen los datos fijos del programa.
     * Se lama a la función menuPrincipal() para que esté comience automáticamente
     * 
     */
    public TextInterface(){
        base = new DataBase();
        calendar = base.calendar;
        userManager = base.userManager;
        testManager = base.testManager;
        vaccineManager = base.vaccineManager;
        key = new Scanner(System.in); 
        mainMenu();        
    }
    
    /**
     * System's main menu. Allows to choose between the different user's menu, add one
     * day to today's date (to allow the progress of time dependent events) or log out of 
     * the application
     */
    public void mainMenu(){  
        System.out.print('\u000C');      
        System.out.println("\n ------------------------------------------------------------------");
        System.out.println(" |                   St Isabel's Covid Health Center              |");                             
        System.out.println(" |                                                                |"); 
        System.out.println(" |                           * Main Menu *                        |");
        System.out.println(" |                                                                |");
        System.out.println(" |                            "+calendar.getTodaysDate()+"                          |");
        System.out.println(" ------------------------------------------------------------------\n");
        System.out.println("1: Admin Menu.");
        System.out.println("2: Nurse Menu.");
        System.out.println("3: Technician Menu.");
        System.out.println("\n4: Add one day.");
        System.out.println("5: Log out.");
        
        do {
            System.out.print("\nChoose a valid option: ");
            option=key.nextLine().trim();
            
            switch(option){
                case "1":
                    adminMenu();
                    break;
                case "2":
                    menuNurse();
                    break;
                case "3":
                    menuTecnico();
                    break;
                case "4":
                    calendar.addOneDay();
                    userManager.setConfinados();
                    mainMenu();
                    break;
                case "5":
                    key.close();
                    System.out.print("\n*    Good Bye!     *\n");
                    System.exit(1);
                default: 
                    System.out.print("\nInsert a valid option");    
            }            
        } while(option !="5");
    
    }
    
    /**
     * Admin menu. Allows to log in without an ID  to easily show the system's functioning.
     * Manages users, vaccine stock, appointments and quarantined patients     * 
     */
    public void adminMenu(){   
        System.out.print('\u000C');
        System.out.println("\n ------------------------------------------------------------------");
        System.out.println(" |                   St Isabel's Covid Health Center              |");                             
        System.out.println(" |                                                                |"); 
        System.out.println(" |                           * Admin Menu *                       |");
        System.out.println(" ------------------------------------------------------------------\n");
        System.out.println("* User Management *\n");
        System.out.println("1: Register a new user");
        System.out.println("2: Delete / Edit an existing user");
        System.out.println("3: Show registered users");
        System.out.println("4: Patient's medical records");
        System.out.println("5: Staff agenda\n");
        System.out.println("* Vaccine Stock Management *\n");
        System.out.println("6: Show stock list");
        System.out.println("7: Order new stock\n");
        System.out.println("* Appointments Management *\n");
        System.out.println("8: Vaccination priority system");
        System.out.println("9: New vaccination");
        System.out.println("10: New test");
        System.out.println("11: Delete appointment");
        System.out.println("12: Show scheduled appointments\n");
        System.out.println("* Quarantine Management *\n");
        System.out.println("13: Show quarantined patients");
        System.out.println("14: Call a quarantined patient");
        System.out.println("15: Post quarantine antibodies test");
        System.out.println("\n16: Go to Main Menu.");
        do {
            System.out.print("\nChoose a valid option: ");
            option=key.nextLine().trim();
            
            switch(option){
                case "1":
                    newUser();
                    break;
                case "2":
                    changePersona();
                    break;
                case "3":
                    System.out.println("\n----------- Registered Staff -----------\n");
                    showMap(userManager.getNurseMap());
                    showMap(userManager.getTechnicianMap());
                    System.out.println("\n----------- Registered Patients -----------\n");
                    showMap(userManager.getPatientMap());
                    break;
                case "4":
                    showMap(userManager.getPatientMap());
                    showMedicalHistory();
                    break;
                case "5":
                    showAgenda();
                    break;
                case "6":
                    System.out.println("\n----------- Vaccine Stock -----------\n");
                    showArray(vaccineManager.getVstock());
                    break;
                case "7":
                    addStock();
                    break;
                case "8":
                	runPrioritySystem();
                    break;  
                case "9":
                    nuevaVacunacion();
                    break;
                case "10":
                    nuevaPrueba(option);
                    break;
                case "11":
                    borrarCita();  
                    break;
                case "12":
                    System.out.println("\n-------------- Scheduled Vaccinations --------------\n");
                    showMap(vaccineManager.getVagenda());
                    System.out.println("\n----------------- Scheduled Tests  -----------------\n");
                    showMap(testManager.getTagenda());
                    break;
                case "13":
                    System.out.println("\n-------------- Quarantined Patients --------------\n");
                    showMap(userManager.getIsoManager().getIsoPatients());
                    break;
                case "14":
                    userManager.getIsoManager().callPatient();
                    break;
                    case "15":
                    bookAntibTest();
                    break;
                    case "16":
                    mainMenu();
                    break;
                default: 
                    System.out.println("\nInsert a valid option");    
            }            
        } while(option != "16");
    }
    
    /**
     * Menu that the technicians of the clinic can access by inserting their ID.
     * Allows technicians to see their scheduled appointments, update the results of their
     * scheduled tests and list the medical records of their assigned patients
     */
    public void menuTecnico(){   
        System.out.print('\u000C');
        System.out.println("\n ------------------------------------------------------------------");
        System.out.println(" |                   St Isabel's Covid Health Center              |");                             
        System.out.println(" |                                                                |"); 
        System.out.println(" |                        * Technician Menu *                     |");
        System.out.println(" ------------------------------------------------------------------\n");
        System.out.println("Introduzca su ID: "); 
        String tecID = key.nextLine().trim(); 
        if(userManager.getTechnicianMap().get(tecID) != null){
          System.out.println("Welcome, " + userManager.getTechnicianMap().get(tecID).getName());
          System.out.println("\n1: Show scheduled appointments ");
          System.out.println("2: Update a test result ");
          System.out.println("3: Medical records of assigned patients \n");
          System.out.println("4: Go to main menu");
        }else{
          cantFind(userManager.getTechnicianMap().get(tecID));
          calendar.standBy();
          mainMenu();
        }
        do {
            System.out.print("\nChoose a valid option: ");
            option=key.nextLine().trim();
            switch(option){
                case "1":
                    showMap(userManager.getTechnicianMap().get(tecID).getAppAgenda());
                    break;
                case "2":
                    actualizarResultado(userManager.getTechnicianMap().get(tecID).getAppAgenda());                   
                    break;
                case "3":
                    showArray(userManager.getTechnicianMap().get(tecID).getAssignedPatients());
                    showMedicalHistory();
                    break;
                case "4":
                    mainMenu();
                    break;    
                default: 
                    System.out.println("\nInsert a valid option");    
            }            
        } while(option != "4");
    }    
    
    /**
     * Menu that the nurses of the clinic can access by inserting their ID.
     * Allows nurses to see their scheduled appointments, update the results of their
     * scheduled tests and list the medical records of their assigned patients
     */
    public void menuNurse(){  
        System.out.print('\u000C');
        System.out.println("\n ------------------------------------------------------------------");
        System.out.println(" |                   St Isabel's Covid Health Center              |");                             
        System.out.println(" |                                                                |"); 
        System.out.println(" |                            * Nurse Menu *                      |");
        System.out.println(" ------------------------------------------------------------------\n");
        System.out.println("Introduzca su DNI: "); 
        String nurseID  = key.nextLine().trim(); 
        if(userManager.getNurseMap().get(nurseID) != null){
            System.out.println("Welcome, " + userManager.getNurseMap().get(nurseID).getName());
            System.out.println("\n1: Show scheduled appointments ");
            System.out.println("2: Update a test result");
            System.out.println("3: Medical records of assigned patients \n");
            System.out.println("4: Go to main menu.");
        }else{
            cantFind(userManager.getNurseMap().get(nurseID));
            calendar.standBy();
            mainMenu();
        }
        do {
            System.out.print("\nChoose a valid option: ");
            option=key.nextLine().trim();            
            switch(option){
                case "1":  
                    showMap(userManager.getNurseMap().get(nurseID).getAppAgenda());  
                    break;
                case "2":
                    actualizarResultado(userManager.getNurseMap().get(nurseID).getAppAgenda());  
                    break;
                case "3":
                    showArray(userManager.getNurseMap().get(nurseID).getAssignedPatients());
                    showMedicalHistory();
                    break;
                case "4":
                    mainMenu();
                    break;    
                default: 
                    System.out.println("\nInsert a valid option");    
            }
            
        } while(option != "4");
    }
         
    /**
     * Receives an ArrayList and prints it out. A generic parameter is used to reuse the method
     * with all the ArrayList structures of the application
     * 
     * @param vector, generic ArrayList
     */
    public void showArray(ArrayList<? extends Object> vector)
    {  
        for(Object objeto : vector){
            System.out.println(objeto.toString());
        }
    }
    
    /**
     * Receives a HashMap and prints it out. A generic parameter is used to reuse the method
     * with all the HashMap structures of the application
     * 
     * @param:  map, generic HashMap
     */
    public void showMap(Map<? extends Object, ? extends Object> map)
    {          
        map.forEach((key, value) -> 
        {
            if(value instanceof Object[]){
                Object[] valor = (Object[]) value;
                System.out.println("ID: " +key +"\n"+ valor[0]+"\n"+ valor[1]+"\n"+ valor[2]+ "\n"+ valor[3]+"\n");
            }else{
                System.out.println(value);
            }
        });
    } 
    
    /**
     * Request the details of a new user and calls the newPerson method from userManager
     * class to store them in the system
     * 
     */
    public void newUser()
    {  
        String birthDate, type, name, id, phoneNum;
        System.out.print("Do you wish to add a new patient or employee?");
        type = key.nextLine().trim().toLowerCase();
        if(type.equals("e")){
            System.out.print("Nurse or Technician?");
            type = key.nextLine().trim().toLowerCase();
        }
        System.out.print("Nombre: ");
        name = key.nextLine().trim();
        System.out.print("DNI: ");
        id = key.nextLine().trim();
        System.out.print("Número de teléfono: ");
        phoneNum = key.nextLine().trim();
        System.out.print("Fecha de nacimiento(yyyy/mm/dd): ");
        do{
            birthDate = key.nextLine().trim();   
        }while(!calendar.checkFormat(birthDate));
        
        userManager.newPerson(type, name, id, phoneNum, birthDate);
        System.out.println("A new " + type + " has been registered in the system");
    } 
    
    /**
     * Deletes or edits the details of an existing user
     * 
     */
    public void changePersona()
    {
        String idUser, option;
        boolean aux = false;
        do{
            System.out.print("\nDo you wish to edit or delete an existing user?(edit/delete): ");
            option = key.nextLine().trim().toLowerCase();
            if(option.equals("edit")||option.equals("delete")){
                System.out.print("\nInsert the user's ID: ");
                idUser = key.nextLine().trim();
                if(userManager.removePersona(idUser)){
                    aux = true;
                    if(option.equals("edit")){
                       System.out.println("Insert the updated details for selected user: \n");
                       newUser();
                       System.out.println("The user's details have been updated");
                    }else{
                       System.out.println("The user's details have been deleted");
                    }
                }
            }else{
                System.out.println("Insert a valid option");  
            }
        }while(!aux);
    }

    
    /**
     * Request the details of a new test and calls testManager.addTest() to store in the system
     * If the function parameter is the String "Antibodies test", then it's being called to schedule
     * a post quarantine test and only shows those patients that have finished self isolating.
     * 
     * @param: t
     */
    public void nuevaPrueba(String t)
    {
        String nurseId, tecId, id, type, date; 
        type = t;
        System.out.println("Insert the ID of the patient that will have the test done: \n");
        
        //Will show one of the patients HahsMaps based on the value of type
        if(type.equals("Antibodies test")){
            showMap(userManager.getIsoManager().getRecPatients());
        }else{
            showMap(userManager.getPatientMap());
        }
        do{
           id = key.nextLine().trim();  
        }while(!cantFind(userManager.getPatientMap().get(id)));
        
        do{
            System.out.print("Insert Date (yyyy/mm/dd): ");
            do{
                date = key.nextLine().trim();
            }while(!calendar.checkFormat(date) || calendar.isBefore(date));
            
            //The following is omitted if it's a post quarantine test
            if(!type.equals("Antibodies test")){
                System.out.println("Select one of the tests available in the clinic: \n");
                System.out.println("( PCR test | Antibodies test | Antigens test: quick | Antigens test: classic )\n");
                type = key.nextLine().trim();    
            }
        }while(!testManager.setTest(date, type, id));
        
        System.out.println("\nInsert the ID of the nurse that will carry out the test: \n");
        showMap(userManager.getNurseMap());
        do{
          nurseId = key.nextLine().trim(); 
        }while(!cantFind(userManager.getNurseMap().get(nurseId)) || !testManager.setNurse(nurseId, date));
        
        System.out.println("\nInsert de ID of the technician that will analyze the test: \n");
        showMap(userManager.getTechnicianMap());
        do{
          tecId = key.nextLine().trim();
        }while(!cantFind(userManager.getTechnicianMap().get(tecId)) || !testManager.setTechnician(tecId, date));  
        
        testManager.addTest(id, nurseId, tecId, date, type);
        System.out.println("A new test has been added to the system");
    }
    
    /**
     * Allows to book an antibodies test for those quarentined patients that have self isolated for
     * 10 days
     * 
     */
    public void bookAntibTest()
    {  
       if(!userManager.getIsoManager().getRecPatients().isEmpty()){
           nuevaPrueba("Antibodies test");          
       }else{
           System.out.println("No patient has finished the 10 days quarantine yet");
       }
    }
        
    /**
     * Shows the scheduled appointments of the clinic and allows to update their results
     * 
     * @param: agenda, HashMap que guarda una serie de citas programadas
     */
    
    public void actualizarResultado(HashMap<String, Object[]> agenda)
    {
        String appointmentID;
        System.out.println("Insert the ID of the test which result you wish to update: ");
        showMap(agenda);
        do{
        	appointmentID = key.nextLine().trim(); 
        }while(!testManager.setResult(appointmentID));
        System.out.println("The test's result has been updated");
    }
    
    /**
     * Requests the information related to a new vaccination appointment and calls
     * vaccineManager.addVaccination() to store them in the system
     */
    public void nuevaVacunacion()
    {
        String id, nurseId, date;
        if(!userManager.getSortedPatients().isEmpty()){
            System.out.println("\nInsert the ID of the patient to vaccinate: \n");
            showMap(userManager.getSortedPatients());        
            do{
                id = key.nextLine().trim();
            }while(!cantFind(userManager.getPatientMap().get(id)) || !vaccineManager.checkPriority(id));
            
            System.out.println("\nInsert the ID of the nurse that will carry out the vaccination: \n");
            showMap(userManager.getNurseMap());
            do{
              nurseId = key.nextLine().trim(); 
            }while( !cantFind(userManager.getNurseMap().get(nurseId)));
            
            System.out.print("Insert Date (yyyy/mm/dd): ");
            do{
                date = key.nextLine().trim();   
            }while(!calendar.checkFormat(date)|| calendar.isBefore(date));
                    
            vaccineManager.addVaccination(id, nurseId, date);
            System.out.println("A new vaccination has been added to the systema");
       }else{
            System.out.println("There are no unvaccinated patients registered in the system");
       }
    }
    
    /**
     * Deletes an appointment from the system by showing the list of scheduled appointments
     * and requesting the ID of the appointment to delete
     * 
     */
    public void borrarCita()
    {
        String testID;
        boolean aux;
        System.out.println("Insert the Id of the test that you wish to delete: \n"); 
        System.out.println("\n-------------- Scheduled Vaccinations --------------\n");
        showMap(vaccineManager.getVagenda());
        System.out.println("\n----------------- Scheduled Tests  -----------------\n");
        showMap(testManager.getTagenda());
        do{
           aux = true;
           testID = key.nextLine().trim();  
           if(!testManager.removePrueba(testID) && !vaccineManager.removeVaccination(testID)){
               System.out.println("Unable to find the appointment, try again: ");
               aux = false;
           }
        }while(aux == false);
        System.out.println("The appointment has been deleted from the system");
    }
    
    /**
     * Runs the Vaccination priority system based on the registered patientis and
     * the vaccine stock of the clinic
     * 
     */
    
    public void runPrioritySystem()
    {
        System.out.print("Available stock: "+vaccineManager.getVstock().size()+ ", Unvaccinated patients: "
        + userManager.getSortedPatients().size() +"\n");
        System.out.println("Vaccination priority list:\n");
        vaccineManager.getPlanificacion();      
    }

    /**
     * This method informs of the total available vaccine stock of the clinic
     * and lets the user order more 
     * 
     */
    public void addStock()
    { 
       String units, option;
       System.out.print("\nTotal vaccine stock: " + vaccineManager.getVstock().size()+". Woudl you like to order more?(S/N): ");
       do{
          option = key.nextLine().trim().toLowerCase();
          if(!option.equals("s") || !option.equals("n")){
             System.out.print("Choose a valid option: ");
          }          
       }while(!option.equals("s") && !option.equals("n"));
       
       if(option.equals("s")){
           System.out.print("Cúantas unidades de cada vacuna?: ");
           do{
             units = key.nextLine();  
           }while(!vaccineManager.newStock(units));           
           System.out.println("\nOrdered " + units + " units of each vaccine type");
       }    
    }
    
    /**
     * Authorizes the user to see a patient's medical history by inserting the
     * patient's ID
     * 
     */
    public void showMedicalHistory()
    {        
       String patientID;
       System.out.println("\nInsert the ID of the patient whose medical history you wish to see:\n");
       do{
           patientID = key.nextLine().trim();
           if(userManager.getPatientMap().get(patientID) !=null){
               showMap(userManager.getPatientMap().get(patientID).getMedicalHistory()); 
           }
       }while(!cantFind(userManager.getPatientMap().get(patientID)));
    }
    
    /**
     * Authorizes the user to see an employee's appointments agenda by inserting the
     * employee's ID
     */
    public void showAgenda()
    {        
       boolean aux;
       System.out.println("\nInsert the ID of the employee whose agenda you wish to see:\n");
       showMap(userManager.getNurseMap());
       showMap(userManager.getTechnicianMap());
       do{    
           aux = true;
           String staffID = key.nextLine().trim();
           if(userManager.getNurseMap().containsKey(staffID)){
               showMap(userManager.getNurseMap().get(staffID).getAppAgenda());
           }else if(userManager.getTechnicianMap().containsKey(staffID)){
               showMap(userManager.getTechnicianMap().get(staffID).getAppAgenda());
           }else{
               cantFind(null);
               aux = false;
           }
       }while(!aux);
    }
    
    /**
     * If this method receives a parameter of null value, it returns false and warns the user
     * 
     * @param    value 
     * @return:  false if value's value is null     
     */
    public boolean cantFind(Object value)
    {        
           boolean aux = true;
           if(value == null){
               aux = false;
               System.out.print("Unable to fin the ID, please try again: ");
           }   
           return aux;
    }
}
