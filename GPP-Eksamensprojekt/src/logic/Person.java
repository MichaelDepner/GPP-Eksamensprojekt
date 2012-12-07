package logic;

public class Person {

	private String firstname, surname, birthday; 
	
	public Person(String firstname, String surname, String birthday) {
		this.firstname = firstname;
		this.surname = surname;
		this.birthday = birthday;
	}
	
	public String GetFirstname() {
		return firstname;
	}
	
	public String GetSurname() {
		return surname;
	}
	
	public String GetBirthday() {
		return birthday;
	}
	
	public String GetFullName() {
		return firstname+" "+surname;
	}
}
