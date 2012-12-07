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
		//new gui.Betaling();
		//new gui.Kundeoplysninger();
		//new gui.Kvittering();
		
		//new gui.Bookinginfo();

		
<<<<<<< HEAD
		//new gui.Pladsbooking(1, 4);
=======
		new gui.Pladsbooking(4, 4);
>>>>>>> branch 'master' of https://github.com/Mibias/GPP-Eksamensprojekt.git
		
		//new gui.Pladsbooking(1, true);
		
//		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
//		db.queryGetAirports();
//		db.queryGetReservedSeats(1);
//		db.queryGetRows(1);
//		db.queryGetCols(1);
//		db.close();
		
		
		
		//Kundeoplysninger ko = new Kundeoplysninger();
		new gui.Gennemse(null, null, null, null);
		
		}
	}
