package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Bookinginfo {
		//Info skal vise alle kundeoplysninger og passagerer, pladsreservationer
		//Skal have en slet og rediger-knap (hvor den låser textFields op) og gem-knap
		//TextFields, setText til den info, vi henter fra databasen


		private JFrame frame;
	    private JTextField firstname, surname, email, phoneNumber, address;
	    private JLabel labelFirstname, labelSurname, labelEmail, labelPhone, labelAddress, labelBirthday, labelPassFirstname, labelPassSurname, labelPassBirthday;
	    private JButton back, next, find;
	    private Container contentPane;
	    private JPanel panel0, panel1, panel2, panel3, panel4, panel5, panel6, panelhead2, panelPas, panelPas1, panelinputPass, panelPashead;
	    private int antalPassagerer;
		private Component birthday;
		private JButton delete;
		
	    public Bookinginfo() {
	        makeFrame();
	        addActionListeners();
	    }
	    
	    private void makeFrame() {
	        frame = new JFrame();
	        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        frame.setSize(750, 760);
	        frame.setResizable(false);
	        
	        contentPane = frame.getContentPane();
	        contentPane.setLayout(new BorderLayout());
	        //contentPane.setBackground(Color.WHITE);
	        //ImageIcon NORTH
	        
	        //Laver en JLabel, og indsætter i NORTH
	        JLabel labelUp = new JLabel("Kundeoplysninger");
	        contentPane.add(labelUp, BorderLayout.NORTH);
	        
	        //Tilføjer panel1 og panel2 til henholdsvist CENTER og EAST,
	        //og giver dem et GridLayout
	        panel1 = new JPanel();
	        contentPane.add(panel1, BorderLayout.CENTER);
	        panel1.setLayout(new GridLayout(5,1));
	        
	        panel2 = new JPanel();
	        contentPane.add(panel2, BorderLayout.EAST);
	        panel2.setLayout(new GridLayout(5,1));
	        
	        //Indsætter labels i vores JPanel panel1
	        labels();
	        panel1.add(labelFirstname);
	        panel1.add(labelSurname);
	        panel1.add(labelEmail);
	        panel1.add(labelPhone);
	        panel1.add(labelAddress);
	        
	        //Laver input-felterne til panel2
	        inputTextFields();
	        panel2.add(firstname);
	        panel2.add(surname);
	        panel2.add(email);
	        panel2.add(phoneNumber);
	        panel2.add(address);
	        
	        //Indsætter panel3 i SOUTH, og laver et BorderLayout heri
	        panel3 = new JPanel();
	        contentPane.add(panel3, BorderLayout.SOUTH);
	        panel3.setLayout(new BorderLayout());
	        
	        //Sætter panelhead2 til NORTH i panel3 og giver det GridLayout
	        panelhead2 = new JPanel();
	        panel3.add(panelhead2, BorderLayout.NORTH);
	        panelhead2.setLayout(new GridLayout(3,1));
	        
	        //JLabel i panel3 NORTH
	        JLabel labelempty = new JLabel ("  ");
	        JLabel labelNorth = new JLabel("Passagerer");
	        JLabel labelunder = new JLabel("Hovedansvarlig (Passager 1)");
	        panelhead2.add(labelempty);
	        panelhead2.add(labelNorth);
	        panelhead2.add(labelunder);
	        
	        //Sætter panel4 til CENTER i panel3, og giver det GridLayout
	        panel4 = new JPanel();
	        panel3.add(panel4, BorderLayout.CENTER);
	        panel4.setLayout(new GridLayout(6,1));
	        
	        //Sætter panel5 til EAST i panel3, og giver det GridLayout
	        panel5 = new JPanel();
	        panel3.add(panel5, BorderLayout.EAST);
	        panel5.setLayout(new GridLayout(6,1));
	        
	        //Indsætter labels i panel4
	        labels();
	        panel4.add(labelFirstname);
	        panel4.add(labelSurname);
	        panel4.add(labelBirthday);
	        panel4.add(labelEmail);
	        panel4.add(labelPhone);
	        panel4.add(labelAddress);
	        
	        //Indsætter TextFields i panel5
	        inputTextFields();
	        panel5.add(firstname);
	        panel5.add(surname);
	        panel5.add(birthday);
	        panel5.add(email);
	        panel5.add(phoneNumber);
	        panel5.add(address);
	        
	        //Indsætter flere passagerer
	        panelPas = new JPanel();
	        panel3.add(panelPas,BorderLayout.SOUTH);
	        panelPas.setLayout(new BorderLayout());
	        
	        //Laver panel til overskriften for passager 2
	        panelPashead = new JPanel();
	        panelPas.add(panelPashead, BorderLayout.NORTH);
	        panelPashead.setLayout(new GridLayout(2,1));
	               
	             
	        // Indsætter yderligere passagerer
	        JLabel veryempty = new JLabel ("  ");
	        JLabel pass1 = new JLabel ("Passager 2");
	        panelPashead.add(veryempty);
	        panelPashead.add(pass1);
	        
	        panelPas1 = new JPanel();
	        panelPas.add(panelPas1, BorderLayout.CENTER);
	        panelPas1.setLayout(new GridLayout(3,1));
	        
	        //Labels til passagerernes info
	        labels();
	        panelPas1.add(labelFirstname);
	        panelPas1.add(labelSurname);
	        panelPas1.add(labelBirthday);
	        
	        //Nyt panel med TextInput
	        panelinputPass = new JPanel();
	        panelPas.add(panelinputPass, BorderLayout.EAST);
	        panelinputPass.setLayout(new GridLayout(3,1));
	        
	        
	        //Textinput til passagerer
	        inputTextFields();
	        panelinputPass.add(firstname);
	        panelinputPass.add(surname);
	        panelinputPass.add(birthday);
	        
	        
	        //Sætter panel6 til SOUTH i panel3, og giver det FlowLayout
	        panel6 = new JPanel();
	        panelPas.add(panel6, BorderLayout.SOUTH);
	        panel6.setLayout(new FlowLayout());
	        
	        //Opretter knapperne, og lægger dem i panel6
	        back = new JButton("Tilbage til oversigten");
	        next = new JButton("Gem");
	        delete = new JButton ("Slet reservation");
	        panel6.add(back);
	        panel6.add(next);
	        panel6.add(delete);
	        
	        /**
	        //For hvert antal passagerer, laver vi en passager-liste
	        //OBS! Har ikke lavet nyt BorderLayout for hver passager. Skal tage hensyn til panels...
	        for(int i = 0; i < antalPassagerer; i++) {
	            passengers();
	        }*/
	        
	        
	        frame.pack();
	        frame.setVisible(true);
	    }
	    
	    private void inputTextFields() {
	        firstname = new JTextField("Fornavn");
	        surname = new JTextField("Efternavn", 20);
	        birthday = new JTextField("Fødselsdag");
	        email = new JTextField("E-mail", 25);
	        phoneNumber = new JTextField("Telefonnr.", 11);
	        address = new JTextField("Adresse", 30);
	    }
	    
	    private void labels() {
	        labelFirstname = new JLabel("Fornavn");
	        labelSurname = new JLabel("Efternavn");
	        labelEmail = new JLabel("E-mail");
	        labelPhone = new JLabel("Telefonnr.");
	        labelAddress = new JLabel("Adresse");
	        labelBirthday = new JLabel("Fødselsdag");
	    }
	    

	    //Tilføjer actionListeners til de to knapper
	    private void addActionListeners(){
	    	back.addActionListener(new Listener());
	    	next.addActionListener(new Listener());
	    	delete.addActionListener(new Listener());
	    }
	    
	    //Lytter til knapperne
	    private class Listener implements ActionListener {
	        public void actionPerformed(ActionEvent event){
	            if(event.getSource() == back) {
	                System.out.println("Tilbage til bookingoversigten");
	            } else if(event.getSource() == next) {
	            	System.out.println("Ændringerne er gemt");
	            }
	            else if(event.getSource() == delete) {
	            	System.out.println("Reservationen er slettet");
	            }
	        }
	    }
	}
	

