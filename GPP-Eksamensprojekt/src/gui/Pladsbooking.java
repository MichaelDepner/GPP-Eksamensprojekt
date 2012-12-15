package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.Border;

import logic.Booking;
import logic.Customer;
import logic.Database;
import logic.Departure;
import logic.Plads;
import logic.PladsArray;

/**
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class Pladsbooking extends JFrame {
	private PladsArray pladsArray1, pladsArray2;
	private int departureId, departureId2;
	private ArrayList<Integer> emptyColumns1;
	private ArrayList<Integer> emptyColumns2 = new ArrayList<>();
	private ArrayList<Plads> panelList1 = new ArrayList<>();
	private ArrayList<Plads> panelList2 = new ArrayList<>();
	private ArrayList<Integer> oldReservations;
	private Boolean booking, multipleDepartures;
	private JPanel rightMiddleTopPanel, rightMiddleMiddlePanel;
	private ArrayList<JPanel> labelList = new ArrayList<>();
	private JLabel udrejseLabel, hjemrejseLabel;
	private boolean turRetur, rebooking;
	private Booking b;
	private Afgangsliste al;
	
	private Departure d1, d2;
	
	//Denne constructor kaldes når vi vil lave et preview-vindue.
	public Pladsbooking(int departureId, Boolean booking) throws SQLException {
		this.booking = booking;
		this.departureId = departureId;
		pladsArray1 = new PladsArray(departureId);
		emptyColumns1 = pladsArray1.getEmptyCols();
		
		if(!booking) {
			makePreviewWindow(pladsArray1);
			reservations(panelList1, pladsArray1);
		} else {
			makeBookingWindow(true);
		}
	}
	
	//Hvis vi skal ændre i en allerede eksisterende booking
	public Pladsbooking(int departureId, Booking b, Customer c) throws SQLException {
		this.b = b;
		String oldReservation = b.getSeats();
		rebooking = true;
		pladsArray1 = new PladsArray(departureId);
		emptyColumns1 = pladsArray1.getEmptyCols();
		turRetur = false;
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		d1 = db.queryGetDeparture(departureId);
		db.close();
		makeBookingWindow(false);
		reservations(panelList1, pladsArray1);
		
		//Deler oldReservation string op i et array
		oldReservations = new ArrayList<Integer>();
		String[] strArray = oldReservation.split(" ");
		for(int i=0; i<strArray.length; i++) {
			oldReservations.add(Integer.parseInt(strArray[i]));
		}
		
		//Laver de gamle sæder 'markeret' i stedet for reserveret
		for(int i=0; i<oldReservations.size(); i++) {
			panelList1.get(oldReservations.get(i)).mark();
		}
	}
	
	//Denne constructor kaldes ved tur-retur bookinger
	public Pladsbooking(int departureId1, int departureId2, Afgangsliste al) throws SQLException {
		turRetur = true;
		this.al = al;
		//Finder al information om afgangene og gemmer dem i et Departure objekt
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		d1 = db.queryGetDeparture(departureId1);
		d2 = db.queryGetDeparture(departureId2);
		db.close();
		
		booking = true;
		this.departureId = departureId1;
		this.departureId2 = departureId2;
		pladsArray1 = new PladsArray(departureId1);
		pladsArray2 = new PladsArray(departureId2);
		emptyColumns1 = pladsArray1.getEmptyCols();
		emptyColumns2 = pladsArray2.getEmptyCols();
		
		makeBookingWindow(true);
		reservations(panelList1, pladsArray1);
		reservations(panelList2, pladsArray2);
	}
	
	//Denne constructor kaldes ved enkelt-bookinger
	public Pladsbooking(int departureId1, Afgangsliste al) throws SQLException {
		this.al = al;
		turRetur = false;
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		d1 = db.queryGetDeparture(departureId1);
		db.close();
		
		booking = true;
		this.departureId = departureId1;
		pladsArray1 = new PladsArray(departureId1);
		emptyColumns1 = pladsArray1.getEmptyCols();
		makeBookingWindow(false);
		reservations(panelList1, pladsArray1);
	}

	//Når man hurtigt skal bruge information fra et pladsarray - uheldigvis bliver en del af
	//logikken udført i denne klasse, så den skal kaldes hvis man vil udfylde et pladsarray
	//ordentligt. Klart en forbedringsmulighed.
	public PladsArray getPladsArray() {
		return pladsArray1;
	}
	
	//Erstatter det forrige preview vindue, hvis det er oprettet
	public void changePreview(int departureId) throws SQLException {
		panelList1.clear();
		this.getContentPane().setVisible(false);
		this.departureId = departureId;
		pladsArray1 = new PladsArray(departureId);
		emptyColumns1 = pladsArray1.getEmptyCols();
		makePreviewWindow(pladsArray1);
		reservations(panelList1, pladsArray1);
		this.getContentPane().setVisible(true);
	}
	
	public void makeInvisible() {
		this.getContentPane().setVisible(false);
	}
	
	public void makeVisible() {
		setTitle("Pladsbooking");
		this.getContentPane().setVisible(true);
	}
	
	//
	private void reservations(ArrayList<Plads> panelList, PladsArray pladsArray) throws SQLException {
		ArrayList<Integer> reservations = pladsArray.findReservations();
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		ArrayList<Integer> emptyColumns = pladsArray.getEmptyCols();
		
		//
		for(int i=0; i<reservations.size(); i++) {
			int r = reservations.get(i);
			panelList.get(r).changeReservation();
		}
		
		//
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
	
	//Lave preview vindue
	private void makePreviewWindow(PladsArray pa) throws SQLException {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		int rows = pa.getRows();
		int cols = pa.getCols();
		
		Container contentPane = this.getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new GridLayout(rows, cols, 2, 2));
		
		//Fylder arrayet med ureserverede pladser
		int counting=0;
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				counting++;
				Plads p = new Plads(counting, this);
				//Plads p = new Plads(counting, false, false, this);
				contentPane.add(p);
				panelList1.add(p);
			}
		}
		
		this.setPreferredSize(new Dimension(cols*30, rows*30));
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	//Laver vinduet
	public void makeBookingWindow(final boolean turRetur) throws SQLException {
		JPanel leftPanel, middlePanel, centerPanel, rightPanel;
		JPanel leftBottomPanel, leftTopPanel, middleBottomPanel, middleTopPanel, rightTopPanel, rightBottomPanel;
		JLabel leftPanelTitle, centerPanelTitle, rightPanelTitle;
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = this.getContentPane();
		
		
		multipleDepartures = true;
		if(multipleDepartures) {
			//Opretter centerPanel, hvori vi lægger left-, middle- og rightPanel
			centerPanel = new JPanel();
			centerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
			contentPane.add(centerPanel);
			
			//LeftPanel
			leftPanel = new JPanel();
			leftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			leftPanel.setLayout(new BorderLayout());
			centerPanel.add(leftPanel);
			
			//Tilføjer flypladserne i midten af leftPanel
			leftBottomPanel = addPlane(d1, pladsArray1, panelList1, rightMiddleTopPanel);
			leftPanel.add(leftBottomPanel, BorderLayout.CENTER);
			
			//Panel til leftPanel NORTH
			leftTopPanel = new JPanel();
			leftTopPanel.setLayout(new BoxLayout(leftTopPanel, BoxLayout.Y_AXIS));
			leftPanel.add(leftTopPanel, BorderLayout.NORTH);
			
			//Tilføjer titel til leftTopPanel
			leftPanelTitle = new JLabel("Udrejse");
            leftPanelTitle.setFont(new Font("String", Font.BOLD, 16));
			leftTopPanel.add(leftPanelTitle);
			
			//Flyfront til leftTopPanel
			JPanel leftTop2Panel = new JPanel();
			leftTop2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			leftTopPanel.add(leftTop2Panel);
			ImageIcon imageLogo3 = new ImageIcon(getClass().getResource("png/fly_front4.jpg"));
			JLabel logoLabel3 = new JLabel(imageLogo3);
			leftTop2Panel.add(logoLabel3);
			
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
			
			//Fly bagende
			JPanel leftBottomBottomPanel = new JPanel();
			leftPanel.add(leftBottomBottomPanel, BorderLayout.SOUTH);
			ImageIcon imageLogo4 = new ImageIcon(getClass().getResource("png/fly_bag3.jpg"));
			JLabel logoLabel4 = new JLabel(imageLogo4);
			leftBottomBottomPanel.add(logoLabel4);
			
			//MiddlePanel
			middlePanel = new JPanel();
			middlePanel.setLayout(new BorderLayout());
			middlePanel.setSize(300, 720);
			centerPanel.add(middlePanel);
			
			//Tilføjer flyets pladser i middlePanel CENTER, hvis det er tur/retur
			if(turRetur) {
				middleBottomPanel = addPlane(d2, pladsArray2, panelList2, rightMiddleMiddlePanel);
				middlePanel.add(middleBottomPanel, BorderLayout.CENTER);
			}

			//Panel til middlePanel NORTH
			middleTopPanel = new JPanel();
			middleTopPanel.setLayout(new BoxLayout(middleTopPanel, BoxLayout.PAGE_AXIS));
			middlePanel.add(middleTopPanel, BorderLayout.NORTH);
			
			//Titel til middlePanel
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
			final JPanel middleRightPanel = new JPanel();
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
			
			//RightPanel
			rightPanel = new JPanel();
			rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			rightPanel.setLayout(new BorderLayout());
			rightPanel.setSize(300, 720);
			centerPanel.add(rightPanel);
			
			//Label til rightPanel
			JLabel udrejseLabel = new JLabel("Udrejse");
            udrejseLabel.setFont(new Font("String", Font.BOLD, 14));
            rightPanel.add(udrejseLabel);
			
			//RightTopPanel til rightPanel
			rightTopPanel = new JPanel();
			rightPanel.add(rightTopPanel, BorderLayout.NORTH);
			
			//Titel til rightTopPanel
			rightPanelTitle = new JLabel("Info");
            rightPanelTitle.setFont(new Font("String", Font.BOLD, 16));
            rightTopPanel.add(rightPanelTitle);
			
			//RightMiddlePanel til rightPanel
			JPanel rightMiddlePanel = new JPanel();
			rightMiddlePanel.setLayout(new GridLayout(3, 1, 5, 5));
			rightPanel.add(rightMiddlePanel, BorderLayout.CENTER);
			
			//Til rightMiddlePanel
			rightMiddleTopPanel = new JPanel();
			rightMiddleTopPanel.setLayout(new BoxLayout(rightMiddleTopPanel, BoxLayout.Y_AXIS));
			rightMiddleTopPanel.add(udrejseLabel);
			rightMiddlePanel.add(rightMiddleTopPanel);
			
			//Til rightMiddlePanel
			rightMiddleMiddlePanel = new JPanel();
			rightMiddleMiddlePanel.setLayout(new BoxLayout(rightMiddleMiddlePanel, BoxLayout.Y_AXIS));
			rightMiddlePanel.add(rightMiddleMiddlePanel);
			
			//Hjemrejse-label
			JLabel hjemrejseLabel = new JLabel("Hjemrejse");
            hjemrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			rightMiddleMiddlePanel.add(hjemrejseLabel);
			
			if(!turRetur) {
				rightMiddleMiddlePanel.setVisible(false);
			}
			
			//Til rightPanel
			rightBottomPanel = new JPanel();
			rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);
			
			//Til rightMiddlePanel
			JPanel rightMiddleBottomPanel = new JPanel();
			rightMiddleBottomPanel.setLayout(new BoxLayout(rightMiddleBottomPanel, BoxLayout.Y_AXIS));
			rightMiddlePanel.add(rightMiddleBottomPanel);
			
			//'Næste'-knappen
			final JButton next = new JButton("Næste");
			rightBottomPanel.add(next);
			next.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(turRetur) {
						if(pladsArray1.getReservations().size() > 0 && 
												pladsArray2.getReservations().size() == 
												pladsArray1.getReservations().size()) {
							Kundeoplysninger ko = new Kundeoplysninger(pladsArray1.getReservations(),
												pladsArray2.getReservations(), d1, d2, getThis());
						} else {
							JOptionPane.showMessageDialog(middleRightPanel, 
											"Du skal vælge mindst 1 plads, og samme antal i begge fly!");
						}
						
					} else if(rebooking) {
						try {
							if(oldReservations.size() != pladsArray1.getReservations().size()) {
								JOptionPane.showMessageDialog(middleRightPanel, 
											"Du skal vælge "+oldReservations.size()+" pladser i alt!");
							} else {
								String seatNums1 = "";
								//lav string med id på reserverede pladser ud
								for(int j=0; j<pladsArray1.getReservations().size(); j++) {
									int num = pladsArray1.getReservations().get(j).getSeatNo();
									seatNums1 = num+" "+seatNums1;
								}

								Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
								db.queryUpdateBookingSeats(b.getId(), seatNums1);

								JOptionPane.showMessageDialog(returnMe(), "Opdatering udført. " +
												"For at se data bliver du nødt til at lave en ny søgning.");
								dispose();
							}
							
							String seatNums1 = "";
							//lav string med id på reserverede pladser ud
		    				for(int j=0; j<pladsArray1.getReservations().size(); j++) {
		    					int num = pladsArray1.getReservations().get(j).getSeatNo();
		    					seatNums1 = num+" "+seatNums1;
		    				}		    				
		    				
							Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
							db.queryUpdateBookingSeats(b.getId(), seatNums1);
							
							JOptionPane.showMessageDialog(returnMe(), "Opdatering udført. " +
											"For at se data bliver du nødt til at lave en ny søgning.");
							dispose();
							
							
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(next, "Fejl i kommunikation med serveren. " +
											"Er internettet nede?");
							System.out.println("Something went wrong when editing the booking");
							e.printStackTrace();
						}
					} else {
						if(pladsArray1.getReservations().size() > 0) {
							Kundeoplysninger ko = new Kundeoplysninger(pladsArray1.getReservations(),
																					d1, getThis());
						} else {
							JOptionPane.showMessageDialog(middleRightPanel, "Du skal vælge mindst 1 plads!");
						}
					}
				}
			});

			if(!turRetur) {
				middlePanel.setVisible(false);
			}

			
			this.setSize(1100,700);
			this.setResizable(false);
			this.setVisible(true);
		}
	}

	public Pladsbooking getThis() {
		return this;
	}
	
	public void removeMe() {
		al.dispose();
		this.dispose();
	}
	
	public JFrame returnMe() {
		return this;
	}
	
	//Tildeler labels til sæderne
	public void addSeatLabel(Plads p, JPanel panel) {
		JLabel label = new JLabel(p.toString());
		panel.add(label);
		panel.validate();
		panel.repaint();
	}
	
	//Tilføjer oversigt over valgte pladser til rightPanel
	public void addReservationLabels(PladsArray pa, JPanel labelPanel) {
		labelPanel.removeAll();
		repaint();
		//Labels til udrejsen
		if(labelPanel == rightMiddleTopPanel) {
			udrejseLabel = new JLabel("Udrejse");
			udrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			labelPanel.add(udrejseLabel);
		}
		//Labels til hjemrejsen
		else if(labelPanel == rightMiddleMiddlePanel) {
			hjemrejseLabel = new JLabel("Hjemrejse");
	        hjemrejseLabel.setFont(new Font("String", Font.BOLD, 14));
			labelPanel.add(hjemrejseLabel);
		}
		for(int i=0; i<pa.getReservations().size(); i++) {
			addSeatLabel(pa.getReservations().get(i), labelPanel);
		}
	}
	
	//
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
	
	//Oprettelse af flyets sæder
	public JPanel addPlane(Departure d, PladsArray pladsArray, ArrayList<Plads> panelList, JPanel labelPanel) throws SQLException {	
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, cols, 2, 2));
		int counting=0;
		for(int i = 0; i<rows; i++) {
			for(int j = 0; j<cols; j++) {
				
				Plads p = new Plads(counting, false, false, this, pladsArray, labelPanel, d.getPrice());
				counting++;
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
	
	//Skaber alle sædernes navne
	public void setNames(ArrayList<Plads> pladser, PladsArray pladsArray) throws SQLException {
		int rows = pladsArray.getRows();
		int cols = pladsArray.getCols();
		ArrayList<Integer> emptyCols = pladsArray.getEmptyCols();
		HashMap nameMap = new HashMap<>();
		
		//Løber igennem kolonner, og tildeler dem et bogstav hvis ikke de er tomme (mellemgange)
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
	
	//Bogstaverne og rækkefølgen der skal tilføjes til rækkerne. Der kan ikke være mere
	//end 25 rækker i flyet - hvilket nok heller ikke kommer til at ske irl
	private String getCharForNumber(int i) {
	    char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	    if (i > 25) {
	        return null;
	    }
	    return Character.toString(alphabet[i]);
	}
}
