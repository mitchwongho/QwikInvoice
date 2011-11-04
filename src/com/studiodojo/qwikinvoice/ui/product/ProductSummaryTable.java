/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.product;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductBean;
import com.studiodojo.qwikinvoice.data.ProductsDAO;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class ProductSummaryTable extends Table implements TableFieldFactory, ItemClickListener, Handler, ClickListener {
	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	private Set<ProductBean> selectedProducts;
	private static ProductSummaryTable theInstance;
	private static final Object[] NATURAL_COL_ORDER = new Object[] { "productName", "productCode", "rateType"};
    private final static String[] COL_HEADERS = new String[] { "Product Name", "Product Code", "Rate Type"};
    private final static int TABLE_PAGE_LENGTH = 20;
    private static final Logger Log = Logger.getLogger(ProductSummaryTable.class.getName());
	
    static {
    	ProductSummaryTable.theInstance = new ProductSummaryTable();
    }
    
    /**
     * private constructor
     */
    private ProductSummaryTable() {
    	super();
    	super.setWidth("100%");
    	super.setHeight("720px");
    	super.setWriteThrough(false); //update value after a commit
    	super.setColumnReorderingAllowed(false); //users can't move the columns
    	super.setColumnCollapsingAllowed(true);
    	super.setColumnCollapsingAllowed(false);
    	super.setEditable(true);
    	super.setSelectable(false);
    	super.setMultiSelect(false);
    	super.addListener((ItemClickListener) this);
    	super.addActionHandler(this);
    }
    
    /**
     * @return a singleton instance of the class
     */
    public static ProductSummaryTable getInstance() {
    	return ProductSummaryTable.theInstance;
    }
	/**
	 * @param theSession
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
	}
	
	/**
	 * Loads the table data
	 * @param products the table data
	 * @throws Exception upon any error
	 */
	public void loadData(List<ProductBean> products) throws Exception {
		//here
		super.setContainerDataSource(new BeanItemContainer<ProductBean>(ProductBean.class, products));
		
		super.setVisibleColumns(ProductSummaryTable.NATURAL_COL_ORDER);
		super.setColumnHeaders(ProductSummaryTable.COL_HEADERS);
		super.setTableFieldFactory(this);
		super.setPageLength(ProductSummaryTable.TABLE_PAGE_LENGTH);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.TableFieldFactory#createField(com.vaadin.data.Container, java.lang.Object, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		Field fld = null;
        if ("productName".equals(propertyId)) {
            fld = new TextField();
            ((TextField)fld).setMaxLength(4);
            ((TextField)fld).setWidth("250px");
            fld.setReadOnly(true);
        } else if ("productCode".equals(propertyId)) {
        	fld = new TextField();
        	((TextField)fld).setMaxLength(5);
        	((TextField)fld).setWidth("100px");
        	fld.setReadOnly(true);
        } else if ("rateType".equals(propertyId)) {
        	Select select = new Select();
            fld = ProductBean.addSelectItems((String)propertyId, select, new Integer(((ProductBean)itemId).getRateType()));
            fld.setReadOnly(true);
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
		if (super.isMultiSelect())
			return new Action[] { new Action("Select") };
		else
			return new Action[] { new Action("Edit"), new Action("Remove")};
	}

	/* (non-Javadoc)
	 * @see com.vaadin.event.Action.Handler#handleAction(com.vaadin.event.Action, java.lang.Object, java.lang.Object)
	 */
	public void handleAction(Action action, Object sender, Object target) {
		ProductBean product = (ProductBean)target;
		try {
			if ("Edit".equals(action.getCaption())) {
				ProductSettingsWindow window = new ProductSettingsWindow();
				window.setCaption("Edit Product");
				window.init(this.theSession, this.theApp, product);
				window.addListener((Button.ClickListener)this);
				this.theApp.getMainWindow().addWindow(window);
			} else if ("Remove".equals(action.getCaption())) {
				ProductsDAO.removeProduct(product, true);
				List<ProductBean> products = ProductsDAO.loadProductByAdminUser(this.theSession.getLogin());
				this.loadData(products);
			} else if ("Select".equals(action.getCaption())) {
				
			}
		} catch (Exception e) {
			Log.log(Level.SEVERE, "Error processing record", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		try {
			List<ProductBean> products = ProductsDAO.loadProductByAdminUser(this.theSession.getLogin());
			this.loadData(products);
		} catch (Exception e) {
			Log.log(Level.SEVERE, "Error updating products", e);
		} finally {
			((ProductSettingsWindow)event.getButton().getWindow()).removeListener((Button.ClickListener)this);
		}
	}
	
	/**
	 * @param isSelectable <code>true</code> to enable multiselect on table
	 */
	public void makeSelectable(boolean isSelectable) {
		super.setSelectable(isSelectable);
		super.setMultiSelect(isSelectable);
		super.removeActionHandler(this);
		if (isSelectable) {
			super.addActionHandler(this);
			super.addListener(new Table.ValueChangeListener() {
				
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					// Save the selected list
					ProductSummaryTable.this.selectedProducts = (Set<ProductBean>)event.getProperty().getValue();
					
				}
			});
		} else
			super.removeActionHandler(this);
	}
	
	/**
	 * @return a list of selected <code>ProductBean</code>
	 */
	public Set<ProductBean> getSelectedProducts() {
		return this.selectedProducts;
	}
}
