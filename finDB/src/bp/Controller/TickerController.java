package bp.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import bp.Model.*;

public class TickerController {
	// Path of the destination
	private String path = "C:\\Users\\marce\\Downloads\\Testdaten\\";
	// exchanges
	private String[] ticker = {"AMEX", "NASDAQ", "NYSE"};
	public ArrayList<Ticker> tickerList;

	public void download() {

		URL url;
		try {
			for (String symbol : ticker) {
				url = new URL("https://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=" + symbol + "&render=download");
				BufferedInputStream bis = new BufferedInputStream(url.openStream());
				FileOutputStream fos = new FileOutputStream(path + symbol + ".csv");
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = bis.read(buffer, 0, 1024)) != -1) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				bis.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		csvFormatter();

	}

	public void csvFormatter() {
		tickerList = new ArrayList<Ticker>();
		String line = "";
		// use comma as separator
		String splitBy = ",";
		for (String symbol : ticker) {
			try (BufferedReader br = new BufferedReader(new FileReader(path + symbol + ".csv"))) {
				while ((line = br.readLine()) != null) {
					// TODO eliminate Taps
					if (!line.contains("Symbol")) {
						line = line.replace(" ", "");
						// remove all tabs
						line = line.replace("\t", "");
						// remove all double quotes
						line = line.replace("\"", "");
						// split the string after every ","
						String[] tempTicker = line.split(splitBy);
						if(!tempTicker[0].contains(".")){
							if(!tempTicker[0].contains("^")) {
								Ticker ticker = new Ticker(tempTicker[0]);
								tickerList.add(ticker);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ArrayList
			for (Ticker ticker : tickerList) {
				// System.out.println(tickerList.size());
				System.out.println(ticker.getSymbol());
				// System.out.println(ticker.getDescription());
			}
		}
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String[] getTicker() {
		return ticker;
	}

	public void setTicker(String[] ticker) {
		this.ticker = ticker;
	}

	public ArrayList<Ticker> getTickerList() {
		return tickerList;
	}

	public void setTickerList(ArrayList<Ticker> tickerList) {
		this.tickerList = tickerList;
	}

}
