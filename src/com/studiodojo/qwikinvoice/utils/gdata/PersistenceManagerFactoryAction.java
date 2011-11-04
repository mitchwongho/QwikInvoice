/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.gdata;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * @author mwho
 *
 */
public class PersistenceManagerFactoryAction {
	private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");
	/**
	 * Singlton constructor.
	 */
	private PersistenceManagerFactoryAction() {
	}
	
	/**
	 * @return the PersistanceManager
	 */
	public final static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
