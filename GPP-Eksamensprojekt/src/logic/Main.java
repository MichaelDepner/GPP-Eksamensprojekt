package logic;

import gui.Bookinginfo;
import gui.Kundeoplysninger;
import gui.Pladsbooking;
import gui.Afgangsliste;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		new gui.Forside2();
		new Bookinginfo();
		//new gui.Betaling();
		//new gui.Kundeoplysninger();
		//new gui.Kvittering();
		
		//new gui.Bookinginfo();
		
		//new gui.Pladsbooking(1, 4);
		//new gui.Pladsbooking(4, 4);
		
		//new gui.Pladsbooking(1, true);
		
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ArrayList<Departure> rs = db.queryGetDeparturesAfterDate("20121210", 1, 8);
//		db.queryMakeCustomer("Dennis", "Hansen", "Pludrevej 17", "Hillerød", "1234", "Danmark", "Dennis@hansen.dk", "14253647");
		db.close();
		
		
		
		//Kundeoplysninger ko = new Kundeoplysninger();
		
		}
	}
