package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import logic.*;

/**
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kj�rsgaard og Nicoline Warming Larsen.
 *
 */

public class Afgangsliste extends JFrame {
	
	private JPanel jp1Udrejse, jp1Hjemrejse;
	private JLabel labelHjemrejse, labelUdrejse;
	private JButton next;
	private TableColumn column;
	private JTabbedPane jtp, jtp2;
	private Pladsbooking pb;
	private JTable departureTable, arrivalTable;
	private boolean turRetur;
	private Departure d;
	//Holder styr p� popup vinduet
	private int popupId;
	
	private AfgangS�gning as, as2;
	ArrayList<Departure> departures, departures2;
	
	//Denne constructor kaldes ved tur/retur eller periodes�gning enkeltrejse. 
	//Boolean 'period' angiver, hvilken.
    public Afgangsliste(Date departureDate, Date arrivalDate, String departureAirport, String arrivalAirport, boolean period) throws SQLException {
    	
    	if(!period) {
    		turRetur = true;
    		//Opretter AfgangS�gning for udrejsen
        	as = new AfgangS�gning(departureDate, departureAirport, arrivalAirport);
        	departures = as.getDepartures();
        	
        	//Opretter AfgangS�gning for hjemrejsen
        	as2 = new AfgangS�gning(arrivalDate, arrivalAirport, departureAirport);
        	departures2 = as2.getDepartures();
        	makeWindow(true);
    	} else {
    		turRetur = false;
    		as = new AfgangS�gning(departureDate, arrivalDate, departureAirport, arrivalAirport);
    		departures = as.getDepartures();
    		makeWindow(false);
    	}
        setTitle("Afgange");
    }
    
    //Denne constructor kaldes ved enkeltrejser
    public Afgangsliste(Date departureDate, String departureAirport, String arrivalAirport) throws SQLException {
    	
    	turRetur = false;
    	//Opretter afgangs�gning
    	as = new AfgangS�gning(departureDate, departureAirport, arrivalAirport);
    	departures = as.getDepartures();
    	
    	setTitle("Afgange");
    	makeWindow(false);
    }
    
    //Denne constructor kaldes ved periode-s�gning tur-retur
    public Afgangsliste(Date departureDate, Date departureDate2, Date arrivalDate, Date arrivalDate2,
    		String departureAirport, String arrivalAirport) throws SQLException {
    	turRetur = true;
    	//Opretter afgangss�gninger p� ud- og hjemrejsen
    	as = new AfgangS�gning(departureDate, departureDate2, departureAirport, arrivalAirport);
    	as2 = new AfgangS�gning(arrivalDate, arrivalDate2, arrivalAirport, departureAirport);
    	departures = as.getDepartures();
    	departures2 = as2.getDepartures();
    	makeWindow(true);
    }
    
    //Opretter en fane, og inds�tter panel og table
    public void addTab(JTabbedPane p, AfgangS�gning as) {
    	try {
			JPanel panel = new JPanel();
			//Tilf�jer en fane til den angivne JTabbedPane, s�tter dato og inds�tter panel
	    	p.addTab(as.getFormattedDate(), panel);
	    	JTable table = departureTable(as.getDepartures());
	    	//Tilf�jer table til panel
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

    	//Opretter panels
    	jp1Udrejse = new JPanel();
    	if(turRetur) {
    		jp1Hjemrejse = new JPanel();
    	}

    	if(departures.size() != 0) {
    		labelUdrejse = new JLabel(departures.get(0).getDepartureAirportName() +
    							" - " + departures.get(0).getArrivalAirportName());
    	} else {
    		labelUdrejse = new JLabel("Ingen afgange fundet");
    	}
    	labelUdrejse.setFont(new Font("String", Font.BOLD, 18));
    	jp1Udrejse.add(labelUdrejse);

    	departureTable = table(jp1Udrejse, departures);
    	jp1Udrejse.add(departureTable);
    	
    	//Tilf�jer panel jp1Udrejse til jtp
    	if(d != null) {
    		jtp.addTab(departures.get(0).getDepartureDate() +
    				" - "+departures.get(departures.size()-1).getDepartureDate(), jp1Udrejse);
    	} else {
    		jtp.addTab("Ingen afgange fundet", jp1Udrejse);
    	}

    	//Tilf�jer tabs til departures fundet efter den valgte dato
    	if(turRetur) {
    		if(departures2.size() != 0) {
    			labelHjemrejse = new JLabel(departures2.get(0).getDepartureAirportName()+
    									" - "+departures2.get(0).getArrivalAirportName());
    		} else {
    			labelHjemrejse = new JLabel("Ingen afgange fundet");
    		}
    		labelHjemrejse.setFont(new Font("String", Font.BOLD, 18));
    		jp1Hjemrejse.add(labelHjemrejse);

    		arrivalTable = table(jp1Hjemrejse, departures2);
    		jp1Hjemrejse.add(arrivalTable);

    		//Tilf�jer panel jp1Hjemrejse til jtp
    		if(departures2.size() > 0) {
    			jtp2.addTab(departures2.get(0).getDepartureDate()+" - "+
    								departures2.get(departures2.size()-1).
    								getDepartureDate(), jp1Hjemrejse);
    		} else {
    			jtp2.addTab("Ingen afgange fundet", jp1Hjemrejse);
    		}
    	}

    	//Laver en next-knap
    	next = new JButton("N�ste");
    	panelSouth.add(next);
    	next.addActionListener(new Listener());

    	setPreferredSize(new Dimension(640, 700));
    	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	pack();
    	setVisible(true);
    }
    
    public void dispose() {
    	if(pb != null) {
    		pb.dispose();
    	}
    	super.dispose();
    }

    //S�tter bredden for det angivne table
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


    	//Overskriver metoden moveColumn, s� man ikke l�ngere kan rykke rundt p� dem.
    	table.setColumnModel(new DefaultTableColumnModel() {  
    		public void moveColumn(int columnIndex, int newIndex) { 
    		}   
    	});  

    	//Laver kolonner
    	model.addColumn("Dato");
    	model.addColumn("Pris"); 
    	model.addColumn("Afrejse - Ankomst"); 
    	model.addColumn("Rejsetid");
    	model.addColumn("Lufthavne");
    	model.addColumn("DepartureID");

    	//Tilf�jer rejser
    	for(int i=0; i<departures.size(); i++) {
    		d = dp.get(i);
    		String date = d.getDepartureDate();
    		String price = d.getPrice()+"";
    		String time = d.getDepartureTime()+" - "+d.getArrivalTime();
    		String travelTime = d.getTravelTime();
    		String fromTo = d.getDepartureAirportAbbrevation()+" - "+d.getArrivalAirportAbbrevation();
    		int id = d.getDepartureId();

    		model.addRow(new Object[]{date,price,time,travelTime,fromTo,id+""});	
    	}

    	//S�tter bredden af kolonner
    	setWidth(table, 0, 120);
    	setWidth(table, 1, 90);
    	setWidth(table, 2, 120);
    	setWidth(table, 3, 100);
    	setWidth(table, 4, 90);

    	//Tif�jer mouselistener
    	table.addMouseListener(new MouseAdapter() {
    		
    		public void mouseClicked(MouseEvent e) {
    			int row = table.rowAtPoint(e.getPoint());
    			int id = dp.get(row).getDepartureId()+1;
    			try {
    				//Hvis pb eksisterer, s� skift den til andet afgangsid. 
    				//Ellers, opret nyt preview
    				if(pb != null) {
    					pb.changePreview(id);
    				} else {
    					pb = new Pladsbooking(id, false);
        				pb.setLocation(700, 1);
    				}
    			} catch (SQLException e1) {
    				JOptionPane.showMessageDialog(table, 
    						"Fejl i kommunikation med serveren. Er internettet nede?");
    				e1.printStackTrace();
    			}
    		}
    	});

    	return table;
    }
    
    //Tilf�jer table og tableHeader til det angivne panel
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

    //ActionListener
    private class Listener implements ActionListener {
    	public void actionPerformed(ActionEvent event){
    		if(event.getSource() == next) {
    			if(turRetur) {
    				int id1 = departureTable.getSelectedRow();
    				int id2 = arrivalTable.getSelectedRow();

    				if (id1<0 ||  id2<0) {
    					JOptionPane.showMessageDialog(getThis(), "Du skal v�lge �n afgang per liste");
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
    						JOptionPane.showMessageDialog(departureTable, 
    								"Fejl i kommunikation med serveren. Er internettet nede?");
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
    						//Dispose preview-pladsbookingen der allerede er �ben
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
