/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Vector;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
//@PersistenceCapable(identityType=IdentityType.DATASTORE, detachable="true")
public class InvoiceItemBean implements Serializable {
	//@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	//@PrimaryKey
	private long id;
	//@Persistent
	private String itemCode = "";
	//@Persistent
	private String itemDescription = "";
	//@Persistent
	private double itemUnitPrice = 0.0D;
	//@Persistent
	private int itemQuantity = 0;
	
	private boolean isInsertRec = false;
	
	
	/**
	 * 
	 */
	public InvoiceItemBean() {
		
	}
	
	/**
	 * @param isInsertRec <code>true</code> if this bean is the insert record
	 */
	public InvoiceItemBean(boolean isInsertRec) {
		this();
		this.isInsertRec = isInsertRec;
	}
	/**
     * @return a list of field names used for table column names.
     */
    public static final Vector<String> getFields() {
        Vector<String> v = new Vector();
        for (Field fld : InvoiceItemBean.class.getDeclaredFields()) {
                if (!fld.getType().equals(InvoiceItemBean.class)) v.add(fld.getName()); //ignore constants
        }
        return v;
    }
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}
	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 * @return the itemUnitPrice
	 */
	public double getItemUnitPrice() {
		return itemUnitPrice;
	}
	/**
	 * @param itemUnitPrice the itemUnitPrice to set
	 */
	public void setItemUnitPrice(double itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}
	/**
	 * @return the itemQuantity
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}
	/**
	 * @param itemQuantity the itemQuantity to set
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	/**
	 * @return the isInsertRec
	 */
	public boolean isInsertRec() {
		return isInsertRec;
	}

	/**
	 * @param isInsertRec the isInsertRec to set
	 */
	public void setInsertRec(boolean isInsertRec) {
		this.isInsertRec = isInsertRec;
	}

	/**
	 * @throws Exception
	 */
	public void validate() throws Exception {
		if (this.itemDescription == null || this.itemDescription.isEmpty())
			throw new Exception("Item desription is required");
	}
}
