/**
 * 
 */
package org.newsapp.client;

import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Shafiq
 *
 */
@Component
public class NewsClient implements INewsClient {

	@Value("${news.feedurl}")
	private String newsFeedUrl;
	
	@Value("${news.country}")
	private String country;
	
	@Value("${news.apiKey}")
	private String apiKey;

	@Autowired
	private RestTemplate template;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (newsFeedUrl == null) {
			throw new IllegalArgumentException("feed url is empty");
		}
	}

	@Override
	public <T> T get(Object param, Class<T> responseType) {
		T responseData = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> entity = null;
		if (param == null) {
			entity = new HttpEntity<>(headers);
		} else {
			entity = new HttpEntity<>(param, headers);
		}
		ResponseEntity<T> response = template.exchange(buildBaseUrl(),HttpMethod.GET, entity, responseType);
		if (HttpStatus.OK.equals(response.getStatusCode())) {
			responseData = response.getBody();
		}
		return responseData;
	}
	
	private String buildBaseUrl() {
		UriComponentsBuilder builder  = UriComponentsBuilder.fromHttpUrl(newsFeedUrl);
		builder.queryParam("country", country);
		builder.queryParam("apiKey", apiKey);
		System.out.println("############################ fetching url "+builder.toUriString());
		return builder.toUriString();
		
	}

}
