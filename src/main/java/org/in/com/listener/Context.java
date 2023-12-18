package org.in.com.listener;

import java.net.URL;

import org.in.com.ehcache.EhcacheManeger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class Context {

	@Bean(name = "firstCacheMngr")
	public String initializeEhCache() {
		URL url = Context.class.getResource("/ehcache.xml");
		EhcacheManeger.InitCacheManager(url);
		return "";
	}

}
