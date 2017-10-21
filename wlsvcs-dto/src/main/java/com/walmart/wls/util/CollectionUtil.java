package com.walmart.wls.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtil {
	
	private CollectionUtil() {
	}
	/**
	 * findOdds API determines the keys from reqIds which are not part of cacheIds.
	 * Please refer to Sourcing Services DAO ShipNodeShiftsDaoImpl class for a business use case and
	 * CollectionUtilTest for more details on Unit test cases..
	 * 
	 * @param reqIds
	 * @param cacheIds
	 * @return an Collection Set instance 
	 */
	public static Set<String> findOdds(Collection<String> reqIds, Collection<String> cacheIds) {
		return reqIds.stream()
					 .filter(p -> !cacheIds.contains(p.trim()))
					 .collect(Collectors.toSet());
	}
	
	/**
	 * unmodifiableList API is a wrapper around Collections.unmodifiableList API.
	 * The only difference is, if the list is null then returns an Empty list to avoid NPE.
	 * 
	 * @param list
	 * @return non null unmodifiable List instance
	 */
	public static <T> List<T> unmodifiableList(List<T> list) {
		List<T> retList = Collections.emptyList();
		if(list != null) {
			retList = Collections.unmodifiableList(list);
		}
		return retList;
	}
	/**
	 * unmodifiableSet API is a wrapper around Collections.unmodifiableSet API.
	 * The only difference is, if the list is null then returns an Empty list to avoid NPE.
	 * 
	 * @param set
	 * @return non null unmodifiable Set instance
	 */
	public static <T> Set<T> unmodifiableSet(Set<T> set) {
		Set<T> retList = Collections.emptySet();
		if(set != null) {
			retList = Collections.unmodifiableSet(set);
		}
		return retList;
	}
	/**
	 * unmodifiableMap API is a wrapper around Collections.unmodifiableMap API.
	 * The only difference is, if the list is null then returns an Empty list to avoid NPE.
	 * @param map
	 * @return non null unmodifiable Map instance
	 */
	public static <K, V> Map<K, V> unmodifiableMap(Map<K, V> map) {
		Map<K, V> retMap = Collections.emptyMap();
		if(map != null) {
			retMap = Collections.unmodifiableMap(map);
		}
		return retMap;
	}
}
