package logic;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

/**
 * Denne class bruges til at teste, at Departure returnerer korrekte værdier
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class DepartureTest {

	Departure d;
	
	@Before
	//Finder en specifik departure i databasn
	public void setUp() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		d = db.queryGetDeparture(1);
		db.close();
	}

	//Følgende tests sikrer, at al information er trukket korrekt fra databasen, og kan 'gettes' fra departure klassen
	
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
