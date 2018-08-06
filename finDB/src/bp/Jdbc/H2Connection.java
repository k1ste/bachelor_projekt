package bp.Jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Connection {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test1";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";
	public void connectCreateTables() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			// Für jede Datei im Ornder eine Tabelle anlegen
			File f = new File("Ticker");
			File[] fileArray = f.listFiles();
			for (File file : fileArray) {
				if (!file.getName().equals("ALL.csv") && !file.getName().equals("FOR.csv")) {
					String sql1 = "DROP TABLE IF EXISTS " + file.getName().substring(0, file.getName().indexOf('.'));
					String sql2 = "CREATE TABLE " + file.getName().substring(0, file.getName().indexOf('.')) + " AS SELECT * FROM CSVREAD('Ticker/" + file.getName() + "');";
					stmt.executeUpdate(sql1);
					System.out.println("Execute Update: " + file.getName());
					stmt.executeUpdate(sql2);
				}
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
		try {
			System.out.println(conn.isClosed());
			if (conn != null)
				conn.close();
			System.out.println("Connection closed");
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}
