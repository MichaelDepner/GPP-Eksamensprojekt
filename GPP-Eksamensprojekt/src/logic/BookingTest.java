package logic;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Denne class bruges til at teste, om Bookingen returnerer det data, den bliver oprettet med
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class BookingTest {

	Booking b;
	
	@Before
	//Opretter en booking
	public void setUp() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		b = db.queryMakeBooking(1, 1, "0 1 2 ", "2 3 ");
		db.close();
	}
	
	//Sletter bookingen igen når testen er færdig
	public void tearDown() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		db.queryDeleteBooking(b.getId());
		db.close();
	}


	@Test
	//Tester, at departureId matcher det, der er givet
	public void testGetdepartureId() {
		int id = b.getdepartureId();
		assertEquals(id, 1);
	}


	@Test
	//Tester, at 'seat'-stringen matcher den, der er givet
	public void testGetSeats() {
		String seats = b.getSeats();
		assertEquals(seats, "0 1 2 ");
	}

}
