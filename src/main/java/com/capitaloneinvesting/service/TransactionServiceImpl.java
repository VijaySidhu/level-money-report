package com.capitaloneinvesting.service;

import java.util.Map;
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

	@Override
	public Map<String, DisplayTransaction> loadTransactions(boolean ignoreDonuts) throws Exception {
		ResponseWrapper res = restTemplate.postForObject(postEntity.getUrl(), postEntity.toString(), ResponseWrapper.class);
		Map<String, DisplayTransaction> transaction = Utilities.getTransactionsToDisplay(res, ignoreDonuts);
		return transaction;
	}

}
