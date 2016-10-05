package lapi.sss.jpm_test_sss;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import lapi.sss.jpm_test_sss.exception.JPMException;
import lapi.sss.jpm_test_sss.model.Stock;
import lapi.sss.jpm_test_sss.model.StockType;
import lapi.sss.jpm_test_sss.model.Trade;
import lapi.sss.jpm_test_sss.model.TradeType;
import lapi.sss.jpm_test_sss.service.StockMarketService;
import lapi.sss.jpm_test_sss.util.JPMUtil;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private final Logger log = Logger.getLogger(App.class);
	private StockMarketService stockMarketService = StockMarketService.getInstance();
	
	/**
	 * Check for the stock objects storage  
	 */
	@Test
	public void checkSaveStockIntoStorage() {
		log.debug("----> Test method checkSaveStockIntoStorage.....BEGIN");
		
		//Clear all datas into storage
		stockMarketService.clearStorage();

		Stock stock = new Stock("TEA", StockType.COMMON, 0.0, null, 100.0, null);
		
		try {
			log.debug("Save stock object into the storage");
			stockMarketService.saveUpdateStock(stock);
			
			log.debug("Get stock object into the storage");
			Stock stockInStorage = stockMarketService.getStock(stock);
			
			log.debug("Compare stock and stockInStorage objects");
			Assert.assertEquals(stockInStorage, stock);
		} catch (JPMException e) {
			log.error(e);
			Assert.assertTrue(false);
		} catch (Exception e) {
			log.error(e);
			Assert.assertTrue(false);
		}

		log.debug("----> Test method checkSaveStockIntoStorage.....END. \nResult **OK**");
	}

	
	/**
	 * Check for the trade objects storage  
	 */
	@Test
	public void checkSaveTradeIntoStorage() {
		log.debug("----> Test method checkSaveTradeIntoStorage.....BEGIN");
		
		//Clear all datas into storage
		stockMarketService.clearStorage();

		Stock stock = new Stock("TEA", StockType.COMMON, 0.0, null, 100.0, null);
		Trade trade = new Trade(stock, TradeType.BUY, 10.0, 100);
		
		try {
			log.debug("Save trade object into the storage");
			stockMarketService.saveUpdateTrade(trade);
			
			log.debug("Get trade object into the storage");
			Trade tradeInStorage = stockMarketService.getTrade(trade);
			
			log.debug("Compare trade and tradeInStorage objects");
			Assert.assertEquals(tradeInStorage, trade);

		} catch (JPMException e) {
			log.error(e);
			Assert.assertTrue(false);
		} catch (Exception e) {
			log.error(e);
			Assert.assertTrue(false);
		}

		log.debug("----> Test method checkSaveTradeIntoStorage.....END. \nResult **OK**");
	}

	
	/**
	 * Check for the calculate of Dividend Yield
	 */
	@Test
	public void checkDividendYield() {
		log.debug("----> Test method checkDividendYield.....BEGIN");
		
		//Clear all datas into storage
		stockMarketService.clearStorage();

		Stock stock1 = new Stock("ALE", StockType.COMMON, 23.0, null, 100.0, null);
		Trade trade1 = new Trade(stock1, TradeType.BUY, 10.0, 100);
		Stock stock2 = new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0, 10.0);
		Trade trade2 = new Trade(stock1, TradeType.BUY, 10.0, 100);
		
		try {
			log.debug("Save stock and trade objects into the storage");
			stockMarketService.saveUpdateStock(stock1);
			stockMarketService.saveUpdateStock(stock2);
			stockMarketService.saveUpdateTrade(trade1);
			stockMarketService.saveUpdateTrade(trade2);
			
			log.debug("Compare the StockMarketService's calculate dividend yield with the result ");
			Assert.assertEquals(new Double(2.3), stockMarketService.calculateDividendYield("ALE"));

			log.debug("Compare the StockMarketService's calculate dividend yield with the result ");
			Assert.assertEquals(new Double(20), stockMarketService.calculateDividendYield("GIN"));
			
		} catch (JPMException e) {
			log.error(e);
			Assert.assertTrue(false);
		} catch (Exception e) {
			log.error(e);
			Assert.assertTrue(false);
		}

		log.debug("----> Test method checkDividendYield.....END. \nResult **OK**");
	}

	
	/**
	 * Check for the calculate P/E Ration
	 */
	@Test
	public void checkPERatio() {
		log.debug("----> Test method checkPERatio.....BEGIN");

		//Clear all datas into storage
		stockMarketService.clearStorage();

		Stock stock1 = new Stock("ALE", StockType.COMMON, 23.0, null, 100.0, null);
		Trade trade1 = new Trade(stock1, TradeType.BUY, 10.0, 100);
		Stock stock2 = new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0, 10.0);
		Trade trade2 = new Trade(stock1, TradeType.BUY, 10.0, 100);
		
		try {
			log.debug("Save stock and trade objects into the storage");
			stockMarketService.saveUpdateStock(stock1);
			stockMarketService.saveUpdateStock(stock2);
			stockMarketService.saveUpdateTrade(trade1);
			stockMarketService.saveUpdateTrade(trade2);
			
			log.debug("Compare the StockMarketService's calculate P/E Ratio with the result ");
			BigDecimal peRatioFromService = new BigDecimal(stockMarketService.calculatePERatio("ALE"));
			//Rounding the value peRatioFromService to 4 decimal
			peRatioFromService = peRatioFromService.setScale(4, RoundingMode.CEILING);
			
			Assert.assertEquals(new Double(4.3479), new Double(peRatioFromService.doubleValue()));

			log.debug("Compare the StockMarketService's calculate P/E Ratio with the result ");
			Assert.assertEquals(new Double(0.5), stockMarketService.calculatePERatio("GIN"));
			
		} catch (JPMException e) {
			log.error(e);
			Assert.assertTrue(false);
		} catch (Exception e) {
			log.error(e);
			Assert.assertTrue(false);
		}

		log.debug("----> Test method checkPERatio.....END. \nResult **OK**");
	}
	
	/**
	 * Check for the calculate Volume Weighted Stock Price
	 */
	@Test
	public void checkVolumeWeightedStockPrice() {
		log.debug("----> Test method checkVolumeWeightedStockPrice.....BEGIN");
		
		//Clear all datas into storage
		stockMarketService.clearStorage();

		Stock stock1 = new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0, 10.0);
		Trade trade1 = null;
		Trade trade2 = null;
		Trade trade3 = null;
		try {
			trade1 = new Trade(stock1, TradeType.BUY, 10.0, 100);
			Thread.sleep(10);
			trade2 = new Trade(stock1, TradeType.SELL, 15.0, 100);
			Thread.sleep(10);
			trade3 = new Trade(stock1, TradeType.BUY, 20.0, 100);

		} catch (InterruptedException e1) {
			log.error(e1);
			Assert.assertTrue(false);
		}

		//Change the timestamp subtracting 30 minutes
		trade1.setTimeStamp(new Timestamp(JPMUtil.getNMinutesAgo(30).getTime()));
		
		try {
			log.debug("Save stock and trade objects into the storage");
			stockMarketService.saveUpdateStock(stock1);
			stockMarketService.saveUpdateTrade(trade1);
			stockMarketService.saveUpdateTrade(trade2);
			stockMarketService.saveUpdateTrade(trade3);
			
			log.debug("Compare the StockMarketService's calculate PVolume Weighted Stock Price with the result ");
			BigDecimal vwspFromService = new BigDecimal(stockMarketService.calculateVolumeWeightedStockPrice("GIN", 15));
			//Rounding the value vwspFromService to 4 decimal
			vwspFromService = vwspFromService.setScale(4, RoundingMode.CEILING);
			
			Assert.assertEquals(new Double(17.5000), new Double(vwspFromService.doubleValue()));
			
		} catch (JPMException e) {
			log.error(e);
			Assert.assertTrue(false);
		} catch (Exception e) {
			log.error(e);
			Assert.assertTrue(false);
		}

		log.debug("----> Test method checkVolumeWeightedStockPrice.....END. \nResult **OK**");
	}	
	
	
	/**
	 * Check for the calculate e GBCE All Share Index
	 */
	@Test
	public void checkGBCEAllShareIndex() {
		log.debug("----> Test method checkGBCEAllShareIndex.....BEGIN");
		
		//Clear all datas into storage
		stockMarketService.clearStorage();
		
		Stock stock1 = new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0, 10.0);
		Stock stock2 = new Stock("ALE", StockType.COMMON, 23.0, null, 60.0, null);
		
		Trade trade1 = null;
		Trade trade2 = null;
		Trade trade3 = null;
		Trade trade4 = null;
		try {
			trade1 = new Trade(stock1, TradeType.BUY, 10.0, 100);
			Thread.sleep(10);
			trade2 = new Trade(stock1, TradeType.SELL, 15.0, 100);
			Thread.sleep(10);
			trade3 = new Trade(stock1, TradeType.BUY, 20.0, 100);

			Thread.sleep(10);			
			trade4 = new Trade(stock2, TradeType.SELL, 12.0, 30);
		} catch (InterruptedException e1) {
			log.error(e1);
			Assert.assertTrue(false);
		}

		try {
			log.debug("Save stock and trade objects into the storage");
			stockMarketService.saveUpdateStock(stock1);
			stockMarketService.saveUpdateStock(stock2);
			stockMarketService.saveUpdateTrade(trade1);
			stockMarketService.saveUpdateTrade(trade2);
			stockMarketService.saveUpdateTrade(trade3);
			stockMarketService.saveUpdateTrade(trade4);
			
			log.debug("Compare the StockMarketService's calculate PVolume Weighted Stock Price with the result ");
			BigDecimal gbceFromService = new BigDecimal(stockMarketService.calculateGBCEAllShareIndex());
			//Rounding the value gbceFromService to 4 decimal
			gbceFromService = gbceFromService.setScale(4, RoundingMode.CEILING);
			
			System.out.println("********************************"+gbceFromService);
			
			Assert.assertEquals(new Double(13.4165), new Double(gbceFromService.doubleValue()));
			
		} catch (JPMException e) {
			log.error(e);
			Assert.assertTrue(false);
		} catch (Exception e) {
			log.error(e);
			Assert.assertTrue(false);
		}

		log.debug("----> Test method checkGBCEAllShareIndex.....END. \nResult **OK**");
	}		
}
