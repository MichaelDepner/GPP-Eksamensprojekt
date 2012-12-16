package logic;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * Denne class bruges til at holde på relevante informationer om en afgang
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class Departure {
	
	private int departureId;
	private Timestamp departureTime, arrivalTime;
	private Date departureDate;
	private int price;
	private String departureName, departureAbbrevation, arrivalName, arrivalAbbrevation;

	public Departure(int departureId, int airplaneId, int departureAirportId, int arrivalAirportId, Timestamp departureTime2, Timestamp arrivalTime2,
			Date departureDate, int price, String departureAirportName, String departureAirportAbbrevation, String arrivalAirportName, String arrivalAirportAbbrevation) {
		this.departureId = departureId;
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
	
	//Formaterer afgangstidspunkt til hh:mm, og returnerer
	public String getDepartureTime() {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateFormatted = formatter.format(departureTime);
		return dateFormatted;
	}
	
	//Formaterer afgangs til dd-MM-yyyy, og returnerer
	public String getDepartureDate() {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		//String dateFormatted = formatter.format(departureTime);
		String dateFormatted = formatter.format(departureDate);
		return dateFormatted;
	}
	
	//Formaterer ankomsttid til hh:mm, og returnerer
	public String getArrivalTime() {
		DateFormat formatter = new SimpleDateFormat("HH:mm");
		String dateFormatted = formatter.format(arrivalTime);
		return dateFormatted;
	}
	
	//Trækker de to tidspunkter fra hinanden for at finde rejsetiden
	public String getTravelTime() {
		long diffInMillies = arrivalTime.getTime() - departureTime.getTime();
		Time t = new Time(diffInMillies-3600000);
		return t.toString();
	}
	
	public int getPrice() {
		return price;
	}
}
