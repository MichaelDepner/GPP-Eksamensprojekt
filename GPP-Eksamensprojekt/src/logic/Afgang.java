package logic;

import java.sql.Date;
import java.sql.Time;

public class Afgang {
	
	private int airplaneId, departureAirportId, arrivalAirportId;
	private Date departuretime, arrivaltime;
	
	public Afgang(int airplaneId, int departureAirportId, int arrivalAirportId, Time departureTime, Time arrivalTime) {
		this.airplaneId = airplaneId;
		this.departureAirportId = departureAirportId;
		this.arrivalAirportId = arrivalAirportId;
	}

}