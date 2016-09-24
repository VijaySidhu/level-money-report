package com.capitaloneinvesting.utilities;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.model.Transaction;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

/**
 * Utility Class to Process Transaction and Formatting
 * 
 * @author VijaySidhu
 *
 */
public class Utilities {

	public static Map<String, DisplayTransaction> getTransactionsToDisplay(ResponseWrapper responseObj, boolean ignoreDonuts) {
		List<Transaction> allTransactionsList;
		if (responseObj != null && responseObj.getTransactions() != null && responseObj.getTransactions().size() > 0) {
			allTransactionsList = responseObj.getTransactions();
			Map<String, DisplayTransaction> displayTransactionsMap = new LinkedHashMap<>();
			long totalSpent = 0, totalIncome = 0;
			long totalSpentTransactionsCount = 0, totalIncomeTransactionsCount = 0;

			for (Transaction transaction : allTransactionsList) {
				DisplayTransaction displayTransaction;
				if (displayTransactionsMap.containsKey(transaction.getTransactionTime())) {
					displayTransaction = displayTransactionsMap.get(transaction.getTransactionTime());
				} else {
					displayTransaction = new DisplayTransaction(0, 0);
				}
				if (transaction.getAmount() > 0) {
					long income = displayTransaction.getIncomeLong();
					long amount = transaction.getAmount();
					displayTransaction.setIncomeLong(income + amount);

					totalIncome += transaction.getAmount();
					totalIncomeTransactionsCount++;

				} else {
					long spent = displayTransaction.getSpentLong();
					long amount = transaction.getAmount();
					if (!(isDonutsSpending(transaction) && ignoreDonuts)) {
						displayTransaction.setSpentLong(spent + Math.abs(amount));

						totalSpent += Math.abs(transaction.getAmount());
						totalSpentTransactionsCount++;
					}
				}
				formatTransaction(displayTransaction);
				displayTransactionsMap.put(transaction.getTransactionTime(), displayTransaction);
			}
			displayTransactionsMap.put("average", new DisplayTransaction(totalSpent / totalSpentTransactionsCount, totalIncome / totalIncomeTransactionsCount));
			return displayTransactionsMap;
		}
		return null;
	}

	private static void formatTransaction(DisplayTransaction displayTransaction) {
		String spent = formatCurrency(displayTransaction.getSpentLong(), Locale.US);
		displayTransaction.setSpent(spent);
		String income = formatCurrency(displayTransaction.getIncomeLong(), Locale.US);
		displayTransaction.setIncome(income);
	}

	private static String formatCurrency(long spent, Locale currentLocale) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		return currencyFormatter.format(spent);
	}

	private static boolean isDonutsSpending(Transaction transaction) {
		return transaction != null && (transaction.getMerchant().equalsIgnoreCase("Krispy Kreme Donuts") || transaction.getMerchant().equalsIgnoreCase("DUNKIN #336784"));
	}

}
