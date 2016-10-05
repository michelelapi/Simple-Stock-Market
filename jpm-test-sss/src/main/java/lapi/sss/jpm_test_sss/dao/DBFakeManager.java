package lapi.sss.jpm_test_sss.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lapi.sss.jpm_test_sss.exception.JPMException;
import lapi.sss.jpm_test_sss.model.Stock;
import lapi.sss.jpm_test_sss.model.SuperModel;
import lapi.sss.jpm_test_sss.model.Trade;

public class DBFakeManager  {
	private Map<Integer, SuperModel> storage = new HashMap<Integer, SuperModel>();
	
	private static DBFakeManager dbFakeMaganer;
	
	/**
	 * This method build and return a single instance of DBFakeManager
	 * 
	 * @return
	 */
	public static DBFakeManager getInstance() {
		if (dbFakeMaganer == null) {
			dbFakeMaganer = new DBFakeManager();
		}
		
		return dbFakeMaganer;
	}
	
	/**
	 * Remove all the elements into storage
	 */
	public void clearAll() {
		storage.clear();
	}

	/**
	 * Save or update every object that implement the SuperModel interface
	 * 
	 * @param data
	 */
	public void saveUpdate(SuperModel data) throws JPMException {
		if (data != null) {
			this.storage.put(data.hashCode(), data);
		} else {
			throw new JPMException("It is not possible save or update an object null");
		}
	}

	/**
	 * Save or update every object that implement the SuperModel interface
	 * 
	 * @param data
	 */
	public void delete(SuperModel data) throws JPMException {
		if (data != null) {
			this.storage.remove(data.hashCode());
		} else {
			throw new JPMException("It is not possible delete an object null");
		}
	}
	
	/**
	 * Get an object from the storage by key
	 * 
	 * @param data
	 * @return
	 * @throws JPMException
	 */
	public SuperModel getByKey(SuperModel data) throws JPMException {
		if (data != null) {
			return this.storage.get(data.hashCode());
		} else {
			throw new JPMException("It is not possible get an object null");
		}
	}
	
	
	/**
	 * Create a predicate to evaluate the class type of the model object insert
	 * into the storage
	 * 
	 * @param c
	 * @return
	 */
	private static Predicate<SuperModel> checkModel(Class<?> c) {
		return value -> value.getClass().getSimpleName().equals(c.getSimpleName());
	}	
	
	/**
	 * Get all the Stock objects into the storage
	 * The use of function allows a possible parallel processing 
	 *  
	 * @return
	 */
	public List<Stock> getAllStocks() {
		List<Stock> stockList = storage.values().stream()
				.filter(checkModel(Stock.class))
				.map(value -> (Stock)value)
				.collect(Collectors.toList());
		
		return stockList;
	}

	/**
	 * Get all the Trade objects into the storage
	 * The use of function allows a possible parallel processing
	 * 
	 * @return
	 */
	public List<Trade> getAllTrades() {
		List<Trade> tradeList = storage.values().stream()
				.filter(checkModel(Trade.class))
				.map(value -> (Trade)value)
				.collect(Collectors.toList());
		
		return tradeList;
	}

}
