package logic;

/**
 * Denne class holder på relevante informationer om en Customer
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */

public class Customer {
	
	private String firstname, surname, email, phone, address, city, postalCode, country;
	private int id;
	
	public Customer(String firstname, String surname, String email, String phone, 
					String address, String city, String postalCode, String country) {
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
	}
	
	//Denne metode kaldes når Customeren importeres fra databasen, 
	//og derved har et givent id
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String GetFirstname() {
		return firstname;
	}
	
	public String GetSurname() {
		return surname;
	}
	
	public String GetFullName() {
		return firstname+" "+surname;
	}
	
	public String GetEmail() {
		return email;
	}
	
	public String GetPhone() {
		return phone;
	}
	
	public String GetAdress() {
		return address;
	}
	
	public String GetCity() {
		return city;
	}
	
	public String GetPostalCode() {
		return postalCode;
	}
	
	public String GetCountry() {
		return country;
	}
}
