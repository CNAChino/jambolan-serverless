package com.three55.jambolanapi.utils;

public class CountryCode {
	// TODO use enum, ISO 3166-1
	
	public static String getByName(String addrCountry) { 
		switch (addrCountry.toLowerCase()) {
			case "philippines":
				return "608";
			case "united states":
				return "840";
			default: 
				break;
		}
		
		return null;
	}

}
