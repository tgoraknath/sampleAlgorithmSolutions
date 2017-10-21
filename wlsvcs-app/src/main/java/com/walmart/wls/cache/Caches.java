/**
 * 
 */
package com.walmart.wls.cache;

import static com.walmart.wls.util.JsonUtil.toObject;
import static io.strati.StratiServiceProvider.getInstance;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import io.strati.configuration.annotation.DefaultValue;
import io.strati.configuration.annotation.Ignore;
import io.strati.configuration.annotation.PostInit;
import io.strati.configuration.annotation.PostRefresh;
import io.strati.configuration.annotation.Property;
import io.strati.configuration.context.ConfigurationContext;
import io.strati.configuration.listener.ChangeLog;
import io.strati.persistence.cache.Cache;
import io.strati.persistence.cache.CacheService;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

import com.google.common.collect.ImmutableMap;
import com.walmart.wls.domain.ManagerEntity;
import com.walmart.wls.util.JsonUtil;

/**
 * <P>Caches manages the cache initialization, 
 * read and write operations across given Cache Clusters. 
 * Its centralized for CACHE Use Cases,
 * so that adding/modifying the cache configurations will become easy to maintain.</P>
 * <P>Caches supports ACTIVE_PASSIVE at DC level, all application owners should override
 * CCM FQDN in order to have ACTIVE_ACTIVE behavior
 * between Primary and Secondary data centers.</P>
 * <P>For every single entry in PRIMARY cache, 
 * Caches will collect the secondary cache data and
 * asynchronously replicate the data into SECONDARY cache.
 * SECONDARY cache connections are initialized on-demand and shutdown
 * when the FQDN is modified and different than previous one.
 * </P>
 * <P>Its initializer/Consumer responsibility to define below properties</P>
 * <P>Configure Caches as a CCM container bean</P>
 *
 * @author <a href="mailto:gtulla@walmartlabs.com">Gorak Tulla</a>
 *
 * Sep 10, 2016
 * @version $Id: $Id
 */
public class Caches {

	@Ignore
	private  final Logger LOGGER = LoggerFactory.getLogger(Caches.class);
	@Ignore
	private  final String TIMETAKEN_LOG_MSG = "Time taken to initialize cache %s msec for the host: %s and bucket name: %s is %s msec";
	@Property(propertyName = "couchbase.put.retry.value")
	@DefaultValue.Int(0)
	private  int retryCount = 0;
	@Property(propertyName = "couchbase.nodes")
	private  String cacheNodes;
	@Property(propertyName = "couchbase.buckets")
	private  String buckets;
	/**
	 * Active Couch base Cluster Connection Pool.  name as key and Cache instance as value.
	 */
	@Ignore
	private volatile Map<String, Serializable> cacheMap = new ConcurrentHashMap<>();
	/**
	 * CacheManager is a spring based singleton class.
	 * Default constructor initializes primary cache and based on schedulers[refer to bean definition].
	 */
	public Caches() {
	}
	
	@SuppressWarnings("unchecked")
	@PostInit
	public void init(String configName, ConfigurationContext context) {
		if(isNull(buckets) || buckets.isEmpty()) {
			LOGGER.warn("Bucket Names in CCM configuration can't be empty");
			return;
		}
		long startTime = 0l;
		long endTime = 0l;
		CacheService cs = getInstance().getCacheService().get();
        String[] bucketNames = buckets.split(",");
        Serializable cacheForBucket;
        for (String bucketName : bucketNames) {
        	startTime = System.currentTimeMillis();
        	cacheForBucket = (Serializable) cs.getCacheBuilder(ManagerEntity.class, bucketName).fromCCM(configName);
        	endTime = System.currentTimeMillis();
        	if (LOGGER.isInfoEnabled()) {
    				LOGGER.info(format(TIMETAKEN_LOG_MSG, cacheNodes, bucketName, (endTime - startTime)));
    			}
        	cacheForBucket = cacheMap.put(bucketName, cacheForBucket);
        	if(nonNull(cacheForBucket)) {
        		close(cacheForBucket);
        	}
		} 
	}

	@PostRefresh
	public void myPostRefresh(String configName, List<ChangeLog> changes, ConfigurationContext context) {
		Set<ChangeLog> fqdnChanged =  
				changes.stream()
			   		   .filter(ch -> ch.getKey().equals(cacheNodes))
			   		   .collect(Collectors.toSet());
		if(! fqdnChanged.isEmpty()) {
			init(configName, context);
		}
	}
	
	/**
	 * shut down api to clean up cache instances safely.
	 */
	public void close() {
		cacheMap.forEach((k, v) -> close(v));
	}
	
	private void close(Serializable cache) {
		try {
			//TODO: check how to release the connection..
    		//invalidateAll/cleanUp may delete key/value from given cache instance.
			//cache.close();
		}catch(Exception e) {
			
		}
	}
	
	
	/**
	 *
	 * toCache API is to persist the key/value into primary cache and 
	 * with re try option just in case if there are any exceptions with first attempt.
	 * <p>You can manage re-try count value automatically. just change the value in CCM.</p>
	 */
	public final  boolean toCache(IBucket bucket, String key,
			Serializable value) {
		long startTime = System.currentTimeMillis();
		String bucketName = bucket.getBucketName();
		Serializable cache = cacheMap.get(bucketName);
		if (isNull(cache)) {
			LOGGER.warn("L2 WRITE is not initialized for  - {}", bucketName);
			return false;
		}
		boolean retFlag = false;
		while (!retFlag && retryCount > 0) {
			try {
				//cache.put(key, value);
				retFlag = true;
			} catch (Exception e) {
				LOGGER.error("L2 PUT ERROR for the cache Option: {} Key: {}",
						bucketName, key);
			}
		}
		if (LOGGER.isInfoEnabled() && retFlag) {
			long endTime = System.currentTimeMillis() - startTime;
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(
						"Time taken(msec) to saveOrUpdate a record into  {} and key {} is "
								+ endTime, bucketName, key);
			}
		}
		return retFlag;
	}
	
	/**
	 * toCache API persist given records into L2 - Couchbase. 
	 * if asJson is true, then converts given Serializable instance into json string
	 * and then persists into cache otherwise Serializable is persisted.
	 * @param bucket
	 * @param keyValueMap
	 * @param asJson
	 */
	public  final void toCache(IBucket bucket, Map<String, Serializable> keyValueMap, boolean asJson) {
		String bucketName = bucket.getBucketName();
		Serializable cache = cacheMap.get(bucketName);
		if (isNull(cache)) {
			LOGGER.warn("L2 BULK WRITE is not initialized for  - {}", bucketName);
			return;
		}
		Map<String, Serializable> bulkMap = keyValueMap;
		if(asJson) {
			bulkMap = new HashMap<>(keyValueMap.size());
			for(Entry<String, Serializable> entry : keyValueMap.entrySet()) {
				Serializable value = JsonUtil.toJson(entry.getValue());
				bulkMap.put(entry.getKey(), value);
			}
		}
		//cache.putAll(bulkMap);
	}
	
	/**
	 *
	 * get fromCache returns the primary cache data for the given key and  name.
	 */
	public  final Serializable fromCache(IBucket bucket, String key) {
		long startTime = System.currentTimeMillis();
		String bucketName = bucket.getBucketName();
		Serializable cache = cacheMap.get(bucketName);
		//here cache could be null bcz of configurations.
		if (isNull(cache)) {
			LOGGER.warn("Cache is not initialized or Disabled: {} and for key {}", bucketName, key);
			return null;
		}
		Serializable value = null;
		try {
			//value = cache.getIfPresent(key);
		}catch(Exception e){
			LOGGER.error("L2 GET ERROR for the cache Option: {} Key: {}", bucketName, key);
		} finally {
			long endTime = System.currentTimeMillis() - startTime;
			if(LOGGER.isInfoEnabled()) {
				LOGGER.info("Time taken(msec) to get a record from cache name {} and key {} is "
						+endTime, bucketName,key);
			}
		}
		return value;
	}
	
	/**
	 * fromCache collects instance associated with the key and bucket.
	 * 
	 * @param bucket
	 * @param key
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  final <T extends Serializable> T fromCache(IBucket bucket, String key, Class<T> clz) {
		return (T) fromCache(bucket, key);
	}

	
	/**
	 * fromCache API to collect data from primary cache for the given cache keys under cacheOption .
	 * fromCache always returns a valid immutable collection map.
	 *
	 * Utilize multiGet API when value in L2 is a Java instance and of Type T.
	 */
	@SuppressWarnings("unchecked")
	/**
	 * 
	 * @param bucket
	 * @param keys
	 * @return
	 */
	public  final <T extends Serializable> Map<String, T> fromCache(IBucket bucket, 
				Set<String> keys) {
		String bucketName = bucket.getBucketName();
		Serializable cache = cacheMap.get(bucketName);
		//here cache could be null bcz of configurations.
		if (isNull(cache)) {
			LOGGER.warn("L2 READ is not initialized or Disabled: {} and for key {}", bucketName, keys);
			return Collections.emptyMap();
		}
		final Map<String, Serializable> results = null;//cache.getAllPresent(keys);
		return isNull(results) ? Collections.emptyMap() : (Map<String, T>) results;
	}
	
	/**
	 * fromCache for the given keys collect the Json data from L2, converts them into
	 * T or TypeRefernce<T>.
	 * 
	 * NOTE: Consumers should utilize this API only when L2 value is in JSON format, otherwise
	 * utilize other fromCache APIs defined in this class.
	 * 
	 * @param bucket
	 * @param keys
	 * @param ref
	 * @return An Immutable collection Map with T as a Collection - List, Set, Map etc.
	 */
	public  final <T extends Serializable> Map<String, T> fromCache(IBucket bucket, 
			Set<String> keys, TypeReference<T> ref) {
		Map<String, String> cacheMap = fromCache(bucket, keys);
		ImmutableMap.Builder<String, T> resultBuilder = ImmutableMap.builder();
		String key = null;
		String value = null;
		for (Entry<String, String> entry : cacheMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			resultBuilder.put(key, toObject(value, ref));
		}
		return resultBuilder.build();
	}
	
	public boolean removeFromCache(IBucket bucket, String key) {
		String bucketName = bucket.getBucketName();
		Serializable cache = cacheMap.get(bucketName);
		if (isNull(cache)) {
			LOGGER.warn("L2 REMOVE is not initialized for : {}",
					bucketName);
			return false;
		}
		boolean isRemoved = false;
		try {
			//cache.invalidate(key);
			isRemoved = true;
		}catch(Exception e) {
			LOGGER.error("L2 REMOVE ERROR for the cache : {} Key: {}", bucketName, key);
		}
		return isRemoved;
	}
	/**
	 * removeFromCache API deletes the keys entries from given cache name. 
	 * remove is perform synchronously.
	 *
	 * @param bucket a {@link com.walmart.platform.cache.IBucket} object.
	 * @param keys a {@link java.util.Collection} object.
	 * @return a valid collection with failed keys
	 */
	@Async
	public  final boolean removeFromCache(IBucket bucket, Collection<String> keys) {
		String bucketName = bucket.getBucketName();
		Serializable cache = cacheMap.get(bucketName);
		if (isNull(cache)) {
			LOGGER.warn("L2 REMOVE is not initialized for : {}",
					bucketName);
			return false;
		}
		boolean isRemoved = false;
		try {
			//cache.invalidateAll(keys);
			isRemoved = true;
		}catch(Exception e) {
			LOGGER.error("L2 REMOVE ERROR for the cache : {} Key: {}", bucketName, keys);
		}
		return isRemoved;
	}

	/**
	 * getBucketNames returns cache names defined in the CCM.
	 *
	 * @return a {@link java.util.Collection} object.
	 */
	public  Collection<String> getBucketNames() {
		return cacheMap.keySet();
	}
	
	/**
	 * getFqdns returns the couch base host name(s) configured in CCM - CACHEaaS (ipAddress)
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public  String getFqdns(){
		return cacheNodes;
	}

}
