package bp.Model;

public class Ticker {
	String symbol;
	String description;
	
	public Ticker(String symbol, String description) {
		this.symbol=symbol;
		this.description=description;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
