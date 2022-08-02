package Project;

import Users.*;
import java.util.*;
/**
 * This class manages those patients that resulted positive on a test and have to 
 * self isolate at home for 10 days
 * 
 * @author Isabel Manzaneque
 * @version 0.1
 */
public class IsolatingManager {
	
	private HashMap<String, Patient> isolatingPatients;     
    private HashMap<String, Patient> recoveredPatients;    
    
    private HashMap<String, String> responseMap;          // Patients answers
    private ArrayList<String> defaultResponses;           // Patients default answers
    Scanner key; 
    
    /**
     * Constructor for objects of class DataBase. Automatically calls the methods that fill up
     * the answers HashMap and ArrayList 
     */
    public IsolatingManager()
    {
    	isolatingPatients = new HashMap<>();
    	recoveredPatients = new HashMap<>();
        responseMap = new HashMap<>();
        defaultResponses = new ArrayList<>();
        key = new Scanner(System.in);
        fillRespuestas();
        fillDefaultResponses();
    }
    
    /**
    * Returns a HashMap with the patients that are self isolating
    * 
    * @return isolatingPatients
    */
    public HashMap<String, Patient> getIsoPatients()
    {
        return isolatingPatients;
    }
    
    /**
    * Returns a HashMap with the patients that have finished their quarantine
    * 
    * @return: recoveredPatients
    */
    public HashMap<String, Patient> getRecPatients()
    {
        return recoveredPatients;
    }
            
    /**
     * Recibe una variable de tipo Paciente y si este está marcado como confinado, lo añade al map de
     * pacientes confinados. Si no lo está, lo añade al map de pacientes recuperados
     * 
     * @param: paciente, variable que recibe desde el gestor de personas
     */
    public void addPatient(Patient patient)
    {
        if(patient != null) {
            if(patient.getIsolating()){
            	isolatingPatients.put(patient.getID(), patient);
            }else if(!patient.getIsolating()){
            	recoveredPatients.put(patient.getID(), patient);
            }
        }
    }
    
    /**
     * Borra de la agenda de pacientes confinados la entrada correspondiente a la clave insertada.
     * 
     * @param: key, La clave de la entrada que se desea eliminar que es el DNI del usuario
     */
    public void removeConfinado(String key)
    {
        if(isolatingPatients.containsKey(key)) {
             isolatingPatients.remove(key);
        }       
    }
    
    /**
     * Iterates through the isolating patients collection and, if the patient hasnt finished the
     * 10 days quarantine, increases the total of days. If the patient has finished the quarantine,
     * its marked as recovered and calls addPatient() method to store in the recoveredPatients HashMap
     */
    public void updateCuarentena()
    {   
        isolatingPatients.forEach((key, value) -> 
        {
            if(value.getQuarantineDays() < 10){
              value.quarantineDaysUp();
            }else{
              value.setConfinado(false);
              addPatient(value);
            }        
        });
    }
       
    // COMING UP...
    
    /**
     * Simulates a phone call to a self isolating patient. The call will go on while the
     * user doesn't insert the word "bye"
     */
    public void callPatient()
    {
        if(!isolatingPatients.isEmpty()){
            boolean finished = false;
            String id;
            System.out.print("Insert the ID of the patient you wish to call: ");
            
            do{
             id = key.nextLine();
             if(isolatingPatients.get(id) == null){
                 System.out.print("Unable to find the patient, try again: ");  
             }
            }while(isolatingPatients.get(id) == null);        
            Patient patient = isolatingPatients.get(id);
            
            System.out.println("\n*Ring, ring ... ring, ring...*\n");
            System.out.println("Hello, "+ patient.getName() +" speaking.");
            while(!finished) {
                HashSet<String> input = getInput();
    
                if(input.contains("bye")) {
                    finished = true;
                }else{
                    String response = generateResponse(input);
                    System.out.println(response);
                }
            }
            System.out.println("Thanks for your call, goodbye");
       }else{
            System.out.println("No patients are self isolating at the moment");
       }
    }
    
    /**
     * Reads an inserted string and returns it as a set of words
     *
     * @return words
     */
    private HashSet<String> getInput() 
    {
        String inputLine = key.nextLine().trim().toLowerCase();
        String[] wordArray = inputLine.split(" ");  //divides text on white spaces

        // adds the wordArray words to a new HashSet 
        HashSet<String> words = new HashSet<>();
        for(String word : wordArray) {
            words.add(word);
        }
        return words;
    }
    
    /**
     * Chooses an answer from the responseMap based on the input. If no words are found
     * in the responseMap, a random answer is chosen from the default answers ArrayList
     * 
     * @param  words  set of words inserted by user
     * @return  generic answer randomly selected
     */
    private String generateResponse(HashSet<String> words)
    {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(defaultResponses.size());        
        for (String word : words) {
            String response = responseMap.get(word);
            if(response != null) {
                return response;
            }
        }
        
        return defaultResponses.get(index);  // If no words from the input were found in responseMap
    }
    
    /**
     * Fills responseMap with with list of answers in which the key is a word recognized from
     * input and the value is the patients answer to that particular word
     */
    private void fillRespuestas()
    {
        responseMap.put("administrator", 
                        "Yes, of course, I was waiting for your call");
        responseMap.put("clinic", 
                        "I felt very comfortable at the clinic, the nurse that saw me was very" +
                        "kind. The installations were clean and modern.");
        responseMap.put("vaccine", 
                        "As soon as I finish my quarantine I'll book an appointment to get my vaccine. Or you can\n" +
                        "call me instead, up to you");
        responseMap.put("breath", 
                        "I'm not experiencing shortness of breath, maybe a bit when I put the heat on\n" +
                        "because the air feels dry");
        responseMap.put("feel", 
                        "Some days are better than others");
        responseMap.put("test", 
                        "A nurse mentioned that I need to have an antibodies test done after I finish my quarantine\n" +
                        "I'll wait for your call.");
        responseMap.put("quarantine", 
                        "It can be difficult at times not seeing anyone and not leaving the house, but i'm \n" +
                        "trying to take it as a forced holiday. At least I have Netflix");
        responseMap.put("isolating", 
                        "I'm not leaving the house, I buy my groceries online and it's left at my doorstep, I just\n" +
                        "go outdoors tu put the bins away");
        responseMap.put("temperature", 
                        "I have a mild temperature, an average of 37º most days");
        responseMap.put("symptoms", 
                        "I feel a bit weak, I have a temperature and a dry cough. My sense of smell and taste\n" +
                        "have been affected too\n");
        responseMap.put("taste", 
                        "My sense of taste has changed, but I can still taste a few things, for example If I\n" +
                        "make myself a coffee and I add a teaspoon of sugar, I can only taste the sugar");
        responseMap.put("smell", 
                        "I cant smell anything at all. It's a problem because most mornings my toast burns");      
    }
    
    /**
     * List of generic answers. When there isn't coincidence between the input and the responseMap, one 
     * is randomly chosen.
     * 
     */
    private void fillDefaultResponses()
    {
        defaultResponses.add("Sorry I didn't catch that, could you repeat it?");
        defaultResponses.add(" * cough cough cough *");
        defaultResponses.add("Aham, I understand");
        defaultResponses.add("I dont quite understand that, could you elaborate?");
        defaultResponses.add("I see, I wasn't aware of that");
        defaultResponses.add("A nurse called to tell me that");
        defaultResponses.add("Excuse me?");
        defaultResponses.add("And what does that mean?");
    }
}