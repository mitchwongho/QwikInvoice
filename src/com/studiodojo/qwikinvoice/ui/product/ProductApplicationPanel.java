/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.product;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductBean;
import com.studiodojo.qwikinvoice.data.ProductsDAO;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.ui.IApplicationPanel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class ProductApplicationPanel extends Panel implements IApplicationPanel, Button.ClickListener {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private ProductSummaryTable productsTable;
	private final static String TABLE_HEADER = "<h1>Products</h1>Right-click and select an action";
	private static final Logger Log = Logger.getLogger(ProductApplicationPanel.class.getName());
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.ui.IApplicationPanel#init(com.studiodojo.qwikinvoice.data.SessionBean, com.studiodojo.qwikinvoice.QwikInvoiceApplication)
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp)
			throws Exception {
		this.theSession = theSession;
		this.theApp = theApp;
		VerticalLayout theLayout = (VerticalLayout)super.getContent();
		theLayout.removeAllComponents();
		theLayout.setHeight("800px");
		theLayout.setWidth("100%");
		theLayout.setMargin(false);
		// Header + 'Add' button
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSizeFull();
		hLayout.setHeight("80px");
		Label desc = new Label(ProductApplicationPanel.TABLE_HEADER, Label.CONTENT_XHTML);
		hLayout.addComponent(desc);
		hLayout.setComponentAlignment(desc, Alignment.BOTTOM_LEFT);
		Button addButton = new Button("Add", new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				try {
					ProductSettingsWindow window = new ProductSettingsWindow();
					window.setCaption("New Product");
					window.init(ProductApplicationPanel.this.theSession, ProductApplicationPanel.this.theApp, new ProductBean());
					window.addListener((Button.ClickListener) ProductApplicationPanel.this.productsTable);
					ProductApplicationPanel.this.getWindow().addWindow(window);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error loading Product Settings window", e);
				}
			}
		});
		hLayout.addComponent(addButton);
		hLayout.setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
		theLayout.addComponent(hLayout);
		theLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);
		// Products Table
		this.productsTable = ProductSummaryTable.getInstance();
		this.productsTable.init(this.theSession, this.theApp);
		this.loadProducts();
		theLayout.addComponent(this.productsTable);
		theLayout.setComponentAlignment(this.productsTable, Alignment.TOP_LEFT);
	}

	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.ui.IApplicationPanel#validate()
	 */
	public void validate() throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.ui.IApplicationPanel#onSave()
	 */
	public void onSave() throws Exception {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @throws Exception
	 */
	private void loadProducts() throws Exception {
		List<ProductBean> products = ProductsDAO.loadProductByAdminUser(this.theSession.getLogin());
		ProductSummaryTable.getInstance().loadData(products);
		this.productsTable.requestRepaint();
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		try {
			event.getButton().removeListener((Button.ClickListener)this);
			List<ProductBean> products = ProductsDAO.loadProductByAdminUser(this.theSession.getLogin());
			this.productsTable.loadData(products);
		} catch (Exception e) {
			
		}
		
	}
}
