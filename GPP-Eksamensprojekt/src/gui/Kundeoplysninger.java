package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Kundeoplysninger {

	//Skal have lavet et vindue, med kontaktperson øverst, og alle passagerer nederst.
	//Start med at lave antal personer som int, vi laver den dynamisk senere.
	
	//Kontaktperson: Titel, fornavn, efternavn, adresse, e-mail. tlf, fødselsdato.
	//Øvrige passagerer: Titel, fornavn, efternavn, fødselsdato
	
	//fortsæt-knap til betaling
	
	//Skal have en importer-kunde-knap (øverst)
	
	private JFrame frame;
    private JTextField firstname, surname, email, phoneNumber, address;
    private JLabel labelFirstname, labelSurname, labelEmail, labelPhone, labelAddress;
    private JButton back, next, importerKunde;
    private Container contentPane;
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6, panelNorth, panelNorth2;
    private int antalPassagerer;
	
    public Kundeoplysninger() {
        makeFrame();
        addActionListeners();
    }
    
    private void makeFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(700, 460);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        //contentPane.setBackground(Color.WHITE);
        //ImageIcon NORTH
        
        //Laver et BoxLayout i contentPane NORTH
        panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        contentPane.add(panelNorth, BorderLayout.NORTH);
        
        //Laver en JLabel og JBUtton, og indsætter i NORTH
        JLabel labelUp = new JLabel("Indtast dine kontaktoplysninger her");
        labelUp.setFont(new Font("String", Font.BOLD, 14));
        importerKunde = new JButton("Importer kunde");
        panelNorth.add(labelUp, BorderLayout.NORTH);
        panelNorth.add(importerKunde, BorderLayout.NORTH);
        
        //Tilføjer panel1 og panel2 til henholdsvist CENTER og EAST,
        //og giver dem et GridLayout
        panel1 = new JPanel();
        contentPane.add(panel1, BorderLayout.CENTER);
	    panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel1.setLayout(new GridLayout(5,1,10,10));
        
        panel2 = new JPanel();
        contentPane.add(panel2, BorderLayout.EAST);
	    panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel2.setLayout(new GridLayout(5,1,10,10));
        
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
        
        panelNorth2 = new JPanel();
        panelNorth2.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        panel3.add(panelNorth2, BorderLayout.NORTH);
        
        //JLabel i panel3 NORTH
        JLabel labelNorth = new JLabel("Indtast personoplysninger her");
        labelNorth.setFont(new Font("String", Font.BOLD, 14));
        panelNorth2.add(labelNorth, BorderLayout.NORTH);
        
        //Sætter panel4 til CENTER i panel3, og giver det GridLayout
        panel4 = new JPanel();
        panel3.add(panel4, BorderLayout.CENTER);
        panel4.setLayout(new GridLayout(5,1,10,10));
	    panel4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Sætter panel5 til EAST i panel3, og giver det GridLayout
        panel5 = new JPanel();
        panel3.add(panel5, BorderLayout.EAST);
        panel5.setLayout(new GridLayout(5,1,10,10));
	    panel5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //Indsætter labels i panel4
        labels();
        panel4.add(labelFirstname);
        panel4.add(labelSurname);
        panel4.add(labelEmail);
        panel4.add(labelPhone);
        panel4.add(labelAddress);
        
        //Indsætter TextFields i panel5
        inputTextFields();
        panel5.add(firstname);
        panel5.add(surname);
        panel5.add(email);
        panel5.add(phoneNumber);
        panel5.add(address);
        
        //Sætter panel6 til SOUTH i panel3, og giver det FlowLayout
        panel6 = new JPanel();
        panel3.add(panel6, BorderLayout.SOUTH);
        panel6.setLayout(new FlowLayout());
        
        //Opretter knapperne, og lægger dem i panel6
        back = new JButton("Tilbage");
        next = new JButton("Næste");
        panel6.add(back);
        panel6.add(next);
        
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
        firstname = new JTextField("Indtast fornavn");
        surname = new JTextField("Indtast efternavn", 20);
        email = new JTextField("Indtast e-mail", 25);
        phoneNumber = new JTextField("Indtast telefonnr.", 11);
        address = new JTextField("Indtast adresse", 30);
    }
    
    private void labels() {
        labelFirstname = new JLabel("Fornavn");
        labelSurname = new JLabel("Efternavn");
        labelEmail = new JLabel("E-mail");
        labelPhone = new JLabel("Telefonnr.");
        labelAddress = new JLabel("Adresse");
    }
    
    //Tilføjer actionListeners til de to knapper
    private void addActionListeners(){
    	back.addActionListener(new Listener());
    	next.addActionListener(new Listener());
    	importerKunde.addActionListener(new Listener());
    }
    
    //Lytter til knapperne
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == back) {
                System.out.println("Going back");
            } else if(event.getSource() == next) {
            	System.out.println("Going to the next step");
            } else if(event.getSource() == importerKunde) {
            	System.out.println("Hent info fra database om eksisterende kunder");
            }
        }
    }
}