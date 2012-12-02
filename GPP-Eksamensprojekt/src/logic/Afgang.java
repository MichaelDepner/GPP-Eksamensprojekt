package logic;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.swing.text.DateFormatter;

public class Afgang {
	
	private int airplaneId, departureAirportId, arrivalAirportId;
	private Time arrivalTime, departureTime;
	private String departureAirportName, arrivalAirportName;
	
	public Afgang(int airplaneId, int departureAirportId, int arrivalAirportId, String departureAirportName, String arrivalAirportName, Time departureTime, Time arrivalTime) {
		this.airplaneId = airplaneId;
		this.departureAirportId = departureAirportId;
		this.arrivalAirportId = arrivalAirportId;
		this.departureAirportName = departureAirportName;
		this.arrivalAirportName = arrivalAirportName;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
	
	public String getDepartureTime() {
		String DATE_FORMAT = "H:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(departureTime.getTime());
	}
	
	public String getArrivalTime() {
		String DATE_FORMAT = "H:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(arrivalTime.getTime());
	}
	
	//TODO yeeah den skal nok ændres til at trække det fulde navn ud af serveren
	public String getDepartureAirport() {
		return ""+departureAirportName;
	}
	
	//TODO skal også trække navn fra serveren, ikke kun ID
	public String getArrivalAirport() {
		return ""+arrivalAirportName;
	}
	
	//TODO antal sæder skal trækkes fra databasen
	public String getSeats() {
		return "100";
	}

}