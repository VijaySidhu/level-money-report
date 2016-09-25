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
import com.capitaloneinvesting.service.TransactionService;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

@RestController
@RequestMapping("/transactions")
public class LevelMoneyResource {

	@Autowired
	private TransactionService transactionService;

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
			txnsMap = transactionService.getAllTransactions(ignoreDonuts);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			SystemException systemException = new SystemException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal System Error");
			throw systemException;
		}
		return txnsMap;
	}

}
