package bp.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class TickerController {
	// Path of the destination
	private static String path = "C:\\Users\\Marcel\\Downloads\\Testdaten\\";
	// exchanges
	private static String[] ticker = {"AMEX","NASDAQ","NYSE"};
	
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
		CSVFormatter();
		
	}

	public static void CSVFormatter() {
		
		String line = "";
		// use comma as separator
		String splitBy = ",";
		for (String symbol : ticker) {
			try(BufferedReader br = new BufferedReader(new FileReader(path + symbol + ".csv"))){
				while((line = br.readLine()) != null) {
					String[] tempTicker = line.split(splitBy);
					//TODO Transform Ticker into Object, eliminate Symbol-String and Taps
					System.out.println(tempTicker[0]);
				}
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
