package bp.Model;

public class Exchange {
	private String symbol;
	public Exchange(String symbol) {
		this.setSymbol(symbol);
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
