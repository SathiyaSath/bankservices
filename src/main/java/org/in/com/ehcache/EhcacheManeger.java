package org.in.com.ehcache;

import java.net.URL;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

public class EhcacheManeger {

	public static CacheManager cacheManager;
	public static Cache namespaceCache;
	public static Cache userCache;
	public static Cache userdataCache;

	public static void InitCacheManager(URL url) {
		if (cacheManager == null) {
			cacheManager = new CacheManager(url);
		}
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

	public void namespaceCache() {
		Ehcache cache = cacheManager.getCache("namespace");
		cache.removeAll();
		System.out.println("Cache removed Successfully");
	}

	public static Cache getNamespaceCache() {
		if (namespaceCache == null) {
			namespaceCache = getCacheManager().getCache("namespaceCache");
		}
		return namespaceCache;
	}

	public void UserCache() {
		Ehcache cache = cacheManager.getCache("user");
		cache.removeAll();
		System.out.println("Cache removed Successfully");
	}

	public static Cache getUserCache() {
		if (userCache == null) {
			userCache = getCacheManager().getCache("userCache");
		}
		return userCache;
	}

	public void UserdataCache() {
		Ehcache cache = cacheManager.getCache("user");
		cache.removeAll();
		System.out.println("Cache removed Successfully");
	}

	public static Cache getUserdataCache() {
		if (userdataCache == null) {
			userdataCache = getCacheManager().getCache("userCache");
		}
		return userdataCache;
	}

	public static Ehcache Initializer(URL url) {
		return null;
	}
}
