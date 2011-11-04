/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.studiodojo.qwikinvoice.data.OrderBean.AlbumSizes;
import com.vaadin.ui.Select;

/**
 * The <code>ProductBean</code> class encapsulates an item that represents a service or item.
 * @author mwho
 */
@SuppressWarnings("serial")
@Cached
public class ProductBean extends PersistableBean {
	@Id	private Long id;
	private String productName = new String();
	private String productCode = new String();
	private int rateType = RateType.RANGE.ordinal();
	private String adminUserId; //identifies the application user (e.g user@gmail.com)
	
	/**
	 *  the different ways how rates are interpretted
	 */
	public static enum RateType {
		RANGE, OPTION
	};
	
	/**
     * Adds items/options to a Select component.
     * @param field identifies the selection field
     * @param select the Select component
     */
    public static final Select addSelectItems(Object field, Select select, Object selectedValue) {
        if ("rateType".equals(field)) {
        	for (RateType size : ProductBean.RateType.values()) {
        		select.addItem(new Integer(size.ordinal())); select.setItemCaption(new Integer(size.ordinal()), size.name());
        	}
            if (selectedValue != null) select.setValue(selectedValue);
        }
        return select;
    }
	
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#validate()
	 */
	@Override
	public void validate() throws Exception {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return the rateType
	 */
	public int getRateType() {
		return rateType;
	}
	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(int rateType) {
		this.rateType = rateType;
	}
	
}
