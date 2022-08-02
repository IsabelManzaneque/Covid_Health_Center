package GUI;

import Project.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Users.Person;


/**
 * Graphic interface of the clinic's system. Shows 3 different menus for the users of the application
 * and the options available to them: Main menu, System admin menu and staff menu for nurses and technicians.
 * 
 * @author  Isabel Manzaneque
 * @version 0.1
 */
public class GUI {
	
	private JFrame frame;
	private JLabel userLabel;
	private JTextField userText;
	private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton button;
    private JLabel success;
    private JLabel registered;
    private JPanel adminPanel;
    private JButton registerButton;
    
    private DataBase base;
    private UserManager userManager;
    private VaccineManager vaccineManager;
    private TestManager testManager;
    private Calendar calendar;
    

    /**
     * Constructor for the GUI class. Creates an instance of the manager classes and sets up
     * the main frame of the GUI.
     * 
     */
	public GUI ()
	{
		base = new DataBase();
        calendar = base.getCalendar();
        userManager = base.getUserManager();
        testManager = base.getTestManager();
        vaccineManager = base.getVaccineManager();        
        frame = new JFrame();  
                
        frameSetUp(); 
      	  
	}
	   
	
    /**
     * Sets up the main frame of the GUI
     * 
     */	
    public void frameSetUp()
    {
    	
    	frame.setResizable(false);
	    frame.setSize(600, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(new ImageIcon("logo.png").getImage());
		frame.getContentPane().setBackground(new Color(15, 145,180));
		frame.getContentPane().setLayout(null);	
		
		mainMenu();
		
		frame.setVisible(true);	
		
    }
	 
    /**
     * System's main menu. Allows to choose between the different user's menu, add one
     * day to today's date (to allow the progress of time dependent events) or log out of 
     * the application
     */
    
	public void mainMenu()
	{
		
		// Reset the frame
		
		frame.getContentPane().removeAll();
		frame.repaint();		
		frame.setTitle("St Isabel Covid Health Center");		
	    
		// Clinic logo
		
        ImageIcon icon = new ImageIcon("clinic.logo.2.png");  
        
        JLabel logoLbl = new JLabel();
        logoLbl.setBounds(160, 20, 350, 300);
        logoLbl.setIcon(icon);
        frame.add(logoLbl);
		
		// date label and button
		
		JLabel timeLabel = new JLabel(" "+calendar.getTodaysDate());
		timeLabel.setBounds(450, 10, 80, 25); 
		frame.add(timeLabel);
		
		JButton upButton = new JButton("up");
		upButton.setFocusable(false);
		upButton.setBounds(525, 10, 60, 25);
		upButton.addActionListener(e -> {
			calendar.addOneDay();
			userManager.setConfinados();
			timeLabel.setText(" "+calendar.getTodaysDate());	
		});
        frame.add(upButton);
        
        success = new JLabel();
        success.setBounds(215, 465, 300, 25);
        frame.add(success);
		
        // login items
        
		userLabel = new JLabel("User");
		userLabel.setBounds(150, 340, 80, 25); 
		frame.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(230, 340, 165, 25);
		frame.add(userText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(150, 390, 80, 25);
		frame.add(passwordLabel);
		
        passwordText = new JPasswordField();
        passwordText.setBounds(230, 390, 165, 25);
        frame.add(passwordText);
        
        button = new JButton("Login");
        button.setBounds(270, 440, 80, 25);
        button.addActionListener(e -> login());
        frame.add(button);
       
       
        frame.validate();
		 
	} 
	
    /**
     * Admin menu. Manages users, vaccine stock, appointments and quarantined patients
     * 
     *  Username = Admin
     *  Password = admin
     */
	public void adminMenu()
	{
		
	    // reset frame
		
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.setTitle("St Isabel Covid Health Center - Admin Menu");
		adminMenuBar(frame);	
		
		// Admin logo
		
        ImageIcon adminIcon = new ImageIcon("a_icon.png");  
        
        JLabel adminLbl = new JLabel("  Welcome, Admin");
        adminLbl.setForeground(Color.WHITE);
        adminLbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 27));
        adminLbl.setBounds(30, -30, 450, 150);
        adminLbl.setIcon(adminIcon);
        frame.add(adminLbl);		
		
		// white panel
		
		adminPanel = new JPanel();
		adminPanel.setBackground(Color.white);
		adminPanel.setLayout(null);
		adminPanel.setBounds(130, 100, 445, 380);
		adminPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 3));	
		frame.add(adminPanel);			
	
		
		// left side buttons
		
		JButton showPatients = new JButton("Patients");
		showPatients.setBounds(15, 100, 100, 60);
		showPatients.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollPatients = getScrollPaneMap(userManager.getPatientMap());
			scrollPatients.setColumnHeaderView(new JLabel(" ID                      Full name                    Contact n"
					+ "          Birth date        Quarantine start date"));
			adminPanel.add(scrollPatients);
			adminPanel.revalidate();
		});
		frame.add(showPatients);
		
		JButton showStaff = new JButton("Staff");
		showStaff.setBounds(15, 180, 100, 60);
		showStaff.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollStaff = getScrollPaneMap(userManager.getStaffMap());
			scrollStaff.setColumnHeaderView(new JLabel(" ID                        Full name                       Position"
					+ "             Contact n"));			
			adminPanel.add(scrollStaff);	
			adminPanel.revalidate();
		});
		frame.add(showStaff);
		
		JButton showTests = new JButton("Tests");
		showTests.setBounds(15, 260, 100, 60);
		showTests.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollTest = getScrollPaneMap(testManager.getTagenda());
			scrollTest.setColumnHeaderView(new JLabel(" Test ID     Date                  Test type"
					+ "                      Results          Patient name              Nurse name                Technician name       "));		
			adminPanel.add(scrollTest);	
			adminPanel.revalidate();
		});
		frame.add(showTests);
		
		JButton showJabs = new JButton("Jabs");
		showJabs.setBounds(15, 340, 100, 60);
		showJabs.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollJab = getScrollPaneMap(vaccineManager.getVagenda());
			scrollJab.setColumnHeaderView(new JLabel(" Test ID     Date                  Patient name             Nurse name               "
					+ "    Serial           Manufacturer     Doses       "));		
			adminPanel.add(scrollJab);
			adminPanel.revalidate();
		});
		frame.add(showJabs);

		JButton showStock = new JButton("Stock");
		showStock.setBounds(15, 420, 100, 60);
		showStock.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollStock = getScrollPaneArray(vaccineManager.getVstock());
			scrollStock.setColumnHeaderView(new JLabel("  Serial           Manufacturer     Doses"));			
			adminPanel.add(scrollStock);	
			adminPanel.revalidate();
		});
		frame.add(showStock);
							
		
		frame.validate();
	}
	
    /**
     * Menu that the staff of the clinic can access by inserting their name and ID.
     * Allows the staff to see their scheduled appointments, update the results of their
     * scheduled tests and list the medical records of their assigned patients. Nurses
     * have an extra button for scheduled jab appointments. 
     * The names and ID of the staff can be seen by login in as admin and displaying
     * the staff list
     * 
     * Username = employee's full name
     * Password = employee's ID
     */
	@SuppressWarnings("deprecation")
	public void staffMenu()
	{
		
		// Reset frame
		
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.setTitle("St Isabel Covid Health Center - Staff Menu");
		staffMenuBar(frame);
	
		
	    // Staff logo
		
        ImageIcon staffIcon = new ImageIcon("s_icon.png");  
        
        JLabel staffLbl = new JLabel("  Welcome, " + userText.getText());
        staffLbl.setForeground(Color.WHITE);
        staffLbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        staffLbl.setBounds(30, -30, 550, 150);
        staffLbl.setIcon(staffIcon);
        frame.add(staffLbl);		
		
		// white panel		
		
		adminPanel = new JPanel();
		adminPanel.setBackground(Color.white);
		adminPanel.setLayout(null);
		adminPanel.setBounds(130, 100, 445, 380);
		adminPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 3));	
		frame.add(adminPanel);			
		
		// left side buttons
		
		JButton showStaff = new JButton("My patients");
		showStaff.setFont(new Font("Arial", Font.BOLD, 11));
		showStaff.setBounds(15, 100, 100, 60);
		showStaff.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollPatients = getScrollPaneArray(userManager.getStaffMap().get(passwordText.getText()).getAssignedPatients());
			scrollPatients.setColumnHeaderView(new JLabel(" ID                      Full name                    Contact n"
					+ "          Birth date        Quarantine start date"));
			adminPanel.add(scrollPatients);
			adminPanel.revalidate();
		});
		
		frame.add(showStaff);
		
		JButton showPatients = new JButton("My tests");
		showPatients.setBounds(15, 180, 100, 60);
		showPatients.addActionListener(e -> {
			adminPanel.removeAll();
			JScrollPane scrollTest = getScrollPaneMap(userManager.getStaffMap().get(passwordText.getText()).getAppAgenda());
			scrollTest.setColumnHeaderView(new JLabel(" Test ID     Date                  Test type"
					+ "                      Results          Patient name              Nurse name                Technician name       "));		
			adminPanel.add(scrollTest);	
			adminPanel.revalidate();
		});
		frame.add(showPatients);
		
		
		if(userManager.getNurseMap().containsKey(passwordText.getText())) {
			JButton showJabs = new JButton("My jabs");
			showJabs.setBounds(15, 260, 100, 60);
			showJabs.addActionListener(e -> {
				adminPanel.removeAll();
				JScrollPane scrollJab = getScrollPaneMap(userManager.getNurseMap().get(passwordText.getText()).getJabsAgenda());
				scrollJab.setColumnHeaderView(new JLabel(" Test ID     Date                  Patient name             Nurse name               "
						+ "    Serial           Manufacturer     Doses       "));		
				adminPanel.add(scrollJab);
				adminPanel.revalidate();
			});
			frame.add(showJabs);
		}
		
		frame.validate();
	}
	
   
	/**
     * Staff menu's menu bar. Apart from going back to the main menu and quiting the aprogram, allows 
     * the employee to see the medical records of the assigned patients and update a test result of
     * the scheduled appointments
     * 
     * @param main frame
     */
	@SuppressWarnings("deprecation")
	public void staffMenuBar(JFrame frame) 
	{
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		
		// Menu bar: menu
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);	
		
		JMenuItem openItem =  new JMenuItem("Go back");
		openItem.addActionListener(e ->{
			
			frame.setJMenuBar(null);
			mainMenu();			
		});
		menu.add(openItem);
		
		JMenuItem quitItem =  new JMenuItem("Quit");
		quitItem.addActionListener(e -> System.exit(0));
		menu.add(quitItem);	
		
		// Menu bar: users
		
		JMenu userMenu = new JMenu("Patients");
		menubar.add(userMenu);	
		
		JMenuItem medicalRecords =  new JMenuItem("Medical records");
		medicalRecords.addActionListener(e -> showMyRecords(passwordText.getText()));
		userMenu.add(medicalRecords);
		
		// Menu bar: tests
		
		JMenu testMenu = new JMenu("Tests");
		menubar.add(testMenu);	
		
		JMenuItem testResults =  new JMenuItem("Update test results");
		testResults.addActionListener(e -> updateResult(passwordText.getText()));
		testMenu.add(testResults);
	}
	
	
	/**
     * Clinic admin menu's menu bar. Apart from going back to the main menu and quiting the aprogram, allows 
     * the admin to register and manage users, add new stock, book  and manage and contact self isolating 
     * patients
     * 
     * @param main frame
     */
		
	public void adminMenuBar(JFrame frame) 
	{
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		
		// Menu bar: menu
		JMenu menu = new JMenu("Menu");
		menubar.add(menu);	
		
		JMenuItem openItem =  new JMenuItem("Go back");
		openItem.addActionListener(e ->{
			
			frame.setJMenuBar(null);
			mainMenu();			
		});
		menu.add(openItem);
		
		
		
		JMenuItem quitItem =  new JMenuItem("Quit");
		quitItem.addActionListener(e -> System.exit(0));
		menu.add(quitItem);	
		
		
		// Menu bar: users
		JMenu userMenu = new JMenu("Users");
		menubar.add(userMenu);	
		
		JMenuItem registerUser =  new JMenuItem("Register user");
		registerUser.addActionListener(e -> newUser());
		userMenu.add(registerUser);

		JMenuItem deleteUser =  new JMenuItem("Delete user");
		deleteUser.addActionListener(e -> deleteUser());
		userMenu.add(deleteUser);
		
		JMenuItem medicalRecords =  new JMenuItem("Medical records");
		medicalRecords.addActionListener(e -> showRecords("patient"));
		userMenu.add(medicalRecords);
		
		JMenuItem staffAgenda =  new JMenuItem("Staff agenda");
		staffAgenda.addActionListener(e -> showRecords("staff"));
		userMenu.add(staffAgenda);
		
		
		// Menu bar: stock
		JMenu stockMenu = new JMenu("Stock");
		menubar.add(stockMenu);
		
		JMenuItem addStock =  new JMenuItem("Add Stock");
		addStock.addActionListener(e -> addStock());
		stockMenu.add(addStock);
		
		// Menu bar: appointments
		JMenu appointmentsMenu = new JMenu("Appointments");
		menubar.add(appointmentsMenu);
		
		JMenuItem priority =  new JMenuItem("Jab Priority system");
		priority.addActionListener(e -> runPrioritySystem());
		appointmentsMenu.add(priority);
		
		JMenuItem newTest =  new JMenuItem("New test appointment");
		newTest.addActionListener(e -> newTest());
		appointmentsMenu.add(newTest);
		
		JMenuItem newJab =  new JMenuItem("New jab appointment");
		newJab.addActionListener(e -> newJab());
		appointmentsMenu.add(newJab);
		
		JMenuItem deleteAppointment =  new JMenuItem("Delete appointment");
		deleteAppointment.addActionListener(e -> deleteAppointment());
		appointmentsMenu.add(deleteAppointment);		
		
		// Menu bar: quarantine
		JMenu quarantineMenu = new JMenu("Quarantine");
		menubar.add(quarantineMenu);
		
		JMenuItem callPatient =  new JMenuItem("Call quarantined patient");
		callPatient.addActionListener(e -> callPatient());
		quarantineMenu.add(callPatient);
		
		
	}
	   
    
	/**
     * Compares the text inserted in the user and password textBoxes against the registered users and 
     * opens the admin, nurse or technician manu based on it
     */
    private void login()
    {
        String user = userText.getText();
        @SuppressWarnings("deprecation")
		String password = passwordText.getText();
               
        if( (userManager.getTechnicianMap().containsKey(password) && userManager.getTechnicianMap().get(password).getName().equals(user)) ||
           (userManager.getNurseMap().containsKey(password) && userManager.getNurseMap().get(password).getName().equals(user)) ){
        	staffMenu();
        }else if(user.equals("Admin") && password.equals("admin")){
        	adminMenu();
        }else {
        	success.setText("Incorrect password or username");
        }
    }
    
    
    /**
     * Request the details of a new user and calls the addUser method to double check
     * that all the inserted details are correct
     * 
     */
    private void newUser()
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	// new user labels and text boxes
    	
    	JLabel userType = new JLabel("User type");
    	userType.setBounds(50, 50, 80, 25); 
		adminPanel.add(userType);
		
        String[] type = {"Patient", "Nurse", "Technician"};
    	JComboBox<String> comboBox = new JComboBox<>(type);
    	comboBox.setBounds(170, 50, 165, 25);
        comboBox.addActionListener(e -> comboBox.getSelectedItem());
    	adminPanel.add(comboBox);
        
    	JLabel userID = new JLabel("User ID");
    	userID.setBounds(50, 90, 100, 25);
    	adminPanel.add(userID);
    	
    	JTextField userIDtext = new JTextField();
    	userIDtext.setBounds(170, 90, 165, 25);
    	adminPanel.add(userIDtext);
    	
    	JLabel userName = new JLabel("User Name");
    	userName.setBounds(50, 130, 100, 25); 
		adminPanel.add(userName);
		
		JTextField userNameText = new JTextField();
		userNameText.setBounds(170, 130, 165, 25);
		adminPanel.add(userNameText);
		
    	JLabel userBirthD = new JLabel("Date of birth");
    	userBirthD.setBounds(50, 170, 100, 25); 
		adminPanel.add(userBirthD);
		
		JTextField userBirthDText = new JTextField();
		userBirthDText.setBounds(170, 170, 165, 25);
		adminPanel.add(userBirthDText);
		
    	JLabel userPhone = new JLabel("Phone number");
    	userPhone.setBounds(50, 210, 100, 25); 
		adminPanel.add(userPhone);
		
		JTextField userPhoneText = new JTextField();
		userPhoneText.setBounds(170, 210, 165, 25);
		adminPanel.add(userPhoneText);
		
		registered = new JLabel();
        adminPanel.add(registered);	
		
		// Register button		
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(170, 280, 100, 25);
        registerButton.addActionListener(e -> addUser(comboBox.getSelectedItem().toString().toLowerCase(), userIDtext.getText(), 
        		userNameText.getText(), userBirthDText.getText(), userPhoneText.getText()));
        adminPanel.add(registerButton);    		
    	
    	frame.validate();
    	
    }
    
    /**
     * Checks that the details of a new user are correct and if they are, passes on those details to the
     * newPerson method of the userManager class
     * 
     * @param the details of a new user
     */
    private void addUser(String type, String ID, String name, String dateOfBirth, String phoneNumber)
    {
    	
    	
    	if(!ID.matches("([0-9]{8})([A-Z]{1})")) {
    		registered.setBounds(80, 320, 300, 25);
    		registered.setText("ID must be 8 digits followed by an uppercase letter");
        }else if(!phoneNumber.matches("([0-9]{9})")) {
        	registered.setBounds(130, 320, 300, 25);
        	registered.setText("Phone number must be 9 digits");
        }else if(!calendar.checkFormat(dateOfBirth)){
        	registered.setBounds(100, 320, 300, 25);
        	registered.setText("Date of birth must have (yyyy/mm/dd) format");
        }else {
    	userManager.newPerson(type, name, ID, phoneNumber, dateOfBirth);
    	registered.setBounds(160, 320, 300, 25);
    	registered.setText("Added to the system");
        }
    }
    
    
    /**
     * Deletes an existing user from the system. This user can be a patient, a nurse or a technician
     * 
     */
    private void deleteUser()
    {   
    	
       	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	Object[] aux = new Object[1];    
    	
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setBounds(10, 100, 420, 200);     	
    	adminPanel.add(scrollPane); 
    	
    	JLabel userType = new JLabel("Enter the type of user: ");
    	userType.setBounds(50, 30, 160, 25); 
		adminPanel.add(userType);
		
		JLabel deleteLbl = new JLabel("");
		deleteLbl.setBounds(140, 340, 150, 25);
        adminPanel.add(deleteLbl);	    	
        
    	JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(160, 310, 100, 25);
        deleteButton.addActionListener(e -> {
        	Person person = (Person) aux[0];
        	userManager.removePersona(person.getID()); 	
        	deleteLbl.setText("Deleted from the system");
        });     
        adminPanel.add(deleteButton);           
		
		
		// comboBox of the user type
		
        String[] type = {"Patient", "Nurse", "Technician"};
    	JComboBox<String> comboBox = new JComboBox<>(type);
    	comboBox.setBounds(200, 30, 165, 25);
        comboBox.addActionListener(e ->{            	
        	
        	ArrayList<Object> arrayHolder = new ArrayList<>();        	      	
        	
        	// Depending on the user type, a differen list of users is stored in 
        	// the array and shown in the scroll panel
        	
        	if( comboBox.getSelectedItem().toString().equals("Patient")) {    	
        	    userManager.getPatientMap().forEach((key, value) -> { arrayHolder.add(value); });
        	    scrollPane.setColumnHeaderView(new JLabel(" ID                      Full name                    Contact n"
    					+ "          Birth date        Quarantine start date"));
        	}else if( comboBox.getSelectedItem().toString().equals("Nurse")) { 
        		userManager.getNurseMap().forEach((key, value) -> { arrayHolder.add(value); });
        		scrollPane.setColumnHeaderView(new JLabel(" ID                        Full name                       Position"
    					+ "             Contact n"));
        	}else if( comboBox.getSelectedItem().toString().equals("Technician")) { 
        		userManager.getTechnicianMap().forEach((key, value) -> { arrayHolder.add(value); });
        		scrollPane.setColumnHeaderView(new JLabel(" ID                        Full name                       Position"
    					+ "             Contact n"));
        	}
        	
        	JList<Object> list = new JList<>(arrayHolder.toArray());
        	list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
        	list.addListSelectionListener (new ListSelectionListener(){
	
        			@Override
        			public void valueChanged(ListSelectionEvent e) {
        				aux[0] = list.getSelectedValue();
        			}        			
        	});    	

          	scrollPane.setViewportView(list);
              	
        });
    	adminPanel.add(comboBox); 
        
        
    	frame.validate();
    }
    
    
    /**
     * Authorizes the user to see a patient's medical history or the staff appointment records 
     * 
     * @param option: patient for medical records and staff for appointment records
     * 
     */    
    private void showRecords(String option)
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	ArrayList<Object> arrayHolder = new ArrayList<>();   
    	
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setBounds(10, 50, 420, 150);     	
    	adminPanel.add(scrollPane); 
    	
		JScrollPane scrollRecords = new JScrollPane();    		    		
		scrollRecords.setBounds(10, 200, 420, 170);
		adminPanel.add(scrollRecords);
    	
    	JLabel selectPatient = new JLabel();    	
    	selectPatient.setBounds(10, 20, 160, 25); 
		adminPanel.add(selectPatient);
		
		if(option.equals("patient")) {
			selectPatient.setText("Select a patient:");
    	    userManager.getPatientMap().forEach((key, value) -> { arrayHolder.add(value); });
    	    scrollPane.setColumnHeaderView(new JLabel(" ID                      Full name                    Contact n"
					+ "          Birth date        Quarantine start date"));
		}else if(option.equals("staff")) {
			selectPatient.setText("Select an employee:");
			userManager.getStaffMap().forEach((key, value) -> { arrayHolder.add(value); });
			scrollPane.setColumnHeaderView(new JLabel(" ID                        Full name                       Position"
					+ "             Contact n"));			
		}
		
		
		
    	JList<Object> list = new JList<>(arrayHolder.toArray());
    	list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
    	list.addListSelectionListener (e -> {
    		
    		Person person = (Person) list.getSelectedValue();
    		    		
    		if(option.equals("patient")) {
    			scrollRecords.setViewportView(getScrollPaneMap(userManager.getPatientMap().get(person.getID()).getMedicalHistory()));
    		}else if(option.equals("staff")) {
    			scrollRecords.setViewportView(getScrollPaneMap(userManager.getStaffMap().get(person.getID()).getAppAgenda()));
    		}   		

    		frame.validate();    		
    		
    	});    	
    	
    	scrollPane.setViewportView(list);    	
    	
    	frame.validate();
    }
    
    /**
     * Authorizes a member of the staff to see the medical records of it's assigned patients only 
     * 
     * @param the ID of the member of the staff
     * 
     */   
    private void showMyRecords(String staffID)
    {
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	JLabel selectPatient = new JLabel("Select a patient");    	
    	selectPatient.setBounds(10, 20, 160, 25); 
		adminPanel.add(selectPatient);		
		
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setColumnHeaderView(new JLabel(" ID                      Full name                    Contact n"
				+ "          Birth date        Quarantine start date"));
    	scrollPane.setBounds(10, 50, 420, 150);     	
    	adminPanel.add(scrollPane); 
    	
		JScrollPane scrollRecords = new JScrollPane();    		    		
		scrollRecords.setBounds(10, 200, 420, 170);
		adminPanel.add(scrollRecords);
		
		
    	JList<Object> list = new JList<>(userManager.getStaffMap().get(staffID).getAssignedPatients().toArray());    
    	list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
    	list.addListSelectionListener (e -> {
    		
    		
    		Person person = (Person) list.getSelectedValue();    		
    		scrollRecords.setViewportView(getScrollPaneMap(userManager.getPatientMap().get(person.getID()).getMedicalHistory()));
    		frame.validate();    		
    		
    	});    	    	
    	
    	scrollPane.setViewportView(list);
    	
    	frame.validate();
    	
    }
    
    
    /**
     * This method informs of the total available vaccine stock of the clinic
     * and lets the user order more 
     * 
     */
    private void addStock()
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	JLabel totalStock = new JLabel("Total vaccine stock is " + vaccineManager.getVstock().size() + " units. Order more?: ");
    	totalStock.setBounds(15, 30, 270, 25);
    	adminPanel.add(totalStock);
    	
    	JLabel amountLbl = new JLabel("Units to order of each type: ");
    	amountLbl.setBounds(15, 90, 200, 25);  
    	amountLbl.setVisible(false);
    	adminPanel.add(amountLbl);
    	
    	JTextField amountTxt = new JTextField();
    	amountTxt.setBounds(200, 90, 50, 25);   
    	amountTxt.setVisible(false);
    	adminPanel.add(amountTxt);
    	
    	JLabel infoLbl = new JLabel();
		infoLbl.setBounds(15, 150, 250, 25); 
    	adminPanel.add(infoLbl);
    	
    	JButton addBtn = new JButton("Add stock");
    	addBtn.setBounds(260, 90, 100, 25);
    	addBtn.setVisible(false);
    	addBtn.addActionListener(e -> { 		
        	
    		if(vaccineManager.newStock(amountTxt.getText())) {
    			infoLbl.setText("Ordered " + amountTxt.getText() + " units of each vaccine type");
    		}else {
    			infoLbl.setText("Entered format is not valid");
    		}    		
		});
		adminPanel.add(addBtn);
    	
		// radio button group
		
    	JRadioButton yesButton = new JRadioButton("Yes");
    	yesButton.addActionListener(e -> {
    		
    		amountLbl.setVisible(true);
    		amountTxt.setVisible(true); 
    		addBtn.setVisible(true);
    		
    	});
    	yesButton.setBounds(290, 30, 50, 25);
    	adminPanel.add(yesButton);
    	
    	JRadioButton noButton = new JRadioButton("No");
    	noButton.addActionListener(e -> adminMenu());
    	noButton.setBounds(340, 30, 50, 25);   	
    	adminPanel.add(noButton);
    	
    	ButtonGroup group = new ButtonGroup();
    	group.add(noButton);
    	group.add(yesButton);       	
    	
    	frame.validate();
    	
    }
    
    /**
     * Runs the Vaccination priority system based on the registered patientis and
     * the vaccine stock of the clinic
     * 
     */
    private void runPrioritySystem()
    {
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	JLabel infoLbl = new JLabel("Available stock: "+vaccineManager.getVstock().size()+ ", Unvaccinated patients: "+ userManager.getSortedPatients().size());
    	infoLbl.setBounds(15, 20, 270, 25);
    	adminPanel.add(infoLbl);
    	
    	JScrollPane scrollPane = getScrollPaneArray(vaccineManager.getPlanificacion());
    	scrollPane.setBounds(10, 70, 420, 300);
    	adminPanel.add(scrollPane);
    	
    	frame.validate();
    }

    /**
     * Requests the details of a new test and calls the addTest method to checks that the entered
     * details are correct
     *
     */
    private void newTest()
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	// new test labels and text boxes
    	
    	JLabel testType = new JLabel("Test type");
    	testType.setBounds(50, 50, 80, 25); 
		adminPanel.add(testType);
		
        String[] type = {"PCR test", "Antibodies test", "Antigens test: quick", "Antigens test: classic"};
    	JComboBox<String> comboBox = new JComboBox<>(type);
    	comboBox.setBounds(170, 50, 165, 25);
        comboBox.addActionListener(e -> comboBox.getSelectedItem());
    	adminPanel.add(comboBox);
        
    	JLabel patientID = new JLabel("Patient ID: ");
    	patientID.setBounds(50, 90, 100, 25);
    	adminPanel.add(patientID);
    	
    	JTextField patientIDtext = new JTextField();
    	patientIDtext.setBounds(170, 90, 165, 25);
    	adminPanel.add(patientIDtext);
    	
    	JLabel nurseID = new JLabel("Nurse ID: ");
    	nurseID.setBounds(50, 130, 100, 25); 
		adminPanel.add(nurseID);
		
		JTextField nurseIDText = new JTextField();
		nurseIDText.setBounds(170, 130, 165, 25);
		adminPanel.add(nurseIDText);
		
    	JLabel techID = new JLabel("Technician ID: ");
    	techID.setBounds(50, 170, 100, 25); 
		adminPanel.add(techID);
		
		JTextField techIDText = new JTextField();
		techIDText.setBounds(170, 170, 165, 25);
		adminPanel.add(techIDText);
		
    	JLabel testDate = new JLabel("Test date: ");
    	testDate.setBounds(50, 210, 100, 25); 
		adminPanel.add(testDate);
		
		JTextField testDateText = new JTextField();
		testDateText.setBounds(170, 210, 165, 25);
		adminPanel.add(testDateText);
		
		registered = new JLabel();
        adminPanel.add(registered);	
		
		// Register button		
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(170, 280, 100, 25);
        registerButton.addActionListener(e -> addTest(comboBox.getSelectedItem().toString(), patientIDtext.getText(), 
        		nurseIDText.getText(), techIDText.getText(), testDateText.getText()));
        adminPanel.add(registerButton);      

		
    	
    	frame.validate();
    	
    }
    
    /**
     * Receives the details of a new test and calls testManager.addTest() to store in the system
     * after checking that they are correct. 
     */
    private void addTest(String type, String patientID, String nurseID, String techID, String testDate)
    {
    	
    	
    	if(!cantFind(userManager.getPatientMap().get(patientID))) {
    		registered.setBounds(100, 320, 300, 25);
    		registered.setText("Unable to find the patient, please try again");
    	}else if(!calendar.checkFormat(testDate) || calendar.isBefore(testDate)){
        	registered.setBounds(70, 320, 350, 25);
        	registered.setText("The date must be future and have (yyyy/mm/dd) format");
        }else if(!cantFind(userManager.getNurseMap().get(nurseID))) {
        	registered.setBounds(150, 320, 300, 25);
        	registered.setText("Unable to find the nurse");
        }else if(!testManager.setNurse(nurseID, testDate)) {
        	registered.setBounds(130, 320, 300, 25);
        	registered.setText("This nurse has reached the limit of appointments for that week");
        }else if(!cantFind(userManager.getTechnicianMap().get(techID))){
        	registered.setBounds(150, 320, 300, 25);
        	registered.setText("Unable to find the technician");
        }else if(!testManager.setTechnician(techID, testDate)) {
           	registered.setBounds(130, 320, 300, 25);
        	registered.setText("This technician has reached the limit of appointments for that week"); 	
        
        }else {
    	testManager.addTest(patientID, nurseID, techID, testDate, type);
    	registered.setBounds(160, 320, 300, 25);
    	registered.setText("Added to the system");
        }
    }
    
    /**
     * Requests the information related to a new vaccination appointment and calls
     * the addJab method to check that these are correct.
     */
    private void newJab() 
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	// new user labels and text boxes
    	
    	JLabel priorityLbl = new JLabel("Priority list:");
    	priorityLbl.setBounds(15, 15, 100, 25); 
		adminPanel.add(priorityLbl);
		
        JScrollPane priorityPanel = getScrollPaneMap(userManager.getSortedPatients());
        priorityPanel.setBounds(15, 40, 420, 150);
        adminPanel.add(priorityPanel);
        
    	JLabel patientID = new JLabel("Patient ID: ");
    	patientID.setBounds(20, 200, 100, 25);
    	adminPanel.add(patientID);
    	
    	JTextField patientIDtext = new JTextField();
    	patientIDtext.setBounds(150, 200, 165, 25);
    	adminPanel.add(patientIDtext);
    	
    	JLabel nurseID = new JLabel("Nurse ID: ");
    	nurseID.setBounds(20, 230, 100, 25); 
		adminPanel.add(nurseID);
		
		JTextField nurseIDText = new JTextField();
		nurseIDText.setBounds(150, 230, 165, 25);
		adminPanel.add(nurseIDText);

    	JLabel jabDate = new JLabel("Appointment date: ");
    	jabDate.setBounds(20, 260, 165, 25); 
		adminPanel.add(jabDate);
		
		JTextField jabDateText = new JTextField();
		jabDateText.setBounds(150, 260, 165, 25);
		adminPanel.add(jabDateText);
		
	
		registered = new JLabel();
        adminPanel.add(registered);	
		
		// Register button		
        
        registerButton = new JButton("Register");
        registerButton.setBounds(170, 320, 100, 25);
        registerButton.setVisible(true);
        registerButton.addActionListener(e -> addJab(patientIDtext.getText(), nurseIDText.getText(),jabDateText.getText()));
        adminPanel.add(registerButton);      		
    	
    	frame.validate();
    	
    }
    
    /**
     * Receives the details of a new vaccine and calls vaccineManager.addVaccination to store in the system
     * after checking that they are correct. 
     */
    private void addJab(String patientID, String nurseID, String jabDate)
    {
    	
    	
    	if(!cantFind(userManager.getPatientMap().get(patientID))){
    		registered.setBounds(150, 350, 300, 25);
    		registered.setText("Unable to find the patient");
    	}else if (!vaccineManager.checkPriority(patientID)) {
    		registered.setBounds(20, 350, 410, 25);
    		registered.setText("There are patients with a higher priority that haven't been vaccinated");
    	}else if(!calendar.checkFormat(jabDate)|| calendar.isBefore(jabDate)){
        	registered.setBounds(60, 350, 350, 25);
        	registered.setText("The date must be future and have (yyyy/mm/dd) format");
        }else if(!cantFind(userManager.getNurseMap().get(nurseID))){
        	registered.setBounds(150, 350, 300, 25);
        	registered.setText("Unable to find the nurse");
        }else{
      
	        int doses = vaccineManager.addVaccination(patientID, nurseID, jabDate);
	        
	        if(doses == 2) {
	        	
	        	adminPanel.repaint();
	        	
	        	JLabel jab2Date = new JLabel("Second dose date: ");
	        	jab2Date.setBounds(20, 290, 165, 25); 
	    		adminPanel.add(jab2Date);
	    		
	    		JTextField jab2DateText = new JTextField();
	    		jab2DateText.setBounds(150, 290, 165, 25);
	    		adminPanel.add(jab2DateText);
	    		
	    		registerButton.setVisible(false);
	    		
	            JButton addButton = new JButton("Add date");
	            addButton.setBounds(170, 320, 100, 25);
	            addButton.addActionListener(e ->{
	            	
	            	if(!vaccineManager.addSecondJab(patientID, nurseID,jab2DateText.getText())) {
	            		registered.setBounds(30, 350, 410, 25);
	            		registered.setText("A minimum gap of 21 days is required between appointments");
	            	}else {
	                    registered.setBounds(150, 350, 300, 25);
	                	registered.setText("Added second appointment");
	            	}
	            });
	            adminPanel.add(addButton);     
	            
	    		adminPanel.validate();
	        }
	    	
	        registered.setBounds(160, 350, 300, 25);
	    	registered.setText("Added to the system");
    	
        }
    }
    
    /**
     * Deletes an appointment from the system by showing the list of scheduled appointments
     * and clicking on the one the user wants to delete
     * 
     */
    private void deleteAppointment()
    {   
    	
       	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	Object[] aux = new Object[1];    
    	
    	// labels and buttons 
    	
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setBounds(10, 100, 420, 200);     	
    	adminPanel.add(scrollPane); 
    	
    	JLabel userType = new JLabel("Type of appointment: ");
    	userType.setBounds(50, 30, 160, 25); 
		adminPanel.add(userType);
		
		JLabel deleteLbl = new JLabel("");
		deleteLbl.setBounds(140, 340, 150, 25);
        adminPanel.add(deleteLbl);	    
        
       	JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(160, 310, 100, 25);
        deleteButton.addActionListener(e -> {
        	
        	String substringID = aux[0].toString().substring(0,aux[0].toString().indexOf(' '));
        			
            if(aux[0].toString().contains("P")) {	    		
	    		testManager.removeTest(substringID);   
	    		
            }else if(aux[0].toString().contains("V")){        		
	        	vaccineManager.removeVaccination(substringID);	        	
        	}
            deleteLbl.setText("Deleted from the system");
        });     
        adminPanel.add(deleteButton);      
		
		
		// Appointment type comboBox 
		
        String[] type = {"Test", "Jab",};
    	JComboBox<String> comboBox = new JComboBox<>(type);
    	comboBox.setBounds(200, 30, 165, 25);
        comboBox.addActionListener(e ->{        	
        	        	
        	ArrayList<Object> arrayHolder = new ArrayList<>();        	      	
        	
        	// Depending on the type, different data is loaded into arrayHolder
        	
        	if( comboBox.getSelectedItem().toString().equals("Test")) {    	     
        	    testManager.getTagenda().forEach((key, value) -> { arrayHolder.add(String.format("%-9s", key) + value[0]+" "+ String.format("%-18s", value[1])+String.format("%-19s", value[2])+ String.format("%-18s", value[3])); });
    			scrollPane.setColumnHeaderView(new JLabel(" Test ID     Date                  Test type"
    					+ "                      Results          Patient name              Nurse name                Technician name       "));
        	
        	}else if( comboBox.getSelectedItem().toString().equals("Jab")) { 
        		vaccineManager.getVagenda().forEach((key, value) -> { arrayHolder.add(String.format("%-9s", key) + value[0]+" "+ String.format("%-18s", value[1])+String.format("%-19s", value[2])+ String.format("%-18s", value[3])); });
    			scrollPane.setColumnHeaderView(new JLabel(" Jab ID      Date                  Patient name             Nurse name               "
    					+ "    Serial           Manufacturer     Doses       "));
        	}
        	
        	JList<Object> list = new JList<>(arrayHolder.toArray());
        	list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
        	list.addListSelectionListener (new ListSelectionListener(){
	
        			@Override
        			public void valueChanged(ListSelectionEvent e) {
        				aux[0] = list.getSelectedValue();
        			}        			
        	});    	
        
          	scrollPane.setViewportView(list);     
        	
        });
    	adminPanel.add(comboBox);
    		
             
    	frame.validate();
    }
    

    /**
     * Shows the scheduled appointments of an employee and allows to update their results
     * 
     * @param: staffID, the ID of the employee that wishes to see its booked tests
     */
    private void updateResult(String staffID)
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	Object[] aux = new Object[1];  
    	Object[] result = new Object[1];  
        
    	// labels and buttons
    	
    	JLabel priorityLbl = new JLabel("Select test to update:");
    	priorityLbl.setBounds(15, 15, 200, 25); 
		adminPanel.add(priorityLbl);	
    	
    	JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setBounds(10, 50, 420, 200);     	
    	adminPanel.add(scrollPane); 
    	
      	JLabel resultLbl = new JLabel("Select the test result: ");
    	resultLbl.setBounds(15, 285, 150, 25); 
    	resultLbl.setVisible(false);
		adminPanel.add(resultLbl);
		
		JLabel updatedLbl = new JLabel("");
		updatedLbl.setBounds(170, 330, 150, 25);
        adminPanel.add(updatedLbl);	   
    	
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.white);
		bottomPanel.setLayout(null);	
		bottomPanel.setBounds(155, 280, 150, 30);	
		adminPanel.add(bottomPanel);	 
		
    	JButton updateButton = new JButton("Update");
    	updateButton.setBounds(320, 285, 100, 25);
    	updateButton.setVisible(false);
    	updateButton.addActionListener(e -> {
        	
    		String testID = aux[0].toString().substring(0,aux[0].toString().indexOf(' '));
    		String results = result[0].toString();    	
    		testManager.setResult(testID, results);
    		updatedLbl.setText("Result Updated");
 
        });     
        adminPanel.add(updateButton);    
   	
        
        // ArrayList to store employee's scheduled tests
        
		ArrayList<Object> arrayHolder = new ArrayList<>();   
		
    	userManager.getStaffMap().get(staffID).getAppAgenda().forEach((key, value) -> 
        {        
             arrayHolder.add(String.format("%-9s", key) + value[0]+" "+ String.format("%-18s", value[1])+String.format("%-19s", value[2])+ String.format("%-18s", value[3]));            
        });
    	
    	
    	// New JList is created with the data stored in arrayHolder
    	
    	JList<Object> list = new JList<>(arrayHolder.toArray());
    	list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
    	list.addListSelectionListener (e -> {    
    		
    		resultLbl.setVisible(true);
    		updateButton.setVisible(true);
    		aux[0] = list.getSelectedValue();    		   		
    		
    		// If the item selected in the JList is an Antibodies test, a new comboBox is
    		// created with the allowed results for an antibodies test (an int of range 0 - 10)
    		
    		if(aux[0].toString().contains("Antibodies")) {
    		    bottomPanel.removeAll();
    		    String[] type1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    			JComboBox<String> comboBox = new JComboBox<>(type1);
    	    	comboBox.setBounds(5, 5, 140, 25);
    	        comboBox.addActionListener(new ActionListener(){
					
					public void actionPerformed(ActionEvent e) {
						result[0] = comboBox.getSelectedItem();						
					}    	        	
    	        });
    	        bottomPanel.add(comboBox);     	     
    	        frame.validate(); 
    	        
        	// If the item selected in the JList is not an Antibodies test, a new comboBox is
        	// created with the allowed results for the other tests (Positive or Negative)	  
    		}else {
    			bottomPanel.removeAll();
    			String[] type = {"Positive", "Negative"};
    			JComboBox<String> comboBox = new JComboBox<>(type);
    	    	comboBox.setBounds(5, 5, 140, 25);
    	        comboBox.addActionListener(new ActionListener(){
                 
					public void actionPerformed(ActionEvent e) {
						result[0] = comboBox.getSelectedItem();						
					}    	        	
    	        });
    	        bottomPanel.add(comboBox);    	       
    	        frame.validate(); 
    		}	 	    	
    	});    	
    	
    	scrollPane.setColumnHeaderView(new JLabel(" Test ID     Date                  Test type"
				+ "                      Results          Patient name              Nurse name                Technician name       "));
    	scrollPane.setViewportView(list);    	
    	
        frame.validate();    	
    
    }
    
  
    /**
     * COMING UP ....
     */
    private void callPatient()
    {
    	
    	adminPanel.removeAll();
    	adminPanel.repaint();
    	
    	// new user labels and text boxes
    	
    	JLabel priorityLbl = new JLabel("Quarantined patients:");
    	priorityLbl.setBounds(15, 15, 160, 25); 
		adminPanel.add(priorityLbl);
		
        JScrollPane priorityPanel = getScrollPaneMap(userManager.getIsoManager().getIsoPatients());
        priorityPanel.setBounds(15, 40, 420, 150);
        adminPanel.add(priorityPanel);
        
    	JLabel patientID = new JLabel("Enter patient's ID: ");
    	patientID.setBounds(20, 200, 160, 25);
    	adminPanel.add(patientID);
    	
    	JTextField patientIDtext = new JTextField();
    	patientIDtext.setBounds(150, 200, 180, 25);
    	adminPanel.add(patientIDtext);
    	
    	
		
		// Register button		
        
        JButton callButton = new JButton("Call patient");
        callButton.setBounds(170, 330, 100, 25);
        callButton.addActionListener(e ->{
        	
        });
        adminPanel.add(callButton);      		
    	
    	frame.validate();
    	
    }
    
    /**
     * Receives an ArrayList to populate and return a scrollPane of the stored data. A generic 
     * parameter is used to reuse the method with all the ArrayList structures of the application
     * 
     * @param:  array, generic ArrayList
     */
    private JScrollPane getScrollPaneArray(ArrayList<? extends Object> array) 
    {
    	JList<Object> list = new JList<>(array.toArray());
    	list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
		JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setBounds(10, 10, 430, 360);
	    return scrollPane;    	
    }
    
    
    /**
     * Receives a HashMap to populate and return a scrollPane of the stored data. A generic 
     * parameter is used to reuse the method with all the HashMap structures of the application
     * 
     * @param:  map, generic HashMap
     */
    private JScrollPane getScrollPaneMap(Map<? extends Object, ? extends Object> map) 
    {
    	
    	ArrayList<String> arrayHolder = new ArrayList<>();
    	map.forEach((key, value) -> 
        {
            if(value instanceof Object[]){
                Object[] valor = (Object[]) value;
                arrayHolder.add(String.format("%-9s", key) + valor[0]+" "+ String.format("%-18s", valor[1])+String.format("%-19s", valor[2])+ String.format("%-18s", valor[3]));
            }else{
            	arrayHolder.add(value.toString());
            }
        });
    	JList<Object> list = new JList<>(arrayHolder.toArray()); 
		list.setFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
    	JScrollPane scrollPane = new JScrollPane(list);
	    scrollPane.setBounds(10, 10, 430, 360);
	    return scrollPane;
    	
    }
    
    
    /**
     * If this method receives a parameter of null value, it returns false 
     * 
     * @param    value 
     * @return:  false if value's value is null     
     */
    public boolean cantFind(Object value)
    {        
          
           if(value == null){
               return false;               
           }   
           return true;
    }

}




