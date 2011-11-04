/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.invoice;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.InvoiceBean;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * @author mwho
 *
 */
@SuppressWarnings({ "serial" })
public class InvoiceForm extends Form implements FormFieldFactory {

	
	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	
	/**
	 * @param theSession
	 * @param theApp
	 * @throws Exception
	 */
	public void init (SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		InvoiceBean theBean = this.theSession.getInvoiceBean();
		super.setSizeFull();
        super.setWriteThrough(false); //buffer the changes
        super.setFormFieldFactory(this);
        super.setItemDataSource(new BeanItem<InvoiceBean>(theBean), InvoiceBean.getFields()); //bind the entity to the form
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field fld = null;
        String pid = (String) propertyId;
        if ("date".equalsIgnoreCase(pid)) {
        	DateField date = new DateField("Invoice Date (yyyy/mm/dd)");
            date.setDateFormat("yyyy/MM/dd");
            fld = date;
            fld.setRequired(false);
            fld.setReadOnly(true);
            fld.setDescription("The date the invoice was created");
        } else if ("quoteExpiryDate".equalsIgnoreCase(pid)) {
        	DateField date = new DateField("Quote Expiry Date (yyyy/mm/dd)");
        	date.setDateFormat("yyyy/MM/dd");
        	fld = date;
        	fld.setRequired(false);
        	fld.setDescription("The date the quote expires");
        } else if ("companyAddress".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Company Address");
        	tf.setDescription("Building name, building number, street address, postal code, city, state/province, country");
        	tf.setNullRepresentation("");
        	tf.setRows(5);
        	tf.setMaxLength(100);
        	tf.setWidth("400px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("customerAddress".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Customer Address");
        	tf.setDescription("Building name, building number, street address, postal code, city, state/province, country");
        	tf.setNullRepresentation("");
        	tf.setRows(5);
        	tf.setMaxLength(100);
        	tf.setWidth("400px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("reference".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Invoice Reference Number/Code");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(20);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("companyName".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Name");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(100);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("companyTaxNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Tax Number");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(23);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	fld= tf;
        } else if ("companyPhoneNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Phone Number");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("companyFaxNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Fax Number");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	fld= tf;
        } else if ("companyEmailAddress".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Email Address");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(500);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	fld= tf;
        } else if ("companyPersonName".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Contact Person");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(30);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("customerName".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Customer Company Name");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(30);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("customerPhoneNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Customer Phone Number");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("customerFaxNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Customer Fax Number");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(12);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	fld= tf;
        } else if ("customerEmailAddress".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Customer Email Address");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(50);
        	tf.setWidth("200px");
        	tf.setRequired(false);
        	fld= tf;
        } else if ("customerPersonName".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Customer Contact Person");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(30);
        	tf.setWidth("200px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("bankingDetails".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Banking Details");
        	tf.setNullRepresentation("");
        	tf.setDescription("Bank name, account number, account type, branch code");
        	tf.setRows(5);
        	tf.setMaxLength(100);
        	tf.setWidth("400px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("taxRatePercentage".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Applicable Tax Rate(%)");
        	tf.setNullRepresentation("");
        	tf.setDescription("The tax rate applied to the invoice total");
        	tf.setMaxLength(4);
        	tf.setRequired(false);
        	fld= tf;
        } else if ("discountAmount".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Discount");
        	tf.setNullRepresentation("");
        	tf.setDescription("The discount applied to the invoice total");
        	tf.setMaxLength(4);
        	tf.setRequired(false);
        	fld= tf;
        } 
		return fld;
	}

}
