package lapi.sss.jpm_test_sss.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * This classs contain the state of Trade
 * 
 * @author Michele Lapi
 *
 */
public class Trade implements SuperModel {
	private Timestamp timeStamp = null;
	private Stock stock = null;
	private TradeType tradeType = TradeType.BUY;
	private Integer quantity = 0;
	private Double price = 0.0;

	public Trade() {
		Date now = new Date();
		timeStamp = new Timestamp(now.getTime());
	}

	/**
	 * When a Trade is created, the timestamp is automatically setted
	 * 
	 * @param stock
	 * @param tradeType
	 * @param price
	 * @param quantity
	 */
	public Trade(Stock stock, TradeType tradeType, Double  price, Integer quantity) {
		this();
		this.stock = stock;
		this.tradeType = tradeType;
		this.price = price;
		this.quantity = quantity;
	}


	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}


	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer sharesQuantity) {
		this.quantity = sharesQuantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		
		if (obj instanceof Trade) {
			Trade t = (Trade)obj;
			
			if (t.getStock().equals(this.getStock()) &&
					t.getTimeStamp().compareTo(this.getTimeStamp())==0) {
				ret = true;
			}
		}
		
		return ret;
	}

	@Override
	public int hashCode() {
		int stockHashCode = stock != null ? stock.hashCode():-1;
		return stockHashCode * timeStamp.hashCode();
	}
	
	@Override
	public String toString() {
		String state = "Trade state [timeStamp: %tF %tT, stock: %s, indicator: %s, shares quantity: %7d, price: %8.2f]";
		return String.format(state, timeStamp, timeStamp, stock, tradeType, quantity, price);
	}

}
