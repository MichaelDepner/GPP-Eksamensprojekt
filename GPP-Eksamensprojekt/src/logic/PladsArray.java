package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class PladsArray {
	private Database db;
	private int departureId;
	private int columns;
	private int rows;
	private int premiumRows;
	private Plads[] pladser;
	
	
	public PladsArray(int departureId) throws SQLException {
		this.departureId = departureId;
		ArrayList<Integer> reservations = findReservations();
		
		
	}
	
	
	
	public ArrayList<Integer> findReservations() throws SQLException {
		ArrayList<Integer> reservationList = new ArrayList<>();
		int counter = 0;
		
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		
		ResultSet rs = db.queryGetReservedSeats(departureId);
		
		while (rs.next()) {
			String s = rs.getString("seats");
			String[] strArray = s.split(" ");
			for(int i=0; i<strArray.length; i++) {
				reservationList.add(Integer.parseInt(strArray[i]));
				counter++;
			}
		}
		
		//sortér arraylisten
		Collections.sort(reservationList);	
		
		//for (int i = 0; i<reservationList.size(); i++) {
		//	System.out.println(reservationList.get(i));
		//}
		
		db.close();
		return reservationList;
		}
	
	public int getRows() throws SQLException {
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ResultSet rs = db.queryGetRows(departureId);
		rs.next();
		return rs.getInt("rows");
	}
	
	public int getCols() throws SQLException {
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ResultSet rs = db.queryGetCols(departureId);
		rs.next();
		return rs.getInt("columns");
	}
	
	public ArrayList<Integer> getEmptyCols() throws SQLException {
		ArrayList<Integer> emptyColumns = new ArrayList();
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ResultSet rs = db.queryGetEmptyCols(departureId);
		
		while (rs.next()) {
			String s = rs.getString("empty_columns");
			String[] strArray = s.split(" ");
			for(int i=0; i<strArray.length; i++) {
				emptyColumns.add(Integer.parseInt(strArray[i]));
			}
		}
		Collections.sort(emptyColumns);
		return emptyColumns;
	}

}
