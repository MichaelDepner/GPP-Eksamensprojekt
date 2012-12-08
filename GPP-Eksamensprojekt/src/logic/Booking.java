package logic;

public class Booking {

	public int departureId, arrivalId;
	String seats, passengerIds;
	
	
	public Booking(int departureId, int arrivalId, String seats, String passengerIds) {
		this.departureId = departureId;
		this.arrivalId = arrivalId;
		this.seats = seats;
		this.passengerIds = passengerIds;
	}
}
