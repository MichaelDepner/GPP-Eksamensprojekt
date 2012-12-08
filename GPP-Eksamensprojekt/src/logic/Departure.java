package logic;

import java.sql.Date;

public class Departure {
	
	private int airplaneId, departureAirportId, arrivalAirportId;
	private Date departureTime, arrivalTime, departureDate;
	private int price;

	public Departure(int airplaneId, int departureAirportId, int arrivalAirportId, Date departureTime, Date arrivalTime,
			Date departureDate, int price) {
		this.airplaneId = airplaneId;
		this.departureAirportId = departureAirportId;
		this.arrivalAirportId = arrivalAirportId;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departureDate = departureDate;
		this.price = price;
	}
}
