package com.walmart.services.wls.options;

import static java.util.Objects.nonNull;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author gtulla
 *
 */
public enum TenantOption {
	WalmartUS("1", "walmart.com", 5), SamsUS("2", "samsclub.com", 5), 
	WalmartCA("3", "walmart.ca", 5);
	String dotcom;
	String id;
	int nbrOfgiftCards;
	private static Map<String, TenantOption> MAP = new HashMap<>();
	static {
		for(TenantOption tenant : TenantOption.values()) {
			MAP.put(tenant.id, tenant);
			MAP.put(tenant.dotcom.toLowerCase(), tenant);
		}
	}
	TenantOption(String id, String dotcom, int nbrOfgiftCards) {
		this.id = id;
		this.dotcom = dotcom;
		this.nbrOfgiftCards = nbrOfgiftCards;
	}
	public String tenantId() {
		return id;
	}
	public String dotcom() {
		return dotcom;
	}
	public int nbrOfgiftCards() {
		return nbrOfgiftCards;
	}
	/**
	 * find always returns a value-TenantOption associated with given idOrDotcom.
	 * @param idOrDotcom - case in-sensitive
	 * @return null or TenantOption associated with given idOrDotcom.
	 */
	public static TenantOption find(String idOrDotcom) {
		return nonNull(idOrDotcom) ? MAP.get(idOrDotcom.toLowerCase()) : null;
	}
	
}
