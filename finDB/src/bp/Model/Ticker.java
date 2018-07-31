package bp.Model;

public class Ticker {
	String symbol;
	String alphaURL;

	public Ticker(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getAlphaURL() {
		return "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol="+ getSymbol()+"&apikey=P1LGMQ36175FIJIL&datatype=csv";
	}
	
	public String getIEXURL(){
		return "https://api.iextrading.com/1.0/stock/"+getSymbol()+"/chart/1d?format=csv";
	}
}
