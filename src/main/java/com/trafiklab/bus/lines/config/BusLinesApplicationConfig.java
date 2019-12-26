package com.trafiklab.bus.lines.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching
public class BusLinesApplicationConfig {
	
	@Value("${connect.timeout}")
	private int timeoutInMillis;
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		return restTemplate;
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = 
				new HttpComponentsClientHttpRequestFactory();

		RequestConfig config = 
				RequestConfig.custom()
				.setConnectTimeout(timeoutInMillis)
				.setConnectionRequestTimeout(timeoutInMillis)
				.setSocketTimeout(timeoutInMillis)
				.build();

		CloseableHttpClient httpClient =
				HttpClientBuilder.create()
				.setDefaultRequestConfig(config)
				.build();

		clientHttpRequestFactory.setHttpClient(httpClient);
		return clientHttpRequestFactory;
	}

}
