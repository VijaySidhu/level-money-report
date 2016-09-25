package com.capitaloneinvesting.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capitaloneinvesting.exceptions.SystemException;
import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.service.TransactionService;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

@RestController
@RequestMapping("/transactions")
public class LevelMoneyResource {

	@Autowired
	private TransactionService transactionService;

	ResponseWrapper allTransactions = null;

	private static final Logger logger = LoggerFactory.getLogger(LevelMoneyResource.class);

	/**
	 * Below method load all transactions from level money and by default it
	 * ignores donut transactions
	 * 
	 * @param ignoreDonuts
	 *            is optional default value is true if set to false it will
	 *            include donut transaction as well
	 * @return JSON response of spent and income by month
	 */
	@GetMapping
	@RequestMapping("/monthlysummary")
	public @ResponseBody Map<String, DisplayTransaction> findMonthlySummary(@RequestParam(value = "ignoreDonuts", defaultValue = "true") boolean ignoreDonuts) throws SystemException {

		Map<String, DisplayTransaction> txnsMap = null;
		try {
			logger.info("Loading Transactions ignoreDonuts is set to ::" + ignoreDonuts);
			allTransactions = transactionService.getAllTransactions(ignoreDonuts);
			/**
			 * Process All Transaction
			 */
			if (allTransactions != null) {
				txnsMap = transactionService.processTransactions(allTransactions, ignoreDonuts);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SystemException systemException = new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
			throw systemException;
		}
		return txnsMap;
	}

	@GetMapping
	@RequestMapping("/predictedReport")
	public @ResponseBody Map<String, DisplayTransaction> predictedReport(@RequestParam(value = "yyyy", required = true) int yyyy, @RequestParam(value = "mm", required = true) int mm, @RequestParam(value = "ignoreDonuts", defaultValue = "true") boolean ignoreDonuts) throws SystemException {
		Map<String, DisplayTransaction> txnsMap = null;
		ResponseWrapper projectedTransactionRes = null;
		ResponseWrapper mergedResponse = null;
		try {
			logger.info("Loading Transactions ignoreDonuts is set to ::" + ignoreDonuts);
			projectedTransactionRes = transactionService.getProjectedTransactions(yyyy, mm, ignoreDonuts);
			if (null != allTransactions && null != allTransactions.getTransactions() && allTransactions.getTransactions().size() > 0) {
				transactionService.mergeProjectedWithAllTransactions(allTransactions.getTransactions(), projectedTransactionRes.getTransactions());
			} else {
				/**
				 * Get All Transactions if null
				 */
				allTransactions = transactionService.getAllTransactions(ignoreDonuts);
				/**
				 * Merge Projected Transactions with all transactions
				 */
				mergedResponse = transactionService.mergeProjectedWithAllTransactions(allTransactions.getTransactions(), projectedTransactionRes.getTransactions());
			}
			if (projectedTransactionRes != null) {
				txnsMap = transactionService.processTransactions(mergedResponse, ignoreDonuts);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SystemException systemException = new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
			throw systemException;
		}
		return txnsMap;
	}

}
