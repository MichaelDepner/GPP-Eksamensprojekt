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
	private JPanel panelLeft1, panelLeft2, panelRight1, panelRight2;
	private JLabel logoLabel, fraLabel, tilLabel;
	private JLabel udrejse, hjemrejse, udrejse2, hjemrejse2;
	private JTextField bookingText;
	private JRadioButton enkelt, turRetur;
	private JCheckBox periode;
	private JXDatePicker udrejseDate, hjemrejseDate, udrejseDate2, hjemrejseDate2;
	private JButton searchButton, searchBooking;
	private JComboBox searchList,searchList1,searchList2;
	private boolean turReturBool = false;
	private boolean periodBoolean = false;
	
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
		
		panel.setPreferredSize(new Dimension(700, 570));
		//setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
	}
	
	private void addHjemrejse() {
		panelRight = new JPanel();
	    panelCenter.add(panelRight);
	    
	    panelRight1 = new JPanel();
	    panelRight1.setLayout(new FlowLayout());
	    panelRight.add(panelRight1);
	    
	    panelRight2 = new JPanel();
	    panelRight2.setLayout(new FlowLayout());
	    panelRight.add(panelRight2);
	    
	    //indhold til panelRight
	    hjemrejse = new JLabel("Hjemrejse");
	    panelRight1.add(hjemrejse);
	    
	    //tilføjer datepicker
	    hjemrejseDate = new JXDatePicker();
	    panelRight1.add(hjemrejseDate);
	    
	    //Tilføjer label til næste datePicker
	    hjemrejse2 = new JLabel("Hjemrejse til  ");
	    panelRight2.add(hjemrejse2);
	    hjemrejse2.setVisible(false);
	    
	    //tilføjer næste datepicker
	    hjemrejseDate2 = new JXDatePicker();
	    hjemrejseDate2.setVisible(false);
	    panelRight2.add(hjemrejseDate2);
	    

	    panelRight.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    
	    pack();
	    panelRight.setVisible(false);
	}
	
	private void searchRejser() {
		
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
		
		//laver box
		periode = new JCheckBox("Søg i en periode");
		radioPanel.add(periode);
		
		//Grupperer radio buttons
	    ButtonGroup group = new ButtonGroup();
	    group.add(enkelt);
	    group.add(turRetur);
	    
	  	//Sætter listeners på knapper
	    enkelt.addActionListener(this);
	    turRetur.addActionListener(this);
	    periode.addActionListener(this);
	    
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
  					"Timbuktu", "Athen", "London", "Paris"};
  		searchList1 = new JComboBox(searchAfgange);
  		searchList2 = new JComboBox(searchAfgange);
  		fraPanel.add(searchList1);
  		tilPanel.add(searchList2);
  		searchList1.setSelectedIndex(6);
  		searchList2.setSelectedIndex(6);
  		searchList1.addActionListener(this);
  		searchList2.addActionListener(this);
	    
	    //Laver et i panelRejser CENTER
	    panelCenter = new JPanel();
	    panelCenter.setLayout(new GridLayout(1,2,10,10));
	    panelRejser.add(panelCenter, BorderLayout.CENTER);
	    
	    //Laver og tilføjer panels til panelCenter
	    panelLeft = new JPanel();
	    panelCenter.add(panelLeft);
	    //panelLeft.setLayout(new GridLayout(2,1));
	    panelLeft.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    //panelLeft.setSize(new Dimension(200, 100));
	    //panelLeft.setSize(200, 100);
	    
	    panelLeft1 = new JPanel();
	    panelLeft1.setLayout(new FlowLayout());
	    panelLeft.add(panelLeft1);
	    
	    panelLeft2 = new JPanel();
	    panelLeft2.setLayout(new FlowLayout());
	    panelLeft.add(panelLeft2);
	    
	    //Laver indhold til panelLeft
	    udrejse = new JLabel("Udrejse");
	    panelLeft1.add(udrejse);
	    
	    //Tilføjer  kalender
	    udrejseDate = new JXDatePicker();
	    panelLeft1.add(udrejseDate);
	    
	    //Tilføjer label til anden DatePicker
	    udrejse2 = new JLabel("Udrejse til  ");
	    panelLeft2.add(udrejse2);
	    udrejse2.setVisible(false);
	    
	    //DatePicker2
	    udrejseDate2 = new JXDatePicker();
	    udrejseDate2.setVisible(false);
	    panelLeft2.add(udrejseDate2);
	    
	    
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
		String[] searchStrings = {"Adresse", "E-mail", "Tlf.nr."};
		searchList = new JComboBox(searchStrings);
		panelNorth.add(searchList);
		searchList.setSelectedIndex(2);
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
			if(searchList1.getSelectedItem() == searchList2.getSelectedItem()) {
				JOptionPane.showMessageDialog(this, "Du kan ikke vælge samme lufthavn i afgang og ankomst!");
			} else {
				if(turReturBool) {

					try {
						if(udrejseDate.getDate() == null || hjemrejseDate.getDate() == null) {
							JOptionPane.showMessageDialog(this, "Dato ikke valgt ordentligt.");
						} else {
							//hvis vi har valgt at søge på en periode, lav afgangsliste i periode. Ellers, lav søgning på den enkelte dato
							if(periodBoolean) {
								if(udrejseDate2.getDate() == null || hjemrejseDate2.getDate() == null) {
									JOptionPane.showMessageDialog(this, "Dato ikke valgt ordentligt.");
								} else {
									Afgangsliste afgange = new Afgangsliste(
											udrejseDate.getDate(), udrejseDate2.getDate(), hjemrejseDate.getDate(),
											hjemrejseDate2.getDate(), (String)searchList1.getSelectedItem(),
											(String)searchList2.getSelectedItem());
								}
							} else {
								Afgangsliste afgange = new Afgangsliste(
										udrejseDate.getDate(),hjemrejseDate.getDate(), 
										(String)searchList1.getSelectedItem(), 
										(String)searchList2.getSelectedItem(), false);
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "Fejl i kommunikation med serveren. Er internettet nede?");
						e.printStackTrace();
					}
				} else if (!turReturBool) {
					try {
						//hvis ikke dato er vist ordentligt, vis besked
						if(udrejseDate.getDate() == null) {
							JOptionPane.showMessageDialog(this, "Dato ikke valgt ordentligt.");
						} else {
							if(!periodBoolean) {
								//ellers, opret afgang baseret på de valgte lufthavne og datoer
								Afgangsliste afgange = new Afgangsliste(udrejseDate.getDate(), (String)searchList1.getSelectedItem(),
										(String)searchList2.getSelectedItem());
							} else {
								if(udrejseDate2.getDate() == null) {
									JOptionPane.showMessageDialog(this, "Dato ikke valgt ordentligt.");
								} else {
									Afgangsliste afgange = new Afgangsliste(udrejseDate.getDate(), udrejseDate2.getDate(),
											(String)searchList1.getSelectedItem(), (String)searchList2.getSelectedItem(), true);
								}
							}
						}
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(this, "Fejl i kommunikation med serveren. Er internettet nede?");
						e.printStackTrace();
					}
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
				JOptionPane.showMessageDialog(this, "Fejl i Database-forbindelse ved oprettelse af bookingliste. Er internettet nede?");
				e.printStackTrace();
			}
	    	
	    	
	    }
	    else if(source == periode) {
	    	if(periode.isSelected()) {
	    		periodBoolean = true;
	    		udrejseDate2.setVisible(true);
	    		hjemrejseDate2.setVisible(true);
	    		udrejse2.setVisible(true);
	    		hjemrejse2.setVisible(true);
	    		
	    		//Ændrer label i panelLeft
	    	    udrejse.setText("Udrejse fra");
	    	    
	    	    //Ændrer label i panelRight
	    	    hjemrejse.setText("Hjemrejse fra");
	    	    
	    	} else {
	    		periodBoolean = false;
	    		udrejseDate2.setVisible(false);
	    		hjemrejseDate2.setVisible(false);
	    		udrejse2.setVisible(false);
	    		hjemrejse2.setVisible(false);
	    		
	    		//Ændrer label i panelLeft
	    	    udrejse.setText("Udrejse");
	    	    
	    	    //Ændrer label i panelRight
	    	    hjemrejse.setText("Hjemrejse");
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
