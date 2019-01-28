package com.three55.jambolanapi.dao;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.three55.jambolanapi.dbmanager.DynamoDBManager;
import com.three55.jambolanapi.function.CreateStoreFunction;
import com.three55.jambolanapi.function.FindStoreFunction;
import com.three55.jambolanapi.model.Store;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class StoreFunctionTest {
	
	ArrayList<AttributeDefinition> tableAttributes = new ArrayList<>();
	ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
	GlobalSecondaryIndex tableGSI = new GlobalSecondaryIndex();
	ArrayList<KeySchemaElement> tableGSISchema = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		// use embedded dynamodb
		DynamoDBManager.setConnectToLive(false);
		System.setProperty("sqlite4java.library.path","src/lib");
		
		setupTableParameters();
		
		createTable();
		
	}

	@Test
	public void testAddStore() {
		// dao that is connected to embedded dynamodb
		StoreDao storeDao = StoreDynamoDBDao.getInstance(); 
		 
		CreateStoreFunction csf = new CreateStoreFunction();
		
		Store newStore = TestHelper.createCocinaStore();
		
		// create store
		String id = csf.createStore(newStore, storeDao);
		assertNotNull(id);
		assertTrue(id.startsWith("Store-"));
		
		// find store
		FindStoreFunction fsf = new FindStoreFunction();
		Optional<Store> optStore = fsf.findStoreById(id, storeDao);
		assertTrue(optStore.isPresent());
		
		System.out.println(newStore);
		System.out.println(optStore.get());
		
		assertTrue(newStore.equals(optStore.get()));
		
	}
	
	/**
	 * Set table and Global secondary index attributes. GSI is needed since index keys are
	 * different from the table keys i.e. GSI index is country as PK and name as SK.
	 * The GSI index allows faster retrieval by country then name
	 */
	private void setupTableParameters() { 
		// Table Schema
		tableAttributes.add(new AttributeDefinition()
				.withAttributeName("itemPK")
				.withAttributeType("S")
				);
		tableAttributes.add(new AttributeDefinition()
				.withAttributeName("itemSK")
				.withAttributeType("S")
				);
		tableAttributes.add(new AttributeDefinition()
				.withAttributeName("name")
				.withAttributeType("S")
				);
		
		tableAttributes.add(new AttributeDefinition()
				.withAttributeName("addrCountry")
				.withAttributeType("S")
				);
		
		
		// Table Schema PK and SK
		tableKeySchema.add(new KeySchemaElement()
				.withAttributeName("itemPK")
				.withKeyType(KeyType.HASH)
				);

		tableKeySchema.add(new KeySchemaElement()
				.withAttributeName("itemSK")
				.withKeyType(KeyType.RANGE)
				);

		// GSI PK and SK
		tableGSISchema.add(new KeySchemaElement()
				.withAttributeName("addrCountry")
				.withKeyType(KeyType.HASH)
				);

		tableGSISchema.add(new KeySchemaElement()
				.withAttributeName("name")
				.withKeyType(KeyType.RANGE)
				);
		
		Projection projection = new Projection().withProjectionType(ProjectionType.KEYS_ONLY);

		tableGSI.withIndexName("StoresAndCountryIndex")
			.withProvisionedThroughput(new ProvisionedThroughput()
					.withReadCapacityUnits((long) 10)
					.withWriteCapacityUnits((long) 5))
			.withProjection(projection)
			.withKeySchema(tableGSISchema);
	}
	
	/**
	 * Create the table with the schema settings defined
	 * in setupTableParameters()
	 */
	private void createTable() {
		DynamoDBManager dbManager = DynamoDBManager.instance();
		DynamoDB dynamoDB = new DynamoDB(dbManager.client());
		String tableName = "JAMBOLAN";
		
		try {
			System.out.println("Attempting to create table; please wait...");

			CreateTableRequest createTableRequest = new CreateTableRequest()
					.withTableName(tableName)
					.withProvisionedThroughput(new ProvisionedThroughput()
					        .withReadCapacityUnits((long) 5)
					        .withWriteCapacityUnits((long) 5))
					.withAttributeDefinitions(tableAttributes)
					.withKeySchema(tableKeySchema)
					.withGlobalSecondaryIndexes(tableGSI);

			Table table = dynamoDB.createTable(createTableRequest);
			table.waitForActive();

			System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

		}
		catch (Exception e) {
			System.err.println("Unable to create table: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
