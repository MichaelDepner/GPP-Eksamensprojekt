package logic;

public class Customer {
	
	private String firstname, surname, email, phone, address, city, postalCode, country;
	
	public Customer(String firstname, String surname, String email, String phone, String address, String city,
			String postalCode, String country) {
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		System.out.println("Making customer: "+firstname+" "+surname+" "+email+" "+phone+" "+address+" "+city+" "+postalCode+" "+country);
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
