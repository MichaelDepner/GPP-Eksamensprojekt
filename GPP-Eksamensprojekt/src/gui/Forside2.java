package gui;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Forside2  extends JFrame implements ActionListener{
	
	private JTabbedPane searchPane;
	private JPanel panel, panelRejser, panelNorth, radioPanel, tilFraPanel, panelSouth;
	private JPanel panelBooking, panelCenter, panelLeft, panelRight, tilPanel, fraPanel;
	private JLabel logoLabel, fraLabel, tilLabel;
	private JLabel udrejse, hjemrejse;
	private JTextField bookingText;
	private JRadioButton enkelt, turRetur;
	private JXDatePicker udrejseDate, hjemrejseDate;
	private JButton searchButton, searchBooking;
	private JComboBox searchList,searchList1,searchList2;
	private boolean turReturBool = false;
	
	public Forside2(){
		setTitle("Forside");
		//setBackground(Color.WHITE);
			
		//Opretter et BorderLayout i contentPane
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);
		
		//Indlæs swan.jpg, gemmer det i en JLabel, og lægger den øverst i vinduet
		//Billedet skal gøres mindre!
		ImageIcon imageLogo = new ImageIcon(getClass().getResource("png/swan5.jpg"));
		logoLabel = new JLabel(imageLogo);
		panel.add(logoLabel, BorderLayout.NORTH);
		
		//Opretter tabbedPane og tilhørende panels
		searchPane = new JTabbedPane();
		panelRejser = new JPanel();
		panelBooking = new JPanel();
		
		//Tilføjer faner med tilhørende paneler
		searchPane.addTab("Bestil rejse", panelRejser);
		searchPane.addTab("Søg bookinger", panelBooking);
				
		//Tilføjer fanevinduerne til panel CENTER
		panel.add(searchPane, BorderLayout.CENTER );
		
		//laver hele fanevinduet til at søge rejser
		searchRejser();
		addHjemrejse();
		searchBookinger();
		
		panel.setPreferredSize(new Dimension(700, 550));
		//setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
	}
	
	private void addHjemrejse() {
		panelRight = new JPanel();
	    panelCenter.add(panelRight);
	    
	    //indhold til panelRight
	    hjemrejse = new JLabel("Hjemrejse");
	    panelRight.add(hjemrejse);
	    
	    //tilføjer datepicker
	    hjemrejseDate = new JXDatePicker();
	    panelRight.add(hjemrejseDate);

	    panelRight.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    
	    pack();
	    panelRight.setVisible(false);
	}
	
	private void searchRejser() {
		
		////////////////////////////////////////////////////////////
    	JDialog dialog = new JDialog();
    	dialog.setVisible(true);
    	JOptionPane.showInputDialog("Daaamn");
    	String[] options = {"Option1","Option2","Option3" };
    	JOptionPane.showOptionDialog(dialog, "choose!", "Quite.", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, "Option 1");
    	////////////////////////////////////////////////////
		
		//Laver et BorderLayout inde i fane-vinduet rejser
		panelRejser.setLayout(new BorderLayout(10,10));
		
		//Laver et panel med at GridLayout, der sættes ind i panel NORTH
		panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(2,1,10,10));
		panelRejser.add(panelNorth, BorderLayout.NORTH);
		
		//Laver et panel, der sættes ind i panelNorth
		radioPanel = new JPanel();
		radioPanel.setLayout(new FlowLayout());
		radioPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panelNorth.add(radioPanel);
		
		//Laver radio buttons til radioPanel
		enkelt = new JRadioButton("Enkelt");
		radioPanel.add(enkelt);
		turRetur = new JRadioButton("Tur/Retur");
		enkelt.setSelected(true);
		radioPanel.add(turRetur);
		
		//Grupperer radio buttons
	    ButtonGroup group = new ButtonGroup();
	    group.add(enkelt);
	    group.add(turRetur);
	    
	  	//Sætter listeners på radio buttons
	    enkelt.addActionListener(this);
	    turRetur.addActionListener(this); 
	    
	    //Skal tilføje actions til knapperne
	    
	    //Tilføjer tilFraPanel til panelNorth, og giver GridLayout
	    tilFraPanel = new JPanel();
	    tilFraPanel.setLayout(new GridLayout(2, 1, 10, 10));
	    tilFraPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    panelNorth.add(tilFraPanel);
	    
	    //opretter to flowLayouts i tilFraPanel
	    fraPanel = new JPanel();
	    fraPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    tilFraPanel.add(fraPanel);
	    
	    tilPanel = new JPanel();
	    tilPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    tilFraPanel.add(tilPanel);
	    
	    //Opretter indhold til labelPanel
	    fraLabel = new JLabel("Fra");
	    fraPanel.add(fraLabel);
	    tilLabel = new JLabel("Til");
	    tilPanel.add(tilLabel);
	    
	    //Combo box til panelNorth
  		String[] searchAfgange = {"København", "Billund", "Rønne", 
  					"Timbuktu", "Athen", "London", "Paris", "Tokyo", "Atlanta"};
  		searchList1 = new JComboBox(searchAfgange);
  		searchList2 = new JComboBox(searchAfgange);
  		fraPanel.add(searchList1);
  		tilPanel.add(searchList2);
  		searchList1.setSelectedIndex(8);
  		searchList2.setSelectedIndex(8);
  		searchList1.addActionListener(this);
  		searchList2.addActionListener(this);
	    
	    //Laver et i panelRejser CENTER
	    panelCenter = new JPanel();
	    panelCenter.setLayout(new GridLayout(1,2,10,10));
	    panelRejser.add(panelCenter, BorderLayout.CENTER);
	    
	    //Laver og tilføjer panels til panelCenter
	    panelLeft = new JPanel();
	    panelCenter.add(panelLeft);
	    panelLeft.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    //panelLeft.setSize(new Dimension(200, 100));
	    //panelLeft.setSize(200, 100);
	    
	    //Laver indhold til panelLeft
	    udrejse = new JLabel("Udrejse");
	    panelLeft.add(udrejse);
	    
	    //Tilføjer  kalender
	    udrejseDate = new JXDatePicker();
	    panelLeft.add(udrejseDate);
	    
	    //Laver et panel, der sættes ind i panel SOUTH
  		panelSouth = new JPanel();
  		panelRejser.add(panelSouth, BorderLayout.SOUTH);
  		
  		//Tilføjer "Søg"-knap til SOUTH
  		searchButton = new JButton("Søg");
  		panelSouth.add(searchButton);
  		//ActionListener
	    searchButton.addActionListener(this);
	}
	
	private void searchBookinger() {
		//Laver et BorderLayout inde i fane-vinduet bookinger
		panelBooking.setLayout(new BorderLayout(10,10));
		
		//Panel north
		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBooking.add(panelNorth, BorderLayout.NORTH);
		
		//Label til panelNorth
		JLabel labelNorth = new JLabel("Søg på ");
		panelNorth.add(labelNorth);
        labelNorth.setFont(new Font("String", Font.BOLD, 14));
		
		//Combo box til panelNorth
		String[] searchStrings = {"Bookingnr.", "Adresse", "E-mail", "Tlf.nr."};
		searchList = new JComboBox(searchStrings);
		panelNorth.add(searchList);
		searchList.setSelectedIndex(3);
        searchList.setFont(new Font("String", Font.BOLD, 13));
		searchList.addActionListener(this);
		
		//Panel center
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		panelBooking.add(panelCenter, BorderLayout.CENTER);
		
		//Label til panelCenter
		JLabel labelCenter = new JLabel("Indtast søgekriterie: ");
		panelCenter.add(labelCenter);
        labelCenter.setFont(new Font("String", Font.BOLD, 14));
        
        bookingText = new JTextField(20);
        panelCenter.add(bookingText);
        bookingText.addActionListener(this);
		
		//Panel til søg-knap
		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		panelBooking.add(panelSouth, BorderLayout.SOUTH);
		
		searchBooking = new JButton("Søg booking");
		panelSouth.add(searchBooking);
		searchBooking.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source.equals(searchButton)) {
			if(turReturBool) {
				try {
					Afgangsliste afgange = new Afgangsliste(
							udrejseDate.getDate(),hjemrejseDate.getDate(), 
							(String)searchList1.getSelectedItem(), 
							(String)searchList2.getSelectedItem());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("SQL fejl");
					e.printStackTrace();
				}
			} else if (!turReturBool) {
				try {
					Afgangsliste afgange = new Afgangsliste(udrejseDate.getDate(), (String)searchList1.getSelectedItem(),
							(String)searchList2.getSelectedItem());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("SQL fejl");
					e.printStackTrace();
				}
			}
		}
		else if(source == turRetur)
		{
			panelRight.setVisible(true);
			turReturBool = true;
		}
	    else if(source == enkelt) {
	    	panelRight.setVisible(false);
	    	turReturBool = false;
	    }
	    else if(source == searchBooking) {
	    	System.out.println("Søger bookinger");
	    	String searchingFor = searchList.getSelectedItem().toString();
	    	String arg = bookingText.getText();
	    	System.out.println("Searching for: "+searchingFor+", arg: "+arg);
	    	
	    	//'oversætter' til database-relevant søgning:
	    	if(searchingFor == "Adresse") searchingFor = "Customer.address";
	    	if(searchingFor == "E-mail") searchingFor = "Customer.email";
	    	if(searchingFor == "Tlf.nr.") searchingFor = "Customer.phone_number";
	    	
	    	try {
				Bookingliste bl = new Bookingliste(searchingFor, arg);
			} catch (SQLException e) {
				System.out.println("SQL problemer ved dannelse af bookingliste!");
				e.printStackTrace();
			}
	    	
	    	
	    }
	    else if(source == bookingText) {
	    	
	    }
	    else if(source == searchList) {
	    	//if == bookingnr.
	    	//if == adresse
	    	//if == e-mail
	    	//if == tlf.nr.
	    	
	    }
	}
}
