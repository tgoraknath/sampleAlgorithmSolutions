package com.walmart.wls.mngrs;

import io.strati.configuration.annotation.ManagedConfiguration;

import com.walmart.wls.bizz.ProfileSCMContext;
import com.walmart.wls.cache.PrimaryCaches;
import com.walmart.wls.cache.SecondaryCaches;
import com.walmart.wls.config.AppConfigProvider;
import com.walmart.wls.config.AppErrorProvider;

/**
 * SCMBeansInitializer is class which holds all those classes which are dependent on
 * SCM. SCMBeansInitializer is like one-stop shop for application owners to refer and
 * can figure out the classes involved, 
 * @author gtulla
 *
 */
public class SCMBeansMngr {
	
	@ManagedConfiguration
	private static AppConfigProvider appConfigProvider;
	//@ManagedConfiguration
	private static AppErrorProvider appErrorProvider;
	@ManagedConfiguration
	private static ProfileSCMContext profileSCMContext;
	//@ManagedConfiguration
	private static PrimaryCaches primaryCaches;
	//@ManagedConfiguration
	private static SecondaryCaches secondaryCaches;
	private SCMBeansMngr() {
	}
	public static AppConfigProvider getAppConfigProvider() {
		return appConfigProvider;
	}
	public static AppErrorProvider getAppErrorProvider() {
		return appErrorProvider;
	}
	public static PrimaryCaches getPrimaryCaches() {
		return primaryCaches;
	}
	public static SecondaryCaches getSecondaryCaches() {
		return secondaryCaches;
	}
	/**
	 * life cycle API and should be invoked at shut down.
	 */
	protected void close() {
		//Close Cache instances..
		primaryCaches.close();
		secondaryCaches.close();
	}
}
