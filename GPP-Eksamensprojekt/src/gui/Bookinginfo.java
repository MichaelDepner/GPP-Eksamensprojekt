//package gui;
//
//import java.awt.*;
//
//import javax.swing.*;
//
//import java.awt.event.*;
//
//public class Bookinginfo {
//
//	////Info skal vise alle kundeoplysninger og passagerer, pladsreservationer
//	//Skal have en slet og rediger-knap (hvor den låser textFields op) og gem-knap
//	//TextFields, setText til den info, vi henter fra databasen
//	
//	//Skal have lavet et vindue, med kontaktperson øverst, og alle passagerer nederst.
//	//Start med at lave antal personer som int, vi laver den dynamisk senere.
//	
//	//Kontaktperson: Titel, fornavn, efternavn, adresse, e-mail. tlf, fødselsdato.
//	//Øvrige passagerer: Titel, fornavn, efternavn, fødselsdato
//	
//	//fortsæt-knap til betaling
//	
//	//Skal have en importer-kunde-knap (øverst)
//	
//	private JFrame frame;
//    private JTextField firstname, surname, email, phoneNumber, address, birthday, country, postal, city;
//    private JLabel labelFirstname, labelSurname, labelEmail, labelPhone, labelAddress, labelBirthday, labelCountry, labelPostal, labelCity;
//    private JButton back, next, importerKunde, delete;
//    private Container contentPane;
//    private JPanel panel1, panel2, panel3, panel4, panel5, panel6, panelNorth, panelNorth2;
//    private int antalPassagerer;
//
//	
//    public Bookinginfo () {
//        makeFrame();
//        addActionListeners();
//    }
//    
//    private void makeFrame() {
//    	frame.setTitle("Bookinginfo");
//    	
//        frame = new JFrame();
//        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        frame.setSize(700, 460);
//        frame.setResizable(false);
//        
//        contentPane = frame.getContentPane();
//        contentPane.setLayout(new BorderLayout());
//        //contentPane.setBackground(Color.WHITE);
//        //ImageIcon NORTH
//        
//        //Laver et BoxLayout i contentPane NORTH
//        panelNorth = new JPanel();
//        panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
//        contentPane.add(panelNorth, BorderLayout.NORTH);
//        
//        //Laver en JLabel og JBUtton, og indsætter i NORTH
//        JLabel labelUp = new JLabel("Kundens kontaktoplysninger");
//        labelUp.setFont(new Font("String", Font.BOLD, 14));
//        panelNorth.add(labelUp, BorderLayout.NORTH);
//        
//        //Tilføjer panel1 og panel2 til henholdsvist CENTER og EAST,
//        //og giver dem et GridLayout
//        panel1 = new JPanel();
//        contentPane.add(panel1, BorderLayout.CENTER);
//	    panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        panel1.setLayout(new GridLayout(8,1,10,10));
//        
//        panel2 = new JPanel();
//        contentPane.add(panel2, BorderLayout.EAST);
//	    panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        panel2.setLayout(new GridLayout(8,1,10,10));
//        
//        //Indsætter labels i vores JPanel panel1
//        labels();
//        panel1.add(labelFirstname);
//        panel1.add(labelSurname);
//        panel1.add(labelEmail);
//        panel1.add(labelPhone);
//        panel1.add(labelAddress);
//        panel1.add(labelCity);
//        panel1.add(labelPostal);
//        panel1.add(labelCountry);
//        
//        //Laver input-felterne til panel2
//        inputTextFields();
//        panel2.add(firstname);
//        panel2.add(surname);
//        panel2.add(email);
//        panel2.add(phoneNumber);
//        panel2.add(address);
//        panel2.add(city);
//        panel2.add(postal);
//        panel2.add(country);
//        
//        //Indsætter panel3 i SOUTH, og laver et BorderLayout heri
//        panel3 = new JPanel();
//        contentPane.add(panel3, BorderLayout.SOUTH);
//        panel3.setLayout(new BorderLayout());
//        
//        panelNorth2 = new JPanel();
//        panelNorth2.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
//        panel3.add(panelNorth2, BorderLayout.NORTH);
//        
//        //JLabel i panel3 NORTH
//        JLabel labelNorth = new JLabel("Passagerer");
//        labelNorth.setFont(new Font("String", Font.BOLD, 14));
//        panelNorth2.add(labelNorth, BorderLayout.NORTH);
//        
//        //Sætter panel4 til CENTER i panel3, og giver det GridLayout
//        panel4 = new JPanel();
//        panel3.add(panel4, BorderLayout.CENTER);
//        panel4.setLayout(new GridLayout(3,1,10,10));
//	    panel4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        
//        //Sætter panel5 til EAST i panel3, og giver det GridLayout
//        panel5 = new JPanel();
//        panel3.add(panel5, BorderLayout.EAST);
//        panel5.setLayout(new GridLayout(3,1,10,10));
//	    panel5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        
//        //Indsætter labels i panel4
//        labels();
//        panel4.add(labelFirstname);
//        panel4.add(labelSurname);
//        panel4.add(labelBirthday);
//        
//        //Indsætter TextFields i panel5
//        inputTextFields();
//        panel5.add(firstname);
//        panel5.add(surname);
//        panel5.add(birthday);
//        
//        //Sætter panel6 til SOUTH i panel3, og giver det FlowLayout
//        panel6 = new JPanel();
//        panel3.add(panel6, BorderLayout.SOUTH);
//        panel6.setLayout(new FlowLayout());
//        
//        //Opretter knapperne, og lægger dem i panel6
//        back = new JButton("Annuler");
//        next = new JButton("Gem ændringer");
//        delete = new JButton("Slet reservation");
//        panel6.add(delete);
//        panel6.add(next);
//        panel6.add(back);
//        
//        /**
//        //For hvert antal passagerer, laver vi en passager-liste
//        //OBS! Har ikke lavet nyt BorderLayout for hver passager. Skal tage hensyn til panels...
//        for(int i = 0; i < antalPassagerer; i++) {
//            passengers();
//        }*/
//        
//        
//        frame.pack();
//        frame.setVisible(true);
//    }
//    
//    private void inputTextFields() {
//        firstname = new JTextField("Indtast fornavn");
//        surname = new JTextField("Indtast efternavn", 30);
//        email = new JTextField("Indtast e-mail", 25);
//        phoneNumber = new JTextField("Indtast telefonnr.", 11);
//        address = new JTextField("Indtast adresse", 30);
//        city = new JTextField ("Indtast by", 30);
//        postal = new JTextField ("Indtast postnummer", 10);
//        country = new JTextField ("Indtast land");
//        birthday = new JTextField ("Indtast fødselsdag");
//    }
//    
//    private void labels() {
//        labelFirstname = new JLabel("Fornavn");
//        labelSurname = new JLabel("Efternavn");
//        labelEmail = new JLabel("E-mail");
//        labelPhone = new JLabel("Telefonnr.");
//        labelAddress = new JLabel("Adresse");
//        labelCity = new JLabel ("By");
//        labelPostal = new JLabel ("Postnummer");
//        labelCountry = new JLabel ("Land");
//        labelBirthday = new JLabel ("Fødselsdag");
//    }
//    
//    
//    //Tilføjer actionListeners til de to knapper
//    private void addActionListeners(){
//    	back.addActionListener(new Listener());
//    	next.addActionListener(new Listener());
//    	delete.addActionListener(new Listener());
//
//    }
//    
//    private void antalPassagere() {
//    	
//    }
//    
//    //Lytter til knapperne
//    private class Listener implements ActionListener {
//        public void actionPerformed(ActionEvent event){
//            if(event.getSource() == back) {
//                System.out.println("Tilbage til oversigten");
//            } else if(event.getSource() == next) {
//            	System.out.println("Dine ændringer er gemt");
//            } else if (event.getSource() == delete) {
//            	System.out.println("Reservationen er slettet.");
//            }
//        }
//    }
//}