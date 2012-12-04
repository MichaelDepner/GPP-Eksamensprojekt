package gui;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import logic.Booking;

public class Bookingliste extends JFrame {
	//Søgelisten skal vise navn, bookingnummer, og en vis-knap
	
	//Laves som afgangsliste, med dynamisk table
	
	private TableColumn column;
	
	public Bookingliste() {
		setPreferredSize(new Dimension(700, 460));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
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
