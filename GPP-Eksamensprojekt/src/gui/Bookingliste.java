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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

/**
 * Viser en liste af bookinger foretaget af en bestemt kunde. 
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class Bookingliste extends JFrame {
	private TableColumn column;
	private ArrayList<Booking> bookings;
	private Customer c;
	private JTable bookingTable;
	private JButton next;

	//Constructor som finder kunden i databasen, og skaber en frame
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
		setTitle("Bookingliste");
		
        getContentPane().setLayout(new BorderLayout());
    	
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
    	
    	//Knap
    	next = new JButton("Næste");
    	next.addActionListener(new Listener());
    	panelSouth.add(next);
    	
    	//Opretter panels
    	JPanel jp1bookings = new JPanel();
    	jp1bookings.setLayout(new FlowLayout());
    	
    	JLabel labelUdrejse = new JLabel();
    	labelUdrejse.setText("Bookinger foretaget af "+c.GetFullName());
    	labelUdrejse.setFont(new Font("String", Font.BOLD, 18));
    	jp1bookings.add(labelUdrejse);

    	//Sætter table og table header ind i jp1bookings
    	bookingTable = bookingTable(bookings);
    	jp1bookings.add(bookingTable.getTableHeader());
    	jp1bookings.add(bookingTable);
    	panelCenter.add(jp1bookings);
	}
	
	//Laver et JTable med informationer fra en arraylist af bookinger
	private JTable bookingTable(ArrayList<Booking> bookings) {
    	final ArrayList<Booking> bk = bookings;
    	//Bruger defaultTableModel med den ene ændring at celler ikke kan ændres
    	DefaultTableModel model = new DefaultTableModel() {
    		public boolean isCellEditable(int row, int column) {
    			return false;
    		}
    	}; 
    	final JTable table = new JTable(model); 
    	
    	//Overskriver metoden moveColumn, så man ikke længere kan rykke rundt på dem.
    	table.setColumnModel(new DefaultTableColumnModel() {  
    		public void moveColumn(int columnIndex, int newIndex) { 
    		}  
    		});
    	
    	//Tilføjer navne til header i table
    	model.addColumn("Dato");
    	model.addColumn("Afrejse - Ankomst"); 
    	model.addColumn("Rejsetid");
    	model.addColumn("Lufthavne");
    	model.addColumn("DepartureId");

    	//Indhold til table
    	for(int i=0; i<bookings.size(); i++) {
    		Booking b = bookings.get(i);
    		Departure d = b.getDeparture();
    		String date = d.getDepartureDate();
    		String time = d.getDepartureTime()+" - "+d.getArrivalTime();
    		String travelTime = d.getTravelTime();
    		String fromTo = d.getDepartureAirportName()+" - "+d.getArrivalAirportName();
    		int id = d.getDepartureId();

    		model.addRow(new Object[]{date,time,travelTime,fromTo,id+""});	
    	}
    	
    	//Tilføjer rejser
    	for(int i=0; i<bookings.size(); i++) {
    		Booking b = bk.get(i);
    	}
    	
    	//Sætter bredden af kolonner
    	setWidth(table, 0, 130);
    	setWidth(table, 1, 100);
    	setWidth(table, 2, 160);
    	setWidth(table, 3, 150);
    	
    	return table;
    }
	
	//Metode for at sætte bredden af kolonner
	private void setWidth(JTable table, int i, int j) {
    	column = table.getColumnModel().getColumn(i);
    	
    	column.setMinWidth(j);
		column.setMaxWidth(j);
		column.setPreferredWidth(j);
    }
	
	private Bookingliste getThis() {
		return this;
	}
	
	//Lytter til 'Næste'-knappen
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent event){
			if(event.getSource() == next) {
				int id1 = bookingTable.getSelectedRow();
				//Hvis der ikke er valgt en row, giv en popup besked til brugeren
				if(id1<0) {
					JOptionPane.showMessageDialog(getThis(), "Booking ikke valgt ordentligt. Klik på en booking!");
				} else {
					try {
						//skab forbindelse til databasen, og find bookingens id via den valgte row i bookingtable
						Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
						String id11 = (String)bookingTable.getValueAt(id1, 4);
						int id2 = Integer.parseInt(id11);

						//Vi skal bruge 4 informationer for at få fuld overblik
						//1. Bookingen
						Booking b = bookings.get(id1); 
						//2. Et udfyldt pladsarray (det bliver udfyldt ved at lave en pladsbooking midlertidigt, som udfylder de reserverede Seats
						Pladsbooking pb = new Pladsbooking(id2, b, c);
						PladsArray pa = pb.getPladsArray();
						pb.dispose();						

						//3. En departure
						Departure d = db.queryGetDeparture(id2);

						//4. Den customer, vi allerede har defineret i constructoren. 
						//Opretter overbliks-vindue over bookingen.
						Gennemse g = new Gennemse(pa.getReservations(), d, b, c);
						db.close();
						dispose();
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(getThis(), "SQL fejl. Er internettet nede?");
						e.printStackTrace();
					}
				}
			}
		}
	}
}
