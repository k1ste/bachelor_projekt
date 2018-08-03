package bp.Controller;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import bp.Model.*;

public class TickerController {
	
	private File exchanges;
	
	
	public TickerController() {
		exchanges = new File("Exchange");
		exchanges.mkdir();
		DownloadController dc = new DownloadController();
		download();
		sort();
		ListToCSV(tickerList);
		dc.downloadTicker(tickerList);
		
	}
	
	// exchanges
	private ArrayList<Exchange> ticker = new ArrayList<Exchange>();
	private ArrayList<Ticker> tickerList;

	public void download() {
		ticker.add(new Exchange("AMEX"));
		ticker.add(new Exchange("NASDAQ"));
		ticker.add(new Exchange("NYSE"));

		tickerList = new ArrayList<Ticker>();
		URL url;
		try {
			for (Exchange symbol : ticker) {
				url = new URL("https://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=" + symbol.getSymbol() + "&render=download");
				BufferedInputStream bis = new BufferedInputStream(url.openStream());
				FileOutputStream fos = new FileOutputStream(exchanges + "/" +symbol.getSymbol() + ".csv");
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
		for (Exchange exchange : ticker) {
			csvFormatter(exchange);
		}
	}

	public void csvFormatter(Exchange e) {
		Path p = Paths.get(exchanges + "/" + e.getSymbol());
		String line = "";
		// use comma as separator
		String splitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(p + ".csv"))) {
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
					if (!tempTicker[0].contains(".")) {
						if (!tempTicker[0].contains("^")) {
							Ticker ticker = new Ticker(tempTicker[0]);
							tickerList.add(ticker);
						}
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
	public void sort() {
		// Sorting arraylist in alphabetical order
		Collections.sort(tickerList, new Comparator<Ticker>() {
			@Override
			public int compare(Ticker item, Ticker t1) {
				String s1 = item.getSymbol();
				String s2 = t1.getSymbol();
				return s1.compareToIgnoreCase(s2);
			}
		});
	}

	public void ListToCSV(ArrayList<Ticker> toDo) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileWriter("AllTickerSymbols"));
			for(Ticker tick : toDo) {
				printWriter.println(tick.getSymbol());
			}
			printWriter.close();
		} catch (IOException e) {
			System.out.println("Fehler bei: " + toDo.toString());
			e.printStackTrace();
		}

	}

	public ArrayList<Ticker> getTickerList() {
		return tickerList;
	}

	public void setTickerList(ArrayList<Ticker> tickerList) {
		this.tickerList = tickerList;
	}

}
