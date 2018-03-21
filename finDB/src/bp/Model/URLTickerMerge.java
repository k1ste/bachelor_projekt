package bp.Model;

public class URLTickerMerge {
	private String url;
	private Ticker tickersymbol;

	public URLTickerMerge(String url, Ticker tickersymbol) {
		this.url = url;
		this.tickersymbol = tickersymbol;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Ticker getTickersymbol() {
		return tickersymbol;
	}
	public void setTickersymbol(Ticker tickersymbol) {
		this.tickersymbol = tickersymbol;
	}
}
