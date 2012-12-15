package logic;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class BookingTest {

	Booking b;
	
	@Before
	public void setUp() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		b = db.queryMakeBooking(1, 1, "0 1 2 ", "2 3 ");
		db.close();
	}
	
	public void tearDown() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		db.queryDeleteBooking(b.getId());
		db.close();
	}


	@Test
	public void testGetdepartureId() {
		int id = b.getdepartureId();
		assertEquals(id, 1);
	}


	@Test
	public void testGetSeats() {
		String seats = b.getSeats();
		assertEquals(seats, "0 1 2 ");
	}

}
