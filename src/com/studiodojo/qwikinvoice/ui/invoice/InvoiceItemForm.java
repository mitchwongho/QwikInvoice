/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.invoice;

import java.util.List;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.InvoiceItemBean;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class InvoiceItemForm extends Form implements ClickListener,
		FormFieldFactory {

	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	private InvoiceItemBean theBean;
	
	/**
	 * @param theSession
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		this.theBean = new InvoiceItemBean();
		List<InvoiceItemBean> items = this.theSession.getItems();
		
		super.setSizeFull();
        super.setWriteThrough(false); //buffer the changes
        super.setFormFieldFactory(this);
        super.setItemDataSource(new BeanItem<InvoiceItemBean>(this.theBean), InvoiceItemBean.getFields()); //bind the entity to the form
        com.vaadin.ui.GridLayout footer = new com.vaadin.ui.GridLayout(3,1); 
        //footer.setWidth("10%");
        footer.setHeight("30px");
        Button addButton = new Button( "Add", this, "commit");
        Button restoreButton = new Button("Restore", this, "discard");
        addButton.addListener((Button.ClickListener)this);
        footer.addComponent(addButton);
        footer.addComponent(restoreButton);
        footer.setComponentAlignment(addButton, Alignment.MIDDLE_CENTER);
        footer.setComponentAlignment(restoreButton, Alignment.MIDDLE_CENTER);
        
        super.setFooter(footer);
	}
	
	/**
	 * Adds a click listener to the 'Add' button 
	 * @param listener a listenr
	 */
	public void addAddButtonClickListener(Button.ClickListener listener) {
		((Button)super.getFooter().getComponentIterator().next()).addListener((Button.ClickListener)listener);
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field fld = null;
        String pid = (String) propertyId;
        if ("itemCode".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Code");
        	tf.setDescription("A short code that identifies the line item");
        	tf.setMaxLength(5);
        	tf.setWidth("100px");
        	tf.setRequired(false);
        	fld= tf;
        } else if ("itemDescription".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Description");
        	tf.setDescription("Description of the line item");
        	tf.setMaxLength(22);
        	tf.setWidth("300px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("itemQuantity".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Quantity");
        	tf.setMaxLength(3);
        	tf.setWidth("100px");
        	tf.setRequired(true);
        	fld= tf;
        } else if ("itemUnitPrice".equalsIgnoreCase(pid)) {
        	TextField tf = new TextField("Unit Price");
        	tf.setMaxLength(5);
        	tf.setWidth("100px");
        	tf.setRequired(true);
        	fld= tf;
        } 
		return fld;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		if ("Add".equals(event.getButton().getCaption())) {
			// Clone bean values and add to the list
			InvoiceItemBean aBean = new InvoiceItemBean();
			aBean.setId( this.theBean.getId() );
			aBean.setItemCode( this.theBean.getItemCode() );
			aBean.setItemDescription( this.theBean.getItemDescription() );
			aBean.setItemQuantity( this.theBean.getItemQuantity() );
			aBean.setItemUnitPrice( this.theBean.getItemUnitPrice() );
			this.theSession.getItems().add(aBean);
			super.discard(); //clear the field
		}

	}

}
