package com.walmart.wls.config;

import io.strati.configuration.annotation.Configuration;
import io.strati.configuration.annotation.Ignore;
import io.strati.configuration.annotation.PostInit;
import io.strati.configuration.annotation.PostRefresh;
import io.strati.configuration.context.ConfigurationContext;
import io.strati.configuration.listener.ChangeLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration(configName = "errors")
public class AppErrorProvider {
	/**
	 * MAP contains error code[xxx.yyy.zzz] as key and its Json representation as value.
	 * where xxx is SOA-RI, yyy is artifact logical id and zzz is application logical error code.
	 * 
	 */
	@Ignore
	private static Map<String, String> errMap = new HashMap<String, String>();
	public AppErrorProvider() {
	}
	@PostInit
	public void init(String configName, ConfigurationContext context) {
	}
	@PostRefresh
	public void myPostRefresh(String configName, List<ChangeLog> changes, ConfigurationContext context) {
		changes.forEach(cl -> error(cl));
	}
	private boolean error(ChangeLog cl) {
		errMap.put(cl.getKey(), cl.getNewValue());
		return true;
	}

}
