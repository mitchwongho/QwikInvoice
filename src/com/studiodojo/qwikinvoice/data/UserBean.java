/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * The <code>UserBean</code> class encapculates User information
 * @author mwho
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType=IdentityType.APPLICATION, detachable="true")
public class UserBean implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	@Persistent
	private int gdocSetup;
	@Persistent
	private int accountSetup;

	/**
	 * Default constructor
	 */
	public UserBean() {
		super();
	}

	/**
	 * @return the id
	 */
	public Key getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Key id) {
		this.id = id;
	}

	/**
	 * @return the gdocSetup
	 */
	public int getGdocSetup() {
		return gdocSetup;
	}

	/**
	 * @param gdocSetup the gdocSetup to set
	 */
	public void setGdocSetup(int gdocSetup) {
		this.gdocSetup = gdocSetup;
	}

	/**
	 * @return the accountSetup
	 */
	public int getAccountSetup() {
		return accountSetup;
	}

	/**
	 * @param accountSetup the accountSetup to set
	 */
	public void setAccountSetup(int accountSetup) {
		this.accountSetup = accountSetup;
	}

	
}
