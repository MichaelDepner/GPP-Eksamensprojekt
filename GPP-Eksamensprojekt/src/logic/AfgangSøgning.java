package logic;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AfgangSøgning {

	private ArrayList<Afgang> afgangeOnDate = new ArrayList<Afgang>();
	private int departureAirport, arrivalAirport;
	private String formattedDate;
	private int departureId, arrivalId;
	
	public AfgangSøgning(java.util.Date date, String departureAirport, String arrivalAirport) throws SQLException {
	
		//formaterer date til YYYYMMDD for at kunne bruge den i databasen
		String DATE_FORMAT = "yyyyMMdd";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    formattedDate = sdf.format(date.getTime());
	    
	    //Finder ID på lufthavnene
	    Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
	    ResultSet rs1 = db.queryGetAirportId(arrivalAirport);
	    while(rs1.next()) {
	    	arrivalId = rs1.getInt("id");
	    }
	    
	    //Finder ID på lufthavnene
	    ResultSet rs2 = db.queryGetAirportId(departureAirport);
	    while(rs2.next()) {
	    	departureId = rs2.getInt("id");
	    }
		
		//Søger først efter afgange på valgte dato, og ligger dem i en arraylist
		System.out.println("Søger afgange "+ formattedDate+" fra "+departureAirport+" til "+arrivalAirport);
		afgangeOnDate = getDepartures(formattedDate);
		
		
		//Søger på afgange før og ligger dem i en arraylist
		
		
		//Søger på afgange efter og ligger dem i en arraylist
	}
	
	
	private ArrayList<Afgang> getDepartures(String date) throws SQLException {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		
		ResultSet rs = db.queryGetDepartures(formattedDate, departureId, arrivalId);
		
		while(rs.next()) {
			int airplaneId;
			Time departureTime, arrivalTime;
			
			airplaneId = rs.getInt("airplane_id");
			departureTime = rs.getTime("departure_time");
			arrivalTime = rs.getTime("arrival_time");
			
			Afgang afgang = new Afgang(airplaneId, departureId, arrivalId, departureTime, arrivalTime);
			System.out.println("Opretter afgang: airplaneId: "+airplaneId+", departureId: "+departureId+"" +
					", arrivalId: "+arrivalId+", departureTime: "+departureTime+", arrivalTime: "+arrivalTime);
			
			afgangeOnDate.add(afgang);
		}
		
		db.close();
		return null;
	}
}
