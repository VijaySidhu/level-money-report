package com.capitaloneinvesting.service.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostEntity {

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

	@Override
	public String toString() {
		return "{\"args\": {\"uid\": " + uid + ", \"token\": \"" + token + "\", \"api-token\": \"" + apiToken + "\", \"json-strict-mode\": " + jsonStrictMode + ", \"json-verbose-response\": " + jsonVerboseMode + "}}";
	}

	public String getUrl() {
		return url;
	}

}
