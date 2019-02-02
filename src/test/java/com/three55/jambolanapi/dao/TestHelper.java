package com.three55.jambolanapi.dao;

import java.time.LocalTime;
import java.util.Arrays;

import com.three55.jambolanapi.model.Store;

public class TestHelper {
	
	/**
	 * Create test store data
	 * 
	 * @return Store
	 */
	public static Store createCocinaStore() {
		Store store;
		
		LocalTime start = LocalTime.of(8, 0,0);
		LocalTime end = LocalTime.of(17, 0,0);

		store = new Store();
		store.setItemPK(Store.PK_PREFIX + "SOMEUSERNAME");
		store.setItemSK(store.getItemPK());
		store.setName("Cocina Conchita");
		store.setEmail("cocina.conchita@sampleemail.com");
		store.setLogoURL("http://cocinaconchita.com/logo.PNG");
		store.setKeywords(Arrays.asList("lechon","carendiria","turo-turo","lutong bahay"));
		store.setPhone1("somephone1"); // do not use ranodom mobile number for testing, sms is enabled
		store.setPhone2("somephone2"); // do not use ranodom mobile number for testing, sms is enabled
		store.setWebsite("http://www.cocinaconchita.com");
		store.setAddrLine1("#41 Panganiban Drive");
		store.setAddrLine2("Dr, Gimenez Patio Barangay");
		store.setAddrCityTown("Naga City");
		store.setAddrProvState("Camarines Sur");
		store.setAddrZipCode("4400");
		store.setAddrCountry("Philippines");
		store.setAddrCoordinates("13.6225851,123.1893002");
		store.setAdminLogin("cocina.conchita@sampleemail.com");
		
		return store;
	}

}
