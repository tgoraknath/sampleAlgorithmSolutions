package com.walmart.wls.cache;

import io.strati.persistence.Model;

/**
 * why do cache consumer needs to implement Model 
 * and whats is the significance or life cycle of these apis... 
 * @author gtulla
 *
 */
public class BaseCache implements Model<String> {

	@Deprecated
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return "";
	}
	@Deprecated
	@Override
	public String getLock() {
		// TODO Auto-generated method stub
		return "";
	}
	@Deprecated
	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return Long.MIN_VALUE;
	}
	@Deprecated
	@Override
	public void setLock(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Deprecated
	@Override
	public void setVersion(Long arg0) {
		// TODO Auto-generated method stub
		
	}

}
