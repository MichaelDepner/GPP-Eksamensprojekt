package logic;

import static org.junit.Assert.*;

import java.util.Date;

import gui.Afgangsliste;
import gui.Pladsbooking;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * Tjekker, at en plads gemmes ordentligt, samt tester dens getters og setters
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
