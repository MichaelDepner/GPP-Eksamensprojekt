package logic;

/**
 * Denne class indeholder alle relevante informationer om en Person (passager)
 * @author Michael Frikke Madsen, Tajanna Bye Kjærsgaard og Nicoline Warming Larsen.
 *
 */
public class Person {

	int id;
	private String firstname, surname, birthday; 
	
	public Person(String firstname, String surname, String birthday) {
		this.firstname = firstname;
		this.surname = surname;
		this.birthday = birthday;
	}
	
	//setId kaldes når vi henter en person ned fra databasen, og den derfor har et id
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
