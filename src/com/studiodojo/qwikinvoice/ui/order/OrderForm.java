/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.order;

import java.util.Date;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.OrderBean;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class OrderForm extends Form implements FormFieldFactory {

	/** maintains the order details*/
	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	
	/**
	 * Initialise this Form
	 * @param bean
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) {
		this.theApp = theApp;
		this.theSession = theSession;
		OrderBean theBean = this.theSession.getTheOrder();
		theBean.setOrderDate(new Date());
		super.setSizeFull();
        super.setWriteThrough(false); //buffer the changes
        super.setFormFieldFactory(this);
        super.setItemDataSource(new BeanItem<OrderBean>(theBean), OrderBean.getFields()); //bind the entity to the form
		
        // *************** HACK TO USE A SELECT FROM DATA SOURCE *****************
        Integer[] isShippedIds = new Integer[] {new Integer(0), new Integer(1)};
        String[] isShippedNames = new String[] {"COLLECTED" , "SHIPPED"};
        
        Select selectIsShipped = super.replaceWithSelect("shippedOrCollected", isShippedIds, isShippedNames);
        selectIsShipped.setNullSelectionAllowed(true);
        selectIsShipped.setNullSelectionItemId(isShippedIds[1]);
        selectIsShipped.setMultiSelect(false);
        selectIsShipped.setNewItemsAllowed(false);
        selectIsShipped.setRequired(true);
        // *************** HACK TO USE A SELECT FROM DATA SOURCE *****************
        Integer[] foilColourIds = new Integer[OrderBean.FoilType.values().length];
        String[] foilColourNames = new String[foilColourIds.length];
        for (int i = 0; i < foilColourIds.length; i++) {
        	foilColourIds[i] = OrderBean.FoilType.values()[i].ordinal();
        	foilColourNames[i] = OrderBean.FoilType.values()[i].name();
        }
        Select selectFoilColour = super.replaceWithSelect("foilColour", foilColourIds, foilColourNames);
        selectFoilColour.setNullSelectionAllowed(true);
        selectFoilColour.setNullSelectionItemId(new Integer(OrderBean.FoilType.NOT_APPLICABLE.ordinal()));
        selectFoilColour.setMultiSelect(false);
        selectFoilColour.setNewItemsAllowed(false);
        selectFoilColour.setRequired(true);
        // *************** HACK TO USE A SELECT FROM DATA SOURCE *****************
        Integer[] leatherTypeIds = new Integer[OrderBean.LeatherTypes.values().length];
        String[] leatherTypeNames = new String[leatherTypeIds.length];
        for (int i = 0; i < leatherTypeIds.length; i++) {
        	leatherTypeIds[i] = OrderBean.LeatherTypes.values()[i].ordinal();
        	leatherTypeNames[i] = OrderBean.LeatherTypes.values()[i].name();
        }
        Select selectLeatherType = super.replaceWithSelect("leatherType", leatherTypeIds, leatherTypeNames);
        selectLeatherType.setNullSelectionAllowed(true);
        selectLeatherType.setNullSelectionItemId(new Integer(OrderBean.LeatherTypes.NOT_APPLICABLE.ordinal()));
        selectLeatherType.setMultiSelect(false);
        selectLeatherType.setNewItemsAllowed(false);
        selectLeatherType.setRequired(true);
        // *************** HACK TO USE A SELECT FROM DATA SOURCE *****************
        Integer[] exclusivePageFinishIds = new Integer[OrderBean.ExclusivePageType.values().length];
        String[] exclusivePageFinishNames = new String[exclusivePageFinishIds.length];
        for (int i = 0; i < exclusivePageFinishIds.length; i++) {
        	exclusivePageFinishIds[i] = OrderBean.ExclusivePageType.values()[i].ordinal();
        	exclusivePageFinishNames[i] = OrderBean.ExclusivePageType.values()[i].name();
        }
        Select selectExclusivePageFinish = super.replaceWithSelect("exclusivePageFinish", exclusivePageFinishIds, exclusivePageFinishNames);
        selectExclusivePageFinish.setNullSelectionAllowed(true);
        selectExclusivePageFinish.setNullSelectionItemId(new Integer(OrderBean.ExclusivePageType.MATT.ordinal()));
        selectExclusivePageFinish.setMultiSelect(false);
        selectExclusivePageFinish.setNewItemsAllowed(false);
        selectExclusivePageFinish.setRequired(true);
        // *************** HACK TO USE A SELECT FROM DATA SOURCE *****************
        Integer[] coverFinishIds = new Integer[OrderBean.CoverTypes.values().length];
        String[] coverFinishNames = new String[coverFinishIds.length];
        for (int i = 0; i < coverFinishIds.length; i++) {
        	coverFinishIds[i] = OrderBean.CoverTypes.values()[i].ordinal();
        	coverFinishNames[i] = OrderBean.CoverTypes.values()[i].name();
        }
        Select selectCoverFinish = super.replaceWithSelect("coverFinish", coverFinishIds, coverFinishNames);
        selectCoverFinish.setNullSelectionAllowed(true);
        selectCoverFinish.setNullSelectionItemId(new Integer(OrderBean.CoverTypes.PRINTED.ordinal()));
        selectCoverFinish.setMultiSelect(false);
        selectCoverFinish.setNewItemsAllowed(false);
        selectCoverFinish.setRequired(true);
        // *************** HACK TO USE A SELECT FROM DATA SOURCE *****************
        Integer[] albumTypeIds = new Integer[OrderBean.AlbumTypes.values().length];
        String[] albumTypeNames = new String[albumTypeIds.length];
        for (int i = 0; i < albumTypeIds.length; i++) {
        	albumTypeIds[i] = OrderBean.AlbumTypes.values()[i].ordinal();
            albumTypeNames[i] = OrderBean.AlbumTypes.values()[i].name();
        }
        Select selectSubscriptionProfile = super.replaceWithSelect("albumType", albumTypeIds, albumTypeNames);
        selectSubscriptionProfile.setNullSelectionAllowed(true);
        selectSubscriptionProfile.setNullSelectionItemId(new Integer(OrderBean.AlbumTypes.CLASSIC.ordinal()));
        selectSubscriptionProfile.setMultiSelect(false);
        selectSubscriptionProfile.setNewItemsAllowed(false);
        selectSubscriptionProfile.setRequired(true);
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field fld = null;
        String pid = (String) propertyId;
        if ("albumType".equalsIgnoreCase(pid)) {
            /** SEE INIT FOR SELECT TRICK*/
            fld = new TextField("Album Type");
        } else if ("shippedOrCollected".equalsIgnoreCase(pid)) {
        	fld = new TextField("Shipped/Collected");
        } else if ("exclusivePageFinish".equalsIgnoreCase(pid)) {
        	fld = new TextField("Page Finish (Exclusive Album)");
        } else if ("coverFinish".equalsIgnoreCase(pid)) {
        	fld = new TextField("Cover Finish");
        } else if ("leatherType".equalsIgnoreCase(pid)) {
        	fld = new TextField("Leather Type (Applicable for leather finish)");
        } else if ("foilColour".equalsIgnoreCase(pid)) {
        	fld = new TextField("Foil Colour");
        } else if ("contactPerson".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Contact Person");
        	tf.setDescription("The name of person submitting the order");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(60);
        	tf.setWidth(400f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("companyName".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Company Name");
        	tf.setDescription("The name of studio");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(60);
        	tf.setWidth(400f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("note".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Notes");
        	tf.setDescription("Additional order notesd");
        	tf.setNullRepresentation("");
        	tf.setRows(6);
        	tf.setMaxLength(300);
        	tf.setWidth(400f, UNITS_PIXELS);
        	tf.setRequired(false);
        	fld = tf;
        } else if ("shippingAddress".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Shipping Address");
        	tf.setDescription("The shipping address");
        	tf.setNullRepresentation("");
        	tf.setRows(5);
        	tf.setMaxLength(180);
        	tf.setWidth(300f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("foilingText".equalsIgnoreCase(pid)) {
        	TextArea tf = new TextArea("Foiling Text");
        	tf.setDescription("The text for foiling");
        	tf.setNullRepresentation("");
        	tf.setRows(2);
        	tf.setMaxLength(180);
        	tf.setWidth(300f, UNITS_PIXELS);
        	tf.setRequired(false);
        	fld = tf;
        } else if ("city".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Shipping Address - City");
        	tf.setDescription("The shipping address - city");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(60);
        	tf.setWidth(260f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("stateProvince".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Shipping Address - State/Province");
        	tf.setDescription("The shipping address - State/Province");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(60);
        	tf.setWidth(260f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("country".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Shipping Address - Country");
        	tf.setDescription("The shipping address - Country");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(60);
        	tf.setWidth(260f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("areacode".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Shipping Address - Area Code");
        	tf.setDescription("The shipping address - Area Code");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(8);
        	tf.setWidth(60f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("phoneNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Phone Number");
        	tf.setDescription("Phone Number (e.g '+27115551234')");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(12);
        	tf.setWidth(260f, UNITS_PIXELS);
        	tf.setRequired(true);
        	fld = tf;
        } else if ("mobileNumber".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Mobile Number");
        	tf.setDescription("Mobile Number (e.g '+27825551234')");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(12);
        	tf.setWidth(260f, UNITS_PIXELS);
        	tf.setRequired(false);
        	fld = tf;
        } else if ("email".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Email");
        	tf.setDescription("Email (e.g 'yourname@somewhere.com'");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(40);
        	tf.setWidth(400f, UNITS_PIXELS);
        	tf.setRequired(false);
        	tf.setReadOnly(true);
        	fld = tf;
        } else if ("websiteAddress".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Website URL");
        	tf.setDescription("Website URL (e.g 'http://www.mywebsite.co.za')");
        	tf.setNullRepresentation("");
        	tf.setMaxLength(80);
        	tf.setWidth(400f, UNITS_PIXELS);
        	tf.setRequired(false);
        	fld = tf;
        } else if ("albumName".equalsIgnoreCase(pid)) {
        	/** SEE INIT FOR SELECT TRICK*/
        	TextField tf = new TextField("Album Name");
        	tf.setMaxLength(60);
        	tf.setNullRepresentation("");
        	tf.setWidth(400f, UNITS_PIXELS);
        	fld = tf;
        	fld.setRequired(true);
        	fld.setDescription("The name of the album or the bride & grooms name");
        } else if ("orderDate".equalsIgnoreCase(pid)) {
        	DateField date = new DateField("Order Date (yyyy/mm/dd)");
            date.setDateFormat("yyyy/MM/dd");
            fld = date;
            fld.setRequired(false);
            fld.setReadOnly(true);
            fld.setDescription("The date of the order");
        } 
        return fld;
	}
}
