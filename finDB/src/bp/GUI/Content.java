package bp.GUI;


import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
	JFileChooser chooser;
	JTextField upload;
	JLabel label1;
	
	public Content() {
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
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
			dbConn.selectAll(textField.getText());
		});
		panel.add(button);
		panel.add(textField);
		button = new JButton("Öffnen");
		upload = new JTextField("", 15);
		button.addActionListener(e -> {
			chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			upload.setText(chooser.getSelectedFile().getAbsolutePath());
		});
		panel.add(button);
		panel.add(upload);
		button = new JButton("Erstellen");
		label1 = new JLabel();
		button.addActionListener(e -> {
			File f = new File(upload.getText());
			try {
				dbConn.createTableFromCSV(f);
				label1.setText("Erfolgreich!");
				label.setForeground (Color.green);
			} catch (SQLException e1) {
				e1.printStackTrace();
				label1.setText("Fehlgeschlagen!");
				label.setForeground (Color.red);
			}
		});
		panel.add(button);
		panel.add(label1);
		add(panel);
	}

}
