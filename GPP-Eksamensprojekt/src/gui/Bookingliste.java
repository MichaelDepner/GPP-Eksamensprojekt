package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
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

public class Bookingliste extends JFrame {
	//Søgelisten skal vise navn, bookingnummer, og en vis-knap
	
	//Laves som afgangsliste, med dynamisk table
	
	private TableColumn column;
	private ArrayList<Booking> bookings;
	
	public Bookingliste(String searchingFor, String arg) throws SQLException {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		Customer c = db.queryFindCustomer(searchingFor, arg);
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
    	JTabbedPane jtp = new JTabbedPane();
    	
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
    	
    	//Opretter panels
    	JPanel jp1bookings = new JPanel();
    	
    	//jp1Hjemrejse.setLayout(new BorderLayout());

    	JLabel labelUdrejse = new JLabel();
    	labelUdrejse.setText("Onsdag d. 28. november 2012 " + "Udrejse - Lufthavn");
    	labelUdrejse.setFont(new Font("String", Font.BOLD, 14));
    	jp1bookings.add(labelUdrejse);

    	//Skal evt. rykkes ned til table-metode
    	JTable bookingTable = bookingTable(bookings);
    	//jp1Udrejse.add(departureTable, BorderLayout.CENTER);
    	jp1bookings.add(bookingTable);
    	//Tilføjer panel jp1Udrejse til jtp
    	jtp.addTab("28/11", jp1bookings);
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
    	model.addColumn("Navn"); 
    	model.addColumn("Bookingnummer");

    	//Tilføjer rejser
    	for(int i=0; i<bookings.size(); i++) {
    		Booking b = bk.get(i);
    		//String name = b.getFistName() + " " + b.getLastName();
    		//String bookingNumber = b.getBookingNumber();
    		
    		//model.addRow(new Object[]{name, bookingNumber});
    	}
    	
    	//sætter bredden af kolonner
    	setWidth(table, 0, 120);
    	setWidth(table, 1, 120);
    	
    	return table;
    }
	
	private void setWidth(JTable table, int i, int j) {
    	column = table.getColumnModel().getColumn(i);
    	
    	column.setMinWidth(j);
		column.setMaxWidth(j);
		column.setPreferredWidth(j);
    }
}
