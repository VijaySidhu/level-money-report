package com.capitaloneinvesting.service;

import java.util.Map;

import com.capitaloneinvesting.ui.model.DisplayTransaction;

public interface TransactionService {

	/**
	 * Get All Transaction from level money API
	 * 
	 * @param ignoreDonuts
	 * @return
	 * @throws Exception
	 */
	public Map<String, DisplayTransaction> getAllTransactions(boolean ignoreDonuts) throws Exception;

}
