package com.capitaloneinvesting.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {

	@JsonProperty(value = "amount")
	private long amount;

	@JsonProperty(value = "is-pending")
	private boolean isPending;

	@JsonProperty(value = "aggregation-time")
	private String aggregationTime;

	@JsonProperty(value = "account-id")
	private String accountId;

	@JsonProperty(value = "clear-date")
	private String clearDate;

	@JsonProperty(value = "transaction-id")
	private String transactionId;

	@JsonProperty(value = "raw-merchant")
	private String rawMerchant;

	@JsonProperty(value = "categorization")
	private String categorization;

	@JsonProperty(value = "merchant")
	private String merchant;

	@JsonProperty(value = "transaction-time")
	private String transactionTime;

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public String getAggregationTime() {
		return aggregationTime;
	}

	public void setAggregationTime(String aggregationTime) {
		this.aggregationTime = aggregationTime;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRawMerchant() {
		return rawMerchant;
	}

	public void setRawMerchant(String rawMerchant) {
		this.rawMerchant = rawMerchant;
	}

	public String getCategorization() {
		return categorization;
	}

	public void setCategorization(String categorization) {
		this.categorization = categorization;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getTransactionTime() {
		return format(transactionTime);
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	private String format(String transactionTime) {
		String[] entries = transactionTime.split("-");
		int year = Integer.parseInt(entries[0]);
		int month = Integer.parseInt(entries[1]);
		String printedYearMonth = String.valueOf(year) + "-" + month;
		return printedYearMonth;

	}

}
