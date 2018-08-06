package bp.GUI;


import java.awt.GridLayout;
import java.sql.SQLException;

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
			}else {
				label.setText("Verbindung fehlgeschlagen!");
			}
		});
		panel.add(button);
		panel.add(label);
		textField = new JTextField("", 15);
		button = new JButton("Select");
		button.addActionListener(e -> {
			String sql1 = "SELECT * FROM " + textField.getText();
			try {
				dbConn.getConn().createStatement().executeQuery(sql1);
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
