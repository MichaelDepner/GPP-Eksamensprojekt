package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;

import logic.Plads;
import logic.PladsArray;

public class Pladsbooking extends JFrame {
	
	//private JFrame frame;
	private PladsArray pladsArray1, pladsArray2;
	//private int rows1, rows2;
	//private int cols1, cols2;
	private int departureId, departureId2;
	ArrayList<Integer> emptyColumns1;
	ArrayList<Integer> emptyColumns2 = new ArrayList<>();
	private ArrayList<Plads> panelList1 = new ArrayList<>();
	private ArrayList<Plads> panelList2 = new ArrayList<>();
	private ArrayList<Plads> newReservations1 = new ArrayList<>();
	private ArrayList<Plads> newReservations2 = new ArrayList<>();
	private Boolean booking, multipleDepartures;
	private JPanel rightMiddlePanel;
	private ArrayList<JPanel> labelList = new ArrayList<>();
	
	public Pladsbooking(int departureId, Boolean booking) throws SQLException {
		this.booking = booking;
		this.departureId = departureId;
		pladsArray1 = new PladsArray(departureId);
		//rows1 = pladsArray1.getRows();
		//cols1 = pladsArray1.getCols();
		emptyColumns1 = pladsArray1.getEmptyCols();
		
		if(!booking) {
			//makePreviewWindow();
			//reservations();
		} else {
			makeBookingWindow();
		}
	}
	
	public Pladsbooking(int departureId1, int departureId2) throws SQLException {
		booking = true;
		this.departureId = departureId1;
		this.departureId2 = departureId2;
		pladsArray1 = new PladsArray(departureId1);
		pladsArray2 = new PladsArray(departureId2);
//		int rows1 = pladsArray1.getRows();
//		int rows2 = pladsArray2.getRows();
//		int cols1 = pladsArray1.getCols();
//		int cols2 = pladsArray2.getCols();
		emptyColumns1 = pladsArray1.getEmptyCols();
		emptyColumns2 = pladsArray2.getEmptyCols();
		
		makeBookingWindow();
		reservations(panelList1, pladsArray1);
		reservations(panelList2, pladsArray2);
		
		
		
	}
	

//	private void reservations() throws SQLException {
//		ArrayList<Integer> reservations = pladsArray1.findReservations();
//		
//		for (int i=0; i<reservations.size(); i++) {
//			int r = reservations.get(i);
//			panelList1.get(r).changeReservation();
//		}
//		
//		int counter = 0;
//		for(int i=0; i<rows1; i++) {
//			for(int j=0; j<cols1; j++) {
//				if (emptyColumns1.contains(j+1)) {
//					panelList1.get(counter).makeAisle();
//				}
//				counter++;
//			}
//		}
//	}
	
	private void reservations(ArrayList<Plads> panelList, PladsArray pladsArray) throws SQLException {
		ArrayList<Integer> reservations = pladsArray.findReservations();
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		ArrayList<Integer> emptyColumns = pladsArray.getEmptyCols();
		
		for(int i=0; i<reservations.size(); i++) {
			int r = reservations.get(i);
			panelList.get(r).changeReservation();
		}
		
		int counter = 0;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				if(emptyColumns.contains(j+1)) {
					panelList.get(counter).makeAisle();
				}
				counter++;
			}
		}
		
		
	}
	
//	private void makePreviewWindow() throws SQLException {
//		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		
//		
//		Container contentPane = this.getContentPane();
//		contentPane.setLayout(new GridLayout(rows1, cols1, 2, 2));
//		
//		//fylder arrayet med ureserverede pladser
//		
//		int counting=0;
//		for(int i = 0; i<rows1; i++) {
//			for(int j = 0; j<cols1; j++) {
//				counting++;
//				Plads p = new Plads(counting, false, false, this);
//				contentPane.add(p);
//				panelList1.add(p);
//			}
//		}
//		
//		this.setPreferredSize(new Dimension(cols1*30, rows1*30));
//		this.setResizable(false);
//		this.pack();
//		this.setVisible(true);
//	}
	
	public void makeBookingWindow() throws SQLException {
		JPanel leftPanel, middlePanel, centerPanel, rightPanel, bottomPanel;
		JPanel leftBottomPanel, leftTopPanel, middleBottomPanel, middleTopPanel, rightTopPanel, rightBottomPanel;
		JLabel leftPanelTitle, centerPanelTitle, rightPanelTitle;
		//frame = new JFrame();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = this.getContentPane();
		
		
		multipleDepartures = true;
		if(multipleDepartures) {
			
			leftPanel = new JPanel();
			leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
			centerPanel = new JPanel();
			centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
			rightPanel = new JPanel();
			rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
			middlePanel = new JPanel();
			bottomPanel = new JPanel();
			
			contentPane.setLayout(new BorderLayout(5, 5));
			contentPane.add(centerPanel, BorderLayout.CENTER);
			centerPanel.setLayout(new GridLayout(1, 3));
			centerPanel.add(leftPanel);
			centerPanel.add(middlePanel);
			centerPanel.add(rightPanel);
			//contentPane.add(bottomPanel, BorderLayout.SOUTH);
			
			leftPanel.setLayout(new BorderLayout());
			leftBottomPanel = addPlane(pladsArray1, panelList1);
			leftPanel.add(leftBottomPanel, BorderLayout.CENTER);
			leftTopPanel = new JPanel();
			leftPanel.add(leftTopPanel, BorderLayout.NORTH);
			leftPanelTitle = new JLabel("Udrejse");
			leftTopPanel.add(leftPanelTitle);
			
			
			middlePanel.setLayout(new BorderLayout());
			middleBottomPanel = addPlane(pladsArray2, panelList2);
			middlePanel.add(middleBottomPanel);
			middleTopPanel = new JPanel();
			middlePanel.add(middleTopPanel, BorderLayout.NORTH);
			centerPanelTitle = new JLabel("Hjemrejse");
			middleTopPanel.add(centerPanelTitle);
			
			
			rightPanel.setLayout(new BorderLayout());
			rightTopPanel = new JPanel();
			rightMiddlePanel = new JPanel();
			rightMiddlePanel.setLayout(new BoxLayout(rightMiddlePanel, BoxLayout.Y_AXIS));
			rightBottomPanel = new JPanel();
			rightPanel.add(rightTopPanel, BorderLayout.NORTH);
			rightPanel.add(rightMiddlePanel, BorderLayout.CENTER);
			rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);
			rightPanelTitle = new JLabel("Info");
			rightTopPanel.add(rightPanelTitle);
			
			
			
			
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
	
//	public void addSeat(Plads plads) {
//		JPanel seat = new JPanel();
//		seat.setLayout(new BoxLayout(seat, BoxLayout.Y_AXIS));
//		JLabel label = new JLabel(plads.GetName()+"    -    "+plads.GetPrice()+"kr.");
//		seat.add(label);
//		
//		rightMiddlePanel.add(seat);
//		labelList.add(seat);
//	}
	public void addSeatLabel(Plads p, JPanel panel) {
		JLabel label = new JLabel(p.toString());		
		panel.add(label);
		validate();
	}
	
	public void addReservationLabels(PladsArray pa) {
		rightMiddlePanel.removeAll();
		for(int i=0; i<pa.getReservations().size(); i++) {
			addSeatLabel(pa.getReservations().get(i), rightMiddlePanel);
		}
	}
	
	
	public JPanel addPlane(PladsArray pladsArray, ArrayList<Plads> panelList) throws SQLException {		
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols, 2, 2));
		int counting=0;
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				counting++;
				Plads p = new Plads(counting, false, false, this, pladsArray);
																				//p.addMouseListener(new MouseListener());
				panel.add(p);
				panelList.add(p);
			}
		}
		setNames(panelList, pladsArray);
		
		return panel;
	}
	

	
//	private class MouseListener extends MouseAdapter {
//		public void mousePressed(MouseEvent e) {
//			
//			for(int i=0; i<panelList1.size(); i++) {
//				Plads p = panelList1.get(i);
//				if (p.getIsMarked()) {
//					if(p.hasJLabel()) {
//						System.out.println(p+" is already marked");
//					} else {
//						p.setHasJLabel();
//						addSeatLabel(p, rightMiddlePanel);
//						
//					}
//				}
//			}
//			
//		}
//	}
	
	public void close() {
		this.dispose();
	}
	
	public int getId() {
		return departureId;
	}
	
	public void setNames(ArrayList<Plads> pladser, PladsArray pladsArray) throws SQLException {
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		ArrayList<Integer> emptyCols = pladsArray.getEmptyCols();
		HashMap nameMap = new HashMap<>();
		
		
		//løb igennem cols, og tildel dem et bogstav hvis ikke de er tomme
		int counter = 0;
		for(int i=0; i<cols; i++) {
			if(!emptyCols.contains(i+1)) {
				nameMap.put(i, getCharForNumber(counter));
				counter++;
			} else {
				nameMap.put(i, false);
			}	
		}
		
		counter = 0;
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				if(!emptyCols.contains(j+1)) {
					pladser.get(counter).SetName(""+(i+1)+nameMap.get(j));
					counter++;
				} else {
					counter++;
				}
				
			}
		}
		
		
	}
	
	private String getCharForNumber(int i) {
	    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	    if (i > 25) {
	        return null;
	    }
	    return Character.toString(alphabet[i]);
	}
	
}
