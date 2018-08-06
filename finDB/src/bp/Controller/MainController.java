package bp.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import bp.Jdbc.H2Connection;
import bp.Model.Ticker;

public class MainController {

	public void start() {
		TickerController tc = new TickerController();
		tc.startController();
		H2Connection h2 = new H2Connection();
		h2.connect();
		h2.connectCreateTables(tc.getTickerList());
		h2.disconnect();
		ArrayList<Ticker> TL = new ArrayList<Ticker>();
		String csvFile = "C:\\Users\\Felix\\git\\bachelor_projekt\\finDB\\AllTickerSymbols";
		BufferedReader br = null;
		String line = "";

		try {
			br = new BufferedReader(new FileReader(csvFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (br.readLine() != null) {
				try {
					TL.add(new Ticker(br.readLine()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Ticker ticker : TL) {
			System.out.println(ticker.getSymbol());
		}
	}
}
