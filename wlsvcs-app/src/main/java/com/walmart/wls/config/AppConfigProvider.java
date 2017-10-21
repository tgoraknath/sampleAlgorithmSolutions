package com.walmart.wls.config;
import io.strati.configuration.annotation.Configuration;
import io.strati.configuration.annotation.Property;
@Configuration(configName = "appConfig")
public class AppConfigProvider {
	
	@Property(propertyName = "isEnabled")
	private static boolean ENABLED;
	public AppConfigProvider() {
		System.out.println("logging...."+ENABLED);
	}
	public static boolean isEnabled() {
		return ENABLED;
	}

}
