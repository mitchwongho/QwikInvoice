/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;

/**
 * 
 * @author mwho
 */
@SuppressWarnings("serial")
@Cached
public class ProductCatalogBean extends PersistableBean {
	@Id private Long id;
	private String name; //e.g. 'Wedding Package 2011/12'
	private String adminUserId;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the adminUserId
	 */
	public String getAdminUserId() {
		return adminUserId;
	}
	/**
	 * @param adminUserId the adminUserId to set
	 */
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#validate()
	 */
	@Override
	public void validate() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
