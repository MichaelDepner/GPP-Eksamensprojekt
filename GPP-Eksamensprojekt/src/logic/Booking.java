package logic;

public class Booking {

	public int departureId;
	String seats, passengerIds;
	
	
	public Booking(int departureId, String seats, String passengerIds) {
		this.departureId = departureId;
		this.seats = seats;
		this.passengerIds = passengerIds;
	}
	
	public int getdepartureId() {
		return departureId;
	}
}
