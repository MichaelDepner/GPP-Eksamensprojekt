package logic;

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
		db.close();
		
		}
	}
