package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPanel rightMiddleTopPanel, rightMiddleMiddlePanel;
	private ArrayList<JPanel> labelList = new ArrayList<>();
	private JLabel udrejseLabel, hjemrejseLabel;
	
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
			
			//contentPane.setLayout(new BorderLayout(5, 5));
			contentPane.add(centerPanel);
			//centerPanel.setLayout(new GridLayout(1, 3));
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
			centerPanel.add(leftPanel);
			centerPanel.add(middlePanel);
			centerPanel.add(rightPanel);
			//contentPane.add(bottomPanel, BorderLayout.SOUTH);
			
			JPanel rightMiddlePanel = new JPanel();
			rightMiddlePanel.setLayout(new GridLayout(3, 1, 5, 5));
			rightPanel.setLayout(new BorderLayout());
			rightPanel.setSize(300, 720);
			rightTopPanel = new JPanel();
			rightMiddleTopPanel = new JPanel();
			rightMiddleTopPanel.setLayout(new BoxLayout(rightMiddleTopPanel, BoxLayout.Y_AXIS));
			JLabel udrejseLabel = new JLabel("Udrejse");
            udrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			rightMiddleTopPanel.add(udrejseLabel);
			rightBottomPanel = new JPanel();
			rightPanel.add(rightTopPanel, BorderLayout.NORTH);
			rightPanel.add(rightMiddlePanel, BorderLayout.CENTER);
			rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);
			rightPanelTitle = new JLabel("Info");
            rightPanelTitle.setFont(new Font("String", Font.BOLD, 16));
			rightTopPanel.add(rightPanelTitle);
			
			rightMiddleMiddlePanel = new JPanel();
			rightMiddleMiddlePanel.setLayout(new BoxLayout(rightMiddleMiddlePanel, BoxLayout.Y_AXIS));
			JLabel hjemrejseLabel = new JLabel("Hjemrejse");
            hjemrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			rightMiddleMiddlePanel.add(hjemrejseLabel);
			rightMiddlePanel.add(rightMiddleTopPanel);
			rightMiddlePanel.add(rightMiddleMiddlePanel);
			
			JPanel rightMiddleBottomPanel = new JPanel();
			rightMiddleBottomPanel.setLayout(new BoxLayout(rightMiddleBottomPanel, BoxLayout.Y_AXIS));
			rightMiddlePanel.add(rightMiddleBottomPanel);
			
			
			leftPanel.setLayout(new BorderLayout());
			leftBottomPanel = addPlane(pladsArray1, panelList1, rightMiddleTopPanel);
			leftPanel.add(leftBottomPanel, BorderLayout.CENTER);
			leftTopPanel = new JPanel();
			leftTopPanel.setLayout(new BoxLayout(leftTopPanel, BoxLayout.Y_AXIS));
			leftPanel.add(leftTopPanel, BorderLayout.NORTH);
			leftPanelTitle = new JLabel("Udrejse");
            leftPanelTitle.setFont(new Font("String", Font.BOLD, 16));
			leftTopPanel.add(leftPanelTitle);
			
			//Indsætter venstre flyvinge
			JPanel leftLeftPanel = new JPanel();
			leftPanel.add(leftLeftPanel, BorderLayout.WEST);
			ImageIcon imageLogo1 = new ImageIcon(getClass().getResource("png/venstreFlyvinge3.jpg"));
			JLabel logoLabel1 = new JLabel(imageLogo1);
			leftLeftPanel.add(logoLabel1);
			
			//Højre flyvinge
			JPanel leftRightPanel = new JPanel();
			leftPanel.add(leftRightPanel, BorderLayout.EAST);
			ImageIcon imageLogo2 = new ImageIcon(getClass().getResource("png/vinge_right3.jpg"));
			JLabel logoLabel2 = new JLabel(imageLogo2);
			leftRightPanel.add(logoLabel2);
			
			//Flyfront
			JPanel leftTop2Panel = new JPanel();
			leftTop2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			leftTopPanel.add(leftTop2Panel);
			ImageIcon imageLogo3 = new ImageIcon(getClass().getResource("png/fly_front4.jpg"));
			JLabel logoLabel3 = new JLabel(imageLogo3);
			leftTop2Panel.add(logoLabel3);
			
			//Fly bagende
			JPanel leftBottomBottomPanel = new JPanel();
			leftPanel.add(leftBottomBottomPanel, BorderLayout.SOUTH);
			ImageIcon imageLogo4 = new ImageIcon(getClass().getResource("png/fly_bag3.jpg"));
			JLabel logoLabel4 = new JLabel(imageLogo4);
			leftBottomBottomPanel.add(logoLabel4);
			
			//Middle panel
			middlePanel.setLayout(new BorderLayout());
			middlePanel.setSize(300, 720);
			middleBottomPanel = addPlane(pladsArray2, panelList2, rightMiddleMiddlePanel);
			middlePanel.add(middleBottomPanel, BorderLayout.CENTER);
			middleTopPanel = new JPanel();
			middleTopPanel.setLayout(new BoxLayout(middleTopPanel, BoxLayout.PAGE_AXIS));
			middlePanel.add(middleTopPanel, BorderLayout.NORTH);
			centerPanelTitle = new JLabel("Hjemrejse");
            centerPanelTitle.setFont(new Font("String", Font.BOLD, 16));
			middleTopPanel.add(centerPanelTitle);
			
			//Indsætter venstre flyvinge
			JPanel middleLeftPanel = new JPanel();
			middlePanel.add(middleLeftPanel, BorderLayout.WEST);
			ImageIcon imageLogo5 = new ImageIcon(getClass().getResource("png/venstreFlyvinge3.jpg"));
			JLabel logoLabel5 = new JLabel(imageLogo5);
			middleLeftPanel.add(logoLabel5);
			
			//Højre flyvinge
			JPanel middleRightPanel = new JPanel();
			middlePanel.add(middleRightPanel, BorderLayout.EAST);
			ImageIcon imageLogo6 = new ImageIcon(getClass().getResource("png/vinge_right3.jpg"));
			JLabel logoLabel6 = new JLabel(imageLogo6);
			middleRightPanel.add(logoLabel6);
			
			//Flyfront
			JPanel middleTop2Panel = new JPanel();
			middleTop2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			middleTopPanel.add(middleTop2Panel);
			ImageIcon imageLogo7 = new ImageIcon(getClass().getResource("png/fly_front4.jpg"));
			JLabel logoLabel7 = new JLabel(imageLogo7);
			middleTop2Panel.add(logoLabel7);
			
			//Fly bagende
			JPanel middleBottomBottomPanel = new JPanel();
			middlePanel.add(middleBottomBottomPanel, BorderLayout.SOUTH);
			ImageIcon imageLogo8 = new ImageIcon(getClass().getResource("png/fly_bag3.jpg"));
			JLabel logoLabel8 = new JLabel(imageLogo8);
			middleBottomBottomPanel.add(logoLabel8);
			
			JButton next = new JButton("Næste");
			rightBottomPanel.add(next);
			next.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Kundeoplysninger ko = new Kundeoplysninger(pladsArray1.getReservations(), pladsArray2.getReservations());
					
				}
			});
			
			
			
			
			
			
			
//			int counting=0;
//			for(int i = 0; i<rows; i++) {
//				for(int j = 0; j<cols; j++) {
//					counting++;
//					Plads p = new Plads(counting, false, false, this);
//					leftBottomPanel.add(p);
//					panelList.add(p);
//				}
//			}
			
			
			this.setSize(1200,720);
			this.setResizable(false);
			//this.pack();
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
		panel.validate();
		panel.repaint();
	}
	
	public void addReservationLabels(PladsArray pa, JPanel labelPanel) {
		labelPanel.removeAll();
		repaint();
		if(labelPanel == rightMiddleTopPanel) {
			udrejseLabel = new JLabel("Udrejse");
			udrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			labelPanel.add(udrejseLabel);
		}
		else if(labelPanel == rightMiddleMiddlePanel) {
			hjemrejseLabel = new JLabel("Hjemrejse");
	        hjemrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			labelPanel.add(hjemrejseLabel);
		}
		for(int i=0; i<pa.getReservations().size(); i++) {
			addSeatLabel(pa.getReservations().get(i), labelPanel);
		}
	}
	
	public void addReservationLabels(PladsArray pa, PladsArray pa2, JPanel labelPanel) {
		labelPanel.removeAll();
		repaint();
		for(int i=0; i<pa.getReservations().size(); i++) {
			addSeatLabel(pa.getReservations().get(i), labelPanel);
		}
		for(int i=0; i<pa2.getReservations().size(); i++) {
			addSeatLabel(pa2.getReservations().get(i), labelPanel);
		}
	}
	
	
	public JPanel addPlane(PladsArray pladsArray, ArrayList<Plads> panelList, JPanel labelPanel) throws SQLException {		
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols, 2, 2));
		int counting=0;
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				counting++;
				Plads p = new Plads(counting, false, false, this, pladsArray, labelPanel);
																				//p.addMouseListener(new MouseListener());
				panel.add(p);
				panelList.add(p);
			}
		}
		setNames(panelList, pladsArray);
		
		return panel;
	}
	
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
