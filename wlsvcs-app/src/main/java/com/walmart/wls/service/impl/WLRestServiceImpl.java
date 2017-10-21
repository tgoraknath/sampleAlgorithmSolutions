package com.walmart.wls.service.impl;
/**
 * Carrier Rate Code Management RESTful APIs.
 * 
 * These APIs are consumed by OMS Portal and Order Release Engines.
 * @author gtulla
 * 
 * Item Attribute Extensions..
 * Channel - Online | StoreFront | Auction(same as StoreFront with more attributes..)
 * Auction - boolean, Bid Price, Qty, Start and End Date(YYYY:MM:DD HH:MM),fulfillmentCenters: Club ids, DSVs etc
 * 
 * Auction Item Life cycle:
 * A regular online item is marked as a Auction item
 */
import static com.walmart.services.wls.options.PaymentOptions.find;
import static com.walmart.services.wls.options.PaymentOptions.findByPiHash;
import static com.walmart.wls.config.AppConfigProvider.isEnabled;
import static com.walmart.wls.mngrs.CacheManager.fromCache;
import static com.walmart.wls.mngrs.CacheManager.removeFromCache;
import static com.walmart.wls.mngrs.CacheManager.toCache;
import static java.util.Objects.isNull;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.services.pymt.PaymentsRequest;
import com.walmart.services.pymt.PaymentsResponse;
import com.walmart.wls.dao.impl.MngrDaoImpl;
import com.walmart.wls.domain.ManagerEntity;
import com.walmart.wls.dto.Manager;
import com.walmart.wls.dto.Pihash;
import com.walmart.wls.enums.Buckets;
import com.walmart.wls.service.IWLRestService;
public class WLRestServiceImpl implements IWLRestService {
	public static final Logger LOG = LoggerFactory.getLogger(WLRestServiceImpl.class);
	//@Autowired
	private MngrDaoImpl impl;
	private WLRestServiceImpl() {
	}
	
	@Override
	public String
			helloStrati(String name) {
		LOG.info("welcome to logmon-strati based logging... enjoy");
		String strati_ccm = "\n with strati-ccm functionality for property enabled ="+isEnabled();
		StringBuilder ret =	new StringBuilder("Welcome to strati..."+name+strati_ccm);
		UUID storeUUID = UUID.fromString("39584344-e9f8-4c66-872c-aa9710e80ac6");
		UUID deptUUID = null;
		String cacheValue = fromCache(Buckets.ZONE, "myKey", String.class);
		ret.append("\n strati-cache fromCache:key-myKey&value-"+cacheValue);
		cacheValue = ((Long)System.currentTimeMillis()).toString();
		toCache(Buckets.ZONE, "myKey",cacheValue);
		ret.append("\n strati-cache toCache:key-myKey&value-"+cacheValue);
		ret.append("\n strati-cache removeFromCache:key-myKey&value-"+removeFromCache(Buckets.ZONE, "myKey"));
		Manager.ManagerBuilder builder = new Manager.ManagerBuilder();
		builder.firstName("fn");
		builder.lastName("ln");
		builder.email("gtulla@walmart.com");
		builder.phone("6504302048");
		builder.role("BO");
		Manager manager = builder.build();
		
		//ManagerEntity entity = impl.addMngr(storeUUID, deptUUID, manager);
		ManagerEntity entity = impl.activate("fn", "ln", "gtulla@walmart.com", "6504302048");
		//entity = impl.view(entity.getId());
		ret.append("\n strati-forklift with CRUD: "+(entity != null ? true : false));
		return ret.toString();
	}

	@Override
	public Pihash determinePaymentOption(String query) {
		String strati_ccm = "\n with strati-ccm functionality for property enabled ="+isEnabled();
		LOG.info("welcome to logmon-strati based logging... enjoy"+strati_ccm);
		//there are 3 ways to determine
		Pihash po = find(query);
		if(isNull(po)) {
			po = findByPiHash(query);
		} 
		return isNull(po) ? new Pihash() : po;
	}

	/**
	 * 
	 */
	@Override
	public Pihash lineOfCredit(String baseMembershipId) {
		Pihash dm = find("603220504"+baseMembershipId);
		String uuid = UUID.randomUUID().toString();
		StringBuilder strBuilder = new StringBuilder("PIH.pang.SMGEDIRECT.CREDITCARD.");
		strBuilder.append(uuid);
		strBuilder.append(".");
		strBuilder.append(baseMembershipId.substring(baseMembershipId.length()-4));
		//PIH.pang.SMGEDIRECT.CREDITCARD.aca46442-f3ee-4eec-8e99-b6da51732200.2202
		dm.pihash = strBuilder.toString();
		return dm;
	}
	@Override
	public PaymentsResponse view(PaymentsRequest request) {
		return new PaymentsServiceImpl().paymentOptions(request);
		
	}
}
