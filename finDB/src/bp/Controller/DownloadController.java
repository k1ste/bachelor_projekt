package bp.Controller;

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

	public DownloadController() {
		tc = new TickerController();
		tc.download();
		downloadFirst30Ticker(tc.getTickerList());
	}

	public void downloadFirst30Ticker(ArrayList<Ticker> toDo) {
		for (int i = 0; i < 30; ++i) {
			try {
				downloadCSVFile(toDo.get(i));
				System.out.println(toDo.get(i).getIEXURL());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void downloadCSVFile(Ticker t) throws IOException {
		Path path = Paths.get(t.getSymbol()+".csv");
		
		if (Files.notExists(path))
		{
			CookieHandler.setDefault(new CookieManager());
			URL url = new URL(t.getIEXURL());
			conn = (HttpsURLConnection) url.openConnection();
			InputStream inputStream = conn.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(t.getSymbol()+ ".csv");
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
