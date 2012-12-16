package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Denne class bruges til at teste, at person-info hentes ordentligt fra databasen
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */
public class PersonTest {

	Person p;
	
	@Before
	//Finder en specifik person i databasen
	public void setUp() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		p = db.queryGetPassenger(3);
	}

	//Følgende tests sikrer, at informationerne er trukket korrekt fra databasen, og kan 'gettes' fra person-klassen
	
	@Test
	public void testGetId() {
		int id = p.getId();
		assertEquals(id, 3);
	}

	@Test
	public void testGetFirstname() {
		String name = p.getFirstname();
		assertEquals(name, "Rudolf");
	}

	@Test
	public void testGetSurname() {
		String name = p.getSurname();
		assertEquals(name, "Rensdyret");
	}

	@Test
	public void testGetBirthday() {
		String birthday = p.getBirthday();
		assertEquals(birthday, "11-13-1976");
	}

	@Test
	public void testGetFullName() {
		String name = p.getFullName();
		assertEquals(name, "Rudolf Rensdyret");
	}

}
