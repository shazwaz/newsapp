/**
 * 
 */
package org.newsapp.api;

import org.newsapp.api.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shafiq
 *
 */
@RestController
@RequestMapping("/api/news")
public class NewsController {
	@Autowired
	private INewsService service;
	
	@GetMapping("/top")
	public ResponseEntity<String> getNews(){
		try {
		return new ResponseEntity<>(service.fetchNews(),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Unknown error",HttpStatus.EXPECTATION_FAILED);
		}
	}
}
