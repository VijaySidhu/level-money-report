package com.capitaloneinvesting.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.service.helper.PostEntity;
import com.capitaloneinvesting.ui.model.DisplayTransaction;
import com.capitaloneinvesting.utilities.Utilities;

@Component
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PostEntity postEntity;

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Override
	public Map<String, DisplayTransaction> loadTransactions(boolean ignoreDonuts) throws Exception {
		Map<String, DisplayTransaction> transaction = null;
		logger.info("Calling Level Money API get-all-transactions");
		ResponseWrapper res = restTemplate.postForObject(postEntity.getUrl(), postEntity.toString(), ResponseWrapper.class);
		if ("no-error".equals(res.getError())) {
			transaction = Utilities.getTransactionsToDisplay(res, ignoreDonuts);
		} else {
			logger.error("Error while calling Level Money REST API::" + res.getError());
		}
		return transaction;
	}

}
