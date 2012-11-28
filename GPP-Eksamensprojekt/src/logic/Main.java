package logic;

import gui.Pladsbooking;

import java.sql.SQLException;

public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		
		new gui.Forside();
		
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		db.queryGetAirports();
		db.queryGetReservedSeats(1);
		db.queryGetRows(1);
		db.queryGetCols(1);
		db.close();
		
		PladsArray pa = new PladsArray(1);
		
		Pladsbooking pb = new Pladsbooking(pa);
		}
	}
