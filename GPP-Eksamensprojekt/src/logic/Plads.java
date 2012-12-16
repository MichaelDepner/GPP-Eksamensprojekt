package logic;

import gui.Pladsbooking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Opretter et lille grønt JPanel, der repræsenterer pladserne i vores pladsbooking.
 * Den har en mouselistener, og sørger for at ændre status når der klikkes på den
 * En plads kan have 4 stadier: reserveret, markeret, mellemgang eller bare ledig.
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */
public class Plads extends JPanel {
	
	//Nummeret, pladsen har i ArrayListen af sæder
	private int seatNo;
	
	//Er pladsen reserveret? er pladsen en mellemgang? Er pladsen markeret?
	private boolean isReserved;
	private boolean isAisle;
	private boolean isMarked;
	
	//Pladsens navn bliver sat uden for klassen, da man skal bruge informationer om rækker og kolonner
	private String name;
	private int price;
	
	//Pladsen oprettes med et PladsArray tilknyttet for at kunne ændre reservations-status
	private PladsArray pa;
	
	//Pladsen oprettes med en Pladsbooking og et JPanel for at kunne bede pladsbookingen om at opdatere det relevante jpanel, når pladsen bliver markeret
	private Pladsbooking pb;
	private JPanel panel;
	
	public Plads(int seatNo, boolean isReserved, boolean isAisle, Pladsbooking pb, PladsArray pa, JPanel panel, int price) {
		this.seatNo = seatNo;
		this.isReserved = isReserved;
		this.isAisle = isAisle;
		this.pa = pa;
		this.pb = pb;
		this.panel = panel;
		this.price = price;
		Color();
		addMouseListener(new MouseListener());		
	}
	
	//Denne simplere constructor kaldes, når vi er ved bare at lave et plads-'preview' under afgangssøgning
	public Plads(int seatNo, Pladsbooking pb) {
		this.seatNo = seatNo;
		isReserved = false;
		isAisle = false;
		this.pb = pb;
		Color();
	}
	
	public int getSeatNo() {
		return seatNo;
	}
	
	//Pladsens navn tilføjes af pladsArrayet, der har overblik over pladsens position
	public void SetName(String name) {
		this.name = name;
	}
	
	public String GetName() {
		return name;
	}
	
	public int GetPrice() {
		return price;
	}	
	
	public String toString() {
		String s = GetName()+"    -    "+GetPrice()+"kr.";
		return s;
	}
	
	//Når en plads markeres, tilføj den som reserveret i pladsarrayet reservationsliste
	//Tilføj samtidig en label med pladsens navn og pris til PladsBookingen
	private void addReservation() {
		pa.addReservation(this);
		pb.addReservationLabels(pa, panel);
	}
	
	//Når en plads af-markeres, fjern reservationen i pladsarrayet reservationsliste
	//Fjern samtidig labelen med pladsens navn og pris i PladsBookingen
	private void removeReservation() {
		pa.removeReservation(this);
		pb.addReservationLabels(pa, panel);
	}
	
	
	public void mark() {
		isReserved = false;
		isMarked = true;
		addReservation();
		Color();
	}
	
	//Tilføjer MouseListener, der reagerer når Pladsen trykkes på
	private class MouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if(!isReserved && !isMarked && !isAisle) {
				isMarked = true;
				addReservation();
				
				Color();
				
			} else if(!isReserved && isMarked && !isAisle) {
				isMarked = false;
				removeReservation();
				
				Color();
			}
		}
	}
	
	//giver pladsen den rette farve
	private void Color() {
		if(isAisle) {
			this.setBackground(Color.GRAY);
		} else if(isReserved) {
			this.setBackground(Color.RED);
		} else if(isMarked) {
			this.setBackground(Color.BLUE);
		} else {
			this.setBackground(Color.GREEN);
		}
	}
	
	public void changeReservation() {
		if(isReserved) {
			isReserved = false;
		} else {
			isReserved = true;
		}
		Color();
	}
	
	//Kaldes hvis pladsen skal være en mellemgang
	public void makeAisle() {
		isAisle = true;
		setVisible(false);
		Color();
	}
	
	
}
