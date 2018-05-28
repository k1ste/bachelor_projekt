package bp.Controller;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bp.Model.Ticker;

public class YahooController implements IDownload {
	public TickerController tc;
	public YahooController() {
		tc = new TickerController();
		tc.download();
		downloadFirst30Ticker(tc.getTickerList());
	}

	public void downloadFirst30Ticker(ArrayList<Ticker> toDo) {
		int i = 0;
		for (Ticker ticker : toDo) {
			if (i < 30) {
				getCrumb(ticker);
				try {
					downloadCSVFile(ticker);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			++i;
		}
	}

	public void downloadCSVFile(Ticker t) throws IOException {
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
				System.out.println("Symbol: " + t.getSymbol() + " , " + con.getResponseMessage() + " , Code: " + con.getResponseCode());
			}
			con.disconnect();
		} else {
			System.out.println("File: " + t.getSymbol() + " already exists");
		}
	}

	public void getCrumb(Ticker t) {
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
