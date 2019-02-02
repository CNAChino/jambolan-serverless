package com.three55.jambolanapi.function;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.three55.jambolanapi.dao.StoreDao;
import com.three55.jambolanapi.dao.StoreDynamoDBDao;
import com.three55.jambolanapi.model.Store;
import com.three55.jambolanapi.utils.Validator;

public class CreateStoreFunction {

	private static final Logger log = LogManager.getLogger(CreateStoreFunction.class);

	/**
	 * AWS Lambda function entry point.
	 * 
	 * @param store Store object
	 * @param ctx AWS Lambda execution context
	 * @return
	 */
	public String handle(Store store, Context ctx) {
		try {
			log.info("TXN|createStore|IN|" + store);

			StoreDao storeDao = StoreDynamoDBDao.getInstance();
			String storeId = createStore(store, storeDao);

			sendSMSNotification(store);

			log.info("TXN|createStore|OUT|" + storeId);
			return storeId;
		} catch (IllegalArgumentException e) {
			String message = "[400]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|createStore|OUT|" + message);
			throw new RuntimeException(message);					
		} catch (AmazonServiceException e) {
			String message = "[503]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|createStore|OUT|" + message);
			throw new RuntimeException(message);					
		} catch (Exception e) {
			String message = "[500]: " + e.getMessage();
			log.error(message, e);
			log.info("TXN|createStore|OUT|" + message);
			throw new RuntimeException(message);
		}


	}

	/**
	 * Creates store in the database
	 * 
	 * @param store
	 * @param storeDao
	 * @return
	 */
	public String createStore(Store store, StoreDao storeDao) {

		Validator.validateParam(store, Validator.requiredNotNull(), "Null Input for createStore");
		
		Validator.validateParam(store.getAddrCountry(), Validator.requiredNotNull(),
				"createStore failed.  Store.addrCountry is required.");
		
		Validator.validateParam(store.getAddrCountry(), Validator.countryValid(),
				"createStore failed.  Store.addrCountry="+store.getAddrCountry()+" is not valid.");
		
		Validator.validateParam(store.getAdminLogin(), Validator.requiredNotNull(),
				"createStore failed.  Store.adminLogin is required."
				);

		String storeId = storeDao.createStore(store);
		return storeId;

	}

	/**
	 * sends SMS notification via Twilio.  environment variables must be set: ACCOUNT_SID, AUTH_TOKEN, MESSAGING_SERVICE_ID
	 *
	 * @param store
	 * @return messageId A string that uniquely identifies this message
	 */
	public String sendSMSNotification(Store store) {
		try {

			// Get Environment Variables from Twilio.  Configured during deployment of AWS LAMBDA function
			String accountSid = System.getenv("ACCOUNT_SID");
			String authToken = System.getenv("AUTH_TOKEN");
			String msgSvcId = System.getenv("MESSAGING_SERVICE_ID");

			if (accountSid  == null || authToken == null ||  msgSvcId == null) {
				throw new IllegalArgumentException("Required environment variables (ACCOUNT_SID|AUTH_TOKEN|MESSAGING_SERVICE_ID) for twilio not set.");
			}

			// Initialize Twilio
			Twilio.init(accountSid, authToken);

			// B-PARTY number (recipient)
			String pn = store.getPhone1();
			PhoneNumber bPartyPhone = new PhoneNumber(pn);

			// SMS Message
			String textMsg = "Welcome to JAMBOLAN.  Your store " + store.getName() + " has been created";

			// send message
			Message message = Message.creator(bPartyPhone, msgSvcId,
					textMsg).create();

			String msgId = message.getSid();
			log.info("Message sent to " + pn + " .  ID = " + msgId);

			return msgId;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
