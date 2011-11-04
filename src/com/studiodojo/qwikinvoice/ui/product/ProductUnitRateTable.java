/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.product;

import java.util.List;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductUnitRateBean;
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
import com.vaadin.ui.Window.Notification;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class ProductUnitRateTable extends Table implements Button.ClickListener, TableFieldFactory, ItemClickListener, Handler {

	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	private List<ProductUnitRateBean> rates;
	
	private static final Object[] NATURAL_COL_ORDER = new Object[] { "orderIndex", "costPerUnit", "pricePerUnit", "profit", "margin" };
    private final static String[] COL_HEADERS = new String[] { "Index", "Cost", "Price", "Profit/Loss", "Margin (%)"};
    private final static int TABLE_PAGE_LENGTH = 20;
	/**
	 * @param theSession
	 * @param theApp
	 * @throws Exception
	 */
	public void init (SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		super.setSizeFull();
        super.setWriteThrough(false); //update value after a commit
        super.setColumnReorderingAllowed(false); //users can't move the columns
        super.setColumnCollapsingAllowed(true);
        super.setColumnCollapsingAllowed(false);
        super.setEditable(true);
        super.setSelectable(true);
        super.setMultiSelect(false);
        super.addListener((ItemClickListener) this);
        super.addActionHandler(this);
        
	}
	
	/**
	 * Loads the data into the table (so you can re-use this component
	 * @throws Exception upon any error
	 */
	public void loadData() throws Exception {
		
		super.setContainerDataSource(new BeanItemContainer<ProductUnitRateBean>(ProductUnitRateBean.class, this.rates));
		
		super.setVisibleColumns(ProductUnitRateTable.NATURAL_COL_ORDER);
		super.setColumnHeaders(ProductUnitRateTable.COL_HEADERS);
		super.setTableFieldFactory(this);
		super.setPageLength(ProductUnitRateTable.TABLE_PAGE_LENGTH);
	}
	/**
	 * Loads the data into the table (so you can re-use this component
	 * @param rates a list of product rates
	 * @throws Exception upon any error
	 */
	public void loadData(List<ProductUnitRateBean> rates) throws Exception {
		this.rates = rates;
		ProductUnitRateBean insertBean = new ProductUnitRateBean();
		insertBean.setInsertBean(true);
		rates.add(insertBean);
		this.loadData();
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.event.Action.Handler#getActions(java.lang.Object, java.lang.Object)
	 */
	public Action[] getActions(Object target, Object sender) {
		ProductUnitRateBean bean = (ProductUnitRateBean)target;
		if (bean == null || bean.isInsertBean()) return new Action[]{};
		else {
			return new Action[] { new Action("Remove")}; //these are the actions available for context menu
		}
	}
	/* (non-Javadoc)
	 * @see com.vaadin.event.Action.Handler#handleAction(com.vaadin.event.Action, java.lang.Object, java.lang.Object)
	 */
	public void handleAction(Action action, Object sender, Object target) {
		ProductUnitRateBean bean = (ProductUnitRateBean)target;
		if (bean.isInsertBean()) return; //nop
		else this.rates.remove(bean);
		try {
			this.loadData(this.rates);
		} catch (Exception e) {}
		
	}
	/* (non-Javadoc)
	 * @see com.vaadin.event.ItemClickEvent.ItemClickListener#itemClick(com.vaadin.event.ItemClickEvent)
	 */
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.TableFieldFactory#createField(com.vaadin.data.Container, java.lang.Object, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		Field fld = null;
        if ("orderIndex".equals(propertyId)) {
            fld = new TextField();
            ((TextField)fld).setNullRepresentation("");
            ((TextField)fld).setMaxLength(3);
            ((TextField)fld).setWidth("50px");
            fld.setReadOnly(false);
        } else if ("costPerUnit".equals(propertyId)) {
        	fld = new TextField();
        	/*
        	((TextField)fld).setPropertyDataSource(new PropertyFormatter() {
				
				@Override
				public Object parse(String formattedValue) throws Exception {
					// TODO Auto-generated method stub
					return null;
				}
				
				@Override
				public String format(Object value) {
					return "nn.nn";
				}
			}));
			*/
        	((TextField)fld).setMaxLength(30);
        	((TextField)fld).setWidth("100px");
        	fld.setReadOnly(false);
        } else if ("pricePerUnit".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(5);
        	((TextField)fld).setWidth("50px");
        	fld.setReadOnly(false);
        } else if ("profit".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(5);
        	((TextField)fld).setWidth("50px");
        	fld.setReadOnly(true);
        } else if ("margin".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(5);
        	((TextField)fld).setWidth("50px");
        	fld.setReadOnly(true);
        } 
        return fld;
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		try {
			synchronized (this.rates) {
				ProductUnitRateBean last = this.rates.get(this.rates.size() - 1);
				last.validate();
				// validate wrt the collection
				for (ProductUnitRateBean aBean : this.rates) {
					if (!aBean.isInsertBean() && last.getOrderIndex().equals(aBean.getOrderIndex()))
						throw new Exception("Rate at index already exists");
				}
				last.setInsertBean(false);
				ProductUnitRateBean insertBean = new ProductUnitRateBean();
				insertBean.setInsertBean(true);
				this.rates.add(insertBean);
			}
			this.loadData();
		} catch (Exception e) {
			this.theApp.getMainWindow().showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
	}
}
