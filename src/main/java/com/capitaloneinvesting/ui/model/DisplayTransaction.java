package com.capitaloneinvesting.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "spentLong", "incomeLong" })
public class DisplayTransaction {

	private long spentLong;

	private long incomeLong;

	private String spent;

	private String income;

	public DisplayTransaction(long spent, long income) {
		this.spentLong = spent;
		this.incomeLong = income;
	}

	public long getSpentLong() {
		return spentLong;
	}

	public void setSpentLong(long spentLong) {
		this.spentLong = spentLong;
	}

	public long getIncomeLong() {
		return incomeLong;
	}

	public void setIncomeLong(long incomeLong) {
		this.incomeLong = incomeLong;
	}

	public String getSpent() {
		return spent;
	}

	public void setSpent(String spent) {
		this.spent = spent;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	
}
