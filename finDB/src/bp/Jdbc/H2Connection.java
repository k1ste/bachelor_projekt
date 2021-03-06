package bp.Jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bp.Controller.TickerController;
import bp.Model.Ticker;

public class H2Connection {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/test1";

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";
	Connection conn;
	Statement stmt;
	public boolean ConnStat;
	TickerController tc;
	// Standard Konstruktor
	public H2Connection() {
		conn = null;
		stmt = null;
	}

	public void connect() {
		try {
			// JDBC driver
			try {
				Class.forName(JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Open connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			ConnStat = true;
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		}
	}
	
	public void selectAll(String textField) {
		String sql1 = "SELECT * FROM " + textField;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql1);
			//Statement rs = dbConn.getConn().createStatement().executeQuery(sql1);
			int columns = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columns; i++) {
	                System.out.print(rs.getString(i) + "\t\t");
	            }
	            System.out.println();
		}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void createTableFromCSV(File f) throws SQLException {
		String fileName = f.getName();
		String out = fileName.substring(0, fileName.indexOf('.'));
		stmt = conn.createStatement();
		
		String sql1 = "DROP TABLE IF EXISTS " + out+";";
		String sql2 = "CREATE TABLE " + out + " AS SELECT * FROM CSVREAD('" + f.getAbsolutePath() + "');";
		
		stmt.executeUpdate(sql1);
		stmt.executeUpdate(sql2);
	}

	public void connectCreateTables(ArrayList<Ticker> tList) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// FÃ¼r jede Datei im Ornder eine Tabelle anlegen
		ArrayList<Ticker> temp = new ArrayList<Ticker>();
		temp.addAll(tList);
		for (Ticker t : temp) {
			System.out.println(t.getSymbol());
			String sql1 = "DROP TABLE IF EXISTS " + t.getSymbol()+";";
			String sql2 = "CREATE TABLE " + t.getSymbol() + " AS SELECT * FROM CSVREAD('Ticker/" + t.getSymbol()
					+ ".csv');";
			try {
				stmt.executeUpdate(sql1);
				stmt.executeUpdate(sql2);				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Execute Update: " + t.getSymbol());			
		}
	}

	public void disconnect() {
		try {
			System.out.println(conn.isClosed());
			if (conn != null)
				conn.close();
			System.out.println("Connection closed");
			ConnStat = false;
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public Statement getStmt() {
		return stmt;
	}
}
