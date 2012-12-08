package logic;

import java.sql.*;

public class Database {
	private final Connection connection;
	private final Statement dbStatement;
	
	String host, database, user, password;
	
	//Åbner forbindelsen til en database
	public Database(String host, String database, String user, String password) throws SQLException {
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException exn) {
			System.out.println("Could not find the path: " +exn);
		}
		
		connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
		dbStatement = connection.createStatement();
		
	}
	
	//sender en string til databasen, og returnerer det resultset, der kommer tilbage
	public ResultSet execute(String cmd) throws SQLException {
		boolean ok;
			ok = dbStatement.execute(cmd);
		if (ok) {
				return dbStatement.getResultSet();
		} else {
			return null;
		}
	}
	
	//lukker forbindelsen til databasen
	public void close() throws SQLException {
		connection.close();
		System.out.println("Database connection closed");
	}
	
	//finder alle airports, hvor der er tilknyttet en departure og returnerer dem i alfabetisk rækkefølge
	public ResultSet queryGetAirports() throws SQLException {
		String query;
		query = "SELECT " +
				"Airports.id, Airports.name " +
				"FROM " +
				"Airports, Departures " +
				"WHERE " +
				"Departures.departure_airport_id = Airports.id OR Departures.arrival_airport_id = Airports.id " +
				//"GROUP BY " +
				//"Airports.id " +
				"ORDER BY " +
				"Airports.name ASC";
		ResultSet rs = this.execute(query);
//		System.out.println(query);
		return rs;
	}
	
	public ResultSet queryGetReservedSeats(int departureId) throws SQLException {
		String query;
//										System.out.println("finding reserved seats from departureId "+departureId);
		query = "SELECT " +
				"Booking.seats " +
				"FROM " +
				"Booking " +
				"WHERE " +
				"Booking.departure_id = " + departureId;
		ResultSet rs = this.execute(query);
//		System.out.println(query);
		//while (rs.next()) {
		//	System.out.println(rs.getString("seats"));
		//}
		return rs;
				
	}
	
	public ResultSet queryGetAirplane(int departureId) throws SQLException {
		String query;
		query = "SELECT " +
				"airplane_id " +
				"FROM " +
				"Departures " +
				"WHERE " +
				"Departures.id = " + departureId;
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetRows(int departureId) throws SQLException {
		ResultSet airId = queryGetAirplane(departureId);
		airId.next();
		int airplaneId = airId.getInt("airplane_id"); 
		String query;
		query = "SELECT " +
				"Airplane.rows " +
				"FROM " +
				"Airplane " +
				"WHERE " +
				"Airplane.id = " + airplaneId;
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetCols(int departureId) throws SQLException {
		ResultSet airId = queryGetAirplane(departureId);
		airId.next();
		int airplaneId = airId.getInt("airplane_id");
		String query;
		query = "SELECT " +
				"Airplane.columns " +
				"FROM " +
				"Airplane " +
				"WHERE " +
				"Airplane.id = " + airplaneId;
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetEmptyCols(int departureId) throws SQLException {
		ResultSet airId = queryGetAirplane(departureId);
		airId.next();
		int airplaneId = airId.getInt("airplane_id");
		String query;
		query = "SELECT " +
				"Airplane.empty_columns " +
				"FROM " +
				"Airplane " +
				"WHERE " +
				"Airplane.id = " + airplaneId;
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetAirportId(String airport) throws SQLException {
		String query;
		query = "SELECT " +
				"Airports.id " +
				"FROM " +
				"Airports " +
				"WHERE " +
				"Airports.name = \"" + airport+"\"";
//		System.out.println(query);
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetDepartures(String date, int departureAirport, int arrivalAirport) throws SQLException {
		String query;
		query = "SELECT " +
				"* " +
				"FROM " +
				"Departures " +
				"WHERE " +
				"Departures.Departure_date = " + date + " " +
				"AND " +
				"Departures.departure_airport_id = " + departureAirport + " " +
				"AND " +
				"Departures.arrival_airport_id = " + arrivalAirport;
		ResultSet rs = this.execute(query);
		return rs;
	}

	public void queryMakeBooking(int departureId, int customerId, String seats, String passengerIds) throws SQLException {
		String query = "INSERT INTO Booking (departure_id, customer_id, seats, passenger-ids) " +
				"VALUES ('" + departureId + "', '" + customerId + "', '" + seats + "', '" + passengerIds + "'); ";
		this.execute(query);
	}
	
	public int queryMakeCustomer(Customer c) throws SQLException {
		//indsæt customer i databasen
		String query = "INSERT INTO Customer (Firstname, Surname, Address, City, Postal_Code, Country, Email, Phone_Number) " +
				"VALUES ('" + c.GetFirstname() + "', '" + c.GetSurname() + "', '" + c.GetAdress() + "', '" + c.GetCity() + "', '" + c.GetPostalCode() + "', '" + c.GetCountry() + "', '" + c.GetEmail() + "', '" + c.GetPhone() + "'); ";
		System.out.println(query);
		this.execute(query);
		
		//find customers givne id og returner det
		String query2 = "SELECT " +
				"id " +
				"FROM " +
				"Customer " +
				"WHERE " +
				"Customer.Firstname = '" + c.GetFirstname() + "' " +
				"AND " +
				"Customer.Surname = '" + c.GetSurname() + "' " +
				"AND " +
				"Customer.Phone_Number = '" + c.GetPhone() + "'";
		ResultSet rs = this.execute(query2);
		rs.next();
		return rs.getInt("id");
	}
	
	public int queryMakePassenger(Person p) throws SQLException {
		//indsæt passageren i databasen
		String query = "INSERT INTO Person (Firstname, Surname, Birthday) " +
				"VALUES ('" + p.GetFirstname() + "', '" + p.GetSurname() + "', '" + p.GetBirthday() + "'); ";
		this.execute(query);
		
		//find hans givne id og returner det
		String query2 = "SELECT " +
				"id " +
				"FROM " +
				"Person " +
				"WHERE " +
				"Person.Firstname = '" + p.GetFirstname() + "' " +
				"AND " +
				"Person.Surname = '" + p.GetSurname() + "' " +
				"AND " +
				"Person.Birthday = '" + p.GetBirthday() + "'";
		ResultSet rs = this.execute(query2);
		rs.next();
		return rs.getInt("id");
	}
}