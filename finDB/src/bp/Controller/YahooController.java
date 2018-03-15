package bp.Controller;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import bp.Model.Ticker;
import bp.Model.URLTickerMerge;

public class YahooController {
	private ArrayList<URLTickerMerge> utmL;
	public YahooController() {

	}

	public void generateURLTickerMerge(){
		utmL = new ArrayList<URLTickerMerge>();
		TickerController tickerController = new TickerController();
		tickerController.download();
		ArrayList<Ticker> tickerList = tickerController.getTickerList();
		for(int i = 0;i< tickerList.size();++i){
			String urlString = "https://de.finance.yahoo.com/quote/" + tickerList.get(i).getSymbol() + "?p=" + tickerList.get(i).getSymbol();
			URLTickerMerge utm = new URLTickerMerge(urlString, tickerList.get(i));
			utmL.add(utm);
		}
		
		
		for (URLTickerMerge utm : utmL) {
			try {
				saveUrl(utm.getTickersymbol().getSymbol(), utm.getUrl());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveUrl(final String filename, final String urlString) throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);

			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
	}
}
