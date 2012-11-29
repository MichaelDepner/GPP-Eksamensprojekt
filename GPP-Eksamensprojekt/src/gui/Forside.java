package gui;

import java.awt.*;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.event.*;
import java.sql.SQLException;

import logic.AfgangSøgning;

public class Forside {
	private JFrame frame;
	private JPanel southPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel northPanel = new JPanel();
	private JButton rejse, booking, afgang;
	private JXDatePicker rejsesøgning;
	private JTextField departureAirport, arrivalAirport;
	
	private JLabel logoLabel;
	
	
	
	public Forside() {
		makeWindow();
	}
	
	private void makeWindow() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		//Sæt layout i contentPane til et borderlayout, og tilføj et gridlayout til centerPanel
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridLayout(3, 2));
		
		//Indlæs Logo.png, og gem det i en JLabel
		ImageIcon imageLogo = new ImageIcon(getClass().getResource("png/Logo.png"));
		logoLabel = new JLabel(imageLogo);
		
		//Tilføj vores 2 panels til contentPanen, på CENTER og NORTH pladserne
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(logoLabel, BorderLayout.NORTH);
		
		//Initialisér de 3 knapper
		rejse = new JButton("Søg rejser");
		booking = new JButton("Søg booking");
		afgang = new JButton("Søg afgang");
		
		//Initialisér inputfelt til rejsesøgning
		rejsesøgning = new JXDatePicker();
		rejsesøgning.getDate();
		
		departureAirport = new JTextField("Copenhagen"); //departure
		arrivalAirport = new JTextField("Rønne"); //arrival
		
		//Tilføjer actionListenere til knapperne
		rejse.addActionListener(new rejseActionListener());
		booking.addActionListener(new bookingActionListener());
		afgang.addActionListener(new afgangActionListener());
		
		//Tilføjet knapperne til centerPanel
		centerPanel.add(rejse);
		centerPanel.add(rejsesøgning);
		centerPanel.add(departureAirport);
		centerPanel.add(arrivalAirport);
		centerPanel.add(afgang);
		centerPanel.add(booking);
		
		
		//Sæt vindue-størrelsen, gør så det ikke kan resizes, og gør det hele synligt.
		frame.setPreferredSize(new Dimension(640, 460));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	private class rejseActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Søger rejser!");
			try {
				AfgangSøgning afgange = new AfgangSøgning(rejsesøgning.getDate(), departureAirport.getText(), arrivalAirport.getText());
			} catch (SQLException e1) {
				 //TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	private class afgangActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Søger afgange!");
		}
	}
	
	private class bookingActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Søger bookingner!");
		}
	}
	
	
	
}
