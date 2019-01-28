package com.three55.jambolanapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.three55.jambolanapi.model.CountryStoreMapping;
import com.three55.jambolanapi.model.Store;
import com.three55.jambolanapi.utils.SingleItemExtractorFromList;
import com.three55.jambolanapi.utils.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 
 * Dao Implementation for access store data   
 */
public class StoreDynamoDBDao extends DynamoDBBaseDao implements StoreDao {

	private static final Logger log = LogManager.getLogger(StoreDynamoDBDao.class);

	private static StoreDynamoDBDao instance;

	private StoreDynamoDBDao() {}

	public static StoreDynamoDBDao getInstance() {
		if (instance == null) {
			synchronized(StoreDynamoDBDao.class) {
				if (instance == null) {
					instance = new StoreDynamoDBDao();
				}
			}
		}

		return instance;
	}

	@Override
	public Optional<Store> findStoreById(String storeId) {

		return executeDaoForResult(() -> {
			Map<String, AttributeValue> eav = new HashMap<>();
			eav.put(":v1", new AttributeValue().withS(storeId));
			eav.put(":v2", new AttributeValue().withS(storeId));

			DynamoDBQueryExpression<Store> query = new DynamoDBQueryExpression<Store>()
					//TODO .withIndexName(Event.CITY_INDEX)
					.withConsistentRead(false)
					.withKeyConditionExpression("itemPK = :v1 and itemSK = :v2")
					.withExpressionAttributeValues(eav);

			List<Store> listStores = mapper.query(Store.class, query);
			return SingleItemExtractorFromList.get(listStores);
		});



	}

	@Override
	public List<CountryStoreMapping> findStoreByNameStartsWith(String country, String name) {

		return executeDaoForResult(() -> {
			Map<String, AttributeValue> eav = new HashMap<>();
			eav.put(":v1", new AttributeValue().withS(country));
			eav.put(":v2", new AttributeValue().withS(name));

			Map<String,String> ean = new HashMap<>();
			ean.put("#storeName", "name");

			DynamoDBQueryExpression<CountryStoreMapping> query = new DynamoDBQueryExpression<CountryStoreMapping>()
					.withIndexName(CountryStoreMapping.INDEX_NAME)
					.withConsistentRead(false)
					.withKeyConditionExpression("addrCountry = :v1 and begins_with(#storeName, :v2)")
					.withExpressionAttributeNames(ean)
					.withExpressionAttributeValues(eav);

			return mapper.query(CountryStoreMapping.class, query);
		});

	}

	@Override
	public String createStore(Store store) {
		
		return executeDaoForResult(() -> {
			
			if (store.getItemPK() == null || store.getItemSK() == null) {
				throw new IllegalArgumentException("Null itemPK or itemSK");
			}
			
			if (!store.getItemPK().equals(store.getItemSK())) {
				throw new IllegalArgumentException("itemPK should be equal to itemSK");
			}
			//store.setItemPK(IdGenerator.generate(STORE_ID_PREFIX, store.getCountryCode()));
			//store.setItemSK(store.getItemPK());
			mapper.save(store);
			return store.getItemPK();
		});
	}

	@Override
	public void updateStore(final Store store) {

		executeDao(() -> {
			
			Validator.validateParam(store.getItemPK(), Validator.requiredNotNull(),
					"Update store failed.  Store.itemPK is required.");
			Validator.validateParam(store.getAddrCountry(), Validator.requiredNotNull(),
					"Update store failed.  Store.addrCountry is required.");
			Validator.validateParam(store.getAdminLogin(), Validator.requiredNotNull(),
					"Update store failed.  Store.adminLogin is required."
					);
			
			DynamoDBMapperConfig updateConfig = DynamoDBMapperConfig.SaveBehavior.CLOBBER.config();
			StoreDynamoDBDao.this.mapper.save(store,updateConfig);
		});
	}

	@Override
	public void deleteStore(Store store, String acctId) {
		// TODO validate 
		executeDao(() -> {
			Optional<Store> s = findStoreById(store.getItemPK());
			if (s.isPresent()) {
				mapper.delete(store);
			} else {
				String errMsg = "Delete failed.  no record exist for storedId = " + store.getItemPK();
				log.error(errMsg);
				throw new IllegalArgumentException(errMsg);
			}
		});
	}

}
