package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import logic.Customer;
import logic.Database;
import logic.Departure;
import logic.Person;
import logic.Plads;

/**
 * Skal have lavet et vindue, med kontaktperson øverst, og alle passagerer nederst.
 * Start med at lave antal personer som int, vi laver den dynamisk senere.
 * 
 * Kontaktperson: Titel, fornavn, efternavn, adresse, e-mail. tlf, fødselsdato.
 * Øvrige passagerer: Titel, fornavn, efternavn, fødselsdato
 * 
 * fortsæt-knap til betaling
 * 
 * Skal have en importer-kunde-knap (øverst)
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class Kundeoplysninger {
	private JFrame frame;
    private JTextField firstname, surname, email, phoneNumber, address, city;
    private JTextField postal, country;
    private JLabel labelFirstname, labelSurname, labelEmail, labelPhone, labelAddress;
	private JLabel labelCity, labelPostal, labelCountry, labelBirthday;
    private JButton back, next, importerKunde, confirm;
    private Container contentPane;
    private JPanel panel1, panel2, panel6, panelNorth;
    private JPanel panel, panelCenter;
    private ArrayList<Plads> reservations1, reservations2;
    private ArrayList<Person> existingPassengers;
    private ArrayList<Person> passengers = new ArrayList<>();
    private Customer customer;
    private ArrayList<JTextField> firstnameList = new ArrayList<>();
    private ArrayList<JTextField> surnameList = new ArrayList<>();
    private ArrayList<JTextField> birthdayList = new ArrayList<>();
    private boolean turRetur;
    private boolean importingCustomer = false;
    private boolean changingExistingCustomer = false;
    private boolean changingExistingPassengers = false;
    private Customer importedCustomer;
    private Customer c;
    private Gennemse g;
    
    private Departure d1, d2;
    
    private String firstnameS, surnameS, emailS, phoneS, addressS, cityS, postalCodeS, countryS;
	private Pladsbooking pb;
	
	//Constructor for tur/retur
    public Kundeoplysninger(ArrayList<Plads> reservations1, ArrayList<Plads> reservations2, Departure d1, Departure d2, Pladsbooking pb) {
    	turRetur = true;
    	this.reservations1 = reservations1;
    	this.reservations2 = reservations2;
    	this.d1 = d1;
    	this.d2 = d2;
    	this.pb = pb;
    	makeFrame();
    	addActionListeners();
    }
    
    //Constructor for enkeltrejse
    public Kundeoplysninger(ArrayList<Plads> reservations1, Departure d1, Pladsbooking pb) {
    	this.pb = pb;
    	turRetur = false;
    	this.reservations1 = reservations1;
    	this.d1 = d1;
    	makeFrame();
    	addActionListeners();
    }
    
    //Når vi skal ændre i en allerede eksisterende kunde
    public Kundeoplysninger(Customer c, Gennemse g) {
    	this.c = c;
    	this.g = g;
    	turRetur = false;
    	changingExistingCustomer = true;
    	makeFrame();
    	importCustomer(c);
    	addActionListeners();
    }
    
    //Når vi skal ændre i allerede eksisterende passagerer
    public Kundeoplysninger(ArrayList<Person> passengers, Gennemse g) {
    	this.g= g;
    	turRetur = false;
    	changingExistingPassengers = true;
    	existingPassengers = passengers;
    	makeFrame();
    	addActionListeners();
    }
    
    //Laver vinduet
    private void makeFrame() {
        frame = new JFrame();
    	frame.setTitle("Kundeoplysninger");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 480);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        contentPane.add(panel);
        
        //Scrollbar
        JScrollPane scroll = new JScrollPane(panel);
        frame.getContentPane().add(scroll);
        
        //Laver et BoxLayout i contentPane NORTH
        panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        panel.add(panelNorth, BorderLayout.NORTH);
        
        //Laver en JLabel og JBUtton, og indsætter i NORTH
        JLabel labelUp = new JLabel("Indtast dine kontaktoplysninger her");
        labelUp.setFont(new Font("String", Font.BOLD, 14));
        importerKunde = new JButton("Importer kunde");
        panelNorth.add(labelUp, BorderLayout.NORTH);
        panelNorth.add(importerKunde, BorderLayout.NORTH);
        
        //
        if(!changingExistingCustomer && !changingExistingPassengers) {
        	int counter = 0;
        	for(int i=0; i<reservations1.size(); i++) {
        		counter++;
        	}
        	centerPanel(counter);
        } else if (changingExistingCustomer) {
        	centerPanel(0);
        } else if (changingExistingPassengers) {
        	int counter = 0;
        	for(int i=0; i<existingPassengers.size(); i++) {
        		counter++;
        	}
        	centerPanel(counter);
        }        
        
        //Sætter panel6 til SOUTH i panel3, og giver det FlowLayout
        panel6 = new JPanel();
        panel.add(panel6, BorderLayout.SOUTH);
        panel6.setLayout(new FlowLayout());

        //Hvis vi er ved at indtaste friske oplysninger, lav tilbage og næste knapper.
        //ellers, lav en enkelt 'confirm' knap.
        if(!changingExistingCustomer && !changingExistingPassengers) {
        	//Opretter knapperne, og lægger dem i panel6
        	back = new JButton("Tilbage");
        	next = new JButton("Næste");
        	panel6.add(back);
        	panel6.add(next);
        } else {
        	confirm = new JButton("Bekræft ændringer");
        	panel6.add(confirm);
        }

        frame.setVisible(true);
    }
    
    //Indsætter labels og textFields for kontaktoplysninger, og kalder passagerer
    private void centerPanel(int antalPassagerer) {
    	//Laver et panel i panel BorderLayout CENTER
        panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
        panel.add(panelCenter, BorderLayout.CENTER);
        
        JPanel flowPanel1 = new JPanel();
        flowPanel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelCenter.add(flowPanel1);
        
        //Panel1 og 2 anvendes til kontaktoplysninger
        //Tilføjer panel1 og panel2 til henholdsvist CENTER og EAST,
        //og giver dem et GridLayout
        panel1 = new JPanel();
        flowPanel1.add(panel1);
        panel1.setLayout(new GridLayout(8,1,10,14));
        
        panel2 = new JPanel();
        flowPanel1.add(panel2);
        panel2.setLayout(new GridLayout(8,1,10,10));
        
        //Hvis vi er ved at ændre i passengers, sæt customer-panel usynligt
        if(changingExistingPassengers) {
        	panelNorth.setVisible(false);
        	panel1.setVisible(false);
            panel2.setVisible(false);
        }
        
        //Indsætter labels i vores JPanel panel1
        labels();
        panel1.add(labelFirstname);
        panel1.add(labelSurname);
        panel1.add(labelEmail);
        panel1.add(labelPhone);
        panel1.add(labelAddress);
        panel1.add(labelCity);
        panel1.add(labelPostal);
        panel1.add(labelCountry);
        
        //Laver input-felterne til panel2
        inputTextFields();
        panel2.add(firstname);
        panel2.add(surname);
        panel2.add(email);
        panel2.add(phoneNumber);
        panel2.add(address);
        panel2.add(city);
        panel2.add(postal);
        panel2.add(country);
        
        JPanel flowPanel2 = new JPanel();
        flowPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelCenter.add(flowPanel2);
        JLabel header = new JLabel("Indtast passageroplysninger her");
        header.setFont(new Font("String", Font.BOLD, 14));

        if(!changingExistingCustomer) {
        	flowPanel2.add(header);
        }
        passengers(antalPassagerer);
    }
    
    //TextFields til kontaktoplysninger
    private void inputTextFields() {
        firstname = new JTextField();
        surname = new JTextField(30);
        email = new JTextField(25);
        phoneNumber = new JTextField(11);
        address = new JTextField(30);
        city = new JTextField (30);
        postal = new JTextField (10);
        country = new JTextField ();
    }
    
    //Labels til kontaktoplysninger
    private void labels() {
        labelFirstname = new JLabel("Fornavn");
        labelSurname = new JLabel("Efternavn");
        labelEmail = new JLabel("E-mail");
        labelPhone = new JLabel("Telefonnr.");
        labelAddress = new JLabel("Adresse");
        labelCity = new JLabel ("By");
        labelPostal = new JLabel ("Postnummer");
        labelCountry = new JLabel ("Land");
        labelBirthday = new JLabel ("Fødselsdag \"dd-mm-åååå\"");
    }
    
    //Laver panels, labels og textFields for passagerer
    private void passengers(int antalPassagerer) {
    	for(int i = 0; i < antalPassagerer; i++) {
    		//Laver passagerer        
            JPanel flowPanel = new JPanel();
            flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelCenter.add(flowPanel);
            JLabel passengerHeader = new JLabel("Passager " + (i+1));
            passengerHeader.setFont(new Font("String", Font.BOLD, 13));
            flowPanel.add(passengerHeader);
            
            JPanel holder = new JPanel();
            holder.setLayout(new FlowLayout(FlowLayout.LEFT));
            panelCenter.add(holder);
            
            JPanel labelPanel = new JPanel();
            labelPanel.setLayout(new GridLayout(3,1,10,15));
    	    labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
    	    holder.add(labelPanel);
    	    
    	    //Indsætter labels i labelPanel
            labels();
            labelPanel.add(labelFirstname);
            labelPanel.add(labelSurname);
            labelPanel.add(labelBirthday);
            
            JPanel textFieldPanel = new JPanel();
            textFieldPanel.setLayout(new GridLayout(3,1,10,10));
    	    textFieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30));
            holder.add(textFieldPanel);
            
            //Indsætter TextFields i textFieldPanel
            JTextField nameField = new JTextField(30);
            JTextField surnameField = new JTextField(30);
            JTextField birthdayField = new JTextField(30);
            textFieldPanel.add(nameField);
            textFieldPanel.add(surnameField);
            textFieldPanel.add(birthdayField);
            
            firstnameList.add(nameField);
            surnameList.add(surnameField);
            birthdayList.add(birthdayField);
            
            if(changingExistingPassengers) {
            	Person p = existingPassengers.get(i);
            	nameField.setText(p.getFirstname());
            	surnameField.setText(p.getSurname());
            	birthdayField.setText(p.getBirthday());
            }
    	}
    }

    //Tilføjer actionListeners til knapper
    private void addActionListeners(){
    	if(!changingExistingCustomer && !changingExistingPassengers) {
    		back.addActionListener(new Listener());
    		next.addActionListener(new Listener());
    		importerKunde.addActionListener(new Listener());
    	} else {
    		confirm.addActionListener(new Listener());
    	}
    }
    
    //Laver kunden
    private void makeCustomer() {
    	firstnameS = firstname.getText();
    	surnameS = surname.getText();
    	emailS = email.getText();
    	phoneS = phoneNumber.getText();
    	addressS = address.getText();
    	cityS = city.getText();
    	postalCodeS = postal.getText();
    	countryS = country.getText();
    	
    	//Tjek, at der er indtastet noget i alle felter, og opret kunden
    	if(firstnameS != "" && surnameS != "" && emailS != "" && phoneS != ""
    			&& addressS != "" && cityS != "" && postalCodeS != "" && countryS != "") {
    		customer = new Customer(firstnameS, surnameS, emailS, phoneS, addressS, cityS, postalCodeS, countryS);
    	} else {
    		JOptionPane.showMessageDialog(frame, "Kan ikke oprette kunde - du mangler at indtaste information!");
    	}
    }
    
    //Laver passagererne
    private void makePeople() {
    	for(int i=0; i<firstnameList.size(); i++) {
    		String firstname = firstnameList.get(i).getText();
    		String surname = surnameList.get(i).getText();
    		String birthday = birthdayList.get(i).getText();
    		
    		
    		if(firstname != "" && surname != "" && birthday != "") {
    			Person p = new Person(firstname, surname, birthday);
        		
        		//hvis vi ændrer i eksisterende passagerer, sæt den 'nyoprettede' passagér til det korrekte id så vi kan gemme i databasen
        		if(changingExistingPassengers) {
        			p.setId(existingPassengers.get(i).getId());
        		}
        		passengers.add(p);
    		} else {
    			JOptionPane.showMessageDialog(frame, "Kan ikke oprette passagér - du mangler at indtaste information!");
    			passengers.clear();
    		}
    		
    	}
    }
    
    //Importér kunde
    private void importCustomer(Customer c) {
    	firstname.setText(c.GetFirstname());
    	surname.setText(c.GetSurname());
    	email.setText(c.GetEmail());
    	phoneNumber.setText(c.GetPhone());
    	address.setText(c.GetAdress());
    	city.setText(c.GetCity());
    	postal.setText(c.GetPostalCode());
    	country.setText(c.GetCountry());
    }
    
    public Kundeoplysninger getThis() {
    	return this;
    }
    
    public void removeMe() {
    	pb.removeMe();
    	frame.dispose();
    }
    
    //Lytter til knapperne
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == back) {
                System.out.println("Going back");
                frame.dispose();
            } else if(event.getSource() == next) {
            	
            	if(importingCustomer) {
            		customer = importedCustomer;
            	} else {
            		makeCustomer();
            	}
            	
            	makePeople();
            	
            	if(turRetur) {
            		Gennemse gennemse = new Gennemse(reservations1, reservations2, passengers, 
            				customer, d1, d2, importingCustomer, getThis());
            	} else {
            		Gennemse gennemse = new Gennemse(reservations1, d1, passengers, customer,
            				importingCustomer, getThis());
            	}
            	
            } else if(event.getSource() == importerKunde) {
            	System.out.println("Hent info fra database om eksisterende kunder");
            	String[] options = {"Telefonnummer","Adresse","Email-adresse" };
            	int chosenOption = JOptionPane.showOptionDialog(frame, "Vælg hvilken " +
            			"information du vil bruge til at importere kunde", "Quite.", 
            			JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "");
            	String option = options[chosenOption];
            	String input = JOptionPane.showInputDialog("Indtast eksisterende kundes "+option);
            	
            	//Laver string om til acceptabelt database input
            	if(option == "Telefonnummer") option = "Customer.phone_number";
            	if(option == "Adresse") option = "Customer.address";
            	if(option == "Email-adresse") option = "Customer.email";
            	
            	Database db;
				try {
					db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
					importedCustomer = db.queryFindCustomer(option, input);
	            	db.close();
	            	importCustomer(importedCustomer);
	            	
	            	firstname.setEditable(false);
	            	surname.setEditable(false);
	            	email.setEditable(false);
	            	phoneNumber.setEditable(false);
	            	address.setEditable(false);
	            	city.setEditable(false);
	            	postal.setEditable(false);
	            	country.setEditable(false);
	            	
	            	importingCustomer = true;
	            	
				} catch (SQLException e) {
					System.out.println("Error when contacting SQL server.");
					e.printStackTrace();
				}
            } else if(event.getSource() == confirm) {
            	
            	if(changingExistingCustomer) {
            		firstnameS = firstname.getText();
                	surnameS = surname.getText();
                	emailS = email.getText();
                	phoneS = phoneNumber.getText();
                	addressS = address.getText();
                	cityS = city.getText();
                	postalCodeS = postal.getText();
                	countryS = country.getText();
                	
                	try {
                		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
                		db.queryUpdateCustomer(c.getId(), firstnameS, surnameS, addressS, 
                					cityS, postalCodeS, countryS, emailS, phoneS);
                		customer = new Customer(firstnameS, surnameS, emailS, phoneS, addressS,
                					cityS, postalCodeS, countryS);
                		g.reload(customer);
                		frame.dispose();
                		
                	} catch (SQLException e) {
                		System.out.println("Error when updating customer.");
                		e.printStackTrace();
                	}
            	} else if(changingExistingPassengers) {
            		makePeople();
            		
            		try {
            			Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
            			for(int i=0; i<passengers.size(); i++) {
            				Person p = passengers.get(i);
            				db.queryUpdatePassenger(p.getId(), p.getFirstname(), p.getSurname(),
            														p.getBirthday());
            			}
            			JOptionPane.showMessageDialog(panel, "Passagerer opdateret");
            			g.dispose();
            			frame.dispose();
            		} catch (SQLException e) {
            			JOptionPane.showMessageDialog
            					(panel, "Fejl i opdatering af passagerer. Er internettet nede?");
            			e.printStackTrace();
            		}
            	}
            }
        }
    }
}
