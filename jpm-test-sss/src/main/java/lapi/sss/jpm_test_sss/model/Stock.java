package lapi.sss.jpm_test_sss.model;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * This class contains the state of a Stock
 * 
 * @author Michele Lapi
 * 
 */
public class Stock implements SuperModel {
	private Logger log = Logger.getLogger(Stock.class);

	private String stockSymbol = null;
	private StockType stockType = StockType.COMMON;
	private Double lastDividend = 0.0;
	private Double fixedDividend = 0.0;
	private Double parValue = 0.0;
	private Double tickerPrice = 0.0;
	
	/**
	 * This list contains all the changing on tickerPrice 
	 */
	private List<Double> tickerPriceLog = new ArrayList<Double>();

	public Stock() {
		
	}
	
	public Stock(String stockSimbol) {
		this.stockSymbol = stockSimbol;
	}

	public Stock(String stockSimbol, StockType stockType, Double lastDividend, Double fixedDividend, Double parValue, Double tickerPrice) {
		this.stockSymbol = stockSimbol;
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;		
		this.setTickerPrice(tickerPrice);
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public Double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(Double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public Double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public Double getParValue() {
		return parValue;
	}

	public void setParValue(Double parValue) {
		this.parValue = parValue;
	}

	public Double getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(Double tickerPrice) {
		if (tickerPrice != null && !tickerPrice.equals(this.tickerPrice)) {
			this.tickerPriceLog.add(tickerPrice);
		}
		
		this.tickerPrice = tickerPrice;		
	}

	@Override
	public boolean equals(Object obj) {
		boolean ret = false;
		
		if (obj instanceof Stock) {
			Stock s = (Stock)obj;
			ret = this.getStockSymbol().equals(s.getStockSymbol());
		}
		
		return ret;
	}
	
	@Override
	public int hashCode() {
		return this.stockSymbol.hashCode();
	}
	
	@Override
	public String toString() {
		String state = "Stock state [stockSymbol: %s, stockType: %s, lastDividend: %7.0f, fixedDividend: %7.2f, parValue: %7.2f, tickerPrice: %7.2f]";
		return String.format(state, stockSymbol, stockType, lastDividend, fixedDividend, parValue, tickerPrice);
	}

	
}
