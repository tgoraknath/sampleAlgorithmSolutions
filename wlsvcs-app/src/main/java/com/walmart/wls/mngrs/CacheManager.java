package com.walmart.wls.mngrs;
import static com.walmart.wls.mngrs.SCMBeansMngr.getPrimaryCaches;
import static com.walmart.wls.mngrs.SCMBeansMngr.getSecondaryCaches;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import com.walmart.wls.cache.Caches;
import com.walmart.wls.cache.IBucket;
/**
 * CacheManager Manages CRUD operations across Primary and
 * Secondary Data Centers.
 * <p><ul>Except</ul> READ-fromCache, because READs are performed only on primary</p>
 * @author gtulla
 *
 */
public class CacheManager {
	private static Caches primary = getPrimaryCaches();
	private static Caches secondary = getSecondaryCaches();
	private CacheManager() {
	}
	/**
	 * *****************************************************************
	 * Below READ apis - fromCache on top of primary cache are 
	 * available globally.
	 * ****************************************************************
	 */
	/**
	 * 
	 * @param bucket
	 * @param key
	 * @return
	 */
	public  static Serializable fromCache(IBucket bucket, String key) {
		return primary.fromCache(bucket, key);
	}
	@SuppressWarnings("unchecked")
	public  static <T extends Serializable> T fromCache(IBucket bucket, String key, Class<T> clz) {
		return (T) primary.fromCache(bucket, key);
	}
	public  static <T extends Serializable> Map<String, T> fromCache(IBucket bucket, 
			Set<String> keys) {
		return primary.fromCache(bucket, keys);
	}
	public  static <T extends Serializable> Map<String, T> fromCache(IBucket bucket, 
			Set<String> keys, TypeReference<T> ref) {
		return primary.fromCache(bucket, keys, ref);
	}
	
	/**
	 * *****************************************************************
	 * Below PUT apis - toCache on top of primary and secondary cache are 
	 * available globally.
	 * ****************************************************************
	 */
	/**
	 * toCache api will try to persist in primary cache first,
	 * if successful then persists in secondary.
	 * 
	 * true - if the value is persisted in both primary 
	 * and secondary otherwise false.
	 * @param bucket
	 * @param key
	 * @param value
	 * @return 
	 */
	public static  boolean toCache(IBucket bucket, String key,
			Serializable value) {
		boolean isTrue = primary.toCache(bucket, key, value);
		if(isTrue) {
			isTrue = secondary.toCache(bucket, key, value);
		}
		return isTrue;
	}
	/**
	 * toCache - bulk persist api will try to persist collection
	 * map into primary and secondary caches.
	 * 
	 * @param bucket
	 * @param keyValueMap
	 * @param asJson
	 */
	public  final void toCache(IBucket bucket, Map<String, Serializable> keyValueMap, boolean asJson) {
		primary.toCache(bucket, keyValueMap, asJson);
		secondary.toCache(bucket, keyValueMap, asJson);
	}
	/**
	 * *****************************************************************
	 * Below REMOVE apis - removeFromCache on top of primary and 
	 * secondary cache are available globally.
	 * ****************************************************************
	 */
	public  static boolean removeFromCache(IBucket bucket, String key) {
		boolean flag = primary.removeFromCache(bucket, key);
		if(flag) {
			flag = secondary.removeFromCache(bucket, key);
		}
		return flag;
	}
	/**
	 * 
	 * @param bucket
	 * @param keys
	 * @return
	 */
	public  static boolean removeFromCache(IBucket bucket, Collection<String> keys) {
		boolean flag = primary.removeFromCache(bucket, keys);
		if(flag) {
			flag = secondary.removeFromCache(bucket, keys);
		}
		return flag;
	}
}
