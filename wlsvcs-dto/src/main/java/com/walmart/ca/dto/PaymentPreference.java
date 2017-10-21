package com.walmart.ca.dto;

import java.util.UUID;

import com.walmart.services.dto.BaseDTO;

public class PaymentPreference extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3883670409536040148L;
	private UUID customerAccountId;
	private UUID preferenceId;
	private String displayString;
	private String displayLabel;
    private String fullName;
    private String companyName;
    private String piHashId;
    private String cardExpiryDate;
    private String firstName;
    private String lastName;
    private Boolean defaultPreference;
    private Boolean cvvRequired;
    private String lastUsedDate;
    //TODO create and last modified date fields
	public PaymentPreference() {
	}
	public UUID getCustomerAccountId() {
		return customerAccountId;
	}
	public UUID getPreferenceId() {
		return preferenceId;
	}
	public String getFullName() {
		return fullName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getPiHashId() {
		return piHashId;
	}
	public String getCardExpiryDate() {
		return cardExpiryDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Boolean getDefaultPreference() {
		return defaultPreference;
	}
	public Boolean getCvvRequired() {
		return cvvRequired;
	}
	public String getLastUsedDate() {
		return lastUsedDate;
	}
	/*
	public PaymentBuilder buildPaymentBuilder() {
		return new PaymentBuilder()
					.id(preferenceId)
					.pihash(piHashId)
					.expireDate(cardExpiryDate);
	}*/
	public String getDisplayString() {
		return displayString;
	}
	public String getDisplayLabel() {
		return displayLabel;
	}

}
