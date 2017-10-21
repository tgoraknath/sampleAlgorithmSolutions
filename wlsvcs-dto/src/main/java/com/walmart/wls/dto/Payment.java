package com.walmart.wls.dto;


import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import com.walmart.services.dto.BaseDTO;
import com.walmart.services.wls.options.PaymentOptions;
public class Payment extends BaseDTO {

	/**
	 * id, pihash, available, balance, allocate, expired
	 */
	private static final long serialVersionUID = 3302400501022977085L;
	@XmlElement private UUID id;
	//indicates that this payment option can't be used for auth and settlements.
	@XmlElement private Long nbrOfDays2Expire;
	@XmlElement private Boolean isTemp; 
	@XmlElement private Pihash pihash;
	//total amount available for transaction's
	@XmlElement private BigDecimal available;
	//balance amount = available - allocate;
	@XmlElement private BigDecimal balance;
	//allocate is a transaction amount.
	@XmlElement private BigDecimal allocate;
	public Payment() {
	}
	public UUID getId() {
		return id;
	}
	public Long getNbrOfDays2Expire() {
		return nbrOfDays2Expire;
	}
	
	public boolean getIsTemp() {
		return isNull(isTemp) ? false : isTemp;
	}
	public Pihash getPihash() {
		return pihash;
	}
	public BigDecimal getAvailable() {
		return available;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public BigDecimal getAllocate() {
		return allocate;
	}
	public PaymentBuilder buildPaymentBuilder() {
		//total amount available for transaction's
		//balance amount = available - allocate;
		//allocate is a transaction amount.
		//indicates that this payment option can't be used for auth and settlements.
		return new PaymentBuilder()
					.id(id)
					.pihash(pihash)
					.available(available)
					.balance(balance)
					.allocate(allocate);
		//.expire(expired);
		//pb.nbrOfDays2Expire = nbrOfDays2Expire;
	}
	public static class PaymentBuilder {
		private UUID id;
		private Boolean isTemp;
		private Pihash pihash;
		//total amount available for transaction's
		private BigDecimal available;
		//balance amount = available - allocate;
		private BigDecimal balance;
		//allocate is a transaction amount.
		private BigDecimal allocate;
		/**
		 * negative value indicates that this payment option can't be used for auth and settlements.
		 * no value indicates good to go for Auth and Settlement.
		 * positive value less than 31, an indication for FE to display a message that payment option is about to expire.
		 */
		private Long nbrOfDays2Expire;
		public PaymentBuilder() {
		}
		public PaymentBuilder id(UUID id) {
			this.id = id;
			return this;
		}
		public PaymentBuilder expireDate(String expireDate, long value2Expire) {
			LocalDate today = LocalDate.now();
			LocalDate date = LocalDate.parse(expireDate);
			long days = DAYS.between(today, date);
			if(days < value2Expire) {
				nbrOfDays2Expire = days;	
			}
			return this;
		}
		public PaymentBuilder isTemp(Boolean isTemp) {
			this.isTemp = isTemp;
			return this;
		}
		public PaymentBuilder pihash(Pihash pihash) {
			this.pihash = pihash;
			return this;
		}
		public PaymentBuilder pihash(String pihash) {
			this.pihash = PaymentOptions.findByPiHash(pihash);
			return this;
		}
		public PaymentBuilder available(BigDecimal available) {
			this.available = available;
			return this;
		}
		public PaymentBuilder balance(BigDecimal balance) {
			this.balance = balance;
			return this;
		}
		public PaymentBuilder allocate(BigDecimal allocate) {
			this.allocate = allocate;
			return this;
		}

		/**
		 * Utility apis
		 */
		public Pihash getPihash() {
			return pihash;
		}
		public String getPihashAsString() {
			return pihash.getPihash();
		}
		/**
		 * 
		 * @return
		 */
		public Payment build() {
			Payment p = new Payment();
			p.id = id;
			p.isTemp = isTemp;
			p.pihash = pihash;
			//total amount available for transaction's
			p.available = available;
			//balance amount = available - allocate;
			p.balance = balance;
			//allocate is a transaction amount.
			p.allocate = allocate;
			/**
			 * negative value indicates that this payment option can't be used for auth and settlements.
			 * no value indicates good to go for Auth and Settlement.
			 * positive value less than 31, an indication for FE to display a message that payment option is about to expire.
			 */
			p.nbrOfDays2Expire = nbrOfDays2Expire;
			return p;
		}
	}

}
