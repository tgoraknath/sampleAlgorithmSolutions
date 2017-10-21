package com.walmart.wls.dto;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.services.dto.BaseDTO;
import com.walmart.services.wls.options.PaymentOptions;
import com.walmart.wls.dto.Payment.PaymentBuilder;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * 
 * @author gtulla
 *
 */
public class Payments extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3302400501022977085L;
	//Payment {id, pihash, available, balance, allocate, expired}
	@XmlElement private Payment preferredCard;
	@XmlElement private Payment  preferAlternative;
	@XmlElement private List<Payment> cards;
	@XmlElement private Payment cashRewards;
	@XmlElement private List<Payment> giftCards;
	@XmlElement private Payment directLine;
	//if subscription exists then will also be part of cards.
	@XmlElement private Payment subscription;
	/**
	 * if auto renewal exists then it may or may not be part of cards.
	 * if the member is plus/primary and auto renewal belongs to him/her then it will be false for
	 * complementary user. it means auto renewal payment id doesn't belongs to current user.
	 */
	@XmlElement private Payment autorenewal;
	public Payments() {
	}
	public Payment getPreferredCard() {
		return preferredCard;
	}
	public Payment getPreferAlternative() {
		return preferAlternative;
	}
	public List<Payment> getCards() {
		return cards;
	}
	public Payment getCashRewards() {
		return cashRewards;
	}
	public List<Payment> getGiftCards() {
		return giftCards;
	}
	public Payment getDirectLine() {
		return directLine;
	}
	public Payment getSubscription() {
		return subscription;
	}
	public Payment getAutorenewal() {
		return autorenewal;
	}
	public static class PaymentsBuilder {
		private PaymentBuilder preferredCard;
		private PaymentBuilder  preferAlternative;
		private List<PaymentBuilder> cards;
		private PaymentBuilder cashRewards;
		private List<PaymentBuilder> giftCards;
		private PaymentBuilder directLine;
		private PaymentBuilder autorenewal;
		//if subscription exists then will also be part of cards.
		private PaymentBuilder subscription;
		public PaymentsBuilder() {
			giftCards = new ArrayList<>();
			cards = new ArrayList<>();
		}
		public PaymentsBuilder preferredCard(PaymentBuilder preferredCard) {
			this.preferredCard = preferredCard;
			return this;
		}
		public PaymentsBuilder preferAlternative(PaymentBuilder preferAlternative) {
			this.preferAlternative = preferAlternative;
			return this;
		}
		public PaymentsBuilder cards(List<PaymentBuilder> cards) {
			this.cards = cards;
			return this;
		}
		public PaymentsBuilder cashRewards(PaymentBuilder cashRewards) {
			this.cashRewards = cashRewards;
			return this;
		}
		public PaymentsBuilder giftCards(List<PaymentBuilder> giftCards) {
			this.giftCards = giftCards;
			return this;
		}
		public PaymentsBuilder directLine(PaymentBuilder directLine) {
			this.directLine = directLine;
			return this;
		}
		public PaymentsBuilder autorenewal(PaymentBuilder autorenewal) {
			this.autorenewal = autorenewal;
			return this;
		}
		public PaymentsBuilder subscription(PaymentBuilder subscription) {
			this.subscription = subscription;
			return this;
		}
		public void assign(PaymentBuilder pymtBuilder) {
			//here pymtBuilder can be either dloc, giftcard, cash rewards or cards
			PaymentOptions po = pymtBuilder.getPihash().paymentOptions();
			//here 3 possibilities
			if(PaymentOptions.DirectLineOfCredit == po) {
				directLine = pymtBuilder;	
			}else if(PaymentOptions.CashRewards == po) {
				cashRewards = pymtBuilder;
			}else if(PaymentOptions.WalmartAndSamsGiftCard == po) {
				giftCards.add(pymtBuilder);
			}else {
				cards.add(pymtBuilder);
			}
			
		}
		public Payments build() {
			Payments ps = new Payments();
			ps.preferredCard = isNull(preferredCard) ? null : preferredCard.build();
			ps.preferAlternative = isNull(preferAlternative) ? null : preferAlternative.build();
			ps.cards = cards.isEmpty() ? null : construct(cards);
			ps.cashRewards = isNull(cashRewards) ? null : cashRewards.build();
			ps.giftCards = giftCards.isEmpty() ? null : construct(giftCards);
			ps.directLine = isNull(directLine) ? null : directLine.build();
			ps.autorenewal = isNull(autorenewal) ? null : autorenewal.build();
			//if subscription exists then will also be part of cards.
			ps.subscription = isNull(subscription) ? null : subscription.build();
			return ps;
		}
		private List<Payment> construct(List<PaymentBuilder> pbs) {
			List<Payment> list = new ArrayList<>();
			for(PaymentBuilder pb : pbs) {
				list.add(pb.build());	
			}
			return list;
		}
	}

}
