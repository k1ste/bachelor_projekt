package bp.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
	
	static final String JDBC_Driver = "com.mysql.jdbc.Driver"; 
	static final String DB_URL = "";
	
	static final String User = ""; 
	static final String Pass = ""; 
	
	public void connect() {
		Connection conn = null;
		Statement stmt = null;
		
		try {
//			Treiber initialisieren.
			Class.forName("com.mysql.jdbc.Driver");
			
//			Verbindung laden.
			System.out.println("Verbindung wird aufgebaut...");
			conn = (Connection) DriverManager.getConnection(DB_URL, User, Pass);
			
//			Statement/Query ausführen
			System.out.println("Query wird übermittelt.");
			stmt = ((java.sql.Connection) conn).createStatement();
			String sql;
			sql = "SELECT * FROM TABLE";
			ResultSet rs = stmt.executeQuery(sql);
		} 

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
