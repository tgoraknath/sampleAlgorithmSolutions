package com.walmart.wls.bizz;

import static java.util.Objects.nonNull;
import static com.walmart.wls.util.JsonUtil.toObject;
import io.strati.configuration.annotation.Configuration;
import io.strati.configuration.annotation.Ignore;
import io.strati.configuration.annotation.PostInit;
import io.strati.configuration.annotation.PostRefresh;
import io.strati.configuration.annotation.Property;
import io.strati.configuration.context.ConfigurationContext;
import io.strati.configuration.listener.ChangeLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.walmart.wls.dto.Member;

@Configuration(configName = "members")
public class ProfileSCMContext {
	@Property(propertyName = "direct")
	private String direct;
	@Ignore
	private static Map<UUID, Member> MAP = new HashMap<>();
	public ProfileSCMContext() {
	}
	@PostInit
	public void init(String configName, ConfigurationContext context) {
		constructAndHold(direct);
	}
	@PostRefresh
	public void myPostRefresh(String configName, List<ChangeLog> changes, ConfigurationContext context) {
		changes.forEach(cl -> manage(cl));
	}

	private void manage(ChangeLog cl) {
		constructAndHold(cl.getNewValue());
	}
	private void constructAndHold(String mJson) {
		Member profile = toObject(mJson, Member.class);
		if(nonNull(profile)) {
			MAP.put(profile.getId(), profile);
		}
	}
	public static Member find(UUID uuid) {
		return MAP.get(uuid);
	}
}
