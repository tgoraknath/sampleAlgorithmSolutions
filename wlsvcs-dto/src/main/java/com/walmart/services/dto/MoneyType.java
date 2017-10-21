package com.walmart.services.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

import com.walmart.services.enums.CurrencyUnitEnum;

public class MoneyType extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5292942318791768761L;
    @XmlElement(name = "currencyAmount")
    private BigDecimal currencyAmount;
    @XmlElement(name = "currencyUnit")
    private CurrencyUnitEnum currencyUnit;
	public MoneyType() {
	}
	public BigDecimal getCurrencyAmount() {
		return currencyAmount;
	}
	public CurrencyUnitEnum getCurrencyUnit() {
		return currencyUnit;
	}
	
	public static class MoneyTypeBuilder {
		private BigDecimal currencyAmount;
		private CurrencyUnitEnum currencyUnit = CurrencyUnitEnum.USD;
		public MoneyTypeBuilder() {
		}
		public MoneyTypeBuilder currencyAmount(BigDecimal currencyAmount) {
			this.currencyAmount = currencyAmount;
			return this;
		}
		public MoneyTypeBuilder currencyUnit(CurrencyUnitEnum currencyUnit) {
			this.currencyUnit = currencyUnit;
			return this;
		}
		public MoneyType build() {
			MoneyType mt = new MoneyType();
			mt.currencyAmount = currencyAmount;
			mt.currencyUnit = currencyUnit;
			return mt;
		}
	}
}
