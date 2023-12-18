package org.in.com.rediscache;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class UserCacheManager {
	@Autowired
	private CacheManager cacheManager;

	public UserDTO getUserData(AuthDTO authDTO, UserDTO userDTO, String code) {
		Cache userCache = cacheManager.getCache("userCache");
		Cache.ValueWrapper cacheValue = userCache.get(code);
		if (cacheValue != null) {
			Object cachedData = cacheValue.get();
			System.out.println("User cache stored " + cachedData);
			if (cachedData instanceof UserDTO) {
				return (UserDTO) cachedData;
			}
		}
		return null;
	}

	public void putUserData(AuthDTO authDTO, UserDTO userDTO, String code) {
		Cache userCache = cacheManager.getCache("userCache");
		userCache.put(code, userDTO);
	}
}
