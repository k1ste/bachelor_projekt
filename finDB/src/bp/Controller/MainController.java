package bp.Controller;

public class MainController {

	public void start() {
		TickerController tc = new TickerController();
		DownloadController dc = new DownloadController();
		tc.download();
		tc.sort();
		tc.ListToCSV(tc.getTickerList());
		dc.downloadTicker(tc.getTickerList());
		
	}
	
}
