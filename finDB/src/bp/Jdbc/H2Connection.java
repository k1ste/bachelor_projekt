package bp.Jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bp.Controller.MainController;
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

	public void connectCreateTables() {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Für jede Datei im Ornder eine Tabelle anlegen
		ArrayList<Ticker> temp = new ArrayList<Ticker>();
		MainController tc = new MainController();
		temp.addAll(tc.getList());
		for (Ticker t : temp) {
			// if (!file.getName().equals("ALL.csv") && !file.getName().equals("FOR.csv")) {
			String sql1 = "UPDATE " + t.getSymbol() + "IF EXISTS " ;
			String sql2 = "CREATE TABLE " + t.getSymbol() + " AS SELECT * FROM CSVREAD('Ticker/" + t.getSymbol()
					+ ".csv');";
			try {
				stmt.executeUpdate(sql1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Execute Update: " + t.getSymbol());
			try {
				stmt.executeUpdate(sql2);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
