package com.three55.jambolanapi.dbmanager;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;

/**
 * Singleton that provides connection  to DynamoDB Live or 
 * DynamoDBLocal (used for unit testing).  The connection 
 * is via DynamoDBMapper or AmazonDynamoDB.
 *
 */
public class DynamoDBManager {
	
	private static volatile DynamoDBManager instance;
	
	private static volatile boolean connectToLive = true;

	private  DynamoDBMapper mapper;
	
	private  AmazonDynamoDB client;

	/**
	 * Constructors a live/local connection 
	 */
	private DynamoDBManager() {
		if (connectToLive) {
			client = AmazonDynamoDBClientBuilder.standard()
					.withRegion(Regions.US_WEST_1)
					.build();
		} else {
			client = DynamoDBEmbedded.create().amazonDynamoDB();
		}

		mapper = new DynamoDBMapper(client);
	}

	/**
	 * singleton instance accessor 
	 *  
	 * @return Singleon DynamoDBManager
	 */
	public static DynamoDBManager instance() {
		if (instance == null) {
			synchronized(DynamoDBManager.class) {
				if (instance == null)
					instance = new DynamoDBManager();
			}
		}

		return instance;
	}
	
	public synchronized static void setConnectToLive(boolean b) {
		connectToLive = b;
	}

	/**
	 * 
	 * @return DynamoDBMapper
	 */
	public  DynamoDBMapper mapper() {
		return mapper;
	}

	/**
	 * 
	 * @return AmazonDynamoDB client
	 */
	public  AmazonDynamoDB client() {
		return client;
	}
}
