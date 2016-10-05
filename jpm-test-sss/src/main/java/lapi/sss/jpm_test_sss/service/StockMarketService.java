package lapi.sss.jpm_test_sss.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;


import lapi.sss.jpm_test_sss.dao.DBFakeManager;
import lapi.sss.jpm_test_sss.exception.JPMException;
import lapi.sss.jpm_test_sss.model.Stock;
import lapi.sss.jpm_test_sss.model.StockType;
import lapi.sss.jpm_test_sss.model.Trade;
import lapi.sss.jpm_test_sss.util.JPMUtil;

/**
 * 
 * Classe per la gestione dei ruoli
 * 
 * @author Michele Lapi
 *
 */
public class StockMarketService {
	final static Logger log = Logger.getLogger(StockMarketService.class);

	private static StockMarketService calciatoreService;
	private static DBFakeManager dbFakeManager;

	/**
	 * Insert in this method everything have to be setting when StockService
	 * instance is created
	 */
	private static void initialize() {
		dbFakeManager = DBFakeManager.getInstance();
	}

	/**
	 * This method build and return a single instance of DBFakeManager
	 * 
	 * @return
	 */
	public static StockMarketService getInstance() {
		if (calciatoreService == null) {
			calciatoreService = new StockMarketService();
			initialize();
		}

		return calciatoreService;
	}	
	
	/**
	 * This method calculate the Dividend Yield for a Stock
	 * 
	 * @return
	 */
	private Double getDividendYield(Stock stock) {
		Double dividendYield = -1.0;
		
		if (stock.getTickerPrice() > 0.0) {
			if (stock.getStockType() == StockType.COMMON) {
				// COMMON
				dividendYield = stock.getLastDividend() / stock.getTickerPrice();
			} else {
				// PREFERRED
				dividendYield = (stock.getFixedDividend() * stock.getParValue()) / stock.getTickerPrice();
			}
		}
		return dividendYield;
	}

	/**
	 * This method calculate the P/E Ratio for a Stock
	 * @return
	 */
	private Double getPeRatio(Stock stock) {
		Double peRatio = -1.0;

		if (stock.getTickerPrice() > 0.0) {
			peRatio = stock.getTickerPrice() / getDividendYield(stock);
		}

		return peRatio;
	}
	
	/**
	 * Remove all the elements into storage
	 */
	public void clearStorage() {
		dbFakeManager.clearAll();
	}
	
	/**
	 * Calculate the Dividend Yield for a stock symbol
	 * 
	 * @param stockSymbol
	 * @return
	 * @throws Exception
	 */
	public Double calculateDividendYield(String stockSymbol) throws Exception{
		Double dividendYield = -1.0;

		try{
			
			Optional<Stock> optStock = dbFakeManager.getAllStocks().stream()
					.filter(stock -> stock.getStockSymbol().equals(stockSymbol))
					.findFirst();
			
			if (optStock.isPresent()) {
				dividendYield = getDividendYield(optStock.get());
				
			} else {
				throw new JPMException("There are not stock with the symbol "+stockSymbol);
			}

			log.debug("Dividend Yield calculated for stock symbol:"+stockSymbol+" is: "+dividendYield);

		}catch(Exception exception){
			throw new Exception("Error calculating Dividend Yield for stock symbol: "+stockSymbol+".", exception);
		}

		return dividendYield;
	}

	/**
	 * Calculate the P/E Ratio for a stock symbol
	 * 
	 * @param stockSymbol
	 * @return
	 * @throws Exception
	 */
	public Double calculatePERatio(String stockSymbol) throws Exception{
		Double peRatio = -1.0;

		try{
			
			Optional<Stock> optStock = dbFakeManager.getAllStocks().stream()
					.filter(stock -> stock.getStockSymbol().equals(stockSymbol))
					.findFirst();
			
			if (optStock.isPresent()) {
				if (optStock.get().getLastDividend() > 0.0) {
					peRatio = getPeRatio(optStock.get());
				} else {
					throw new JPMException("It is not possible calculate the P/E Ratio of this Stock: "+stockSymbol + " because the last dividend is not greater than zero.");
				}
				
			} else {
				throw new JPMException("There are not stock with the symbol "+stockSymbol);
			}

			log.debug("P/E Ratio calculated for stock symbol:"+stockSymbol+" is: "+peRatio);

		}catch(Exception exception){
			throw new Exception("Error calculating P/E Ratio for stock symbol: "+stockSymbol+".", exception);
		}

		return peRatio;
	}

	
	/**
	 * Delete a Stock.
	 * Two stock are same if have both the same StockSymbol 
	 * 
	 * @param trade
	 * @throws JPMException
	 * @throws Exception
	 */
	public void deleteStock(Stock stock) throws JPMException, Exception{
		try{
			log.debug("Delete a Stock: "+stock);

			dbFakeManager.delete(stock);

		}catch(Exception exception){
			throw new Exception("Error while delete a stock.", exception);
		}
	}

	/**
	 * Get a Stock from the storage
	 * 
	 * @param stock
	 * @return
	 * @throws JPMException
	 * @throws Exception
	 */
	public Stock getStock(Stock stock) throws JPMException, Exception {
		Stock ret = null;
		try{
			ret = (Stock)dbFakeManager.getByKey(stock);

		}catch(Exception exception){
			throw new Exception("Error while get a stock from storage.", exception);
		}
		
		return stock;
	}	
	
	/**
	 * Get a Trade from the storage
	 * 
	 * @param trade
	 * @return
	 * @throws JPMException
	 * @throws Exception
	 */
	public Trade getTrade(Trade trade) throws JPMException, Exception {
		Trade ret = null;
		try{
			ret = (Trade)dbFakeManager.getByKey(trade);

		}catch(Exception exception){
			throw new Exception("Error while get a trade from storage.", exception);
		}
		
		return trade;
	}	

	/**
	 * Save or update a Stock
	 * 
	 * @param trade
	 * @throws JPMException
	 * @throws Exception
	 */
	public void saveUpdateStock(Stock stock) throws JPMException, Exception{
		try{

			//Trade object can not be null
			if(stock==null){
				throw new JPMException("Trade object can not be null.");
			}

			//Stock symbol can not be null
			if(stock.getStockSymbol() == null){
				throw new JPMException("Stock symbol can not be null.");
			}

			//Stock type can not be null
			if(stock.getStockType() == null){
				throw new JPMException("Stock type can not be null.");
			}

			//Last dividend value in Stock have to be greater than zero
			if(stock.getLastDividend()<0.0){
				throw new JPMException("Last dividend value in Stock have to be greater or equal than zero.");
			}
			//Par value in Stock have to be greater than zero
			if(stock.getParValue()<=0.0){
				throw new JPMException("Par value in Stock have to be greater than zero.");
			}

			log.debug("SaveUpdate a Stock: "+stock);

			dbFakeManager.saveUpdate(stock);

		}catch(Exception exception){
			throw new Exception("Error while save or update a stock.", exception);
		}
	}	
	
	/**
	 * Delete a Trade.
	 * Two trade are same if have both the same StockSymbol and TimeStamp
	 * 
	 * @param trade
	 * @throws JPMException
	 * @throws Exception
	 */
	public void deleteTrade(Trade trade) throws JPMException, Exception{
		try{
			log.debug("Delete a Trade: "+trade);

			dbFakeManager.delete(trade);

		}catch(Exception exception){
			throw new Exception("Error while delete a trade.", exception);
		}
	}

	/**
	 * Save or update a Trade
	 * 
	 * @param trade
	 * @throws JPMException
	 * @throws Exception
	 */
	public void saveUpdateTrade(Trade trade) throws JPMException, Exception{
		try{

			//Trade object can not be null
			if(trade==null){
				throw new JPMException("Trade object can not be null.");
			}

			//Stock object in Trade can not be null
			if(trade.getStock()==null){
				throw new JPMException("Stock object in Trade can not be null");
			}

			//Quantity value in Trade have to be greater than zero
			if(trade.getQuantity()<=0){
				throw new JPMException("Quantity value in Trade have to be greater than zero.");
			}

			//Price value in Trade have to be greater than zero
			if(trade.getPrice()<=0.0){
				throw new JPMException("Price value in Trade have to be greater than zero.");
			}

			log.debug("SaveUpdate a Trade: "+trade);

			dbFakeManager.saveUpdate(trade);

			trade.getStock().setTickerPrice(trade.getPrice());


		}catch(Exception exception){
			throw new Exception("Error while save or update a trade.", exception);
		}
	}
	
	private static Predicate<Trade> checkMinutesBetweenTrades(int mins) {
		return value -> value.getTimeStamp().compareTo(JPMUtil.getNMinutesAgo(mins)) >= 0;
	}
	
	public double calculateVolumeWeightedStockPrice(String stockSymbol, int mins)  {
		double vwStockPrice = 0.0;
		log.debug("Calculating Volume Weighted Stock Price for the: "+stockSymbol+" in the past "+mins+" minutes");

		log.debug("Get a list of Trades belonging to a Stock and managed into "+mins+" minutes");
		//Get a list of Trades belonging to a Stock and managed into "mins" minutes
		
		List<Trade> listTrade = null;
		if (mins > 0) {
			listTrade = dbFakeManager.getAllTrades().stream()
					.filter(trade -> trade.getStock().equals(new Stock(stockSymbol)))
					.filter(checkMinutesBetweenTrades(mins))
					.collect(Collectors.toList());
		} else if (mins == 0) {
			listTrade = dbFakeManager.getAllTrades().stream()
					.filter(trade -> trade.getStock().equals(new Stock(stockSymbol)))
					.collect(Collectors.toList());
		} else {
		}
		
		log.debug("Get a sum of Price*Quantity from above listTrade");
		//Get a sum of Price*Quantity from above listTrade
		double sumPriceQuantity = listTrade.stream()
				.map(trade -> trade.getPrice()*trade.getQuantity())
				.reduce(0.0, (a,b) -> a+b);
				
		log.debug("Get a sum of Quantity from above listTrade");
		//Get a sum of Quantity from above listTrade
		double sumQuantity = listTrade.stream()
				.map(trade -> trade.getQuantity())
				.mapToInt(Integer::new)
				.sum();
		
		// calculate the Volume Weighted Stock Price
		vwStockPrice = sumPriceQuantity / sumQuantity;	

		log.debug("Value for Volume Weighted Stock Price is: "+vwStockPrice);
		return vwStockPrice;
	}
	
	
	public double calculateGBCEAllShareIndex() {
		Double allShareIndex = 0.0;
		
		log.debug("Get all Stocks");
		//Get a list of all stocks 
		List<Stock> listStock = dbFakeManager.getAllStocks();
		
		log.debug("Get a list with Volume Weighted Stock Price calculate per Stock");
		//Get a list of Volume Weighted Stock Price for every Stock
		List<Double> listStockPrices = listStock.stream()
			.map(stock -> calculateVolumeWeightedStockPrice(stock.getStockSymbol(),0))
			.collect(Collectors.toList());
		
		// Calculates the GBCE All Share Index
		allShareIndex = Math.pow(listStockPrices.stream()
				.reduce(1.0, (a,b) -> a*b),  1.0/listStockPrices.size());

		
		log.debug("Value for GBCE All Share Index is: "+allShareIndex);
		return allShareIndex;
	}	
}
