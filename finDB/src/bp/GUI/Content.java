package bp.GUI;


import java.awt.Color;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bp.Jdbc.H2Connection;

public class Content extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton button;
	JTextField textField;
	JPanel panel;
	JLabel label;
	H2Connection dbConn;
	
	public Content() {
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		label = new JLabel();
		dbConn = new H2Connection();
		button = new JButton("Connect");
		button.addActionListener(e -> {
			dbConn.connect();
			if(dbConn.ConnStat) {
				label.setText("Die Verbindung war erfolgreich!");
				label.setForeground (Color.green);
			}else {
				label.setText("Verbindung fehlgeschlagen!");
				label.setForeground (Color.red);
			}
		});
		panel.add(button);
		panel.add(label);
		textField = new JTextField("", 15);
		button = new JButton("Select");
		button.addActionListener(e -> {
			String sql1 = "SELECT * FROM " + textField.getText();
			try {
				Statement statement = dbConn.getConn().createStatement();
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
		});
		panel.add(button);
		panel.add(textField);
		
		
		add(panel);
	}

}
