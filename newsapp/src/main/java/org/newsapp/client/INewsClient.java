/**
 * 
 */
package org.newsapp.client;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author Shafiq
 *
 */
public interface INewsClient extends InitializingBean{
	
	<T> T get(Object param,Class<T> responseType);
	default void afterPropertiesSet() throws Exception{
		
	}
}
