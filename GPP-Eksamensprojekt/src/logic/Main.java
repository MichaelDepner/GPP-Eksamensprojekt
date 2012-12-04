package logic;

import gui.Kundeoplysninger;
import gui.Pladsbooking;
import gui.Afgangsliste;

import java.sql.SQLException;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		
		
		
		new gui.Forside2();
		new gui.Betaling();
		new gui.Kundeoplysninger();
		
		new gui.Bookinginfo();

		
		//new gui.Pladsbooking(1, false, false);
		
		new gui.Pladsbooking(1, true);
		
//		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
//		db.queryGetAirports();
//		db.queryGetReservedSeats(1);
//		db.queryGetRows(1);
//		db.queryGetCols(1);
//		db.close();
		
		//PladsArray pa = new PladsArray(1);
		
		//Pladsbooking pb = new Pladsbooking(pa);
		
		
		//Kundeoplysninger ko = new Kundeoplysninger();
		
		//Afgangsliste tp = new Afgangsliste();
		//tp.setVisible(true);
		
		}
	}
