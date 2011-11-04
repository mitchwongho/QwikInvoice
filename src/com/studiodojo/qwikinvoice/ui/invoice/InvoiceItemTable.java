/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.InvoiceItemBean;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class InvoiceItemTable extends Table implements Button.ClickListener, TableFieldFactory, ItemClickListener, Handler {
	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	
	private static final Object[] NATURAL_COL_ORDER = new Object[] { "itemCode", "itemDescription", "itemQuantity", "itemUnitPrice" };
    private final static String[] COL_HEADERS = new String[] { "Code", "Description", "Quantity", "Unit Price"};
    private final static int TABLE_PAGE_LENGTH = 20;
    
    private List<InvoiceItemBean> items;
	
	/**
	 * @param theSession
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		this.items = new Vector<InvoiceItemBean>(this.theSession.getItems());
		
		super.setWidth("100%");
		super.setHeight("150px");
        super.setWriteThrough(false); //update value after a commit
        super.setColumnReorderingAllowed(false); //users can't move the columns
        super.setColumnCollapsingAllowed(true);
        super.setColumnCollapsingAllowed(false);
        super.setEditable(true);
        super.setSelectable(true);
        super.setMultiSelect(false);
        super.addListener((ItemClickListener) this);
        super.addActionHandler(this);
        super.setWriteThrough(true);
        //BeanItemContainer bic = new BeanItemContainer(InvoiceItemBean.class);
        //super.setContainerDataSource(new BeanItemContainer<InvoiceItemBean>(items));
        
        this.items.add(new InvoiceItemBean(true));
		this.loadData(this.items);
	}
	
	/**
	 * Loads data into the table
	 * @param items
	 */
	private void loadData(List<InvoiceItemBean> items) {
		super.setContainerDataSource(new BeanItemContainer(InvoiceItemBean.class, items));
		
		super.setVisibleColumns(InvoiceItemTable.NATURAL_COL_ORDER);
		super.setColumnHeaders(InvoiceItemTable.COL_HEADERS);
		super.setTableFieldFactory(this);
		super.setPageLength(InvoiceItemTable.TABLE_PAGE_LENGTH);
	}
	
	/**
	 * @return the insert bean
	 */
	private InvoiceItemBean getInsertBean() {
		InvoiceItemBean retval = null;
		for (InvoiceItemBean bean : this.items) {
			if (bean.isInsertRec()) {
				retval = bean;
				break;
			}
		}
		return retval;
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		if ("Add".equals(event.getButton().getCaption())) {
			try {
				InvoiceItemBean insertBean = this.getInsertBean();
				insertBean.validate();
				insertBean.setInsertRec(false);
				this.items.add(new InvoiceItemBean(true));
				this.loadData(this.items);
			} catch (Exception e) {}
		}
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.TableFieldFactory#createField(com.vaadin.data.Container, java.lang.Object, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		Field fld = null;
		InvoiceItemBean item = (InvoiceItemBean)itemId;
        if ("itemCode".equals(propertyId)) {
            fld = new TextField();
            ((TextField)fld).setMaxLength(5);
            ((TextField)fld).setWidth("50px");
            fld.setReadOnly(false);
        } else if ("itemDescription".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(30);
        	((TextField)fld).setWidth("500px");
        	fld.setReadOnly(false);
        } else if ("itemQuantity".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(3);
        	((TextField)fld).setWidth("50px");
        	fld.setReadOnly(false);
        } else if ("itemUnitPrice".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(5);
        	((TextField)fld).setWidth("50px");
        	fld.setReadOnly(false);
        } 
        return fld;
	}

	/* (non-Javadoc)
	 * @see com.vaadin.event.ItemClickEvent.ItemClickListener#itemClick(com.vaadin.event.ItemClickEvent)
	 */
	public void itemClick(ItemClickEvent event) {
		//NOP
	}

	/* (non-Javadoc)
	 * @see com.vaadin.event.Action.Handler#getActions(java.lang.Object, java.lang.Object)
	 */
	public Action[] getActions(Object target, Object sender) {
		InvoiceItemBean bean = (InvoiceItemBean)target;
		if (bean == null || bean.isInsertRec()) return new Action[]{}; //only data records/beans can be deleted
		else {
			return new Action[] { new Action("Remove")};
		}
	}

	/* (non-Javadoc)
	 * @see com.vaadin.event.Action.Handler#handleAction(com.vaadin.event.Action, java.lang.Object, java.lang.Object)
	 */
	public void handleAction(Action action, Object sender, Object target) {
		InvoiceItemBean bean = (InvoiceItemBean)target;
		this.items.remove(bean);
		try {
			this.loadData(this.items);
		} catch (Exception e) {}
		
	}
	
	/**
	 * Returns the list of invoice items
	 * @return the list of invoice items
	 */
	public List<InvoiceItemBean> getItems() {
		List<InvoiceItemBean> retval = new ArrayList<InvoiceItemBean>();
		for (InvoiceItemBean item : this.items) {
			if (!item.isInsertRec())
				retval.add(item);
		}
		return retval;
	}
}
