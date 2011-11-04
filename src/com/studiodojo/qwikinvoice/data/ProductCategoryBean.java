/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;


/**
 * @author mwho
 * The <code>ProductCategoryBean</code> defines a Product Category.
 */
@Cached
@SuppressWarnings("serial")
public class ProductCategoryBean extends PersistableBean {
	@Id private Long id;
	private String name; //e.g. 'Wedding','Commercial','Sport','Editorial'
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
	@Override
	public void validate() throws Exception {
		if (this.name == null || this.name.isEmpty())
			throw new Exception("The 'name' property cannot be null or empty");
		if (this.adminUserId == null || this.adminUserId.isEmpty())
			throw new Exception("The 'adminUserId' property cannot be null or empty");
	}
}
