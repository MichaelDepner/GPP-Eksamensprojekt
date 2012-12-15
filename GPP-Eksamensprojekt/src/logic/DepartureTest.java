package logic;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

public class DepartureTest {

	Departure d;
	
	@Before
	public void setUp() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		d = db.queryGetDeparture(1);
	}

	@Test
	public void testGetDepartureId() {
		int id = d.getDepartureId();
		assertEquals(id, 1);
	}

	@Test
	public void testGetArrivalAirportName() {
		String name = d.getArrivalAirportName();
		assertEquals(name, "Billund");
	}

	@Test
	public void testGetArrivalAirportAbbrevation() {
		String abbrevation = d.getArrivalAirportAbbrevation();
		assertEquals(abbrevation, "BLL");
	}

	@Test
	public void testGetDepartureAirportName() {
		String name = d.getDepartureAirportName();
		assertEquals(name, "København");
	}

	@Test
	public void testGetDepartureAirportAbbrevation() {
		String abbrevation = d.getDepartureAirportAbbrevation();
		assertEquals(abbrevation, "CPH");
	}

	@Test
	public void testGetDepartureTime() {
		String date = d.getDepartureTime();
		assertEquals(date, "11:00");
	}

	@Test
	public void testGetDepartureDate() {
		String date = d.getDepartureDate();
		assertEquals(date, "30-11-2012");
	}

	@Test
	public void testGetArrivalTime() {
		String date = d.getArrivalTime();
		assertEquals(date, "12:00");
	}

	@Test
	public void testGetTravelTime() {
		String date = d.getTravelTime();
		assertEquals(date, "01:00:00");
	}

	@Test
	public void testGetPrice() {
		int price = d.getPrice();
		assertEquals(price, 500);
	}

}
