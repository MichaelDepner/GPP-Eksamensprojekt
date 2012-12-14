package logic;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class Departure {
	
	private int departureId, airplaneId, departureAirportId, arrivalAirportId;
	private Timestamp departureTime, arrivalTime;
	private Date departureDate;
	private int price;
	private String departureName, departureAbbrevation, arrivalName, arrivalAbbrevation;

	public Departure(int departureId, int airplaneId, int departureAirportId, int arrivalAirportId, Timestamp departureTime2, Timestamp arrivalTime2,
			Date departureDate, int price, String departureAirportName, String departureAirportAbbrevation, String arrivalAirportName, String arrivalAirportAbbrevation) {
		this.departureId = departureId;
		this.airplaneId = airplaneId;
		this.departureAirportId = departureAirportId;
		this.arrivalAirportId = arrivalAirportId;
		this.departureTime = departureTime2;
		this.arrivalTime = arrivalTime2;
		this.departureDate = departureDate;
		this.price = price;
		this.departureName = departureAirportName;
		this.departureAbbrevation = departureAirportAbbrevation;
		this.arrivalName = arrivalAirportName;
		this.arrivalAbbrevation = arrivalAirportAbbrevation;
	}
	
	public int getDepartureId() {
		return departureId;
	}
	
	
	public String getArrivalAirportName() {
		return arrivalName;
	}
	
	public String getArrivalAirportAbbrevation() {
		return arrivalAbbrevation;
	}
	
	public String getDepartureAirportName() {
		return departureName;
	}
	
	public String getDepartureAirportAbbrevation() {
		return departureAbbrevation;
	}
	
	public String getDepartureTime() {
		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'klokken' HH:mm:ss");
		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateFormatted = formatter.format(departureTime);
		return dateFormatted;
	}
	
	public String getDepartureDate() {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		//String dateFormatted = formatter.format(departureTime);
		String dateFormatted = formatter.format(departureDate);
		return dateFormatted;
	}
	
	public String getArrivalTime() {
		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'klokken' HH:mm:ss");
		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateFormatted = formatter.format(arrivalTime);
		return dateFormatted;
	}
	
	public String getTravelTime() {
		long diffInMillies = arrivalTime.getTime() - departureTime.getTime();
		Time t = new Time(diffInMillies-3600000);
		return t.toString();
	}
	
	public int getPrice() {
		return price;
	}
}
