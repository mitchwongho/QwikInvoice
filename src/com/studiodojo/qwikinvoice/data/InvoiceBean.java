/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Vector;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class InvoiceBean implements Serializable {

	private Long id;
	private Date date = new Date();
	private Date quoteExpiryDate = new Date();
	private String reference = "";
	private String companyName = "";
	private String companyAddress = "";
	private String companyTaxNumber = "";
	private String companyPhoneNumber = "";
	private String companyFaxNumber = "";
	private String companyEmailAddress = "";
	private String companyPersonName = "";
	
	private String customerName = "";
	private String customerAddress = "";
	private String customerPhoneNumber = "";
	private String customerFaxNumber = "";
	private String customerEmailAddress = "";
	private String customerPersonName = "";
	
	private String bankingDetails = "";
	private double taxRatePercentage = 14.0D;
	private double discountAmount = 0.0D;
	
	/**
     * @return a list of field names used for table column names.
     */
    public static final Vector<String> getFields() {
        Vector<String> v = new Vector();
        for (Field fld : InvoiceBean.class.getDeclaredFields()) {
                if (!fld.getType().equals(InvoiceBean.class)) v.add(fld.getName()); //ignore constants
        }
        return v;
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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the quoteExpiryDate
	 */
	public Date getQuoteExpiryDate() {
		return quoteExpiryDate;
	}
	/**
	 * @param quoteExpiryDate the quoteExpiryDate to set
	 */
	public void setQuoteExpiryDate(Date quoteExpiryDate) {
		this.quoteExpiryDate = quoteExpiryDate;
	}
	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(String reference) {
		this.reference = reference;
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
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the companyTaxNumber
	 */
	public String getCompanyTaxNumber() {
		return companyTaxNumber;
	}
	/**
	 * @param companyTaxNumber the companyTaxNumber to set
	 */
	public void setCompanyTaxNumber(String companyTaxNumber) {
		this.companyTaxNumber = companyTaxNumber;
	}
	/**
	 * @return the companyPhoneNumber
	 */
	public String getCompanyPhoneNumber() {
		return companyPhoneNumber;
	}
	/**
	 * @param companyPhoneNumber the companyPhoneNumber to set
	 */
	public void setCompanyPhoneNumber(String companyPhoneNumber) {
		this.companyPhoneNumber = companyPhoneNumber;
	}
	/**
	 * @return the companyFaxNumber
	 */
	public String getCompanyFaxNumber() {
		return companyFaxNumber;
	}
	/**
	 * @param companyFaxNumber the companyFaxNumber to set
	 */
	public void setCompanyFaxNumber(String companyFaxNumber) {
		this.companyFaxNumber = companyFaxNumber;
	}
	/**
	 * @return the companyEmailAddress
	 */
	public String getCompanyEmailAddress() {
		return companyEmailAddress;
	}
	/**
	 * @param companyEmailAddress the companyEmailAddress to set
	 */
	public void setCompanyEmailAddress(String companyEmailAddress) {
		this.companyEmailAddress = companyEmailAddress;
	}
	/**
	 * @return the companyPersonName
	 */
	public String getCompanyPersonName() {
		return companyPersonName;
	}
	/**
	 * @param companyPersonName the companyPersonName to set
	 */
	public void setCompanyPersonName(String companyPersonName) {
		this.companyPersonName = companyPersonName;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}
	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	/**
	 * @return the customerPhoneNumber
	 */
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	/**
	 * @param customerPhoneNumber the customerPhoneNumber to set
	 */
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	/**
	 * @return the customerFaxNumber
	 */
	public String getCustomerFaxNumber() {
		return customerFaxNumber;
	}
	/**
	 * @param customerFaxNumber the customerFaxNumber to set
	 */
	public void setCustomerFaxNumber(String customerFaxNumber) {
		this.customerFaxNumber = customerFaxNumber;
	}
	/**
	 * @return the customerEmailAddress
	 */
	public String getCustomerEmailAddress() {
		return customerEmailAddress;
	}
	/**
	 * @param customerEmailAddress the customerEmailAddress to set
	 */
	public void setCustomerEmailAddress(String customerEmailAddress) {
		this.customerEmailAddress = customerEmailAddress;
	}
	/**
	 * @return the customerPersonName
	 */
	public String getCustomerPersonName() {
		return customerPersonName;
	}
	/**
	 * @param customerPersonName the customerPersonName to set
	 */
	public void setCustomerPersonName(String customerPersonName) {
		this.customerPersonName = customerPersonName;
	}
	/**
	 * @return the bankingDetails
	 */
	public String getBankingDetails() {
		return bankingDetails;
	}
	/**
	 * @param bankingDetails the bankingDetails to set
	 */
	public void setBankingDetails(String bankingDetails) {
		this.bankingDetails = bankingDetails;
	}
	/**
	 * @return the taxRatePercentage
	 */
	public double getTaxRatePercentage() {
		return taxRatePercentage;
	}
	/**
	 * @param taxRatePercentage the taxRatePercentage to set
	 */
	public void setTaxRatePercentage(double taxRatePercentage) {
		this.taxRatePercentage = taxRatePercentage;
	}
	/**
	 * @return the discountAmount
	 */
	public double getDiscountAmount() {
		return discountAmount;
	}
	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	/**
	 * @throws Exception upon any error
	 */
	public void validate() throws Exception {
		String errMsg = null;
		if (this.reference == null || this.reference.isEmpty())
			errMsg = "Required field missing : Invoice Reference Number";
		else if (this.companyName == null || this.companyName.isEmpty())
			errMsg = "Required field missing : Company name";
		else if (this.companyAddress == null || this.companyAddress.isEmpty())
			errMsg = "Required field missing : Company Address";
		else if (this.companyPhoneNumber == null || this.companyPhoneNumber.isEmpty())
			errMsg = "Required field missing : Company Phone Number";
		else if (this.customerAddress == null || this.customerAddress.isEmpty())
			errMsg = "Required field missing : Customer Address";
		else if (this.bankingDetails == null || this.bankingDetails.isEmpty())
			errMsg = "Required field missing : Banking Details";
		
		if (errMsg == null) return;
		else throw new Exception(errMsg);
	}
	
}
