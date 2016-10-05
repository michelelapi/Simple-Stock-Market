package lapi.sss.jpm_test_sss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import lapi.sss.jpm_test_sss.exception.JPMException;
import lapi.sss.jpm_test_sss.model.Stock;
import lapi.sss.jpm_test_sss.model.StockType;
import lapi.sss.jpm_test_sss.model.Trade;
import lapi.sss.jpm_test_sss.model.TradeType;
import lapi.sss.jpm_test_sss.service.StockMarketService;
import lapi.sss.jpm_test_sss.util.JPMUtil;

public class App {
	private static final Logger log = Logger.getLogger(App.class);

	public static void main( String[] args )
    {
    	StockMarketService stockMarketService = StockMarketService.getInstance();
    	
    	try {
    		//Create Stocks 
    		log.debug("I'm going to create the stocks:");
    		Stock s1 = new Stock("TEA", StockType.COMMON, 0.0, null, 100.0, null);
    		Stock s2 = new Stock("POP", StockType.COMMON, 8.0, null, 100.0, null);    		
    		Stock s3 = new Stock("ALE", StockType.COMMON, 23.0, null, 60.0, null);    		
    		Stock s4 = new Stock("GIN", StockType.PREFERRED, 8.0, 2.0, 100.0, 10.0);
    		Stock s5 = new Stock("JOE", StockType.COMMON, 13.0, null, 100.0, null);    		

    		//Save Stocks 
    		stockMarketService.saveUpdateStock(s1);
    		stockMarketService.saveUpdateStock(s2);
    		stockMarketService.saveUpdateStock(s3);
    		stockMarketService.saveUpdateStock(s4);
    		stockMarketService.saveUpdateStock(s5);

    		//Create Trades
    		log.debug("I'm going to create the stocks:");
    		Trade t1 = new Trade(s1, TradeType.BUY, 10.0, 10);
    		Trade t2 = new Trade(s2, TradeType.BUY, 11.0, 20);
    		Trade t3 = new Trade(s3, TradeType.SELL, 12.0, 30);
    		Trade t4 = new Trade(s4, TradeType.SELL, 13.0, 40);
    		Trade t5 = new Trade(s5, TradeType.SELL, 14.0, 50);
    		
    		//Change the timestamp subtracting minutes
    		t1.setTimeStamp(new Timestamp(JPMUtil.getNMinutesAgo(6).getTime()));
    		t2.setTimeStamp(new Timestamp(JPMUtil.getNMinutesAgo(7).getTime()));
    		t3.setTimeStamp(new Timestamp(JPMUtil.getNMinutesAgo(5).getTime()));

    		//Save Trades
    		stockMarketService.saveUpdateTrade(t1);
    		stockMarketService.saveUpdateTrade(t2);
    		stockMarketService.saveUpdateTrade(t3);
    		stockMarketService.saveUpdateTrade(t4);
    		stockMarketService.saveUpdateTrade(t5);
    		

    		try {
        		log.debug("I'm going to calutate:");

        		log.debug("Value for Volume Weighted Stock Price is: " + stockMarketService.calculateVolumeWeightedStockPrice("TEA", 15));
        		log.debug("Value for GBCE All Share Index is: " + stockMarketService.calculateGBCEAllShareIndex());

        		log.debug("Dividend Yield calculated for stock symbol: TEA is: " + stockMarketService.calculateDividendYield("TEA"));
        		log.debug("Dividend Yield calculated for stock symbol: POP is: " + stockMarketService.calculateDividendYield("POP"));
        		log.debug("Dividend Yield calculated for stock symbol: ALE is: " + stockMarketService.calculateDividendYield("ALE"));
        		log.debug("Dividend Yield calculated for stock symbol: GIN is: " + stockMarketService.calculateDividendYield("GIN"));
        		log.debug("Dividend Yield calculated for stock symbol: JOE is: " + stockMarketService.calculateDividendYield("JOE"));
        		log.debug("P/E Ratio calculated for stock symbol: TEA is: " + stockMarketService.calculatePERatio("TEA"));
        		log.debug("P/E Ratio calculated for stock symbol: POP is: " + stockMarketService.calculatePERatio("POP"));
        		log.debug("P/E Ratio calculated for stock symbol: ALE is: " + stockMarketService.calculatePERatio("ALE"));
        		log.debug("P/E Ratio calculated for stock symbol: GIN is: " + stockMarketService.calculatePERatio("GIN"));
        		log.debug("P/E Ratio calculated for stock symbol: JOE is: " + stockMarketService.calculatePERatio("JOE"));

    		} catch (Exception e) {
    			e.printStackTrace();
    		}

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
//    	BufferedReader br = null;
//
//        try {
//
//            br = new BufferedReader(new InputStreamReader(System.in));
//
//            while (true) {
//
//                System.out.print("Enter something : ");
//                String input = br.readLine();
//
//                if ("q".equals(input)) {
//                    System.out.println("Exit!");
//                    System.exit(0);
//                }
//
//                System.out.println("input : " + input);
//                System.out.println("-----------\n");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
