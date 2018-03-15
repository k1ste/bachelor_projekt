package bp.Controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import bp.Model.Ticker;

public class YahooController {
	private ArrayList<URL> urlList;

	public URL getURLfromTicker() throws IOException {
		this.urlList = new ArrayList<URL>();
		TickerController tickerController = new TickerController();
		tickerController.download();
		ArrayList<Ticker> tickerList = tickerController.getTickerList();

		for (Ticker tick : tickerList) {
			try {
				URL u = new URL("https://de.finance.yahoo.com/quote/" + tick.getSymbol() + "?p=" + tick.getSymbol());
				urlList.add(u);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		for (URL u : urlList) {
			System.out.println(u);
		}
		return null;
	}

	public ArrayList<URL> getUrlList() {
		return urlList;
	}

	public void setUrlList(ArrayList<URL> urlList) {
		this.urlList = urlList;
	}
}
