package bp.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import bp.Model.Ticker;

public class YahooController {
	private ArrayList<URL> urlList;
	
	public URL getURLfromTicker(Ticker ticker){
		this.urlList = new ArrayList<URL>();
		TickerController tickerController = new TickerController();
		ArrayList<Ticker> tickerList = tickerController.getTickerList();
		for (Ticker tick: tickerList){
			try {
				URL u = new URL("https://de.finance.yahoo.com/quote/" + tick.getSymbol() + "?p=" + tick.getSymbol());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
			
		return null;
		
	}
}
