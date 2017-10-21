package com.walmart.wls.dto;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.ca.dto.PaymentPreference;
import com.walmart.services.dto.BaseDTO;
import com.walmart.services.wls.options.MemberOptions;
import com.walmart.wls.util.CollectionUtil;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Member extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3302400501022977085L;
	@XmlElement private UUID id;
	@XmlElement private String membershipId;
	@XmlElement private String baseMembershipId;
	@XmlElement private MemberOptions type;//DM, Regular, Plus,
	@XmlElement private UUID autorenewal;
	@XmlElement private UUID subscription;
	@XmlElement private List<PaymentPreference> paymentPreferences;
	public Member() {
	}
	public UUID getId() {
		return id;
	}
	public String getMembershipId() {
		return membershipId;
	}
	public String getBaseMembershipId() {
		return baseMembershipId;
	}
	public MemberOptions getType() {
		return type;
	}
	public UUID getAutorenewal() {
		return autorenewal;
	}
	public UUID getSubscription() {
		return subscription;
	}
	public List<PaymentPreference> getPaymentPreferences() {
		return CollectionUtil.unmodifiableList(paymentPreferences);
	}
	public MemberBuilder builder() {
		return new MemberBuilder()
					.id(id)
					.membershipId(membershipId)
					.baseMembershipId(baseMembershipId)
					.type(type.name())
					.autorenewal(autorenewal)
					.subscription(subscription);
	}
	public static class MemberBuilder {
		private UUID id;
		private String membershipId;
		private String baseMembershipId;
		private MemberOptions type;//DM, Regular, Plus,
		private UUID autorenewal;
		private UUID subscription;
		public MemberBuilder() {
		}
		public MemberBuilder id(UUID id) {
			this.id = id;
			return this;
		}
		public MemberBuilder membershipId(String membershipId) {
			this.membershipId = membershipId;
			return this;
		}
		public MemberBuilder baseMembershipId(String baseMembershipId) {
			this.baseMembershipId = baseMembershipId;
			return this;
		}
		public MemberBuilder type(String type) {
			this.type = MemberOptions.valueOf(type);
			return this;
		}
		public MemberBuilder autorenewal(UUID autorenewal) {
			this.autorenewal = autorenewal;
			return this;
		}
		public MemberBuilder subscription(UUID subscription) {
			this.subscription = subscription;
			return this;
		}
		public Member build() {
			Member m = new Member();
			m.id = id;
			m.membershipId = membershipId;
			m.baseMembershipId = baseMembershipId;
			m.type = type;
			m.autorenewal = autorenewal;
			m.subscription = subscription;
			return m;
		}
	}

}
