package com.capitaloneinvesting.model;

import java.util.List;
import com.capitaloneinvesting.model.Transaction;

public class ResponseWrapper {

	private String error;

	private List<Transaction> transactions;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


}
