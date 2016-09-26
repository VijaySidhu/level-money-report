package com.capitaloneinvesting.businesslogic;

import java.math.BigDecimal;
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

	public static Map<String, DisplayTransaction> getTransactionsToDisplay(ResponseWrapper responseObj, boolean ignoreDonuts, boolean crystalBall, boolean ignoreCreditCardPayment) {
		int donutTxnCounter = 0;
		int totalCreditCardPayments = 0;
		List<Transaction> allTransactionsList;
		if (responseObj != null && responseObj.getTransactions() != null && responseObj.getTransactions().size() > 0) {
			allTransactionsList = responseObj.getTransactions();
			Map<String, DisplayTransaction> displayTransactionsMap = new LinkedHashMap<>();
			long totalSpent = 0, totalIncome = 0;
			long totalSpentTransactionsCount = 0, totalIncomeTransactionsCount = 0;

			for (Transaction transaction : allTransactionsList) {
				if (ignoreCreditCardPayment) {
					boolean isCreditCardPayment = isCreditCardPayment(allTransactionsList, transaction);
					if (true == isCreditCardPayment) {
						totalCreditCardPayments = totalCreditCardPayments + 1;
						logger.info("                                               ");
						logger.info("*******Credit Card Payment detected************");
						logger.info(transaction.toString());
						logger.info("*******Credit Card Payment detected************");
						logger.info("                                               ");
						continue;
					}
				}
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
					/**
					 * Ignore donut Spending if ignore-donut=true
					 */
					if (!isDonutsSpending(transaction, ignoreDonuts)) {
						displayTransaction.setSpentLong(spent + Math.abs(amount));
						totalSpent += Math.abs(transaction.getAmount());
						totalSpentTransactionsCount++;
					} else {
						donutTxnCounter = donutTxnCounter + 1;
						logger.info("Ignoring donut transaction ID ::" + transaction.getTransactionId() + "Donut Merchant Name" + transaction.getRawMerchant());
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
			if (ignoreCreditCardPayment) {
				logger.info("");
				logger.info("****************************************************************");
				logger.info("Total number of credit card payments :: " + totalCreditCardPayments);
				logger.info("****************************************************************");
				logger.info("");
			}
			return displayTransactionsMap;
		}

		return null;

	}

	private static DisplayTransaction calculateAverage(long totalSpent, long totalIncome, long totalSpentTransactionsCount, long totalIncomeTransactionsCount) {
		long avgSpent = 0;
		long avgIncome = 0;
		if (0 != totalSpentTransactionsCount) {
			avgSpent = totalSpent / totalSpentTransactionsCount;
		}
		if (0 != totalIncomeTransactionsCount) {
			avgIncome = totalIncome / totalIncomeTransactionsCount;
		}
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
	 * 
	 * @param spent
	 * @param currentLocale
	 * @return
	 */
	private static String formatCurrency(long spent, Locale currentLocale) {
		BigDecimal spentDoll = new BigDecimal(spent).movePointLeft(4);
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
		return currencyFormatter.format(spentDoll);
	}

	private static boolean isDonutsSpending(Transaction transaction, boolean ignoreDonuts) {
		boolean isDonut = false;
		if (true == ignoreDonuts) {
			isDonut = transaction != null && (transaction.getMerchant().equalsIgnoreCase("Krispy Kreme Donuts") || transaction.getMerchant().equalsIgnoreCase("DUNKIN #336784"));
		}
		return isDonut;
	}

	private static boolean isCreditCardPayment(List<Transaction> transaction, Transaction currentTxn) {
		for (Transaction txn : transaction) {
			if (0 == (txn.getAmount() + currentTxn.getAmount()) && (("CREDIT CARD PAYMENT".equalsIgnoreCase(txn.getRawMerchant()) || "CC PAYMENT".equalsIgnoreCase(txn.getRawMerchant()) || ("CC PAYMENT".equalsIgnoreCase(txn.getMerchant())) || ("CREDIT CARD PAYMENT".equalsIgnoreCase(txn.getMerchant()))))) {
				return true;
			}
		}
		return false;
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
