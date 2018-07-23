/**
 * 
 */
package org.newsapp.api.service;

import org.newsapp.client.INewsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Shafiq
 *
 */
@Service
public class NewsService implements INewsService{

	@Autowired
	private INewsClient newsClient;

	@Override
	public String fetchNews() {
		return newsClient.get(null, String.class);
	}

}
