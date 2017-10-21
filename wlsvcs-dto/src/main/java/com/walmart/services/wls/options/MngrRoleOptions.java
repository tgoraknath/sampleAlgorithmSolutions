package com.walmart.services.wls.options;

import static java.util.Objects.isNull;
import java.util.HashMap;
import java.util.Map;
/**
 * RoleOptions enum is to define a set of values to manage store/department
 * meta data that is shown to customers.
 * 
 * @author gtulla
 * May 18, 2016
 */
public enum MngrRoleOptions {
	BO("Business Owner"),
	PO("Product Owner"),
	SE("Support Engineer"),
	SM("Store Manager"),
	CM("Club Manager"),
	DM("Department Manager"),
	NM("Node Manager");
	private static Map<String, MngrRoleOptions> MAP = new HashMap<>();
	static {
		for (MngrRoleOptions op : MngrRoleOptions.values()) {
			MAP.put(op.role, op);
			MAP.put(op.getName().toUpperCase(), op);
		}
	}
	private String role;
	MngrRoleOptions(String name) {
		this.role = name;
	}
	public String getName() {
		return this.name();
	}
	public String getDisplayName() {
		return role;
	}
	/**
	 * getMngrRole API by default return 'Support Engineer' when the
	 * input name is null or not defined otherwise returns enum associated
	 * with the name.
	 * 
	 * <br>name either can be name of the enum like BO 
	 * or case in sensitive - 'business owner'
	 * 
	 * @param role
	 * @return a non null ManageOptions instance.
	 */
	public static MngrRoleOptions getMngrRole(String role) {
		return isNull(role) || isNull(MAP.get(role.toUpperCase())) ? 
				SE : MAP.get(role.toUpperCase());
	}
}
