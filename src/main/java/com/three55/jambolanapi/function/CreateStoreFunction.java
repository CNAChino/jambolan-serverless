package com.three55.jambolanapi.function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.three55.jambolanapi.dao.StoreDao;
import com.three55.jambolanapi.dao.StoreDynamoDBDao;
import com.three55.jambolanapi.model.Store;
import com.three55.jambolanapi.utils.Validator;

public class CreateStoreFunction {

	private static final Logger log = LogManager.getLogger(CreateStoreFunction.class);

	/**
	 * AWS Lambda function entry point.
	 * 
	 * @param store Store object
	 * @param ctx AWS Lambda execution context
	 * @return
	 */
	public String handle(Store store, Context ctx) {
		try {
			log.info("TXN|createStore|IN|" + store);

			StoreDao storeDao = StoreDynamoDBDao.getInstance();
			String storeId = createStore(store, storeDao);

			log.info("TXN|createStore|OUT|" + storeId);
			return storeId;
		} catch (IllegalArgumentException e) {
			String message = "[400]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|createStore|OUT|" + message);
			throw new RuntimeException(message);					
		} catch (AmazonServiceException e) {
			String message = "[503]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|createStore|OUT|" + message);
			throw new RuntimeException(message);					
		} catch (Exception e) {
			String message = "[500]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|createStore|OUT|" + message);
			throw new RuntimeException(message);
		}
	}

	/**
	 * Creates store in the database
	 * 
	 * @param store
	 * @param storeDao
	 * @return
	 */
	public String createStore(Store store, StoreDao storeDao) {

		Validator.validateParam(store, Validator.requiredNotNull(), "Null Input for createStore");
		
		Validator.validateParam(store.getAddrCountry(), Validator.requiredNotNull(),
				"createStore failed.  Store.addrCountry is required.");
		
		Validator.validateParam(store.getAddrCountry(), Validator.countryValid(),
				"createStore failed.  Store.addrCountry="+store.getAddrCountry()+" is not valid.");
		
		Validator.validateParam(store.getAdminLogin(), Validator.requiredNotNull(),
				"createStore failed.  Store.adminLogin is required."
				);

		String storeId = storeDao.createStore(store);
		return storeId;

	}

}
