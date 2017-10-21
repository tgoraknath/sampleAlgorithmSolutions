package com.walmart.wls.domain;

import static com.walmart.services.wls.options.MngrRoleOptions.getMngrRole;
import io.strati.persistence.Model;
import io.strati.persistence.ak.AlternateKey;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.walmart.wls.dto.Manager;
@Entity
@Table(schema="GSF", name="STORE_AND_DEPT_MANAGERS")
public class ManagerEntity implements Model<String> {
	public static final String AK_FN = "firstName";
	public static final String AK_LN = "lastName";
	public static final String AK_EM = "email";
	public static final String AK_PHN = "phone";
	public static final String AK_STORE_UUID = "STORE_UUID";
	public static final String AK_DEPT_UUID = "DEPT_UUID";
	/**
	 * 
	 */
	private UUID id = null;
	//@AlternateKey(composite = "manager", key = "spk")
	private UUID storePK = null;
	//@AlternateKey(composite = "manager", key = "sdpk")
	private UUID storeDeptPK = null;
	@AlternateKey(composite = "manager", key = "fn")
    private String firstName;
	@AlternateKey(composite = "manager", key = "ln")
    private String lastName;
	@AlternateKey(composite = "manager", key = "em")
    private String email;
	@AlternateKey(composite = "manager", key = "ph")
    private String phone;
	private String role;
	//active is a read only field, this field will be updated by EntityManager.
    private String active = "N";
	//deleted is to indicate that this mngr is no longer allowed to manage the store/dept meta data
	private String deleted = "N";
	
    @Transient
    private String lock;
    private Long version = 0l;
    
	public ManagerEntity() {
	}
	@Column(name="STORE_PK")
	public UUID getStorePK() {
		return storePK;
	}

	public void setStorePK(UUID storePK) {
		this.storePK = storePK;
	}
	@Column(name="STORE_DEPARTMENT_PK")
	public UUID getStoreDeptPK() {
		return storeDeptPK;
	}

	public void setStoreDeptPK(UUID storeDeptPK) {
		this.storeDeptPK = storeDeptPK;
	}

	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="ROLE_NAME")
	public String getRole() {
		return getMngrRole(role).getDisplayName();
	}

	public void setRole(String role) {
		this.role = getMngrRole(role).name();
	}

//	public boolean isActive() {
//		//return getFlag(active);
//		return true;
//	}
	@Column(name="IS_ENABLED")
	public String getActive() {
	    return this.active;
	}
	public void setActive(String active) {
		this.active = active;
	}

//	public boolean isDeleted() {
//		//return getFlag(deleted);
//		return true;
//	}
	
	@Column(name="IS_DELETED")
	public String getDeleted() {
	    return this.deleted;
	}
	
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	/*
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}*/
	
	/**
	 * buildProfile API constructs a Manager instance from current instance.
	 * @return non null Manager instance.
	 */
	public Manager buildProfile() {
		Manager profile = new Manager.ManagerBuilder()
									 .id(UUID.fromString(getId()))
									 .firstName(firstName)
									 .lastName(lastName)
									 .email(email)
									 .phone(phone)
									 .role(getRole())
									 .active(true)
									 .markedAsDeleted(true)
									 .build();
		return profile;
	}

	@Id
	@Override
	public String getId() {
		return id.toString();
	}

	public void setId(UUID id) {
	    this.id = id;
	}

	@Override
	@Transient
	public String getLock() {
		return lock;
	}
	@Override
	public void setLock(String lock) {
		this.lock = lock;
	}

	@Override
	//@Version
	@Column(name="DB_LOCK_VERSION")
	public Long getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
