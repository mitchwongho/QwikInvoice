/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.user;

import com.studiodojo.qwikinvoice.data.UserCompanyBean;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 *
 * @author mwho
 */
@SuppressWarnings("serial")
public class UserCompanySetupForm extends Form implements FormFieldFactory {
	private static UserCompanySetupForm theInstance;
	private UserCompanyBean userCompanyBean;
	
	/**
	 * Constructor
	 */
	private UserCompanySetupForm() {
		super();
	}
	
	/**
	 * @return returns the singleton instance
	 */
	public final static UserCompanySetupForm getInstance() {
		if (UserCompanySetupForm.theInstance == null) {
			UserCompanySetupForm.theInstance = new UserCompanySetupForm();
			UserCompanySetupForm.theInstance.init();
		}
		return UserCompanySetupForm.theInstance;
	}
	
	/**
	 * @param userCompanyBean the userCompanyBean to set
	 * @throws Exception upon any error
	 */
	public void setUserCompanyBean(UserCompanyBean userCompanyBean) throws Exception {
		this.userCompanyBean = userCompanyBean;
		super.setItemDataSource(new BeanItem<UserCompanyBean>(this.userCompanyBean), UserCompanyBean.getFields()); //bind the entity to the form
	}

	/**
	 * Initializes the form
	 */
	private void init() {
		super.setSizeFull();
        super.setWriteThrough(false); //buffer the changes
        super.setFormFieldFactory(this);
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field fld = null;
        String pid = (String) propertyId;
        if ("companyName".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Name");
        	tf.setMaxLength(100);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("physicalAddress".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Physical Address");
        	tf.setDescription("Building name, building number, street address, postal code, city, state/province, country");
        	tf.setRows(5);
        	tf.setMaxLength(100);
        	tf.setWidth("400px");
        	tf.setRequired(true);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("postalAddress".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Postal/Shipping Address");
        	tf.setDescription("Building name, building number, street address, postal code, city, state/province, country");
        	tf.setRows(5);
        	tf.setMaxLength(100);
        	tf.setWidth("400px");
        	tf.setRequired(true);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("taxNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Tax Number");
        	tf.setMaxLength(23);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("phoneNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Phone Number");
        	tf.setDescription("e.g. '+27115551234'");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("mobileNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Mobile Number");
        	tf.setDescription("e.g. '+27115551234'");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("faxNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Fax Number");
        	tf.setDescription("e.g. '+27115551234'");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("emailAddress".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Email Address");
        	tf.setDescription("e.g. 'info@your-company.com'");
        	tf.setMaxLength(100);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	tf.setNullRepresentation("");
        	fld= tf;
        } else if ("website".equalsIgnoreCase(pid)) {
			TextField tf = new TextField("Website");
			tf.setDescription("e.g. 'www.your-company.com'");
			tf.setMaxLength(80);
			tf.setWidth("200px");
			tf.setRequired(false);
			tf.setNullRepresentation("");
			fld= tf;
        }
		return fld;
	}
	/**
	 * @return the userCompanyBean
	 */
	public UserCompanyBean getUserCompanyBean() {
		return userCompanyBean;
	}

}
