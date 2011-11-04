/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.gdata;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;

/**
 * @author mwho
 * 
 */
public class TokenStore {
	/**
	 * @param key
	 * @return
	 */
	public final static TokenBean getToken(Key key) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();
		TokenBean retval = null;
		try {
			retval =  pm.getObjectById(TokenBean.class, key);
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
	public final static void addToken(Key key, TokenBean token) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();

		try {
			token.setId(key);
			pm.makePersistent(token);
		} finally {
			pm.close();
		}
	}
	/**
	 * Updates the record of the specified <code>token</code>.
	 * @param token
	 */
	public final static void updateToken(TokenBean token) {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();

		try {
			pm.makePersistent(token);
		} finally {
			pm.close();
		}
	}
}
