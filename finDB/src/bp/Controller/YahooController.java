package bp.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bp.Model.Ticker;
import bp.Model.URLTickerMerge;

public class YahooController {
	private ArrayList<URLTickerMerge> utmL;
	public YahooController() {

	}

	public void generateURLTickerMerge() throws FileNotFoundException {
		utmL = new ArrayList<URLTickerMerge>();
		TickerController tickerController = new TickerController();
		tickerController.download();
		ArrayList<Ticker> tickerList = tickerController.getTickerList();
		System.out.print(tickerList.size());
		for (int i = 0; i < tickerList.size(); ++i) {
			String urlString = "https://de.finance.yahoo.com/quote/" + tickerList.get(i).getSymbol() + "?p=" + tickerList.get(i).getSymbol();
			URLTickerMerge utm = new URLTickerMerge(urlString, tickerList.get(i), null);
			utmL.add(utm);
		}

		for (URLTickerMerge utm : utmL) {
			File f = new File(utm.getTickersymbol().getSymbol());
			if (f.exists()) {
				utm.setCrumb(readFile(utm.getTickersymbol().getSymbol(), f));
			} else if (!f.exists()) {
				try {
					downloadURL(utm.getTickersymbol().getSymbol(), utm.getUrl());
					utm.setCrumb(readFile(utm.getTickersymbol().getSymbol(), f));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void downloadURL(String symbol, String urlString) throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(symbol);

			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
			fout.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public String readFile(String filename, File f) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner fileScanner = new Scanner(f);
		String crumb = null;
		Pattern crumbPattern = Pattern.compile(".*\"CrumbStore\":\\{\"crumb\":\"([^\"]+)\"\\}.*");
		if (f.exists()) {
			while (fileScanner.hasNext()) {
				String line = fileScanner.next();
				Matcher matcher = crumbPattern.matcher(line);
				if (matcher.matches())
					crumb = matcher.group(1);

			}
		}
		return crumb;
	}
}
