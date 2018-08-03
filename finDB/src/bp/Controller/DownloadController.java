package bp.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

import bp.Model.Ticker;

public class DownloadController{
	public TickerController tc;
	private HttpsURLConnection conn;

	public void downloadTicker(ArrayList<Ticker> toDo) {
		for(Ticker tick : toDo) {
			try {
				downloadCSVFile(tick);
				System.out.println(tick.getSymbol());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void downloadCSVFile(Ticker t) throws IOException {
		File ticker = new File("Ticker");
		ticker.mkdir();
		
		Path path = Paths.get(ticker + "/" + t.getSymbol()+ ".csv");
		if (Files.notExists(path))
		{
			CookieHandler.setDefault(new CookieManager());
			URL url = new URL(t.getIEXURL());
			conn = (HttpsURLConnection) url.openConnection();
			InputStream inputStream = conn.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(ticker + "/" + t.getSymbol() + ".csv");
			int bytesRead = -1;
			byte[] buffer = new byte[4096];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
			conn.disconnect();
		}else{
			System.out.println("File already exist");
		}	
	}
}
