package com.capitaloneinvesting.businesslogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.model.Transaction;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

public class BusinessLogicTest {

	Businesslogic businessLogic;

	@Before
	public void setup() {
		businessLogic = new Businesslogic();
	}

	//@Test
	public void testgetTransactionsToDisplay() {
		boolean ignoreDonuts = true;
		ResponseWrapper responseObj = new ResponseWrapper();
		List<Transaction> transactions = new ArrayList<>();
		Transaction txn1 = new Transaction();
		txn1.setAccountId("1");
		txn1.setTransactionId("1");
		txn1.setTransactionTime("2014-10-07T12:59:00.000Z");
		txn1.setMerchant("Krispy Kreme Donuts");
		Transaction txn2 = new Transaction();
		txn2.setAccountId("1");
		txn2.setMerchant("Krispy Kreme Donuts");
		txn2.setTransactionId("1");
		txn2.setTransactionTime("2014-10-07T12:59:00.000Z");
		transactions.add(txn1);
		transactions.add(txn2);
		//responseObj.setTransactions(transactions);
		Map<String, DisplayTransaction> map = businessLogic.getTransactionsToDisplay(responseObj, ignoreDonuts);

		for (Map.Entry<String, DisplayTransaction> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue().getIncome());
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue().getSpent());
		}

	}

}
