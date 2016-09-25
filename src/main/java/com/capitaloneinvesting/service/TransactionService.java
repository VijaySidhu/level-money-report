package com.capitaloneinvesting.service;

import java.util.Map;

import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

public interface TransactionService {

	/**
	 * Get All Transaction from level money API
	 * 
	 * @param ignoreDonuts
	 * @return
	 * @throws Exception
	 */
	public ResponseWrapper getAllTransactions(boolean ignoreDonuts) throws Exception;

	/**
	 * --CyrstalBal Get project transaction for given month from level money
	 */

	public ResponseWrapper getProjectTransactions(int year, int month,boolean ignoreDonuts) throws Exception;
	
	public Map<String, DisplayTransaction> processTransactions(ResponseWrapper response, boolean ignoreDonuts);

}
