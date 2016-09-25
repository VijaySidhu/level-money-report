package com.capitaloneinvesting.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.capitaloneinvesting.businesslogic.Businesslogic;
import com.capitaloneinvesting.model.ResponseWrapper;
import com.capitaloneinvesting.ui.model.DisplayTransaction;

@Component
public class TransactionServiceImpl implements TransactionService {

	@Value("${uid}")
	private String uid;

	@Value("${apitoken}")
	private String apiToken;

	@Value("${token}")
	private String token;

	@Value("${url}")
	private String url;

	@Value("${jsonstrictmode}")
	private String jsonStrictMode;

	@Value("${jsonverbosemode}")
	private String jsonVerboseMode;

	@Autowired
	private RestTemplate restTemplate;

	private Map<String, DisplayTransaction> transaction;

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Override
	public Map<String, DisplayTransaction> getAllTransactions(boolean ignoreDonuts) throws Exception {
		logger.info("Calling Level Money API get-all-transactions");
		ResponseWrapper res = restTemplate.postForObject(url, this.toString(), ResponseWrapper.class);
		if ("no-error".equals(res.getError())) {
			transaction = Businesslogic.getTransactionsToDisplay(res, ignoreDonuts);
		} else {
			logger.error("Error while calling Level Money REST API::" + res.getError());
		}
		return transaction;
	}

	@Override
	public String toString() {
		return "{\"args\": {\"uid\": " + uid + ", \"token\": \"" + token + "\", \"api-token\": \"" + apiToken + "\", \"json-strict-mode\": " + jsonStrictMode + ", \"json-verbose-response\": " + jsonVerboseMode + "}}";
	}

}
