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
		boolean ok = dbStatement.execute(cmd);
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
		System.out.println(query);
		while (rs.next()) {
			System.out.println(rs.getString("name"));
		}
		return rs;
	}
	
	public ResultSet queryGetReservedSeats(int departureId) throws SQLException {
		String query;
		query = "SELECT " +
				"Booking.seats " +
				"FROM " +
				"Booking " +
				"WHERE " +
				"Booking.departure_id = " + departureId;
		ResultSet rs = this.execute(query);
		System.out.println(query);
		//while (rs.next()) {
		//	System.out.println(rs.getString("seats"));
		//}
		return rs;
				
	}
	
	public ResultSet queryGetRows(int departureId) throws SQLException {
		String query;
		query = "SELECT " +
				"Airplane.rows " +
				"FROM " +
				"Airplane " +
				"WHERE " +
				"Airplane.id = " + departureId;
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetCols(int departureId) throws SQLException {
		String query;
		query = "SELECT " +
				"Airplane.columns " +
				"FROM " +
				"Airplane " +
				"WHERE " +
				"Airplane.id = " + departureId;
		ResultSet rs = this.execute(query);
		return rs;
	}
	
	public ResultSet queryGetEmptyCols(int departureId) throws SQLException {
		String query;
		query = "SELECT " +
				"Airplane.empty_columns " +
				"FROM " +
				"Airplane " +
				"WHERE " +
				"Airplane.id = " + departureId;
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
		System.out.println(query);
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
}
