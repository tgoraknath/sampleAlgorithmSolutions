package com.walmart.wls.dao;

import java.util.UUID;

import com.walmart.wls.domain.ManagerEntity;
import com.walmart.wls.dto.Manager;
/**
 * IManagerDAO defines APIs around STORE_DEPT_MANAGERS to manage
 * activation/de-activation of Managers or to send Alerts/Notifications
 * to the Managers for the changes made to the respective store or
 * departments operational meta data.
 * 
 * @author gtulla
 * Mar 21, 2016
 */
public interface IManagerDAO {
	public static String ROLE_NAME = "demo-oracle-role"; //"storefront.gecwalmart.com";
	/**
	 * <p>addMngr API is to add a Manager to the existing store or a department.
	 * when both storeUUID and deptUUID is provided then that means given mngr
	 * is associated for the department otherwise he/she is a Store Manager. 
	 * Manager will be eligible or have privileges to manage 
	 * store or dept Operational hours, lunch hours, phone number and/or active
	 * status.</p>
	 * <p>addMngr API is intended for Store/Clubs Meta data Management Mobile App interaction</p>
	 * <p>Consumers of addMngr - Store/Club Management Portal</p>
	 * <p>addMngr will return null,<br> 
	 * when mngr is not exist<br> 
	 * when given storeUUID and deptUUID are not associated with given mngr.
	 * </p>
	 * @param storeUUID - mandatory
	 * @param deptUUID - optional
	 * @param mngr - manager for the given store or department
	 * @return Manager with manageable store meta data.
	 */
	ManagerEntity addMngr(UUID storeUUID, UUID deptUUID, Manager mngr);
	/**
	 * activate API is intended for Mobile App and
	 * the purpose or aim is to ensure that the store/department
	 * manager(s) are activated through app using activate API.
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param phNbr
	 * @return
	 */
	ManagerEntity activate(String firstName, String lastName, String email, String phNbr);
	/**
	 * <p>deActivate API is intended to de-activate an existing manager to remove
	 * Privileges to manage store/department meta data.</p>
	 * <p>deActivate is intended for Mobile App or Store Management Portal</p>
	 * @param mngrId
	 * @return Manager instance indicates de-activation details.
	 */
	ManagerEntity deActivate(UUID mngrId);
	/**
	 * <p>Logically a Manager can update store/department contact info and/or
	 * Operational Hours including Lunch Hours</p>
	 * <p>view API is to view the Store meta data for the given Manager id.
	 * response Manager will have Manager contact details,
	 * store and departments viewable and modifiable fields.</p>
	 * 
	 * <p>view API is intended for Mobile App where authorized Managers can view 
	 * and manage store/department modifiable fields on Mobile.</p>
	 *  
	 * @param mngrId
	 * @return
	 */
	ManagerEntity view(UUID mngrId);
	/**
	 * markAsDelete API is to mark an existing Manager delete flag as true.
	 * so that any updates/modifications from this manager are ignored or
	 * error out.
	 * 
	 * @param mngrId
	 * @return
	 */
	ManagerEntity markAsDelete(UUID mngrId);
	ManagerEntity persist(ManagerEntity entity);

}
