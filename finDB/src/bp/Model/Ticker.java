package bp.Model;

import java.net.MalformedURLException;
import java.net.URL;

public class Ticker {
	String symbol;
	URL yahooURL;
	String googleURL;
	String alphaURL;
	String crumb;

	public Ticker(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getYahooURL() {
		return "https://de.finance.yahoo.com/quote/" + getSymbol() + "?p=" + getSymbol();
	}

	public String getGoogleURL() {
		return googleURL;
	}

	public String getAlphaURL() {
		return alphaURL;
	}

	public String getCrumb() {
		return crumb;
	}

	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}

	public String getYahooDownloadLink() {
		return "https://query1.finance.yahoo.com/v7/finance/download/" + getSymbol() + "?period1=-3600&period2=1521414000&interval=1d&events=history&crumb=" + getCrumb();
	}
}
