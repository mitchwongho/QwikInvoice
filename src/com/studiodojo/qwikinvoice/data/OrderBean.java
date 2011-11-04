package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Vector;

import com.vaadin.ui.Select;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class OrderBean implements Serializable {

	private Long id = 0L;
	private Date orderDate;
	private String albumName;
	private String contactPerson;
	private String companyName;
	private String shippingAddress;
	private String city;
	private String stateProvince;
	private String country;
	private String areacode;
	private String phoneNumber;
	private String mobileNumber;
	private String email;
	private String websiteAddress ;
	//
	private int albumType = AlbumTypes.CLASSIC.ordinal(); //0=classic; 1=exclusive
	private int exclusivePageFinish = ExclusivePageType.MATT.ordinal();
	private int coverFinish = CoverTypes.PRINTED.ordinal();
	private int leatherType = LeatherTypes.NOT_APPLICABLE.ordinal();
	private int foilColour = FoilType.NOT_APPLICABLE.ordinal();
	private String foilingText;
	private int shippedOrCollected = 1;
	private String note;
	
	public static enum OrderQuantity {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN
	};
	public static enum AlbumTypes {
		CLASSIC, EXCLUSIVE
	};
	
	public static enum CoverTypes {
		WIBALIN, NOVALITE, PRINTED, CANVAS, SKIVETEX, LEATHER
	};
	
	public static enum LeatherTypes {
		BLACK, BROWN, BLACK_BUFFALO, BROWN_BUFFALO, NOT_APPLICABLE
	};
	
	public static enum FoilType {
		GOLD, SILVER, NOT_APPLICABLE
	};
	
	public static enum ExclusivePageType {
		FINEART, GLOSS, MATT, NOT_APPLICABLE
	};
	
	public static enum AlbumSizes {
		_5x5_,_5x7_, _7x7_, _8x12_, _10x10_, _12x12_, _12x16_, _16x16_
	}
	
		/**
     * @return a list of field names used for table column names.
     */
    public static final Vector<String> getFields() {
        Vector<String> v = new Vector<String>();
        for (Field fld : OrderBean.class.getDeclaredFields()) {
                if (!fld.getType().equals(OrderBean.class)) v.add(fld.getName()); //ignore constants
        }
        return v;
    }
    
    /**
     * Adds items/options to a Select component.
     * @param field identifies the selection field
     * @param select the Select component
     */
    public static final Select addSelectItems(Object field, Select select, Object selectedValue) {
            if ("albumSize".equals(field)) {
            	for (AlbumSizes size : OrderBean.AlbumSizes.values()) {
            		select.addItem(new Integer(size.ordinal())); select.setItemCaption(new Integer(size.ordinal()), size.name());
            	}
	            if (selectedValue != null) select.setValue(selectedValue);
            }
            else if ("boxSize".equals(field)) {
            	for (AlbumSizes size : OrderBean.AlbumSizes.values()) {
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
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * @param albumName the albumName to set
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	/**
	 * @return the contactPerson
	 */
	public String getContactPerson() {
		return contactPerson;
	}

	/**
	 * @param contactPerson the contactPerson to set
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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
	 * @return the shippingAddress
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress the shippingAddress to set
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the stateProvince
	 */
	public String getStateProvince() {
		return stateProvince;
	}

	/**
	 * @param stateProvince the stateProvince to set
	 */
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the areacode
	 */
	public String getAreacode() {
		return areacode;
	}

	/**
	 * @param areacode the areacode to set
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the websiteAddress
	 */
	public String getWebsiteAddress() {
		return websiteAddress;
	}

	/**
	 * @param websiteAddress the websiteAddress to set
	 */
	public void setWebsiteAddress(String websiteAddress) {
		this.websiteAddress = websiteAddress;
	}

	/**
	 * @return the albumType
	 */
	public int getAlbumType() {
		return albumType;
	}

	/**
	 * @param albumType the albumType to set
	 */
	public void setAlbumType(int albumType) {
		this.albumType = albumType;
	}

	/**
	 * @return the foilingText
	 */
	public String getFoilingText() {
		return foilingText;
	}

	/**
	 * @param foilingText the foilingText to set
	 */
	public void setFoilingText(String foilingText) {
		this.foilingText = foilingText;
	}

	/**
	 * @return the isShipped
	 */
	public int getShippedOrCollected() {
		return shippedOrCollected;
	}

	/**
	 * @param isShipped the isShipped to set
	 */
	public void setShippedOrCollected(int isShipped) {
		this.shippedOrCollected = isShipped;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the exclusivePageFinish
	 */
	public int getExclusivePageFinish() {
		return exclusivePageFinish;
	}

	/**
	 * @param exclusivePageFinish the exclusivePageFinish to set
	 */
	public void setExclusivePageFinish(int exclusivePageFinish) {
		this.exclusivePageFinish = exclusivePageFinish;
	}

	/**
	 * @return the coverFinish
	 */
	public int getCoverFinish() {
		return coverFinish;
	}

	/**
	 * @param coverFinish the coverFinish to set
	 */
	public void setCoverFinish(int coverFinish) {
		this.coverFinish = coverFinish;
	}

	/**
	 * @return the leatherType
	 */
	public int getLeatherType() {
		return leatherType;
	}

	/**
	 * @param leatherType the leatherType to set
	 */
	public void setLeatherType(int leatherType) {
		this.leatherType = leatherType;
	}

	/**
	 * @return the foilColour
	 */
	public int getFoilColour() {
		return foilColour;
	}

	/**
	 * @param foilColour the foilColour to set
	 */
	public void setFoilColour(int foilColour) {
		this.foilColour = foilColour;
	}
	
	
}
