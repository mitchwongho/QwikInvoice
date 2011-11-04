package com.studiodojo.qwikinvoice.data;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.studiodojo.qwikinvoice.utils.gdata.PersistenceManagerFactoryAction;

/**
 * The <code>UserDAO</code> class is the DAO to persist application user details
 * @author mwho
 */
public class UserDAO {
	
	static {
		ObjectifyService.register(UserCompanyBean.class);
	}
	/**
	 * @param key
	 * @return
	 */
	public final static UserBean getUserBean(Key key) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();
		UserBean retval = null;
		try {
			retval =  pm.getObjectById(UserBean.class, key);
		} catch (JDOObjectNotFoundException e) {
			//
		} finally {
			pm.close();
		}
		return retval;
	}

	/**
	 * @param key
	 * @param sessionToken
	 */
	public final static void addToken(Key key, UserBean bean) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();

		try {
			bean.setId(key);
			pm.makePersistent(bean);
		} finally {
			pm.close();
		}
	}
	/**
	 * Updates the record of the specified <code>token</code>.
	 * @param token
	 */
	public final static void updateToken(UserBean bean) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();

		try {
			pm.makePersistent(bean);
		} finally {
			pm.close();
		}
	}
	
	/**
	 * @param userLogin identifes the user
	 * @return a <code>UserCompanyBean</code> instance
	 */
	public final static UserCompanyBean getUserCompanyBean(String userLogin) {
		Objectify ofy = ObjectifyService.begin();
		return ofy.get(new com.googlecode.objectify.Key<UserCompanyBean>(UserCompanyBean.class, userLogin));
	}
	
	/**
	 * Add or update the UserCompany entity
	 * @param userLogin identifies the user. Used as the entities key
	 * @param bean the entity
	 * @return the updated entity
	 * @throws Exception upon any error
	 */
	public final static UserCompanyBean addUpdateUserCompany(String userLogin, UserCompanyBean bean) throws Exception {
		bean.validate();
		bean.setId(userLogin);
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bean);
		assert bean.getId() != null;
		return bean;
	}
}
