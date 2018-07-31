package bp.Jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MyConnection {
	
	static final String JDBC_Driver = "com.mysql.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://localhost/testba";
	
	static final String User = "root"; 
	static final String Pass = ""; 
	
//	public static void main (String[] args) {
	public static void connection() {
		Connection conn = null;
		Statement stmt = null;
		
		try {
//			Treiber initialisieren.
			Class.forName("com.mysql.jdbc.Driver");
			
//			Verbindung laden.
			System.out.println("Verbindung wird aufgebaut...");
			conn = DriverManager.getConnection(DB_URL, User, Pass);
						
// 			Auslesen der .csv Dateien und erstellen der Insert- und Create-Befehle
			String QueryReader = "LOAD DATA LOCAL INFILE '" + "C:/Users/Felix/Downloads/AAPL.csv" + "' INTO TABLE aapltest FIELDS TERMINATED BY ','"
					+ " LINES TERMINATED BY '\n' (`Date`, `Open`, `High`, `Low`, `Close`, `Adj Close`, `Volume`) ";
//			Query aus PHP-MyAdmin:
//			INSERT INTO `aapltest` (`Date`, `Open`, `High`, `Low`, `Close`, `Adj Close`, `Volume`) VALUES ('', '', '', '', '', '', '')
			
			stmt = conn.createStatement();
			stmt.execute(QueryReader);
			
//			Statement/Query ausführen
//			System.out.println("Query wird übermittelt.");
//			stmt = ((java.sql.Connection) conn).createStatement();
//			String sql;
//			sql = "SELECT * FROM testba";
//			ResultSet rs = stmt.executeQuery(sql);
			
// 			Daten auslesen und anzeigen
//			while (rs.next()) {
//				int Ident = rs.getInt("Ident");
//				String Content = rs.getString("Content");
//				
//				System.out.println("Ident: " + Ident);
//				System.out.println("Content: " + Content);
//				System.out.println();
//			}
			
// 			"Aufräumen"
//			rs.close();
			stmt.close();
			conn.close();
			System.out.println("Done!");
		} 

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
