package com.three55.jambolanapi.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.three55.jambolanapi.model.CountryStoreMapping;
import com.three55.jambolanapi.model.Store;

public interface StoreDao {
	
	Optional<Store> findStoreById(String storeId);
	
	List<CountryStoreMapping> findStoreByNameStartsWith(String country, String name); 
	
	/**
	 * Creates store record 
	 *
	 * @param  store  Store object to be created. id must be null.  Requires addrCountry, adminLogin
	 * @return        id generated for this store
	 * @throws IllegalArgumentException If store is not valid.
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws DaoException
	 */
	String createStore(Store store);
	
	/**
	 * Updates store record based on id. 
	 * <p>
	 * Update behavior is based on DynamoDBMapperConfig.SaveBehavior.CLOBBER 
	 *
	 * @param  store  Store object to be update. Requires id, addrCountry, adminLogin
	 * @return        void
	 * @throws IllegalArgumentException If store is not valid.
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws DaoException
	 */
	void updateStore(Store store);
	
	void deleteStore(Store store, String acctId);

}
