package gui;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import logic.PladsArray;

public class Pladsbooking {
	
	private JFrame frame;
	private PladsArray pladsArray;
	private int rows;
	private int cols;
	ArrayList<Integer> emptyColumns = new ArrayList<>();
	private ArrayList<JPanel> panelList = new ArrayList<>();
	
	public Pladsbooking(PladsArray pa) throws SQLException {
		pladsArray = pa;
		rows = pa.getRows();
		cols = pa.getCols();
		emptyColumns = pa.getEmptyCols();
		makeWindow();
		reservations();
	}

	private void reservations() throws SQLException {
		ArrayList<Integer> reservations = pladsArray.findReservations();
		
		for (int i=0; i<reservations.size(); i++) {
			int r = reservations.get(i);
			panelList.get(r).setBackground(Color.RED);
			
		}
	}
	
	private void makeWindow() throws SQLException {
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		Container contentPane = frame.getContentPane();
		//contentPane.setLayout(new GridLayout(pladsArray.getRows(), pladsArray.getCols()));
		contentPane.setLayout(new GridLayout(rows, cols, 5, 5));
		
		//fylder arrayet med ureserverede pladser
		System.out.println("Rows: "+rows+", cols: "+cols);
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				JPanel jp = new JPanel();
				jp.setBackground(Color.GREEN);
				jp.setName(i+","+j);
				contentPane.add(jp);
				panelList.add(jp);
				
				JLabel jl = new JLabel(i+","+j);
				contentPane.add(jl);
			}
		}
		
		frame.setPreferredSize(new Dimension(640, 460));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
