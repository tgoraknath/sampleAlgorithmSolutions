package com.walmart.wls.helper;


/**
 * IErrorCode interface defines an getErrorCode API, so that ErrorCodeHelper class
 * can map to an appropriate error object defined in CCM.
 * 
 * @see ErrorCodeHelper
 * @author gtulla
 *
 */
public interface IErrorCode {
	 String getErrorCode();
	 String DEFAULT_ERROR = "{\"code\":\"204.UNKNOWN.001\",\"field\":\"application\",\"description\":\"un-defined error code and desc\",\"info\":\"configure error code and json in CCM portal\",\"severity\":\"WARN\",\"category\":\"APPLICATION\"}";
}
