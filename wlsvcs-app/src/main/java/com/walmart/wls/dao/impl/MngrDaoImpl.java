package com.walmart.wls.dao.impl;

import static io.strati.StratiServiceProvider.getInstance;
import io.strati.persistence.ak.AKBuilder;
import io.strati.persistence.ak.AKValue;
import io.strati.persistence.datastore.DataStore;
import io.strati.persistence.datastore.DataStoreBuilder;
import io.strati.persistence.datastore.DataStoreService;
import io.strati.persistence.exceptions.ConnectionException;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.walmart.wls.dao.IManagerDAO;
import com.walmart.wls.domain.ManagerEntity;
import com.walmart.wls.dto.Manager;

public class MngrDaoImpl implements IManagerDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(MngrDaoImpl.class);
	private DataStore<String, ManagerEntity> dataStore;
    private AKBuilder akBuilder;
	public MngrDaoImpl() {
		 DataStoreService dsService = 
				 				getInstance()
		         				.getDataStoreService()
		         				.get();
         DataStoreBuilder<String, ManagerEntity> dsBuilder = dsService.getDataStoreBuilder(ManagerEntity.class, ROLE_NAME);
         dataStore = dsBuilder.fromCCM("forklift");
         akBuilder = dsService.getAKBuilder();
	}

	@Override
	public ManagerEntity addMngr(UUID storeUUID, UUID deptUUID, Manager mngr) {
		String email = mngr.getEmail();
		ManagerEntity entity = view(storeUUID, deptUUID, email);
		//its a new record, so let the DAO impl take care of it..
		//ManagerEntity entity = new ManagerEntity();
		if(entity == null) {
			entity = new ManagerEntity();
			entity.setId(UUID.randomUUID());
		}
		entity.setStorePK(storeUUID);
		entity.setStoreDeptPK(deptUUID);
		entity.setFirstName(mngr.getFirstName());
		entity.setLastName(mngr.getLastName());
		entity.setEmail(email);
		entity.setPhone(mngr.getPhone());
		entity.setRole(mngr.getRole().getName());
		return persist(entity);
	}
	
	@Override
	public ManagerEntity activate(String firstName, String lastName, String email,
			String phNbr) {
		/*
		final QManagerEntity dbRecord = QManagerEntity.managerEntity;
		JPAQuery jpaQuery = this.impl.where(
							dbRecord.firstName.equalsIgnoreCase(firstName).and (
							dbRecord.lastName.equalsIgnoreCase(lastName)).and (
							dbRecord.email.equalsIgnoreCase(email)).and (
							dbRecord.phone.eq(phNbr)));
		DALResult<ManagerEntity> resStore=this.impl.query(jpaQuery, dbRecord);
		*/
		AKValue akValue = akBuilder
							.build(ManagerEntity.AK_FN, firstName, 
								   ManagerEntity.AK_LN, lastName,
								   ManagerEntity.AK_EM, email,
								   ManagerEntity.AK_PHN, phNbr);
		ManagerEntity mngr = null;
		try {
			Optional<ManagerEntity> result = dataStore.get()
					 .ak(akValue)
					 .execute();
			mngr = result.get();
		} catch (ConnectionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mngr != null) {
			mngr.setActive("Y");
			mngr = persist(mngr);
		}
		return mngr;
	}

	@Override
	public ManagerEntity deActivate(UUID mngrId) {
		ManagerEntity mngr = view(mngrId);
		if (mngr != null) {
			mngr.setActive("N");
			mngr = persist(mngr);
		}
		return mngr;
	}

	@Override
	public ManagerEntity view(UUID mngrId) {
		Optional<ManagerEntity> result = null;
		ManagerEntity resultPerson = null;
		try {
			result = dataStore.get(mngrId.toString());
			resultPerson = result.get();
		} catch (ConnectionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return resultPerson;
	}
	
	@Override
	public ManagerEntity markAsDelete(UUID mngrId) {
		ManagerEntity mngr = view(mngrId);
		if (mngr != null) {
			mngr.setActive("N");
			mngr.setDeleted("N");
			mngr = persist(mngr);
		}
		return mngr;
	}
	@Override
	public ManagerEntity persist(ManagerEntity mngr) {
		ManagerEntity dbRecord = null;
		try {
			dbRecord = dataStore.put(mngr);
		} catch(Throwable e) {
			LOGGER.error("Error while persist API: ", e, mngr);
		}
		return dbRecord;
	}
	
	private ManagerEntity view(UUID store, UUID dept, String email) {
		AKValue akValue = null;
		if(dept != null) {
			akValue = akBuilder
					  .build(ManagerEntity.AK_STORE_UUID, store, 
							 ManagerEntity.AK_DEPT_UUID, dept,
							 ManagerEntity.AK_EM, email.toLowerCase());
		} else {
			akValue = akBuilder
					  .build(ManagerEntity.AK_STORE_UUID, store, 
							 ManagerEntity.AK_EM, email.toLowerCase());
		}
		ManagerEntity entity = null;
		try {
			Optional<ManagerEntity> option = 
							dataStore.get()
						      .ak(akValue)
						      .execute();
			entity = option.get();
		} catch (ConnectionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

}
