package logic;

public class Person {

	private String firstname, surname, birthday; 
	
	public Person(String firstname, String surname, String birthday) {
		this.firstname = firstname;
		this.surname = surname;
		this.birthday = birthday;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getBirthday() {
		return birthday;
	}
	
	public String getFullName() {
		return firstname+" "+surname;
	}
}
