package com.walmart.wls.dto;

import static com.walmart.services.wls.options.MngrRoleOptions.getMngrRole;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.walmart.services.wls.options.MngrRoleOptions;

/**
 * Manager dto is to be used to perform below operations
 * <p>activate an existing manager.</p>
 * <p>de-activate an existing manager.</p>
 * <p>view store meta data, so that manager can manage OpHrs, Lunch Hrs, 
 * phone number and active attributes at Store/Department meta data.</p>
 * 
 * @author gtulla
 * Mar 21, 2016
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Manager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4710639833878534411L;
	/** utilize id to persist in mobile app, update store/dept meta data, 
	 * deactivate and view store meta data.
	 */
	@XmlElement private UUID id = null;
	@XmlElement private String firstName = null;
	@XmlElement private String lastName = null;
	@XmlElement private String email = null;
	@XmlElement private String phone = null;
	@XmlElement private String role = null;
	@XmlElement private Boolean active;
	@XmlElement private Boolean markedAsDeleted;
	//TODO add lastAccessed, create date/time, nbr of times activated 
	/**
	 * Default constructor for json marshalling/un-marshalling
	 */
	public Manager() {
	}
	/**
	 * getId may return null, when an instance is created to add a new entry.
	 * @return id
	 */
	public UUID getId() {
		return id;
	}

	public boolean hasUUID() {
		return true;
	}
	public String getFirstName() {
		return firstName.trim();
	}

	public String getLastName() {
		return lastName.trim();
	}

	public String getEmail() {
		return email.trim();
	}

	public String getPhone() {
		return phone.trim();
	}

	public MngrRoleOptions getRole() {
		return getMngrRole(role);
	}

	public boolean isMarkedAsDeleted() {
		if(Boolean.FALSE.equals(markedAsDeleted)) {
			return false;
		}
		return true;
	}

	public boolean isActive() {
		if(Boolean.FALSE.equals(active)) {
			return false;
		}
		return true;
	}

	
	public Boolean getActive() {
		return active;
	}
	public Boolean getMarkedAsDeleted() {
		return markedAsDeleted;
	}
	public boolean hasMandatoryForActivation() {
		if(nonNull(firstName) 
				&& nonNull(lastName)
				&& nonNull(email)
				&& nonNull(phone)) {
			return true;
		}
		return false;
	}
	/*
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}*/

	public static class ManagerBuilder {
		private UUID id = null;
		private String firstName = null;
		private String lastName = null;
		private String email = null;
		private String phone = null;
		private String role = null;
		private Boolean active = null;
		private Boolean deleted = null;
		public ManagerBuilder() {
		}
		public ManagerBuilder id(UUID uuid) {
			this.id = uuid;
			return this;
		}
		public ManagerBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		public ManagerBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		public ManagerBuilder email(String email) {
			this.email = email;
			return this;
		}
		public ManagerBuilder phone(String phone) {
			this.phone = phone;
			return this;
		}
		public ManagerBuilder role(String role) {
			this.role = role;
			return this;
		}
		public ManagerBuilder active(boolean active) {
			this.active = active;
			return this;
		}
		public ManagerBuilder markedAsDeleted(boolean deleted) {
			this.deleted = deleted;
			return this;
		}
		public Manager build() {
			Manager mngr = new Manager();
			mngr.id = id;
			mngr.firstName = firstName;
			mngr.lastName = lastName;
			mngr.email = email;
			mngr.phone = phone;
			mngr.role = role;
			mngr.active = active;
			mngr.markedAsDeleted = deleted;
			return mngr;
		}
	}
}
