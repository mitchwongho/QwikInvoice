/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;

/**
 * The <code>UserCompanyBean</code> class encapculates User information
 * @author mwho
 */
@SuppressWarnings("serial")
@Cached
public class UserCompanyBean implements Serializable {
	@Id String id; //user login
	private String companyName;
	private String physicalAddress;
	private String postalAddress;
	private String taxNumber;
	private String phoneNumber;
	private String mobileNumber;
	private String faxNumber;
	private String emailAddress;
	private String website;
	private byte[] logo; //blob

	/**
     * @return a list of field names used for table column names.
     */
    public static final Vector<String> getFields() {
        Vector<String> v = new Vector<String>();
        for (Field fld : UserCompanyBean.class.getDeclaredFields()) {
                if (!fld.getType().equals(UserCompanyBean.class)) v.add(fld.getName()); //ignore constants
        }
        return v;
    }
	/**
	 * Default constructor
	 */
	public UserCompanyBean() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the physicalAddress
	 */
	public String getPhysicalAddress() {
		return physicalAddress;
	}

	/**
	 * @param physicalAddress the physicalAddress to set
	 */
	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	/**
	 * @return the postalAddress
	 */
	public String getPostalAddress() {
		return postalAddress;
	}

	/**
	 * @param postalAddress the postalAddress to set
	 */
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * @return the taxNumber
	 */
	public String getTaxNumber() {
		return taxNumber;
	}

	/**
	 * @param taxNumber the taxNumber to set
	 */
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}
	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#validate()
	 */
	public void validate() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
