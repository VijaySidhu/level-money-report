package com.capitaloneinvesting.service;

import java.util.Map;

import com.capitaloneinvesting.ui.model.DisplayTransaction;

public interface TransactionService {

	//public void process(InputStream inputStream) throws Exception;

	public Map<String, DisplayTransaction> loadTransactions(boolean ignoreDonuts) throws Exception;

}
