package com.three55.jambolanapi.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="JAMBOLAN")
public class CountryStoreMapping {

	public static final String INDEX_NAME = "StoresAndCountryIndex";
	
	private String storeName;
	private String country;
	private String storeId;
	
	@DynamoDBRangeKey(attributeName="name")
	public String getStoreName() {
		return storeName;
	}
	
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@DynamoDBHashKey(attributeName="addrCountry")
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	@DynamoDBAttribute(attributeName="itemPK")
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	public String toString() {
		return  country + ","
				+ storeName + ","
				+ storeId;
	}
	
	
	

}
