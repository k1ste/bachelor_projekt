package bp.Jdbc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import bp.Controller.TickerController;
import bp.Controller.YahooController;
import bp.Model.Ticker;

public class MyConnection {
	
	static final String JDBC_Driver = "com.mysql.jdbc.Driver"; 
	static final String DB_URL = "jdbc:mysql://localhost/testba";
	
	static final String User = "root"; 
	static final String Pass = ""; 
	
//	public static void main (String[] args) {
	public static void connection() {
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		
		try {
//			Treiber initialisieren.
			Class.forName("com.mysql.jdbc.Driver");
			
//			Verbindung laden.
			System.out.println("Verbindung wird aufgebaut...");
			conn = DriverManager.getConnection(DB_URL, User, Pass);
			
			YahooController yc = new YahooController();
			TickerController tc = new TickerController(); 
			tc.download();
			
			for (int i=0; i<10; ++i) {
			
			Path path = Paths.get(tc.getTickerList().get(i).getSymbol());
			System.out.println(path);
			
			//date,open,high,low,close,volume,unadjustedVolume,change,changePercent,vwap,label,changeOverTime
			String CreateTables = "CREATE TABLE `" +tc.getTickerList().get(i).getSymbol()+ "` (`Date` varchar(255) NOT NULL, `Open` varchar(255) NOT NULL, `High` varchar(255) NOT NULL, `Low` varchar(255) NOT NULL, `Close` varchar(255) NOT NULL, `Volume` varchar(255) NOT NULL, `UnadjustedVolume` varchar(255) NOT NULL, `Change` varchar(255) NOT NULL, `ChangePercent` varchar(255) NOT NULL, `vwap` varchar(255) NOT NULL, `Label` varchar(255) NOT NULL, `ChangeOverTime` varchar(255) NOT NULL)";
			
//					"CREATE TABLE `" + tc.getTickerList().get(i).getSymbol() + "` (\r\n" + 
//					"  `Date` varchar(50) NOT NULL,\r\n" +
//					"  `Open` varchar(50) NOT NULL,\r\n" +
//					"  `High` varchar(50) NOT NULL,\r\n" +
//					"  `Low` varchar(50) NOT NULL,\r\n" +
//					"  `Close` varchar(50) NOT NULL,\r\n" +
//					"  `Volume` varchar(50) NOT NULL,\r\n" +
//					"  `UnadjustedVolume` varchar(50) NOT NULL,\r\n" +
//					"  `Change` varchar(50) NOT NULL,\r\n" +
//					"  `ChangePercent` varchar(50) NOT NULL,\r\n" +
//					"  `vwap` varchar(50) NOT NULL,\r\n" +
//					"  `Label` varchar(50) NOT NULL,\r\n" +
//					"  `ChangeOverTime` varchar(50) NOT NULL,\r\n" +
//					")";
//			
			
// 			Auslesen der .csv Dateien und erstellen der Insert- und Create-Befehle
			String QueryReader = "LOAD DATA LOCAL INFILE '" + tc.getTickerList().get(i).getSymbol() + ".csv" + "' INTO TABLE " + tc.getTickerList().get(i).getSymbol() + " FIELDS TERMINATED BY ','"
					+ " LINES TERMINATED BY '\n' (`Date`, `Open`, `High`, `Low`, `Close`, `Volume`, `UnadjustedVolume`, `Change`, `ChangePercent`, `vwap`, `Label`, `ChangeOverTime`) ";
//			Query aus PHP-MyAdmin:
//			INSERT INTO `aapltest` (`Date`, `Open`, `High`, `Low`, `Close`, `Adj Close`, `Volume`) VALUES ('', '', '', '', '', '', '')
			
			stmt = conn.createStatement();
			stmt.execute(CreateTables);
			stmt2 = conn.createStatement();
			stmt2.execute(QueryReader);
			}
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
