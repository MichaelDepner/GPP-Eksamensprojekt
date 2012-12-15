package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

	Customer c;
	
	@Before
	public void setUp() throws Exception {
		Database db = new Database("mysql.itu.dk", "Swan_Airlines", "swan", "mintai");
		c = db.queryFindCustomer("Phone_Number", "22627702");
	}

	@Test
	public void testGetId() {
		int id = c.getId();
		assertEquals(id, 39);
	}

	@Test
	public void testGetFirstname() {
		String name = c.GetFirstname();
		assertEquals(name, "Michael");
	}

	@Test
	public void testGetSurname() {
		String name = c.GetSurname();
		assertEquals(name, "Frikke Madsen");
	}

	@Test
	public void testGetFullName() {
		String name = c.GetFullName();
		assertEquals(name, "Michael Frikke Madsen");
	}

	@Test
	public void testGetEmail() {
		String email = c.GetEmail();
		assertEquals(email, "mibias.madsen@gmail.com");
	}

	@Test
	public void testGetPhone() {
		String phone = c.GetPhone();
		assertEquals(phone, "22627702");
	}

	@Test
	public void testGetAdress() {
		String address = c.GetAdress();
		assertEquals(address, "Ålekæret 41");
	}

	@Test
	public void testGetCity() {
		String city = c.GetCity();
		assertEquals(city, "Allerød");
	}

	@Test
	public void testGetPostalCode() {
		String postal = c.GetPostalCode();
		assertEquals(postal, "3450");
	}

	@Test
	public void testGetCountry() {
		String country = c.GetCountry();
		assertEquals(country, "Danmark");
	}

}
