package bp.Controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.Oneway;

import bp.Model.Ticker;

public class YahooController {
	private ArrayList<Ticker> errorList;
	public TickerController tc;
	public YahooController() {
		tc = new TickerController();
		tc.download();
		errorList = new ArrayList<>();
		errorList.clear();
		doSomething(tc.getTickerList());
	}

	public void addTickerToErrorList(Ticker t) {
		geterrorList().add(t);
	}

	public void doSomething(ArrayList<Ticker> toDo) {
		int i = 0;
		for (Ticker ticker : toDo) {
			if (i < 30) {
				test(ticker);
				try {
					downloadFile(ticker);					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			++i;
		}
		for (Ticker ticker : geterrorList()) {
			System.out.println(ticker.getSymbol());
		}
	}

	public void downloadFile(Ticker t) throws IOException {
		URL url = new URL(t.getYahooDownloadLink());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int responseCode = con.getResponseCode();
		Path p = Paths.get(t.getSymbol() + ".csv");
		boolean exists = Files.exists(p);
		if (!exists) {
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = con.getInputStream();
				FileOutputStream outputStream = new FileOutputStream(t.getSymbol() + ".csv");

				int bytesRead = -1;
				byte[] buffer = new byte[4096];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				outputStream.close();
				inputStream.close();
				System.out.println(t.getSymbol() + ".csv downloaded");
			} else {
				addTickerToErrorList(t);
				System.out.println("Symbol: " + t.getSymbol() + " , " + con.getResponseMessage() + " , Code: " + con.getResponseCode());
			}
			con.disconnect();
		} else {
			System.out.println("File: " + t.getSymbol() + " already exists");
		}
	}

	public ArrayList<Ticker> geterrorList() {
		return errorList;
	}

	public boolean getTickerFromErrorList(String symbol) {
		for (Ticker ticker : geterrorList()) {
			if (ticker.getSymbol().equals(symbol)) {
				return true;
			}
		}
		return false;
	}

	public void test(Ticker t) {
		try {
			CookieManager cm = new CookieManager();
			cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
			CookieHandler.setDefault(cm);
			HttpURLConnection connection = (HttpURLConnection) new URL(t.getYahooURL()).openConnection();
			InputStream stream = connection.getInputStream();
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				String crumb = null;
				Pattern crumbPattern = Pattern.compile(".*\"CrumbStore\":\\{\"crumb\":\"([^\"]+)\"\\}.*");
				String inputLine;
				Matcher m;
				while ((inputLine = in.readLine()) != null) {
					m = crumbPattern.matcher(inputLine);
					if (m.matches()) {
						crumb = m.group(1);
						t.setCrumb(crumb);
					}

				}
				in.close();
			} else {
				System.out.println("Symbol: " + t.getSymbol() + " , Http code:" + responseCode);
			}
			connection.disconnect();
		} catch (Exception e) {
		}
	}
}
