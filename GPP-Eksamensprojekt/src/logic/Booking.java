package logic;

import java.sql.SQLException;
import java.util.ArrayList;

public class Booking {

	private int departureId, id;
	String seats, passengerIds;
	public Departure d;
	ArrayList<Plads> reserved;
	ArrayList<Person> passengers = new ArrayList<Person>();
	
	
	public Booking(int departureId, String seats, String passengerIds, boolean importing) throws SQLException {
		this.departureId = departureId;
		this.seats = seats;
		this.passengerIds = passengerIds;
		findPassengers();
		
		//henter information fra databasen om afgangen, hvis vi er ved at importere
		if(importing) {
			Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
			d = db.queryGetDeparture(departureId);
		}
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void findPassengers() throws SQLException {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		//deler passengerId strengen ind i små bidder, og finder passagerernes id. Herefter findes personernes informationer i databasen ud fra id'et.
		System.out.println(passengerIds);
		String[] strArray = passengerIds.split(" ");
		
		for(int i=0; i<strArray.length; i++) {
			int id = Integer.parseInt(strArray[i]);
			passengers.add(db.queryGetPassenger(id));
		}
		db.close();
	}
	
	public ArrayList<Person> getPassengers() {
		return passengers;
	}
	
	public int getdepartureId() {
		return departureId;
	}
	
	public Departure getDeparture() {
		return d;
	}
	
	public String getSeats() {
		return seats;
	}
}
