package org.in.com.rediscache;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CustomerCacheManager {

	@Autowired
	private CacheManager cacheManager;

	private static final String CUSTOMER_CACHE_NAME = "customerCache";

	public CustomerDTO getCustomerData(AuthDTO authDTO, CustomerDTO customerDTO, String code) {
		Cache customerCache = cacheManager.getCache(CUSTOMER_CACHE_NAME);
		Cache.ValueWrapper cacheValue = customerCache.get(code);
		if (cacheValue != null) {
			Object cachedData = cacheValue.get();
			System.out.println("Customer(redis) cache data " + cachedData);
			if (cachedData instanceof CustomerDTO) {
				return (CustomerDTO) cachedData;
			}
		}
		System.out.println("Customer(redis) cache miss for code: " + code);
		return null;
	}

	public void putCustomerData(AuthDTO authDTO, CustomerDTO customerDTO, String code) {
		Cache customerCache = cacheManager.getCache(CUSTOMER_CACHE_NAME);
		customerCache.put(code, customerDTO);
	}

	public CustomerDTO getCustomerDetails(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO) {
		Cache customerCache = cacheManager.getCache(CUSTOMER_CACHE_NAME);
		Cache.ValueWrapper cacheValue = customerCache.get(authDTO);
		if (cacheValue != null) {
			Object cachedData = cacheValue.get();
			if (cachedData instanceof CustomerDTO) {
				System.out.println("Customer(redis) cache hit for authDTO: " + authDTO);
				return (CustomerDTO) cachedData;
			}
		}
		System.out.println("Customer(redis) cache miss for authDTO: " + authDTO);
		return null;
	}

	public void putCustomerDetails(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO) {
		Cache customerCache = cacheManager.getCache(CUSTOMER_CACHE_NAME);
		customerCache.put(authDTO, bankTransactionDTO);
	}
}
