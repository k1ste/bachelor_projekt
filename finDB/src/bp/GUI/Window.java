package bp.GUI;

import javax.swing.*;

public class Window extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFrame frame;
	Content content;
	private String title = "Datenbank v1";
	
	public Window() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame = new JFrame(title);
		setSize(400, 400);
//		// MenuBar
//		JMenuBar menuBar = new JMenuBar();
//		// Menu 
//		JMenu menu = new JMenu("Select...");
//		menuBar.add(menu);
//		// Items
//		JMenuItem item = new JMenuItem("Item");
//		menu.add(item);
//		frame.setJMenuBar(menuBar);
		content = new Content();
		add(content);
		setVisible(true);
		
		
	}
	
}
