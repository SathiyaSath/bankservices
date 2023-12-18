package org.in.com.rediscache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.in.com.dto.NamespaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class NamespaceCacheManager {
	@Autowired
	private CacheManager cacheManager;

	@SuppressWarnings("unchecked")
	public List<NamespaceDTO> getNamespaceData(String code) {
		Cache namespaceCache = cacheManager.getCache("namespaceCache");
		Cache.ValueWrapper cacheValue = namespaceCache.get(code);
		if (cacheValue != null) {
			Object cachedData = cacheValue.get();
			System.out.println("Namespace stored cache data" + cachedData);
			if (cachedData instanceof List<?>) {
				return (List<NamespaceDTO>) cachedData;
			}
		}

		return null;
	}

	public void putNamespaceData(String code, List<NamespaceDTO> namespaceDTO) {
		Cache namespaceCache = cacheManager.getCache("namespaceCache");
		Map<String, List<NamespaceDTO>> cacheEntry = new HashMap<>();
		cacheEntry.put("data", namespaceDTO);
		namespaceCache.put(code, cacheEntry);
	}

}
