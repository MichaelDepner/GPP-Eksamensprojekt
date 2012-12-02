package gui;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import logic.Plads;
import logic.PladsArray;

public class Pladsbooking {
	
	private JFrame frame;
	private PladsArray pladsArray;
	private int rows;
	private int cols;
	ArrayList<Integer> emptyColumns = new ArrayList<>();
	private ArrayList<Plads> panelList = new ArrayList<>();
	private ArrayList<Plads> newReservations = new ArrayList<>();
	
	public Pladsbooking(int departureId) throws SQLException {
		pladsArray = new PladsArray(departureId);
		rows = pladsArray.getRows();
		cols = pladsArray.getCols();
		emptyColumns = pladsArray.getEmptyCols();
		makeWindow();
		reservations();
	}

	private void reservations() throws SQLException {
		ArrayList<Integer> reservations = pladsArray.findReservations();
		
		for (int i=0; i<reservations.size(); i++) {
			int r = reservations.get(i);
			panelList.get(r).changeReservation();
		}
		
		int counter = 0;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				if (emptyColumns.contains(j+1)) {
					panelList.get(counter).makeAisle();
				}
				counter++;
			}
		}
	}
	
	private void makeWindow() throws SQLException {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		Container contentPane = frame.getContentPane();
		//contentPane.setLayout(new GridLayout(pladsArray.getRows(), pladsArray.getCols()));
		contentPane.setLayout(new GridLayout(rows, cols, 2, 2));
		
		//fylder arrayet med ureserverede pladser
		System.out.println("Rows: "+rows+", cols: "+cols);
		
		int counting=0;
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				counting++;
				Plads p = new Plads(counting, false, false, this);
				contentPane.add(p);
				panelList.add(p);
			}
		}
		
		frame.setPreferredSize(new Dimension(cols*30, rows*30));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addReservation(Plads p) {
		newReservations.add(p);
	}
}
