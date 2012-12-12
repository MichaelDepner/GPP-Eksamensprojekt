package gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import logic.*;

public class Afgangsliste extends JFrame {
	
	private JPanel jp1Udrejse, jp2Udrejse, jp3Udrejse, jp4Udrejse;
	private JPanel jp1Hjemrejse, jp2Hjemrejse, jp3Hjemrejse, jp4Hjemrejse;
	private JLabel labelHjemrejse, labelUdrejse;
	private JButton lastWeek, nextWeek, next;
	private TableColumn column;
	//private JTable table;
	private JTabbedPane jtp, jtp2;
	private Pladsbooking pb;
	private JTable departureTable, arrivalTable;
	private boolean turRetur;
	private Departure d;
	
	//holder styr p� popup vinduet
	private int popupId;
	
	private AfgangS�gning as, as2;
	ArrayList<Departure> departures, departures2;
	
    public Afgangsliste(Date departureDate, Date arrivalDate, String departureAirport, String arrivalAirport) throws SQLException {
    	
    	turRetur = true;
    	//Opretter AfgangS�gning
    	as = new AfgangS�gning(departureDate, departureAirport, arrivalAirport);
    	departures = as.getDepartures();
    	
    	as2 = new AfgangS�gning(arrivalDate, arrivalAirport, departureAirport);
    	departures2 = as2.getDepartures();
    	
        setTitle("Afgange");
        
        makeWindow(true);
    }
    
    public Afgangsliste(Date departureDate, String departureAirport, String arrivalAirport) throws SQLException {
    	
    	turRetur = false;
    	//opretter afgangs�gning
    	as = new AfgangS�gning(departureDate, departureAirport, arrivalAirport);
    	departures = as.getDepartures();
    	
    	setTitle("Afgange");
    	makeWindow(false);
    }

    public void makeWindow(boolean turRetur) {


    	//Laver vores fane-vinduer
    	jtp = new JTabbedPane();
    	if(turRetur) {
    		jtp2 = new JTabbedPane();
    	}
    	



    	//S�tter BorderLayout i contentPane, og laver panels indeni
    	getContentPane().setLayout(new BorderLayout());
    	//CENTER
    	JPanel panelCenter = new JPanel();
    	getContentPane().add(panelCenter, BorderLayout.CENTER);
    	panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));

    	//SOUTH
    	JPanel panelSouth = new JPanel();
    	getContentPane().add(panelSouth, BorderLayout.SOUTH);
    	panelSouth.setLayout(new FlowLayout());

    	//S�tter fane-vinduerne ind i layouts'ene
    	panelCenter.add(jtp);
    	if(turRetur) {
    		panelCenter.add(jtp2);
    	}
    	

    	//tilf�jer tabs til departures fundet f�r den valgte dato
    	//addTabsBeforeDate(as, jtp);

    	//Opretter panels
    	jp1Udrejse = new JPanel();
    	//jp1Udrejse.setLayout(new BorderLayout());
    	if(turRetur) {
    		jp1Hjemrejse = new JPanel();
    	}
    	
    	//jp1Hjemrejse.setLayout(new BorderLayout());

    	labelUdrejse = new JLabel("Udrejsedato og lufthavne");
    	//labelUdrejse.setText(d.getDepartureDate()+ " " + d.getDepartureAirportName() 
    	//											+ " " + d.getArrivalAirportName());
    	labelUdrejse.setFont(new Font("String", Font.BOLD, 14));
    	jp1Udrejse.add(labelUdrejse);

    	//Skal evt. rykkes ned til table-metode
    	departureTable = table(jp1Udrejse, departures);
    	//jp1Udrejse.add(departureTable, BorderLayout.CENTER);
    	jp1Udrejse.add(departureTable);
    	//Tilf�jer panel jp1Udrejse til jtp
    	jtp.addTab(d.getDepartureDate(), jp1Udrejse);

    	//tilf�jer tabs til departures fundet efter den valgte dato
    	//addTabsAfterDate(as, jtp);

    	if(turRetur) {
    		labelHjemrejse = new JLabel("Udrejsedato og lufthavne");
    		//labelHjemrejse.setText(d.getDepartureDate()+ " " + d.getDepartureAirportName() 
    		//												+ d.getArrivalAirportName());
    		labelHjemrejse.setFont(new Font("String", Font.BOLD, 14));
    		jp1Hjemrejse.add(labelHjemrejse);

    		//Skal evt. rykkes ned til table-metode
    		arrivalTable = table(jp1Hjemrejse, departures2);
    		//jp1Hjemrejse.add(arrivalTable);
    		jp1Hjemrejse.add(arrivalTable);
    		//github.com/Mibias/GPP-Eksamensprojekt.git

    		//Tilf�jer panel jp1Hjemrejse til jtp
    		jtp2.addTab("hej", jp1Hjemrejse);
    	}

    	//Laver en next-knap
    	next = new JButton("N�ste");
    	panelSouth.add(next);
    	next.addActionListener(new Listener());

    	/*
        //Scrollbar
        JScrollPane scrollUdrejse = new JScrollPane(jp1Udrejse);
        jtp.add(scrollUdrejse);
        JScrollPane scrollHjemrejse = new JScrollPane(jp1Hjemrejse);
        jtp2.add(scrollHjemrejse);*/

    	//Har tilf�jet scrollPane og layouts til jp1Udrejse og -hjemrejse.
    	//Nu vil den ikke vise navne i fanerne
    	//Evt. ryk overskifterne over fanerne
    	/**
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();

		/**
        label2 = new JLabel();
        label2.setText("Torsdag d. 29. november 2012");
        label3 = new JLabel();
        label3.setText("Fredag d. 30. november 2012");
        label4 = new JLabel();
        label4.setText("L�rdag d. 1. december 2012");

        jp2.add(label2);
        jp3.add(label3);
        jp4.add(label4);

        // Her tilf�jes titlerne til fanerne
        jtp.addTab("28/11", jp1);
        jtp.addTab("29/11", jp2);
        jtp.addTab("30/11", jp3);
        jtp.addTab("01/12", jp4); */

    	setPreferredSize(new Dimension(640, 460));
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	pack();
    	setVisible(true);
    }

    private void addTabsBeforeDate(AfgangS�gning as, JTabbedPane tp) {
    	for(int i=5; i>0; i--) {
    		if(as.getDeparturesBefore().size()>i) {
    			JPanel panel = new JPanel();
    			tp.addTab(as.getDeparturesBefore().get(i).getDepartureDate(), panel);
    		}
    	}
    }

    private void addTabsAfterDate(AfgangS�gning as, JTabbedPane tp) {
    	for(int i=0; i<5; i++) {
    		if(as.getDeparturesAfter().size()>i) {
    			JPanel panel = new JPanel();
    			tp.addTab(as.getDeparturesAfter().get(i).getDepartureDate(), panel);
    		}
    	}
    }


    private void setWidth(JTable table, int i, int j) {
    	//column = table.getColumnModel().getColumn(i);
    	column = table.getColumnModel().getColumn(i);

    	column.setMinWidth(j);
    	column.setMaxWidth(j);
    	column.setPreferredWidth(j);
    }

    private JTable departureTable(ArrayList<Departure> departures) {
    	final ArrayList<Departure> dp = departures;
    	DefaultTableModel model = new DefaultTableModel() {
    		public boolean isCellEditable(int row, int column) {
    			return false;
    		}
    	}; 
    	final JTable table = new JTable(model); 


    	//overskriver metoden moveColumn, s� man ikke l�ngere kan rykke rundt p� dem.
    	table.setColumnModel(new DefaultTableColumnModel() {  
    		public void moveColumn(int columnIndex, int newIndex) { 
    		}   
    	});  

    	//Laver columns
    	model.addColumn("Pris"); 
    	model.addColumn("Afrejse - Ankomst"); 
    	model.addColumn("Rejsetid");
    	model.addColumn("Lufthavne");
    	model.addColumn("Ledige pladser");
    	model.addColumn("DepartureId");

    	//Tilf�jer rejser
    	for(int i=0; i<departures.size(); i++) {
    		d = dp.get(i);
    		//TODO mangler at tilf�je priser i databasen
    		String price = d.getPrice()+"";
    		String time = d.getDepartureTime()+" - "+d.getArrivalTime();
    		//TODO tilf�j udregning af rejsetid
    		String travelTime = d.getTravelTime();
    		String fromTo = d.getDepartureAirportName()+" - "+d.getArrivalAirportName();
    		String seats = " ";//d.getSeats();
    		int id = d.getDepartureId();

    		//Har fjernet seats og id+""
    		model.addRow(new Object[]{price,time,travelTime,fromTo,seats,id+""});	
    	}

    	//s�tter bredden af kolonner
    	setWidth(table, 0, 90);
    	setWidth(table, 1, 120);
    	setWidth(table, 2, 100);
    	setWidth(table, 3, 120);
    	setWidth(table, 4, 90);


    	//tilf�jer actionlistener som �bner r�kkens afgang som pladss�gning
    	table.addMouseMotionListener(new MouseMotionAdapter() {
    		//    		public void mouseClicked(MouseEvent e) {
    		//    			int row = table.getSelectedRow();
    		//    			System.out.println("You've clicked on row "+row);
    		//    			
    		//    			int id = dp.get(row).getId()+1;
    		//    			System.out.println("Du har trykket p� en afgang med id: "+id);
    		//    			try {
    		//					Pladsbooking pb = new Pladsbooking(id-1);
    		//				} catch (SQLException e1) {
    		//					// TODO Auto-generated catch block
    		//					System.out.println("Something sql went wrong.");
    		//					e1.printStackTrace();
    		//				}
    		//    		}
    		
    		public void mouseEntered(MouseEvent e) {
    			int row = table.rowAtPoint(e.getPoint());
    			int id = dp.get(row).getDepartureId()+1;
    			try {
    				pb = new Pladsbooking(id-1, false);
    				popupId = id-1;
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				System.out.println("Something sql went wrong.");
    				e1.printStackTrace();
    			}


    		}

    		public void mouseMoved(MouseEvent e)
    		{
    			JTable aTable =  (JTable)e.getSource();
    			int itsRow = aTable.rowAtPoint(e.getPoint());

    			int id = dp.get(itsRow).getDepartureId();

    			if(id == popupId) {

    			} else if(pb == null) {
    				mouseEntered(e);
    			} else {
    				popupId = id;
    				try {
    					pb.makeInvisible();
    					pb.changePreview(id);
    					pb.makeVisible();
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				//mouseExited(e);
    				//mouseEntered(e);
    			}  
    		}

    		public void mouseExited(MouseEvent e) {
    			pb.close();
    		}
    	});

    	//tif�jer mouselistener
    	table.addMouseListener(new MouseAdapter() {
    		
    		public void mouseEntered(MouseEvent e) {
    			int row = table.rowAtPoint(e.getPoint());
    			int id = dp.get(row).getDepartureId()+1;
    			try {
    				pb = new Pladsbooking(id-1, false);
    				popupId = id-1;
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				System.out.println("Something sql went wrong.");
    				e1.printStackTrace();
    			}


    		}

    		public void mouseExited(MouseEvent e) {
    			pb.close();
    		}
    	});

    	return table;
    }

    private JTable table(JPanel panel, ArrayList<Departure> departures){
    	JTable table = departureTable(departures);
    	//Indhold af panel
    	panel.add(table.getTableHeader());
    	panel.add(table);
    	return table;
    }


    private class Listener implements ActionListener {
    	public void actionPerformed(ActionEvent event){
    		if(event.getSource() == nextWeek) {
    			System.out.println("N�ste uge");
    		} else if(event.getSource() == lastWeek) {
    			System.out.println("Forrige uge");

    		} else if(event.getSource() == next) {

    			if(turRetur) {
    				int id1 = departureTable.getSelectedRow();
    				int id2 = arrivalTable.getSelectedRow();

    				if (id1<0 ||  id2<0) {
    					System.out.println("Rows not selected properly!");
    				} else {
    					try {
    						String id11 = (String)departureTable.getValueAt(id1, 5);
    						id1 = Integer.parseInt(id11);

    						String id22 = (String)arrivalTable.getValueAt(id2, 5);
    						id2 = Integer.parseInt(id22);

    						System.out.println("Making pladsbooking with ID's "+id1+" and "+id2);
    						Pladsbooking pb = new Pladsbooking(id1, id2);
    					} catch (SQLException e) {
    						System.out.println("Something SQL went wrong!");
    						e.printStackTrace();
    					}
    				}
    			} else if(!turRetur) {
    				int id1 = departureTable.getSelectedRow();
    				if(id1<0) {
    					System.out.println("Rows not selected properly!");
    				} else {
    					try {
    						String id11 = (String)departureTable.getValueAt(id1, 5);
    						id1 = Integer.parseInt(id11);
    						Pladsbooking pb = new Pladsbooking(id1);
    					} catch (SQLException e) {
    						System.out.println("Something SQL went wrong!");
    						e.printStackTrace();
    					}
    				}
    			}
    		}


    	}
    }
}
