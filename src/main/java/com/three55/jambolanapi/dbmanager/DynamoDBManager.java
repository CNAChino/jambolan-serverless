package com.three55.jambolanapi.dbmanager;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Singleton that provides connection  to DynamoDB Live or 
 * DynamoDBLocal (used for unit testing).  The connection 
 * is via DynamoDBMapper or AmazonDynamoDB.
 *
 */
public class DynamoDBManager {

	private  static DynamoDBMapper mapper;

	private  static AmazonDynamoDB client;

	/**
	 *
	 * By default, this returns a singleton DynamoDBMapper that is connected to DynamodDB in AWS Cloud US-WEST-1.
	 *
	 * To get a DynamoDBMapper using DynamoDBLocal (embedded or localhost), call DynamoDBManager.setClient
	 * before the very first call of mapper()
	 *
	 * @return DynamoDBMapper
	 */
	public  static DynamoDBMapper mapper() {

		if (mapper == null) {
			synchronized(DynamoDBManager.class) {
				if (mapper == null) {
					if (client == null) {
						client = AmazonDynamoDBClientBuilder.standard()
								.withRegion(Regions.US_WEST_1)
								.build();
					}
					mapper = new DynamoDBMapper(client);
				}
			}
		}
		return mapper;
	}

	public  static void setClient(AmazonDynamoDB c) {
		client = c;
	}
}
