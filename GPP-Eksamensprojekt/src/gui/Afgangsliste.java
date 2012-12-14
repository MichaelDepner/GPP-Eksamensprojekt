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
	
	//holder styr på popup vinduet
	private int popupId;
	
	private AfgangSøgning as, as2;
	ArrayList<Departure> departures, departures2;
	
	//denne konstruktor kaldes ved tur/retur eller periodesøgning enkeltrejse. Boolean 'period' angiver, hvilken.
    public Afgangsliste(Date departureDate, Date arrivalDate, String departureAirport, String arrivalAirport, boolean period) throws SQLException {
    	
    	
    	if(!period) {
    		turRetur = true;
    		//Opretter AfgangSøgning
        	as = new AfgangSøgning(departureDate, departureAirport, arrivalAirport);
        	departures = as.getDepartures();
        	
        	as2 = new AfgangSøgning(arrivalDate, arrivalAirport, departureAirport);
        	departures2 = as2.getDepartures();
        	makeWindow(true);
    	} else {
    		turRetur = false;
    		as = new AfgangSøgning(departureDate, arrivalDate, departureAirport, arrivalAirport);
    		departures = as.getDepartures();
    		makeWindow(false);
    	}
    	
    	
        setTitle("Afgange");
    }
    
    //denne konstruktor kaldes ved enkeltrejser
    public Afgangsliste(Date departureDate, String departureAirport, String arrivalAirport) throws SQLException {
    	
    	turRetur = false;
    	//opretter afgangsøgning
    	as = new AfgangSøgning(departureDate, departureAirport, arrivalAirport);
    	departures = as.getDepartures();
    	
    	//laver afgangssøgninger 5 dage tilbage
    	//for()
    	
    	setTitle("Afgange");
    	makeWindow(false);
    	
    	//addTab(jtp, as);
    }
    
    //denne konstruktor kaldes ved periode-søgning tur-retur
    public Afgangsliste(Date departureDate, Date departureDate2, Date arrivalDate, Date arrivalDate2,
    		String departureAirport, String arrivalAirport) throws SQLException {
    	turRetur = true;
    	//opretter afgangssøgninger
    	as = new AfgangSøgning(departureDate, departureDate2, departureAirport, arrivalAirport);
    	as2 = new AfgangSøgning(arrivalDate, arrivalDate2, arrivalAirport, departureAirport);
    	departures = as.getDepartures();
    	departures2 = as2.getDepartures();
    	makeWindow(true);
    }
    
    //Opretter en fane, og indsætter panel og table
    public void addTab(JTabbedPane p, AfgangSøgning as) {
    	try {
			JPanel panel = new JPanel();
			//Tilføjer en fane til den angivne JTabbedPane, sætter dato og indsætter panel
	    	p.addTab(as.getFormattedDate(), panel);
	    	JTable table = departureTable(as.getDepartures());
	    	//Tilføjer table til panel
	    	panel.add(table.getTableHeader());
	    	panel.add(table);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Fejl i kommunikation med serveren. Er internettet nede?");
			e.printStackTrace();
		}
    }

    //Opretter vinduet
    public void makeWindow(boolean turRetur) {
    	//Laver vores fane-vinduer
    	jtp = new JTabbedPane();
    	if(turRetur) {
    		jtp2 = new JTabbedPane();
    	}
    	
    	//Sætter BorderLayout i contentPane, og laver panels indeni
    	getContentPane().setLayout(new BorderLayout());
    	//CENTER
    	JPanel panelCenter = new JPanel();
    	getContentPane().add(panelCenter, BorderLayout.CENTER);
    	panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));

    	//SOUTH
    	JPanel panelSouth = new JPanel();
    	getContentPane().add(panelSouth, BorderLayout.SOUTH);
    	panelSouth.setLayout(new FlowLayout());

    	//Sætter fane-vinduerne ind i layouts'ene
    	panelCenter.add(jtp);
    	if(turRetur) {
    		panelCenter.add(jtp2);
    	}
    	
    	

    	//tilføjer tabs til departures fundet før den valgte dato
    	//addTabsBeforeDate(as, jtp);

    	//Opretter panels
    	jp1Udrejse = new JPanel();
    	//jp1Udrejse.setLayout(new BorderLayout());
    	if(turRetur) {
    		jp1Hjemrejse = new JPanel();
    	}
    	
    	//jp1Hjemrejse.setLayout(new BorderLayout());

    	//labelUdrejse = new JLabel("Udrejsedato og lufthavne");
    	if(departures.size() != 0) {
    		labelUdrejse = new JLabel(departures.get(0).getDepartureAirportName()+" - "+departures.get(0).getArrivalAirportName());
    	} else {
    		labelUdrejse = new JLabel("Ingen afgange fundet");
    	}
    	//labelUdrejse.setText(d.getDepartureDate()+ " " + d.getDepartureAirportName() 
    	//											+ " " + d.getArrivalAirportName());
    	labelUdrejse.setFont(new Font("String", Font.BOLD, 18));
    	jp1Udrejse.add(labelUdrejse);

    	//Skal evt. rykkes ned til table-metode
    	departureTable = table(jp1Udrejse, departures);
    	//jp1Udrejse.add(departureTable, BorderLayout.CENTER);
    	jp1Udrejse.add(departureTable);
    	
    	//Tilføjer panel jp1Udrejse til jtp
    	if(d != null) {
    		jtp.addTab(departures.get(0).getDepartureDate()+" - "+departures.get(departures.size()-1).getDepartureDate(), jp1Udrejse);
    	} else {
    		jtp.addTab("Ingen afgange fundet", jp1Udrejse);
    	}
    	

    	//tilføjer tabs til departures fundet efter den valgte dato
    	//addTabsAfterDate(as, jtp);

    	if(turRetur) {
    		if(departures2.size() != 0) {
    			labelHjemrejse = new JLabel(departures2.get(0).getDepartureAirportName()+" - "+departures2.get(0).getArrivalAirportName());
    		} else {
    			labelHjemrejse = new JLabel("Ingen afgange fundet");
    		}
    		//labelHjemrejse = new JLabel("Udrejsedato og lufthavne");
    		//labelHjemrejse.setText(d.getDepartureDate()+ " " + d.getDepartureAirportName() 
    		//												+ d.getArrivalAirportName());
    		labelHjemrejse.setFont(new Font("String", Font.BOLD, 18));
    		jp1Hjemrejse.add(labelHjemrejse);

    		//Skal evt. rykkes ned til table-metode
    		arrivalTable = table(jp1Hjemrejse, departures2);
    		//jp1Hjemrejse.add(arrivalTable);
    		jp1Hjemrejse.add(arrivalTable);
    		//github.com/Mibias/GPP-Eksamensprojekt.git

    		//Tilføjer panel jp1Hjemrejse til jtp
    		if(d != null) {
    			jtp2.addTab(departures2.get(0).getDepartureDate()+" - "+departures2.get(departures2.size()-1).getDepartureDate(), jp1Hjemrejse);
    		} else {
    			jtp2.addTab("Ingen afgange fundet", jp1Hjemrejse);
    		}
    	}

    	//Laver en next-knap
    	next = new JButton("Næste");
    	panelSouth.add(next);
    	next.addActionListener(new Listener());

    	/*
        //Scrollbar
        JScrollPane scrollUdrejse = new JScrollPane(jp1Udrejse);
        jtp.add(scrollUdrejse);
        JScrollPane scrollHjemrejse = new JScrollPane(jp1Hjemrejse);
        jtp2.add(scrollHjemrejse);*/

    	setPreferredSize(new Dimension(640, 700));
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	pack();
    	setVisible(true);
    }

    //Sætter bredden for det angivne table
    private void setWidth(JTable table, int i, int j) {
    	column = table.getColumnModel().getColumn(i);

    	column.setMinWidth(j);
    	column.setMaxWidth(j);
    	column.setPreferredWidth(j);
    }

    //Laver table for afgange
    private JTable departureTable(ArrayList<Departure> departures) {
    	final ArrayList<Departure> dp = departures;
    	DefaultTableModel model = new DefaultTableModel() {
    		public boolean isCellEditable(int row, int column) {
    			return false;
    		}
    	};  
    	final JTable table = new JTable(model); 


    	//overskriver metoden moveColumn, så man ikke længere kan rykke rundt på dem.
    	table.setColumnModel(new DefaultTableColumnModel() {  
    		public void moveColumn(int columnIndex, int newIndex) { 
    		}   
    	});  

    	//Laver columns
    	model.addColumn("Dato");
    	model.addColumn("Pris"); 
    	model.addColumn("Afrejse - Ankomst"); 
    	model.addColumn("Rejsetid");
    	model.addColumn("Lufthavne");
    	model.addColumn("DepartureID");

    	//Tilføjer rejser
    	for(int i=0; i<departures.size(); i++) {
    		d = dp.get(i);
    		String date = d.getDepartureDate();
    		String price = d.getPrice()+"";
    		String time = d.getDepartureTime()+" - "+d.getArrivalTime();
    		//TODO tilføj udregning af rejsetid
    		String travelTime = d.getTravelTime();
    		String fromTo = d.getDepartureAirportAbbrevation()+" - "+d.getArrivalAirportAbbrevation();
    		//String seats = " ";//d.getSeats();
    		int id = d.getDepartureId();

    		//Har fjernet seats
    		model.addRow(new Object[]{date,price,time,travelTime,fromTo,id+""});	
    	}

    	//sætter bredden af kolonner
    	setWidth(table, 0, 120);
    	setWidth(table, 1, 90);
    	setWidth(table, 2, 120);
    	setWidth(table, 3, 100);
    	setWidth(table, 4, 90);



    	//tiføjer mouselistener
    	table.addMouseListener(new MouseAdapter() {
    		
    		public void mouseClicked(MouseEvent e) {
    			int row = table.rowAtPoint(e.getPoint());
    			int id = dp.get(row).getDepartureId()+1;
    			try {
    				//hvis pb eksisterer, så skift den til andet afgangsid. Ellers, opret nyt preview
    				if(pb != null) {
    					pb.changePreview(id);
    				} else {
    					pb = new Pladsbooking(id, false);
        				pb.setLocation(700, 1);
    				}
    			} catch (SQLException e1) {
    				JOptionPane.showMessageDialog(table, "Fejl i kommunikation med serveren. Er internettet nede?");
    				e1.printStackTrace();
    			}
    		}
    		
//    		public void mouseEntered(MouseEvent e) {
//    			int row = table.rowAtPoint(e.getPoint());
//    			int id = dp.get(row).getDepartureId()+1;
//    			try {
//    				pb = new Pladsbooking(id-1, false);
//    				popupId = id-1;
//    			} catch (SQLException e1) {
//    				// TODO Auto-generated catch block
//    				System.out.println("Something sql went wrong.");
//    				e1.printStackTrace();
//    			}
//
//
//    		}

//    		public void mouseExited(MouseEvent e) {
//    			pb.close();
//    		}
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
    
    private Afgangsliste getThis() {
    	return this;
    }


    private class Listener implements ActionListener {
    	public void actionPerformed(ActionEvent event){
    		if(event.getSource() == nextWeek) {
    			System.out.println("Næste uge");
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
    						pb.dispose();
    						Pladsbooking pb = new Pladsbooking(id1, id2, getThis());
    					} catch (SQLException e) {
    						JOptionPane.showMessageDialog(departureTable, "Fejl i kommunikation med serveren. Er internettet nede?");
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
    						//dispose preview-pladsbookingen der allerede er åben
    						pb.dispose();
    						Pladsbooking pb = new Pladsbooking(id1, getThis());
    					} catch (SQLException e) {
    						JOptionPane.showMessageDialog(departureTable, "Fejl i kommunikation med serveren. Er internettet nede?");
    						e.printStackTrace();
    					}
    				}
    			}
    		}


    	}
    }
}
