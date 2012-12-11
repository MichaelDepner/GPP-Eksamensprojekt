package logic;

import gui.Pladsbooking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Plads extends JPanel {
	
	private int seatNo;
	private boolean isReserved;
	private boolean isAisle;
	private boolean isMarked;
	private boolean hasJLabel = false;
	private String name;
	private int price;
	private PladsArray pa;
	private Pladsbooking pb;
	private JPanel panel;
	
	/**
	 * @param seatNo Placeringen af sædet i afgang-arrayet
	 * @param isReserved Pladsens status
	 * @param isAisle Er pladsen en mellemgang?
	 */
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
	
	public Plads(int seatNo, Pladsbooking pb) {
		this.seatNo = seatNo;
		isReserved = false;
		isAisle = false;
		this.pa = pa;
		this.pb = pb;
		Color();
	}
	
	public int getSeatNo() {
		return seatNo;
	}
	
	
	public void SetName(String name) {
		this.name = name;
		JLabel nameLabel = new JLabel(name);
		this.add(nameLabel);
	}
	
	public String GetName() {
		return name;
	}
	
	public int GetPrice() {
		return price;
	}
	
	public boolean getIsMarked() {
//		System.out.println("Somebody asked me if I was marked and I told them "+isMarked);
		return isMarked;
		
	}
	
	public boolean hasJLabel() {
		return hasJLabel;
	}
	
	public void setHasJLabel() {
		hasJLabel = true;
	}
	
	public void removeHasJLabel() {
		hasJLabel = false;
	}
	
	
	public String toString() {
		String s = GetName()+"    -    "+GetPrice()+"kr.";
		return s;
	}
	
	public void addReservation() {
		pa.addReservation(this);
		pb.addReservationLabels(pa, panel);
	}
	
	public void removeReservation() {
		pa.removeReservation(this);
		pb.addReservationLabels(pa, panel);
	}
	
	
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
	public void Color() {
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
	
	public void makeAisle() {
		isAisle = true;
		setVisible(false);
		Color();
	}
	
	
}
