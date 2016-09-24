package com.capitaloneinvesting.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.capitaloneinvesting.service.TransactionService;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

@RestController
@RequestMapping("/transactions")
public class LevelMoneyResource {

	@Autowired
	TransactionService transactionService;

	private static final Logger logger = LoggerFactory.getLogger(LevelMoneyResource.class);

	@GetMapping
	@RequestMapping("/monthlysummary")
	public @ResponseBody Map<String, DisplayTransaction> findMonthlySummary(@RequestParam(value = "ignoreDonuts") boolean ignoreDonuts) {
		Map<String, DisplayTransaction> txnsMap = null;
		try {
			txnsMap = transactionService.loadTransactions(ignoreDonuts);
			if (null != txnsMap) {
				// TODO Formatting
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txnsMap;
	}

}
