/**
 * 
 */
package org.newsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Shafiq
 *
 */
@SpringBootApplication
public class NewsApp {
	public static void main(String[] args) {
		SpringApplication.run(NewsApp.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
	
}
