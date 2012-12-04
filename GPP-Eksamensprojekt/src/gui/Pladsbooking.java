package gui;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import logic.Plads;
import logic.PladsArray;

public class Pladsbooking extends JFrame {
	
	//private JFrame frame;
	private PladsArray pladsArray;
	private int rows;
	private int cols;
	private int departureId;
	private int departureId2;
	ArrayList<Integer> emptyColumns = new ArrayList<>();
	private ArrayList<Plads> panelList = new ArrayList<>();
	private ArrayList<Plads> newReservations = new ArrayList<>();
	private Boolean booking, multipleDepartures;
	
	public Pladsbooking(int departureId, Boolean booking) throws SQLException {
		this.booking = booking;
		this.departureId = departureId;
		pladsArray = new PladsArray(departureId);
		rows = pladsArray.getRows();
		cols = pladsArray.getCols();
		emptyColumns = pladsArray.getEmptyCols();
		
		if(!booking) {
			makePreviewWindow();
			reservations();
		} else {
			makeBookingWindow();
		}
	}
	
	public Pladsbooking(int departureId1, int departureId2) throws SQLException {
		booking = true;
		this.departureId = departureId1;
		this.departureId2 = departureId2;
		
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
	
	private void makePreviewWindow() throws SQLException {
		//frame = new JFrame();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		Container contentPane = this.getContentPane();
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
		
		this.setPreferredSize(new Dimension(cols*30, rows*30));
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	public void makeBookingWindow() {
		JPanel leftPanel, middlePanel, centerPanel, rightPanel, bottomPanel;
		JPanel leftBottomPanel, leftTopPanel;
		JLabel leftPanelTitle, centerPanelTitle, rightPanelTitle;
		//frame = new JFrame();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = this.getContentPane();
		
		multipleDepartures = true;
		if(multipleDepartures) {
			leftPanel = new JPanel();
			leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			centerPanel = new JPanel();
			rightPanel = new JPanel();
			middlePanel = new JPanel();
			bottomPanel = new JPanel();
			contentPane.setLayout(new BorderLayout(5, 5));
			contentPane.add(centerPanel, BorderLayout.CENTER);
			centerPanel.setLayout(new GridLayout(1, 3, 2, 2));
			centerPanel.add(leftPanel);
			centerPanel.add(middlePanel);
			centerPanel.add(rightPanel);
			contentPane.add(bottomPanel, BorderLayout.SOUTH);
			
			leftPanel.setLayout(new BorderLayout());
			leftBottomPanel = addPlane(rows, cols, panelList);
//			leftBottomPanel = new JPanel();
//			leftBottomPanel.setLayout(new GridLayout(rows, cols, 2, 2));
			leftPanel.add(leftBottomPanel, BorderLayout.CENTER);
			leftTopPanel = new JPanel();
			leftPanel.add(leftTopPanel, BorderLayout.NORTH);
			leftPanelTitle = new JLabel("Afgang");
			leftTopPanel.add(leftPanelTitle);
			
			
//			int counting=0;
//			for(int i = 0; i<rows; i++) {
//				for(int j = 0; j<cols; j++) {
//					counting++;
//					Plads p = new Plads(counting, false, false, this);
//					leftBottomPanel.add(p);
//					panelList.add(p);
//				}
//			}
			
			
			this.setSize(720,720);
			this.setVisible(true);
}
	}
	
	public JPanel addPlane(int rows, int cols, ArrayList<Plads> pladsList) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols, 2, 2));
		int counting=0;
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				counting++;
				Plads p = new Plads(counting, false, false, this);
				panel.add(p);
				panelList.add(p);
			}
		}
		
		return panel;
	}
	
	public void addReservation(Plads p) {
		newReservations.add(p);
	}
	
	public void close() {
		this.dispose();
	}
	
	public int getId() {
		return departureId;
	}
}
