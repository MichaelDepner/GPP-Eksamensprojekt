package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

import logic.Customer;
import logic.Person;
import logic.Plads;
import logic.Reservation;

public class Kundeoplysninger {

	//Skal have lavet et vindue, med kontaktperson øverst, og alle passagerer nederst.
	//Start med at lave antal personer som int, vi laver den dynamisk senere.
	
	//Kontaktperson: Titel, fornavn, efternavn, adresse, e-mail. tlf, fødselsdato.
	//Øvrige passagerer: Titel, fornavn, efternavn, fødselsdato
	
	//fortsæt-knap til betaling
	
	//Skal have en importer-kunde-knap (øverst)
	
	private JFrame frame;
    private JTextField firstname, surname, email, phoneNumber, address, city;
    private JTextField postal, country, birthday;
    private JLabel labelFirstname, labelSurname, labelEmail, labelPhone, labelAddress;
	private JLabel labelCity, labelPostal, labelCountry, labelBirthday;
    private JButton back, next, importerKunde;
    private Container contentPane;
    private JPanel panel1, panel2, panel6, panelNorth;
    private JPanel panel, panelHeader, panelCenter, panelEast;
    private ArrayList<Plads> reservations1, reservations2;
    private ArrayList<Person> passengers = new ArrayList<>();
    private ArrayList<JPanel> passengerPanels = new ArrayList<>();
    private Customer customer;
    private ArrayList<JTextField> firstnameList = new ArrayList<>();
    private ArrayList<JTextField> surnameList = new ArrayList<>();
    private ArrayList<JTextField> birthdayList = new ArrayList<>();
	
    public Kundeoplysninger(ArrayList<Plads> reservations) {
    	this.reservations1 = reservations;
        makeFrame();
        addActionListeners();
    }
    
    public Kundeoplysninger(ArrayList<Plads> reservations1, ArrayList<Plads> reservations2) {
    	this.reservations1 = reservations1;
    	this.reservations2 = reservations2;
    	makeFrame();
    	addActionListeners();
    }
    
    private void makeFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 480);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        //contentPane.setBackground(Color.WHITE);
        
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
        
        
        int counter = 0;
        for(int i=0; i<reservations1.size(); i++) {
        	counter++;
        }
        centerPanel(counter);
        
        /**
         //Tilføjer panel1 og panel2 til henholdsvist CENTER og EAST,
        //og giver dem et GridLayout
        panel1 = new JPanel();
        panel.add(panel1, BorderLayout.CENTER);
	    panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setLayout(new GridLayout(8,1,10,10));
        
        panel2 = new JPanel();
        panel.add(panel2, BorderLayout.EAST);
	    panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panel2.setLayout(new GridLayout(8,1,10,10));
        
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
        
        
        //Herfra og ned skal isoleres i en metode, og kalde en forløkke
        //Indsætter panel3 i SOUTH, og laver et BorderLayout heri
        panel3 = new JPanel();
        panel.add(panel3, BorderLayout.SOUTH);
        panel3.setLayout(new BorderLayout());
        
        panelNorth2 = new JPanel();
        panelNorth2.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        panel3.add(panelNorth2, BorderLayout.NORTH);
        
        //JLabel i panel3 NORTH
        JLabel labelNorth = new JLabel("Indtast oplysninger på passagererne\n");
        labelNorth.setFont(new Font("String", Font.BOLD, 14));
        panelNorth2.add(labelNorth, BorderLayout.NORTH);
        
        //Sætter panel4 til CENTER i panel3, og giver det GridLayout
        panel4 = new JPanel();
        panel3.add(panel4, BorderLayout.CENTER);
        panel4.setLayout(new GridLayout(3,1,10,10));
	    panel4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Sætter panel5 til EAST i panel3, og giver det GridLayout
        panel5 = new JPanel();
        panel3.add(panel5, BorderLayout.EAST);
        panel5.setLayout(new GridLayout(3,1,10,10));
	    panel5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        
        //Indsætter labels i panel4
        labels();
        panel4.add(labelFirstname);
        panel4.add(labelSurname);
        panel4.add(labelBirthday);
        
        //Indsætter TextFields i panel5
        inputTextFields();
        panel5.add(firstname);
        panel5.add(surname);
        panel5.add(birthday);
         */
        
        
        //Sætter panel6 til SOUTH i panel3, og giver det FlowLayout
        panel6 = new JPanel();
        panel.add(panel6, BorderLayout.SOUTH);
        panel6.setLayout(new FlowLayout());
        
        //Opretter knapperne, og lægger dem i panel6
        back = new JButton("Tilbage");
        next = new JButton("Næste");
        panel6.add(back);
        panel6.add(next);
        
        
        frame.setVisible(true);
    }
    
    private void centerPanel(int antalPassagerer) {
    	//Laver et panel i panel BorderLayout CENTER og EAST
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
	    //panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setLayout(new GridLayout(8,1,10,14));
        
        panel2 = new JPanel();
        flowPanel1.add(panel2);
	    //panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        panel2.setLayout(new GridLayout(8,1,10,10));
        
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
        flowPanel2.add(header);
        
        
        
        passengers(antalPassagerer);
        
        
        
    }
    
    private void inputTextFields() {
        firstname = new JTextField("Indtast fornavn");
        surname = new JTextField("Indtast efternavn", 30);
        email = new JTextField("Indtast e-mail", 25);
        phoneNumber = new JTextField("Indtast telefonnr.", 11);
        address = new JTextField("Indtast adresse", 30);
        city = new JTextField ("Indtast by", 30);
        postal = new JTextField ("Indtast postnummer", 10);
        country = new JTextField ("Indtast land");
        birthday = new JTextField ("Indtast fødselsdag");
    }
    
    private void labels() {
        labelFirstname = new JLabel("Fornavn");
        labelSurname = new JLabel("Efternavn");
        labelEmail = new JLabel("E-mail");
        labelPhone = new JLabel("Telefonnr.");
        labelAddress = new JLabel("Adresse");
        labelCity = new JLabel ("By");
        labelPostal = new JLabel ("Postnummer");
        labelCountry = new JLabel ("Land");
        labelBirthday = new JLabel ("Fødselsdag");
    }
    
    private void passengers(int antalPassagerer) {
    	for(int i = 1; i < antalPassagerer+1; i++) {
    		//Laver passagerer        
            JPanel flowPanel = new JPanel();
            flowPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelCenter.add(flowPanel);
            JLabel passengerHeader = new JLabel("Passager " + i);
            passengerHeader.setFont(new Font("String", Font.BOLD, 13));
            flowPanel.add(passengerHeader);
            
            JPanel holder = new JPanel();
            holder.setLayout(new FlowLayout());
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
            inputTextFields();
            textFieldPanel.add(firstname);
            textFieldPanel.add(surname);
            textFieldPanel.add(birthday);
            
            System.out.println("OPRETTER");;
            
            firstnameList.add(firstname);
            surnameList.add(surname);
            birthdayList.add(birthday);
    	}
    }
    
    //Tilføjer actionListeners til de to knapper
    private void addActionListeners(){
    	back.addActionListener(new Listener());
    	next.addActionListener(new Listener());
    	importerKunde.addActionListener(new Listener());
    }
    
    private void makeCustomer() {
    	String firstnameS = firstname.getText();
    	String surnameS = surname.getText();
    	String emailS = email.getText();
    	String phoneS = phoneNumber.getText();
    	String addressS = address.getText();
    	String cityS = city.getText();
    	String postalCodeS = postal.getText();
    	String countryS = country.getText();
    	
    	customer = new Customer(firstnameS, surnameS, emailS, phoneS, addressS, cityS, postalCodeS, countryS);
    }
    
    private void makePeople() {
    	for(int i=0; i<firstnameList.size(); i++) {
    		String firstname = firstnameList.get(i).getText();
    		String surname = surnameList.get(i).getText();
    		String birthday = birthdayList.get(i).getText();
    		
    		passengers.add(new Person(firstname, surname, birthday));
    	}
    }
    
    //Lytter til knapperne
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == back) {
                System.out.println("Going back");
            } else if(event.getSource() == next) {
            	
            	makeCustomer();
            	makePeople();
            	
            	System.out.println("Going to the next step");
            	
            	
            } else if(event.getSource() == importerKunde) {
            	System.out.println("Hent info fra database om eksisterende kunder");
            }
        }
    }
}
