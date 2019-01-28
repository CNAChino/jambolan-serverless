package com.three55.jambolanapi.function;

import java.util.Optional;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.three55.jambolanapi.dao.StoreDao;
import com.three55.jambolanapi.dao.StoreDynamoDBDao;
import com.three55.jambolanapi.exceptions.RecordNotFoundException;
import com.three55.jambolanapi.model.Store;
import com.three55.jambolanapi.utils.Validator;

public class FindStoreFunction {
	
	private static final Logger log = LogManager.getLogger(FindStoreFunction.class);

	public Store handle(String storeId, Context ctx) {
		
		try {
			log.info("TXN|findStoreById|IN|" + storeId);
			
			StoreDao storeDao = StoreDynamoDBDao.getInstance();
			Optional<Store> opt = findStoreById(storeId, storeDao); 

			if (opt.isPresent()) {
				Store store = opt.get(); 
				log.info("TXN|findStoreById|OUT|" + store);
				return store;
			} else {
				log.info("TXN|findStoreById|OUT|" + null);
				throw new RecordNotFoundException("Store not found for id " + storeId);
			}
		} catch (RecordNotFoundException e) {
			String message = "[404]: " + e.getMessage();
			log.info("TXN|findStoreById|OUT|" + message);
			throw new RuntimeException(message);
		} catch (IllegalArgumentException e) {
			String message = "[400]: " + e.getMessage();
			log.info("TXN|findStoreById|OUT|" + message);
			throw new RuntimeException(message);					
		} catch (AmazonServiceException e) {
			String message = "[503]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|findStoreById|OUT|" + message);
			throw new RuntimeException(message);					
		} catch (Exception e) {
			String message = "[500]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|findStoreById|OUT|" + message);
			throw new RuntimeException(message);
		}

	}
	
	public Optional<Store> findStoreById(String storeId, StoreDao storeDao) {
		Validator.validateParam(storeId, Validator.requiredNotNull(), "findStoreById failed.  Null Input.");
		
		Optional<Store> opt = storeDao.findStoreById(storeId);
		
		return opt;
	}
	
	
}
