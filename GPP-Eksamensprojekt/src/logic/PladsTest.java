package logic;

import static org.junit.Assert.*;

import java.util.Date;

import gui.Afgangsliste;
import gui.Pladsbooking;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester at Pladsen gemmer de informationer, der er blevet den givet, og at man kan hente dem igen
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */
public class PladsTest {
	
	private Plads p;

	@Before
	public void setUp() throws Exception {
		Pladsbooking pb = new Pladsbooking(1, new Afgangsliste(new Date(2012, 11, 30), "København", "Billund"));
		p = new Plads(0, pb);
	}

	@Test
	public void testGetSeatNo() {
		int seatNo = p.getSeatNo();
		assertEquals(seatNo, 0);
	}

	@Test
	public void testName() {
		p.SetName("Testname");
		assertEquals(p.GetName(), "Testname");
	}

}
