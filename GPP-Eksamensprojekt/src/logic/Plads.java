package logic;

import gui.Pladsbooking;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class Plads extends JPanel {
	
	private int seatNo;
	private boolean isReserved;
	private boolean isAisle;
	
	
	/**
	 * @param seatNo Placeringen af sædet i afgang-arrayet
	 * @param isReserved Pladsens status
	 * @param isAisle Er pladsen en mellemgang?
	 */
	public Plads(int seatNo, boolean isReserved, boolean isAisle, Pladsbooking pb) {
		this.seatNo = seatNo;
		this.isReserved = isReserved;
		this.isAisle = isAisle;
		Color();
		addMouseListener(new MouseListener());
	}
	
	private class MouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if(!isReserved) {
				isReserved = true;
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
		Color();
	}
	
	public class addActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("I have been clicked!");
		}
	}
}
