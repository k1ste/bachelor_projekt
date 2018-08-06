package bp.Controller;

import bp.Jdbc.H2Connection;

public class MainController {

	public void start() {
		TickerController tc = new TickerController();
		tc.startController();
		H2Connection h2 = new H2Connection();
		h2.connect();
		h2.connectCreateTables(tc.getTickerList());
		h2.disconnect();
	}	
}
