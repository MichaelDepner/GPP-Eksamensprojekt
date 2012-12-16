package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Opretter et array, der holder styr p� antallet af reserverede pladser n�r vi laver en PladsBooking
 * @author Michael Frikke Madsen, Tajanna Bye Kj�rsgaard og Nicoline Warming Larsen.
 *
 */
public class PladsArray {
	private Database db;
	private int departureId;
	private ArrayList<Plads> reservations = new ArrayList<>();
	
	public PladsArray(int departureId) throws SQLException {
		this.departureId = departureId;
	}
	
	public void addReservation(Plads p) {
		reservations.add(p);
	}
	
	public void removeReservation(Plads p) {
		reservations.remove(p);
	}
	
	public ArrayList<Plads> getReservations() {
		return reservations;
	}
	
	//Returnerer en ArayList med numrene p� de s�der, der allerede er reserveret
	public ArrayList<Integer> findReservations() throws SQLException {
		ArrayList<Integer> reservationList = new ArrayList<>();
		
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		
		ResultSet rs = db.queryGetReservedSeats(departureId);
		
		//Deler 'seat' strengen fra databasen op i Integers, og placerer dem i en ArrayList
		while (rs.next()) {
			String s = rs.getString("seats");
			String[] strArray = s.split(" ");
			for(int i=0; i<strArray.length; i++) {
				reservationList.add(Integer.parseInt(strArray[i]));
			}
		}
		
		//sort�r arraylisten
		Collections.sort(reservationList);	
		
		db.close();
		return reservationList;
		}
	
	//Returnerer antallet af r�kker i det specifikke fly
	public int getRows() throws SQLException {
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ResultSet rs = db.queryGetRows(departureId);
		rs.next();
		return rs.getInt("rows");
	}
	
	//Returnerer antallet af kolonner i det specifikke fly
	public int getCols() throws SQLException {
		db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ResultSet rs = db.queryGetCols(departureId);
		rs.next();
		return rs.getInt("columns");
	}
	
	//Returnerer en ArrayList med antallet af tomme kolonner (mellemgange) i det specifikke fly
	public ArrayList<Integer> getEmptyCols() throws SQLException {
		ArrayList<Integer> emptyColumns = new ArrayList<Integer>();
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
