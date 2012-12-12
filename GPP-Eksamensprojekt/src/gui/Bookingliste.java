package gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import logic.Booking;
import logic.Customer;
import logic.Database;
import logic.Departure;
import logic.PladsArray;

public class Bookingliste extends JFrame {
	//Søgelisten skal vise navn, bookingnummer, og en vis-knap
	
	//Laves som afgangsliste, med dynamisk table
	
	private TableColumn column;
	private ArrayList<Booking> bookings;
	private Customer c;
	private JTable bookingTable;
	private JButton next;
	
	public Bookingliste(String searchingFor, String arg) throws SQLException {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		c = db.queryFindCustomer(searchingFor, arg);
		bookings = db.queryFindBookingsMadeBy(c.getId());
		for(int i=0; i<bookings.size(); i++) {
			System.out.println(bookings.get(i).getdepartureId()+"");
		}
		makeWindow();
		setPreferredSize(new Dimension(700, 460));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
	}
	
	private void makeWindow() {
        getContentPane().setLayout(new BorderLayout());
        
      //Laver vores fane-vinduer
    	//JTabbedPane jtp = new JTabbedPane();
    	
    	//Sætter BorderLayout i contentPane, og laver panels indeni
    	getContentPane().setLayout(new BorderLayout());
    	//CENTER
    	JPanel panelCenter = new JPanel();
    	getContentPane().add(panelCenter, BorderLayout.CENTER);
    	panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));

    	//SOUTH
    	JPanel panelSouth = new JPanel();
    	getContentPane().add(panelSouth, BorderLayout.SOUTH);
    	panelSouth.setLayout(new FlowLayout());
    	
    	next = new JButton("Next");
    	next.addActionListener(new Listener());
    	panelSouth.add(next);

    	//Sætter fane-vinduerne ind i layouts'ene
    	//panelCenter.add(jtp);
    	
    	//Opretter panels
    	JPanel jp1bookings = new JPanel();
    	
    	//jp1Hjemrejse.setLayout(new BorderLayout());

    	//jp1bookings.setLayout(new BoxLayout(jp1bookings, BoxLayout.Y_AXIS));
    	jp1bookings.setLayout(new FlowLayout());
    	JLabel labelUdrejse = new JLabel();
    	labelUdrejse.setText("Bookings foretaget af "+c.GetFullName());
    	labelUdrejse.setFont(new Font("String", Font.BOLD, 14));
    	jp1bookings.add(labelUdrejse);

    	//Skal evt. rykkes ned til table-metode
    	bookingTable = bookingTable(bookings);
    	jp1bookings.add(bookingTable.getTableHeader());
    	jp1bookings.add(bookingTable);
    	panelCenter.add(jp1bookings);
	}
	
	private JTable bookingTable(ArrayList<Booking> bookings) {
    	final ArrayList<Booking> bk = bookings;
    	DefaultTableModel model = new DefaultTableModel(); 
    	final JTable table = new JTable(model); 
    	
    	//overskriver metoden moveColumn, så man ikke længere kan rykke rundt på dem.
    	table.setColumnModel(new DefaultTableColumnModel() {  
    		public void moveColumn(int columnIndex, int newIndex) { 
    		}  
    		});  

    	//Laver columns
//    	model.addColumn("Navn"); 
//    	model.addColumn("Bookingnummer");

    	//model.addColumn("Pris"); 
    	model.addColumn("Afrejse - Ankomst"); 
    	model.addColumn("Rejsetid");
    	model.addColumn("Lufthavne");
    	model.addColumn("Ledige pladser");
    	model.addColumn("DepartureId");

    	//Tilføjer rejser
    	for(int i=0; i<bookings.size(); i++) {
    		Booking b = bookings.get(i);
    		Departure d = b.getDeparture();
    		//TODO mangler at tilføje priser i databasen
    		//String price = d.getPrice()+"";
    		String time = d.getDepartureTime()+" - "+d.getArrivalTime();
    		//TODO tilføj udregning af rejsetid
    		String travelTime = d.getTravelTime();
    		String fromTo = d.getDepartureAirportName()+" - "+d.getArrivalAirportName();
    		String seats = " ";//d.getSeats();
    		int id = d.getDepartureId();

    		model.addRow(new Object[]{time,travelTime,fromTo,seats,id+""});	
    	}
    	
    	
    	
    	
    	//Tilføjer rejser
    	for(int i=0; i<bookings.size(); i++) {
    		Booking b = bk.get(i);
    		//String name = b.getFistName() + " " + b.getLastName();
    		//String bookingNumber = b.getBookingNumber();
    		
    		//model.addRow(new Object[]{name, bookingNumber});
    	}
    	
    	//sætter bredden af kolonner
    	setWidth(table, 0, 230);
    	setWidth(table, 1, 120);
    	
    	return table;
    }
	
	private void setWidth(JTable table, int i, int j) {
    	column = table.getColumnModel().getColumn(i);
    	
    	column.setMinWidth(j);
		column.setMaxWidth(j);
		column.setPreferredWidth(j);
    }
	
	 private class Listener implements ActionListener {
	    	public void actionPerformed(ActionEvent event){
	    		if(event.getSource() == next) {
	    				int id1 = bookingTable.getSelectedRow();
	    				if(id1<0) {
	    					System.out.println("Rows not selected properly!");
	    				} else {
	    					try {
	    						Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
	    						String id11 = (String)bookingTable.getValueAt(id1, 4);
	    						int id2 = Integer.parseInt(id11);
	    						
	    						Booking b = bookings.get(id1); 
	    						Pladsbooking pb = new Pladsbooking(id2, b, c);
	    						PladsArray pa = pb.getPladsArray();
	    						pb.dispose();						
	    						
	    						//Pladsbooking pb = new Pladsbooking(id2, bookings.get(id1).getSeats());
	    						Departure d = db.queryGetDeparture(id2);
	    						
	    						Gennemse g = new Gennemse(pa.getReservations(), d, b, c);
	    						db.close();
	    						dispose();
	    					} catch (SQLException e) {
	    						System.out.println("SQL error when making Pladsbooking");
	    						e.printStackTrace();
	    					}
	    				}
	    			}
	    		


	    	}
	    }
}
