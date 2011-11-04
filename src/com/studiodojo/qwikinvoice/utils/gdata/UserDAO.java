package com.studiodojo.qwikinvoice.utils.gdata;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;

/**
 * The <code>UserDAO</code> class is the DAO to persist application user details
 * @author mwho
 */
public class UserDAO {
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
	 * @param key
	 * @return
	 */
	public final static UserCompanyBean getUserCompanyBean(Key key) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();
		UserCompanyBean retval = null;
		try {
			retval =  pm.getObjectById(UserCompanyBean.class, key);
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
	public final static void addToken(Key key, UserCompanyBean bean) {
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
	public final static void updateToken(UserCompanyBean bean) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();
		
		try {
			pm.makePersistent(bean);
		} finally {
			pm.close();
		}
	}
}
