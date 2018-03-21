package bp.Model;

public class URLTickerMerge {
	private String url;
	private Ticker tickersymbol;
  private String crumb;
	
  public URLTickerMerge(String url, Ticker tickersymbol, String crumb) {
		this.url = url;
		this.tickersymbol = tickersymbol;
		this.setCrumb(crumb);
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
	public String getCrumb() {
		return crumb;
	}
	public void setCrumb(String crumb) {
		this.crumb = crumb;
	}
}
