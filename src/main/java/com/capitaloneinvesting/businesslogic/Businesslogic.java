package com.capitaloneinvesting.businesslogic;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.model.Transaction;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

/**
 * This class has business logic that calculate spent,income & average
 * 
 * @author VijaySidhu
 *
 */
public class Businesslogic {

	private static final Logger logger = LoggerFactory.getLogger(Businesslogic.class);

	public static Map<String, DisplayTransaction> getTransactionsToDisplay(ResponseWrapper responseObj, boolean ignoreDonuts) {
		int donutTxnCounter = 0;
		List<Transaction> allTransactionsList;
		if (responseObj != null && responseObj.getTransactions() != null && responseObj.getTransactions().size() > 0) {
			allTransactionsList = responseObj.getTransactions();
			Map<String, DisplayTransaction> displayTransactionsMap = new LinkedHashMap<>();
			long totalSpent = 0, totalIncome = 0;
			long totalSpentTransactionsCount = 0, totalIncomeTransactionsCount = 0;
			for (Transaction transaction : allTransactionsList) {
				transaction.setTransactionTime(format(transaction.getTransactionTime()));
				logger.info(transaction.toString());
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
					} else {
						donutTxnCounter = donutTxnCounter + 1;
					}
				}
				formatTransaction(displayTransaction);
				displayTransactionsMap.put(transaction.getTransactionTime(), displayTransaction);
			}
			/**
			 * Calculate Average
			 */
			DisplayTransaction ds = calculateAverage(totalSpent, totalIncome, totalSpentTransactionsCount, totalIncomeTransactionsCount);
			/**
			 * Add Average Object to map
			 */
			displayTransactionsMap.put("average", ds);
			logger.info("Total Number of donut transactions ::" + donutTxnCounter);
			return displayTransactionsMap;
		}
		return null;
	}

	private static DisplayTransaction calculateAverage(long totalSpent, long totalIncome, long totalSpentTransactionsCount, long totalIncomeTransactionsCount) {
		long avgSpent = totalSpent / totalSpentTransactionsCount;
		long avgIncome = totalIncome / totalIncomeTransactionsCount;
		DisplayTransaction ds = new DisplayTransaction(avgSpent, avgIncome);
		formatTransaction(ds);
		return ds;
	}

	private static void formatTransaction(DisplayTransaction displayTransaction) {
		String spent = formatCurrency(displayTransaction.getSpentLong(), Locale.US);
		displayTransaction.setSpent(spent);
		String income = formatCurrency(displayTransaction.getIncomeLong(), Locale.US);
		displayTransaction.setIncome(income);
	}

	/**
	 * format currency
	 * @param spent
	 * @param currentLocale
	 * @return
	 */
	private static String formatCurrency(long spent, Locale currentLocale) {
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		return currencyFormatter.format(spent);
	}

	private static boolean isDonutsSpending(Transaction transaction) {
		boolean isDonut = transaction != null && (transaction.getMerchant().equalsIgnoreCase("Krispy Kreme Donuts") || transaction.getMerchant().contains("DUNKIN"));
		return isDonut;
	}

	/**
	 * Date formatter
	 * 
	 * @param transactionTime
	 * @return
	 */
	private static String format(String transactionTime) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
		String dts = null;
		try {
			Date dt = sf.parse(transactionTime);
			SimpleDateFormat sfs = new SimpleDateFormat("yyyy-MM");
			dts = sfs.format(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dts;

	}

}
