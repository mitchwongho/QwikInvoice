/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public abstract class PersistableBean implements Serializable {

	/**
	 * @return the id
	 */
	public abstract Long getId();

	/**
	 * @param id the id to set
	 */
	public abstract void setId(Long id);

	/**
	 * validates the properties
	 * @throws Exception upon any error
	 */
	public abstract void validate() throws Exception;
}
