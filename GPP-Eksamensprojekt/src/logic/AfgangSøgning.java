package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * S�ger p� afgange mellem to lufthavne, enten p� en bestemt dato eller i en periode
 * @author Michael Frikke Madsen, Tajanna Bye Kj�rsgaard og Nicoline Warming Larsen.
 *
 */

public class AfgangS�gning {

	private ArrayList<Departure> departuresOnDate = new ArrayList<Departure>();
	private ArrayList<Departure> departuresBeforeDate, departuresAfterDate;
	private int departureId, arrivalId;
	private String formattedDate, formattedDate2;
	private boolean period;
	
	//Constructor til s�gning p� en enkelt dato
	public AfgangS�gning(java.util.Date date, String departureAirport, String arrivalAirport) throws SQLException {
		period = false;
		formattedDate = formatDate(date);
	    
	    //Finder ID p� lufthavnene
	    arrivalId = getId(arrivalAirport);
	    departureId = getId(departureAirport);
	}
	
	//Constructor til s�gning i en periode
	public AfgangS�gning(java.util.Date periodStartDate, java.util.Date periodEndDate, 
					String departureAirport, String arrivalAirport) throws SQLException {
		period = true;
		formattedDate = formatDate(periodStartDate);
		formattedDate2 = formatDate(periodEndDate);
		
		//Finder ID p� lufthavnene
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
	
	//Henter afgange p� de s�gte datoer
	public ArrayList<Departure> getDepartures() throws SQLException {
		//Hvis der ikke s�ges p� en periode
		if(!period) {
			//F� et resultset med departures fra databasen, og l�g dem i et array
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
		
		//Hvis det er en periodes�gning
		} else {
			Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
			departuresOnDate = db.queryGetDeparturesInPeriod(formattedDate, formattedDate2, 
														departureId, arrivalId);
			db.close();
			for(int i=0; i<departuresOnDate.size(); i++) {
				System.out.println(departuresOnDate.get(i).getDepartureId());
			}
			return departuresOnDate;
		}
	}
}
