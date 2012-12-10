package logic;

import gui.Afgangsliste;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfgangS�gning {

	private ArrayList<Departure> departuresOnDate = new ArrayList<Departure>();
	private ArrayList<Departure> departuresBeforeDate, departuresAfterDate;
	private String departureAirport, arrivalAirport;
	private int departureId, arrivalId;
	private String formattedDate;
	
	public AfgangS�gning(java.util.Date date, String departureAirport, String arrivalAirport) throws SQLException {
	
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		
		formattedDate = formatDate(date);
	    
	    //Finder ID p� lufthavnene
	    arrivalId = getId(arrivalAirport);
	    departureId = getId(departureAirport);
		
		//S�ger f�rst efter afgange p� valgte dato, og ligger dem i en arraylist
		//afgangeOnDate = getDepartures();
		
		
	    Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
	    //S�ger p� afgange f�r og ligger dem i en arraylist, s�ledes at datoen t�ttest p� ligger ved index = 0
	    departuresBeforeDate = db.queryGetDeparturesBeforeDate(formattedDate, departureId, arrivalId);
	    //S�ger p� afgange efter og ligger dem i en arraylist, s�ledes at datoen t�ttest p� ligger ved index = 0
	    departuresAfterDate = db.queryGetDeparturesAfterDate(formattedDate, departureId, arrivalId);
	}
	
	public String formatDate(java.util.Date date) {
		//formaterer date til YYYYMMDD for at kunne bruge den i databasen
		String fd;
		String DATE_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		fd = sdf.format(date.getTime());
		return fd;
	}
	
	public int getId(String airport) throws SQLException {
		int id = 0;
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
	    ResultSet rs = db.queryGetAirportId(airport);
	    while(rs.next()) {
	    	id = rs.getInt("id");
	    }
		return id;
	}
	
	
	public ArrayList<Departure> getDepartures() throws SQLException {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		ResultSet rs = db.queryGetDeparturesOnDate(formattedDate, departureId, arrivalId);
		
		ArrayList<Integer> ids = new ArrayList<>();
		while(rs.next()) {
			int id = rs.getInt("id");
			ids.add(id);
		}
		
		for(int i=0; i<ids.size(); i++) {
			Departure d = db.queryGetDeparture(ids.get(i));
			departuresOnDate.add(d);
		}
		
		db.close();
		return departuresOnDate;
	}
	
	public ArrayList<Departure> getDeparturesBefore() {
		return departuresBeforeDate;
	}
	
	public ArrayList<Departure> getDeparturesAfter() {
		return departuresAfterDate;
	}
}
