package logic;

import gui.Afgangsliste;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * 
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class AfgangSøgning {

	private ArrayList<Departure> departuresOnDate = new ArrayList<Departure>();
	private ArrayList<Departure> departuresBeforeDate, departuresAfterDate;
	private String departureAirport, arrivalAirport;
	private int departureId, arrivalId;
	private String formattedDate, formattedDate2;
	private boolean period;
	
	public AfgangSøgning(java.util.Date date, String departureAirport, String arrivalAirport) throws SQLException {
		period = false;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		
		formattedDate = formatDate(date);
	    
	    //Finder ID på lufthavnene
	    arrivalId = getId(arrivalAirport);
	    departureId = getId(departureAirport);
	}
	
	public AfgangSøgning(java.util.Date periodStartDate, java.util.Date periodEndDate, 
					String departureAirport, String arrivalAirport) throws SQLException {
		period = true;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		formattedDate = formatDate(periodStartDate);
		formattedDate2 = formatDate(periodEndDate);
		
		//Finder ID på lufthavnene
		arrivalId = getId(arrivalAirport);
		departureId = getId(departureAirport);
	}
	
	//Formaterer date til YYYYMMDD for at kunne bruge den i databasen
	public String formatDate(java.util.Date date) {
		String fd;
		String DATE_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		fd = sdf.format(date.getTime());
		return fd;
	}
	
	public String getFormattedDate() {
		return formattedDate;
	}
	
	//Lufthavnens ID
	public int getId(String airport) throws SQLException {
		int id = 0;
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
	    ResultSet rs = db.queryGetAirportId(airport);
	    while(rs.next()) {
	    	id = rs.getInt("id");
	    }
		return id;
	}
	
	//Henter afgange på de søgte datoer
	public ArrayList<Departure> getDepartures() throws SQLException {
		//Hvis der ikke søges på en periode
		if(!period) {
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
		
		//Hvis det er en peiodesøgning
		} else {
			Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
			departuresOnDate = db.queryGetDeparturesInPeriod(formattedDate, formattedDate2, 
														departureId, arrivalId);
			for(int i=0; i<departuresOnDate.size(); i++) {
				System.out.println(departuresOnDate.get(i).getDepartureId());
			}
			return departuresOnDate;
		}
	}
	
	//Afgange før søgning
	public ArrayList<Departure> getDeparturesBefore() {
		return departuresBeforeDate;
	}
	
	//Afgange efter søgning
	public ArrayList<Departure> getDeparturesAfter() {
		return departuresAfterDate;
	}
}
