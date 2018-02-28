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
	private static String path = "C:\\Users\\Marcel\\Downloads\\Testdaten\\";
	// exchanges
	private static String[] ticker = {"AMEX","NASDAQ","NYSE"};
	public static ArrayList<Ticker> tickerList;
	
	public static void main(String[]args) {
		download();
	}
	
	public static void download(){
		
		URL url;
		try {
			for (String symbol : ticker) {
				url = new URL("https://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=" + symbol + "&render=download");
				BufferedInputStream bis = new BufferedInputStream(url.openStream());
				FileOutputStream fos = new FileOutputStream(path + symbol + ".csv");
				byte[] buffer = new byte[1024];
				int count = 0;
				while((count = bis.read(buffer,0,1024)) != -1) {
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

	public static void csvFormatter() {
		tickerList = new ArrayList<Ticker>();
		String line = "";
		// use comma as separator
		String splitBy = ",";
		for (String symbol : ticker) {
			try(BufferedReader br = new BufferedReader(new FileReader(path + symbol + ".csv"))){
				while((line = br.readLine()) != null) {
					//TODO eliminate Taps
					if(!line.equals("Symbol")) {
						String[] tempTicker = line.split(splitBy);
						Ticker ticker = new Ticker(tempTicker[0], tempTicker[1]);
						tickerList.add(ticker);
					}
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			// ArrayList
			for (Ticker ticker : tickerList) {
				System.out.println(tickerList.size());
				//System.out.println(ticker.getSymbol());
				//System.out.println(ticker.getDescription());
			}
		}
	}
}
