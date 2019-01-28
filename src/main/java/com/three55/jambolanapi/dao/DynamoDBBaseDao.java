package com.three55.jambolanapi.dao;

import java.util.concurrent.Callable;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.three55.jambolanapi.dbmanager.DynamoDBManager;
import com.three55.jambolanapi.exceptions.DaoException;

/**
 * Base class for Dynamo Data Access objects.  
 * The Dao implementations should focus on data access logic.
 *
 */
public class DynamoDBBaseDao {
	
	protected final DynamoDBMapper mapper = DynamoDBManager.instance().mapper();
	
	protected void executeDao(Runnable r) {
		try {			
			r.run();
		} catch (AmazonClientException e) {
			throw e;
		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}
	
	protected <R> R executeDaoForResult(Callable<R> c) {
		try {			
			return c.call();
		} catch (AmazonClientException e) {
			throw e;
		} catch(Exception e) {
			throw new DaoException(e);
		} 
	}

}
