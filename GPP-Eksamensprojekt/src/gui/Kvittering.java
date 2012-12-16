package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import logic.Booking;
import logic.Customer;
import logic.Departure;
import logic.Person;
import logic.Plads;

/**
 * Kvitteringen skal udprintes / sendes med email til kunden.
 * Den giver et overblik over den booking der lige er foretaget, og tr�kker tilmed bookingens ID fra databasen.
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kj�rsgaard og Nicoline Warming Larsen.
 *
 */
public class Kvittering extends JFrame {
	private JPanel panel, panelKontaktoplysninger, panelRejse, panelUdrejse, panelHjemrejse;
	private JPanel panelPladser, panelPladserUdrejse, panelPladserHjemrejse;
	private JPanel panelPassengers, panelPris, flowPanel1, flowPanel2;
	private JPanel flowPanel3, logoPanel;
	private JLabel name, address, city;
	private JLabel  country, phoneNumber, email, kontaktoplysninger, udrejse, hjemrejse;
	private JLabel airport, afgang, ankomst, rejsetid;
	private JLabel pladser, labelSeat, labelPassengers, passenger, birthday, header;
	private JLabel labelPris, total, prisTekst, logoLabel;
	private Betaling b;
	
	//Ting, der skal sendes til databasen
	ArrayList<Person> passengers;
	Customer customer;
	ArrayList<Plads> reserved1;
	ArrayList<Plads> reserved2;
	Departure d1, d2;
	Boolean turRetur;
	Booking booking1, booking2;
	
	//Constructor ved enkeltrejsebestilling
		public Kvittering(ArrayList<Plads> reserved, Departure d1, ArrayList<Person> passengers, Customer customer, Betaling b,
				Booking booking) {
			this.passengers = passengers;
			this.customer = customer;
			this.reserved1 = reserved;
			this.d1 = d1;
			turRetur = false;
			booking1 = booking;
			makeWindow(turRetur);
		}
		
		//Constructor ved tur/retur bestilling
		public Kvittering(ArrayList<Plads> reserved1, ArrayList<Plads> reserved2,
				ArrayList<Person> passengers, Customer customer, Departure d1, Departure d2,
				Betaling b, Booking b1, Booking b2) {
			this.passengers = passengers;
			this.customer = customer;
			this.reserved1 = reserved1;
			this.reserved2 = reserved2;
			this.d1 = d1;
			this.d2 = d2;
			turRetur = true;
			this.booking1 = b1;
			this.booking2 = b2;
			makeWindow(turRetur);
		}
	
		//Laver vinduet
		public void makeWindow(boolean turRetur) {
			setTitle("Kvittering");
			
			panel = new JPanel();
			getContentPane().add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			
			//Panel til logo
	        logoPanel = new JPanel();
	        panel.add(logoPanel);
	        
	        ImageIcon imageLogo = new ImageIcon(getClass().getResource("png/swan6.jpg"));
			logoLabel = new JLabel(imageLogo);
			logoPanel.add(logoLabel);

			//Scrollbar
			JScrollPane scroll = new JScrollPane(panel);
			getContentPane().add(scroll);

			//Panel til kontaktoplysninger
			flowPanel1 = new JPanel();
			panel.add(flowPanel1);
			flowPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));

			panelKontaktoplysninger = new JPanel();
			flowPanel1.add(panelKontaktoplysninger);
			panelKontaktoplysninger.setLayout
			(new BoxLayout(panelKontaktoplysninger, BoxLayout.PAGE_AXIS));

			panelKontaktoplysninger.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			//Tilf�jer kontaktoplysninger fra Customer
			kontaktoplysninger = new JLabel("Kontaktoplysninger");
			kontaktoplysninger.setFont(new Font("String", Font.BOLD, 16));
			panelKontaktoplysninger.add(kontaktoplysninger);
			name = new JLabel(customer.GetFirstname() + " " + customer.GetSurname());
			panelKontaktoplysninger.add(name);
			address = new JLabel(customer.GetAdress());
			panelKontaktoplysninger.add(address);
			city = new JLabel(customer.GetPostalCode() + " " + customer.GetCity());
			panelKontaktoplysninger.add(city);
			country = new JLabel(customer.GetCountry());
			panelKontaktoplysninger.add(country);
			phoneNumber = new JLabel(customer.GetPhone());
			panelKontaktoplysninger.add(phoneNumber);
			email = new JLabel(customer.GetEmail());
			panelKontaktoplysninger.add(email);

			panelRejse = new JPanel();
			panel.add(panelRejse);
			panelRejse.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
			panelRejse.setLayout(new GridLayout(1,2,10,10));
			panelRejse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			//Udrejse-info
			panelUdrejse = new JPanel();
			panelRejse.add(panelUdrejse);
			panelUdrejse.setLayout(new GridLayout(5,1));
			panelUdrejse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			panelUdrejse.setBackground(Color.lightGray);

			udrejse = new JLabel("Udrejse - Booking nr. "+booking1.getId());
			udrejse.setFont(new Font("String", Font.BOLD, 16));
			panelUdrejse.add(udrejse);
			airport = new JLabel(d1.getDepartureAirportName() + " "+ d1.getDepartureAirportAbbrevation() 
					+ " - " + d1.getArrivalAirportName() + " " + d1.getArrivalAirportAbbrevation());
			panelUdrejse.add(airport);
			afgang = new JLabel("Afgang: " + d1.getDepartureDate() + " "+ d1.getDepartureTime());
			panelUdrejse.add(afgang);
			ankomst = new JLabel("Ankomst: " + d1.getDepartureDate() + " "  + d1.getArrivalTime());
			panelUdrejse.add(ankomst);
			rejsetid = new JLabel("Rejsetid: " + d1.getTravelTime());
			panelUdrejse.add(rejsetid);

			//Hjemrejse-info
			panelHjemrejse = new JPanel();
			panelRejse.add(panelHjemrejse);

			//Udvider vinduet, i tilf�lde af tur/retur bestilling
			if(turRetur) {
				panelHjemrejse.setLayout(new GridLayout(5,1));
				panelHjemrejse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				panelHjemrejse.setBackground(Color.lightGray);

				hjemrejse = new JLabel("Hjemrejse - Booking nr. "+booking2.getId());
				hjemrejse.setFont(new Font("String", Font.BOLD, 16));
				panelHjemrejse.add(hjemrejse);
				airport = new JLabel(d2.getDepartureAirportName() + " "+ d2.getDepartureAirportAbbrevation() + " - " + d2.getArrivalAirportName() + " " + d2.getArrivalAirportAbbrevation());
				panelHjemrejse.add(airport);
				afgang = new JLabel("Afgang: " + d2.getDepartureDate() + " " + d2.getDepartureTime());
				panelHjemrejse.add(afgang);
				ankomst = new JLabel("Ankomst: " + d2.getDepartureDate() + " " + d2.getArrivalTime());
				panelHjemrejse.add(ankomst);
				rejsetid = new JLabel("Rejsetid: " + d2.getTravelTime());
				panelHjemrejse.add(rejsetid);
			}

			//Pladser info
			panelPladser = new JPanel();
			panel.add(panelPladser);
			panelPladser.setLayout(new GridLayout(1,2,10,10));
			panelPladser.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

			//Info om pladser til udrejsen
			panelPladserUdrejse = new JPanel();
			panelPladser.add(panelPladserUdrejse);
			panelPladserUdrejse.setLayout(new GridLayout(10,1));
			panelPladserUdrejse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			panelPladserUdrejse.setBackground(Color.lightGray);

			pladser = new JLabel("Pladser udrejse");
			pladser.setFont(new Font("String", Font.BOLD, 16));
			panelPladserUdrejse.add(pladser);

			//Bestilte pladser
			antalPladser(reserved1, panelPladserUdrejse);

			//Info om pladser til hjemrejsen
			panelPladserHjemrejse = new JPanel();
			panelPladser.add(panelPladserHjemrejse);
			
			//Info om pladser til hjemrejsen
			if(turRetur) {
				panelPladserHjemrejse.setLayout(new GridLayout(10,1));
				panelPladserHjemrejse.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				panelPladserHjemrejse.setBackground(Color.lightGray);

				pladser = new JLabel("Pladser hjemrejse");
				pladser.setFont(new Font("String", Font.BOLD, 16));
				panelPladserHjemrejse.add(pladser);

				antalPladser(reserved2, panelPladserHjemrejse);
			}

			//Passagerer
			flowPanel2 = new JPanel();
			panel.add(flowPanel2);
			flowPanel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

			panelPassengers = new JPanel();
			flowPanel2.add(panelPassengers);
			panelPassengers.setLayout(new BoxLayout(panelPassengers, BoxLayout.PAGE_AXIS));

			labelPassengers = new JLabel("Passagerer");
			labelPassengers.setFont(new Font("String", Font.BOLD, 16));
			panelPassengers.add(labelPassengers);
			
			//Skaber passagerer
			passengers(passengers.size());

			//Pris
			flowPanel3 = new JPanel();
			panel.add(flowPanel3);
			flowPanel3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

			panelPris = new JPanel();
			flowPanel3.add(panelPris);
			panelPris.setLayout(new BoxLayout(panelPris, BoxLayout.PAGE_AXIS));
			
			//Inds�tter overskrift
			labelPris = new JLabel("Pris");
			labelPris.setFont(new Font("String", Font.BOLD, 16));
			panelPris.add(labelPris);
			
			//Tekst mht. pris
			if(turRetur) {
				prisTekst = new JLabel(passengers.size() + " x s�der � " + d1.getPrice() +
							" + " + passengers.size() + " x s�der � " + d2.getPrice());
				total = new JLabel("Total = " + (passengers.size()*d1.getPrice()+
							passengers.size()*d2.getPrice()));
			} else {
				prisTekst = new JLabel(passengers.size() + " x s�der � " + d1.getPrice());
				total = new JLabel("Total = " + (passengers.size()*d1.getPrice()) + " kr.");
			}
			
			panelPris.add(prisTekst);
			total.setFont(new Font("String", Font.BOLD, 14));
			panelPris.add(total);

			setPreferredSize(new Dimension(640, 500));
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setVisible(true);
			setResizable(true);
		}
		
		//Tekst til passager-info
		private void passengers(int antalPassagerer) {
			for(int i = 0; i < antalPassagerer; i++) {
				header = new JLabel("Passager " + (i+1));
		        header.setFont(new Font("String", Font.BOLD, 14));
				panelPassengers.add(header);
				passenger = new JLabel(passengers.get(i).getFirstname() + " " 
											+ passengers.get(i).getSurname());
				panelPassengers.add(passenger);
				birthday = new JLabel(passengers.get(i).getBirthday());
				panelPassengers.add(birthday);
				JLabel emptyLabel = new JLabel(" ");
				panelPassengers.add(emptyLabel);
			}
		}
		
		//Tilf�j pladsers navne
		private void antalPladser(ArrayList<Plads> reservedSeats, JPanel panel) {
			for(int i = 0; i < reservedSeats.size(); i++) {
				labelSeat = new JLabel(reservedSeats.get(i).GetName());
				panel.add(labelSeat);
			}
		}	
}
