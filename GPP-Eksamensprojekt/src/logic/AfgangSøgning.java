package logic;

import gui.Afgangsliste;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfgangSøgning {

	private ArrayList<Afgang> afgangeOnDate = new ArrayList<Afgang>();
	private String departureAirport, arrivalAirport;
	private int departureId, arrivalId;
	private String formattedDate;
	
	public AfgangSøgning(java.util.Date date, String departureAirport, String arrivalAirport) throws SQLException {
	
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		
		formattedDate = formatDate(date);
	    
	    //Finder ID på lufthavnene
	    arrivalId = getId(arrivalAirport);
	    departureId = getId(departureAirport);
		
		//Søger først efter afgange på valgte dato, og ligger dem i en arraylist
		//afgangeOnDate = getDepartures();
		
		
		//Søger på afgange før og ligger dem i en arraylist
		
		
		//Søger på afgange efter og ligger dem i en arraylist
		
		//Laver Afgangsliste - nej lad være med det.
		//Afgangsliste al = new Afgangsliste();
		
		
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
	
	
	public ArrayList<Afgang> getDepartures() throws SQLException {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
	
		
		ResultSet rs = db.queryGetDepartures(formattedDate, departureId, arrivalId);
		
		while(rs.next()) {
			int airplaneId;
			Time departureTime, arrivalTime;
			
			airplaneId = rs.getInt("airplane_id");
			departureTime = rs.getTime("departure_time");
			arrivalTime = rs.getTime("arrival_time");
			
			Afgang afgang = new Afgang(airplaneId, departureId, arrivalId, departureAirport, arrivalAirport, departureTime, arrivalTime);
			System.out.println("Opretter afgang: airplaneId: "+airplaneId+", departureId: "+departureId+"" +
					", arrivalId: "+arrivalId+", departureTime: "+departureTime+", arrivalTime: "+arrivalTime);
			
			afgangeOnDate.add(afgang);
		}
		
		db.close();
		return afgangeOnDate;
	}
}
