package com.three55.jambolanapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="JAMBOLAN")
public class Store {
	public static final String PK_PREFIX = "Store-";
	
	public static final String INDIVIDUAL = "INDIVIDUAL";
	public static final String BUSINESS = "BUSINESS";

	private String itemPK;
	private String itemSK;
	private String name; 
	private String type;
	private String email;
	private String phone1;
	private String phone2;
	private String addrLine1;
	private String addrLine2;
	private String addrCityTown;
	private String addrProvState;
	private String addrZipCode;
	private String addrCountry;
	private String addrCoordinates; 
	private List<String> keywords; 
	private String logoURL;
	private String website;
	private String language = "en";
	private String adminLogin;
	private String ownerGivenName;
	private String ownerMiddleName;
	private String ownerFamilyName;

	public Store() { }
	
	@DynamoDBHashKey(attributeName="itemPK")
	public String getItemPK() {
		return this.itemPK;
	}
	
	public void setItemPK(String itemPK) {
		this.itemPK = itemPK;
	}

	@DynamoDBRangeKey(attributeName="itemSK")
	public String getItemSK() {
		return this.itemSK;
	}
	
	public void setItemSK(String itemSK) {
		this.itemSK = itemSK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (!INDIVIDUAL.equals(type)) {
			throw new IllegalArgumentException(type + " not allowed for Store.type attribute.");
		}
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getAddrCityTown() {
		return addrCityTown;
	}

	public void setAddrCityTown(String addrCityTown) {
		this.addrCityTown = addrCityTown;
	}

	public String getAddrProvState() {
		return addrProvState;
	}

	public void setAddrProvState(String addrProvState) {
		this.addrProvState = addrProvState;
	}

	public String getAddrZipCode() {
		return this.addrZipCode;
	}

	public void setAddrZipCode(String addrZipCode) {
		this.addrZipCode = addrZipCode;
	}


	public String getAddrCountry() {
		return addrCountry;
	}

	public void setAddrCountry(String addrCountry) {
		this.addrCountry = addrCountry;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAddrCoordinates() {
		return addrCoordinates;
	}

	public void setAddrCoordinates(String addrCoordinates) {
		this.addrCoordinates = addrCoordinates;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAdminLogin() {
		return adminLogin;
	}

	public void setAdminLogin(String adminLogin) {
		this.adminLogin = adminLogin;
	}

	public String getOwnerGivenName() {
		return ownerGivenName;
	}

	public void setOwnerGivenName(String ownerGivenName) {
		this.ownerGivenName = ownerGivenName;
	}

	public String getOwnerMiddleName() {
		return ownerMiddleName;
	}

	public void setOwnerMiddleName(String ownerMiddleName) {
		this.ownerMiddleName = ownerMiddleName;
	}

	public String getOwnerFamilyName() {
		return ownerFamilyName;
	}

	public void setOwnerFamilyName(String ownerFamilyName) {
		this.ownerFamilyName = ownerFamilyName;
	}

	public String toString() {
		return itemPK + ","
				+ itemSK + ","
				+ name + ","
				+ email + ","
				+ phone1 + ","
				+ phone2 + ","
				+ keywords + ","
				+ logoURL + ","
				+ website;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Store)) {
			return false;
		}

		List<Object> thisList = new ArrayList<>(Arrays.asList(
				this.itemPK,  
				this.itemSK,
				this.name, 
				this.email,
				this.phone1,
				this.phone2,
				this.addrLine1,
				this.addrLine2,
				this.addrCityTown,
				this.addrProvState,
				this.addrZipCode,
				this.addrCountry,
				this.addrCoordinates, 
				this.keywords, 
				this.logoURL,
				this.website,
				this.language
				));

		Store o = (Store) other;
		
		List<Object> otherList = new ArrayList<Object>(Arrays.asList(
				o.itemPK,  
				o.itemSK,  
				o.name, 
				o.email,
				o.phone1,
				o.phone2,
				o.addrLine1,
				o.addrLine2,
				o.addrCityTown,
				o.addrProvState,
				o.addrZipCode,
				o.addrCountry,
				o.addrCoordinates, 
				o.keywords, 
				o.logoURL,
				o.website,
				o.language
				));
		
		for (int i = 0; i < thisList.size(); i++) {
			if (!thisList.get(i).equals(otherList.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	

}
