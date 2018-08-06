package bp.Controller;

import java.util.ArrayList;

import bp.Model.*;

public class MainController {
	TickerController tc;
	DownloadController dc;

	public void start() {
		tc = new TickerController();
		dc = new DownloadController();
		tc.download();
		tc.sort();
		tc.ListToCSV(tc.getTickerList());
		dc.downloadTicker(tc.getTickerList());

	}

	public ArrayList<Ticker> getList() {
		return tc.getTickerList();
	}

}
