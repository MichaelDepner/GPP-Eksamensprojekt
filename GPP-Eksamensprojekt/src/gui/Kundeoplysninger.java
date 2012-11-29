package gui;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class Kundeoplysninger {

	//Skal have lavet et vindue, med kontaktperson �verst, og alle passagerer nederst.
	//Start med at lave antal personer som int, vi laver den dynamisk senere.
	
	//Kontaktperson: Titel, fornavn, efternavn, adresse, e-mail. tlf, f�dselsdato.
	//�vrige passagerer: Titel, fornavn, efternavn, f�dselsdato
	
	//forts�t-knap til betaling
	
	private JFrame frame;
    private JTextField firstname, surname, email, phoneNumber, address;
    private JLabel labelFirstname, labelSurname, labelEmail, labelPhone, labelAddress;
    private JButton back, next;
    private Container contentPane;
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
    private int antalPassagerer;
	
    public Kundeoplysninger() {
        makeFrame();
        addActionListeners();
    }
    
    private void makeFrame() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(640, 460);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        //contentPane.setBackground(Color.WHITE);
        //ImageIcon NORTH
        
        //Laver en JLabel, og inds�tter i NORTH
        JLabel labelUp = new JLabel("Indtast dine kontaktoplysninger her");
        contentPane.add(labelUp, BorderLayout.NORTH);
        
        //Tilf�jer panel1 og panel2 til henholdsvist CENTER og EAST,
        //og giver dem et GridLayout
        panel1 = new JPanel();
        contentPane.add(panel1, BorderLayout.CENTER);
        panel1.setLayout(new GridLayout(5,1));
        
        panel2 = new JPanel();
        contentPane.add(panel2, BorderLayout.EAST);
        panel2.setLayout(new GridLayout(5,1));
        
        //Inds�tter labels i vores JPanel panel1
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
        
        //Inds�tter panel3 i SOUTH, og laver et BorderLayout heri
        panel3 = new JPanel();
        contentPane.add(panel3, BorderLayout.SOUTH);
        panel3.setLayout(new BorderLayout());
        
        //JLabel i panel3 NORTH
        JLabel labelNorth = new JLabel("Indtast personoplysninger her");
        panel3.add(labelNorth, BorderLayout.NORTH);
        
        //S�tter panel4 til CENTER i panel3, og giver det GridLayout
        panel4 = new JPanel();
        panel3.add(panel4, BorderLayout.CENTER);
        panel4.setLayout(new GridLayout(5,1));
        
        //S�tter panel5 til EAST i panel3, og giver det GridLayout
        panel5 = new JPanel();
        panel3.add(panel5, BorderLayout.EAST);
        panel5.setLayout(new GridLayout(5,1));
        
        //Inds�tter labels i panel4
        labels();
        panel4.add(labelFirstname);
        panel4.add(labelSurname);
        panel4.add(labelEmail);
        panel4.add(labelPhone);
        panel4.add(labelAddress);
        
        //Inds�tter TextFields i panel5
        inputTextFields();
        panel5.add(firstname);
        panel5.add(surname);
        panel5.add(email);
        panel5.add(phoneNumber);
        panel5.add(address);
        
        //S�tter panel6 til SOUTH i panel3, og giver det FlowLayout
        panel6 = new JPanel();
        panel3.add(panel6, BorderLayout.SOUTH);
        panel6.setLayout(new FlowLayout());
        
        //Opretter knapperne, og l�gger dem i panel6
        back = new JButton("Tilbage");
        next = new JButton("N�ste");
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
    
    //Tilf�jer actionListeners til de to knapper
    private void addActionListeners(){
    	back.addActionListener(new Listener());
    	next.addActionListener(new Listener());
    }
    
    //Lytter til knapperne
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == back) {
                System.out.println("Going back");
            } else if(event.getSource() == next) {
            	System.out.println("Going to the next step");
            }
        }
    }
}