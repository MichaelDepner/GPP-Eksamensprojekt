package logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class PladsArray {
	private Database db;
	private int departureId;
	private int columns;
	private int rows;
	private Plads[] pladser;
	private ArrayList<Integer> premiumRows = new ArrayList<>();
	private ArrayList<Integer> aisles = new ArrayList<>();
	private int[][] overblikArray;
	
	
	
	
	public PladsArray(int departureId) throws SQLException {
		this.departureId = departureId;
		ArrayList<Integer> reservations = findReservations();
		
		overblikArray = new int[getCols()][getRows()];
		
		
	}
	
	
	
	//returnerer en ArayList med numrene på de sæder, der allerede er reserveret
	public ArrayList<Integer> findReservations() throws SQLException {
		ArrayList<Integer> reservationList = new ArrayList<>();
		
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		
		ResultSet rs = db.queryGetReservedSeats(departureId);
		
		while (rs.next()) {
			String s = rs.getString("seats");
			String[] strArray = s.split(" ");
			for(int i=0; i<strArray.length; i++) {
				reservationList.add(Integer.parseInt(strArray[i]));
			}
		}
		
		//sortér arraylisten
		Collections.sort(reservationList);	
		
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
