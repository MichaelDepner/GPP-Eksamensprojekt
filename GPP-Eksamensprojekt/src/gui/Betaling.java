package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import logic.Booking;
import logic.Customer;
import logic.Database;
import logic.Departure;
import logic.Person;
import logic.Plads;

/**
 * Navn på kort, udløbsmåned, udløbsår, kortnr, kontrolcifre, korttype
 * tilbage- og til-gennemse
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */
public class Betaling {
	private Container contentPane;
	private JFrame frame;
	private JLabel labelName, labelMonth, labelYear, labelNumber, labelCVR, labelType;
	private JTextField name, month, year, number, cvr, type;
	private JButton next, back;
	
	//Ting, der skal sendes til databasen
	private ArrayList<Person> passengers;
	private Customer customer;
	private ArrayList<Plads> reserved1, reserved2;
	private Departure d1, d2;
	private boolean turRetur;
	private Booking b;
	private Gennemse g;
	private Booking b1, b2;

	
	//Constructor ved enkeltrejsebestilling
	public Betaling(ArrayList<Plads> reserved, Departure d1, ArrayList<Person> passengers, 
			Customer customer, Gennemse g) {
		this.passengers = passengers;
		this.customer = customer;
		this.reserved1 = reserved;
		this.d1 = d1;
		this.g = g;
		turRetur = false;
		makeFrame();
	}
	
	//Constructor ved tur/retur bestilling
	public Betaling(ArrayList<Plads> reserved1, ArrayList<Plads> reserved2, 
			ArrayList<Person> passengers, Customer customer, Departure d1, Departure d2, 
			Gennemse g) {
		this.passengers = passengers;
		this.customer = customer;
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
		this.d1 = d1;
		this.d2 = d2;
		this.g = g;
		turRetur = true;
		makeFrame();
	}
	
	//Laver vinduet
	public void makeFrame() {
		frame = new JFrame();
		frame.setTitle("Betaling");
		
		frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(700, 460);
        frame.setResizable(false);
        
        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        //Laver panel til overskrift
        JPanel panelNorth = new JPanel();
        contentPane.add(panelNorth, BorderLayout.NORTH);
        panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        
        //Overskrift
        JLabel overskrift = new JLabel("Indtast betalingsoplysninger her");
        overskrift.setFont(new Font("String", Font.BOLD, 14));
        panelNorth.add(overskrift);
        
        //Laver et panel i contentPane.CENTER, der får et Gridlayout
        JPanel panelCenter = new JPanel();
        contentPane.add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new GridLayout(6,1,10,10));
	    panelCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
	    //Laver et panel i contentPane.EAST, der får et Gridlayout
	    JPanel panelEast = new JPanel();
        contentPane.add(panelEast, BorderLayout.EAST);
        panelEast.setLayout(new GridLayout(6,1,10,10));
	    panelEast.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    
        //Opretter vores labels
        labelName = new JLabel("Navn på kort");
        labelMonth = new JLabel("Udløbsmåned");
        labelYear = new JLabel("Udløbsår");
        labelNumber = new JLabel("Kortnummer");
        labelCVR = new JLabel("Kontrolcifre");
        labelType = new JLabel("Korttype");
        
        //Opretter vores textFields
        name = new JTextField(20);
        month = new JTextField();
        year = new JTextField();
        number = new JTextField();
        cvr = new JTextField();
        type = new JTextField();
        
        //Tilføjer labels og textFields to GridLayoutet
		panelCenter.add(labelName);
		panelCenter.add(labelMonth);
		panelCenter.add(labelYear);
		panelCenter.add(labelNumber);
		panelCenter.add(labelCVR);
		panelCenter.add(labelType);

		panelEast.add(name);
		panelEast.add(month);
		panelEast.add(year);
		panelEast.add(number);
		panelEast.add(cvr);
		panelEast.add(type);
		
		//Laver et panel i contentPane.SOUTH, der får et Gridlayout
		JPanel panelSouth = new JPanel();
        contentPane.add(panelSouth, BorderLayout.SOUTH);
        panelSouth.setLayout(new FlowLayout());
        
        //Knapper
        next = new JButton("Bestil");
        back = new JButton("Tilbage");
        panelSouth.add(back);
        panelSouth.add(next);
        
        //ActionListeners til knapperne
        next.addActionListener(new Listener());
        back.addActionListener(new Listener());
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public Betaling getThis() {
		return this;
	}
	
	//Lytter til knapperne
    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent event){
            if(event.getSource() == back) {
                frame.dispose();
            } else if(event.getSource() == next) {
            	//Foretag bestilling i databasen
            	
            	//Opretter passagerer
    			String passengerString = "";
    			int customerId = 0;
    			String seatNums1 = "";
    			String seatNums2 = "";

    			try {
    				Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
    				
    				//Skab passagér og gem passengerId
    				for(int i=0; i<passengers.size(); i++) {
    					int passengerId = db.queryMakePassenger(passengers.get(i));
    					passengerString = passengerId + " " + passengerString;
    				}

    				//Skab customer og gem customerId
    				customerId = db.queryMakeCustomer(customer);
    				
    				//Lav string med id på reserverede pladser ud
    				for(int j=0; j<reserved1.size(); j++) {
    					int num = reserved1.get(j).getSeatNo();
    					seatNums1 = num+" "+seatNums1;
    				}
    				//Skab booking for udrejse
    				b1 = db.queryMakeBooking(d1.getDepartureId(), customerId, seatNums1, passengerString);


    				if(turRetur) {
    					//Lav string med id på reserverede pladser hjem
    					for(int j=0; j<reserved2.size(); j++) {
    						int num = reserved2.get(j).getSeatNo();
    						seatNums2 = num+" "+seatNums2;
    					}
    					//Skab booking for hjemrejse
    					b2 = db.queryMakeBooking(d2.getDepartureId(), customerId, seatNums2, passengerString);

    					System.out.println("passengerString: "+passengerString);
    					System.out.println("customerId: "+customerId);
    				}
    				
    			JOptionPane.showMessageDialog
    				(frame, "Bestillingen er gennemført! Kvitteringen vil nu blive vist");
    			if(turRetur) {
    				Kvittering kv = new Kvittering
    						(reserved1, reserved2, passengers, customer, d1, d2, getThis(), b1, b2);
    			} else {
    				Kvittering kv = new Kvittering
    						(reserved1, d1, passengers, customer, getThis(), b1);
    			}
    			g.removeMe();
    			frame.dispose();
					
    			} catch (SQLException e) {
    				JOptionPane.showMessageDialog
    					(frame, "Fejl under bestilling. Tjek internetforbindelsen, og prøv igen.");
    				e.printStackTrace();
    			}
            }
        }
    }
}
