package com.capitaloneinvesting.service;

import java.util.List;
import java.util.Map;

import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.model.Transaction;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

public interface TransactionService {

	/**
	 * Get All Transaction from level money API
	 * 
	 * @param ignoreDonuts
	 * @return
	 * @throws Exception
	 */
	public ResponseWrapper getAllTransactions() throws Exception;

	/**
	 * --CyrstalBal Get project transaction for given month from level money
	 */

	public ResponseWrapper getProjectedTransactions(int year, int month) throws Exception;

	public ResponseWrapper mergeProjectedWithAllTransactions(List<Transaction> allTxns, List<Transaction> projectedTxns) throws Exception;

	public Map<String, DisplayTransaction> processTransactions(ResponseWrapper response, boolean ignoreDonuts, boolean crystalBall,boolean ignoreCreditCardPayment);

}
