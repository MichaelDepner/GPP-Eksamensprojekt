package gui;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Forside2  extends JFrame implements ActionListener{
	
	private JTabbedPane searchPane;
	private JPanel panel, panelRejser, panelNorth, radioPanel, labelPanel, panelSouth;
	private JPanel panelAfgange, panelBooking, panelCenter, panelLeft, panelRight;
	private JLabel logoLabel, fraLabel, tilLabel, tomLabel1, tomLabel2, tomLabel3, tomLabel4;
	private JLabel udrejse, hjemrejse;
	private JTextField fraTextField, tilTextField;
	private JRadioButton enkelt, turRetur;
	private JXDatePicker udrejseDate, hjemrejseDate;
	private JButton searchButton;
	
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
		panelAfgange = new JPanel();
		panelBooking = new JPanel();
		
		//Tilføjer faner med tilhørende paneler
		searchPane.addTab("Søg rejser", panelRejser);
		searchPane.addTab("Søg afgange", panelAfgange);
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
	
	public void actionPerformed(ActionEvent event) {
	    Object source = event.getSource();
	    if(source.equals(searchButton)) {
	    	try {
				Afgangsliste afgange = new Afgangsliste(udrejseDate.getDate(),hjemrejseDate.getDate(), fraTextField.getText(), tilTextField.getText());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("SQL fejl");
				e.printStackTrace();
			}
	    }
		if(source == turRetur)
		{
			panelRight.setVisible(true);
		}
	    if(source == enkelt) {
	    	panelRight.setVisible(false);
	    }
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
	    
	    //Tilføjer labelPanel til panelNorth, og giver GridLayout
	    labelPanel = new JPanel();
	    labelPanel.setLayout(new GridLayout(2,4));
	    labelPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    panelNorth.add(labelPanel);
	    
	    //Opretter indhold til labelPanel
	    fraLabel = new JLabel("Fra");
	    tilLabel = new JLabel("Til");
	    fraTextField = new JTextField();
	    tilTextField = new JTextField();
	    tomLabel1 = new JLabel();
	    tomLabel2 = new JLabel();
	    tomLabel3 = new JLabel();
	    tomLabel4 = new JLabel();
	    
	    //Opretter indhold til labelPanel
	    labelPanel.add(tomLabel1);
	    labelPanel.add(fraLabel);
	    labelPanel.add(fraTextField);
	    labelPanel.add(tomLabel2);
	    labelPanel.add(tomLabel3);
	    labelPanel.add(tilLabel);
	    labelPanel.add(tilTextField);
	    labelPanel.add(tomLabel4);
	    
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
	
	private void searchAfgange() {
		
	}
	
	private void searchBookinger() {
		//Laver et BorderLayout inde i fane-vinduet bookinger
		panelBooking.setLayout(new BorderLayout(10,10));
	}
}
