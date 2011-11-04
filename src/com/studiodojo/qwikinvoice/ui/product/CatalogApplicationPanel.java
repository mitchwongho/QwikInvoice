/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.product;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductCatalogBean;
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
public class CatalogApplicationPanel extends Panel implements IApplicationPanel, Button.ClickListener {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private CatalogSummaryTable summaryTable;
	private final static String TABLE_HEADER = "<h1>Catalogs</h1>Right-click and select an action";
	private static final Logger Log = Logger.getLogger(CatalogApplicationPanel.class.getName());
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
		//
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSizeFull();
		hLayout.setHeight("80px");
		Label desc = new Label(CatalogApplicationPanel.TABLE_HEADER, Label.CONTENT_XHTML);
		hLayout.addComponent(desc);
		hLayout.setComponentAlignment(desc, Alignment.BOTTOM_LEFT);
		Button addButton = new Button("Add", new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				try {
					CatalogSettingsWindow window = new CatalogSettingsWindow();
					window.setCaption("New Catalog");
					window.init(CatalogApplicationPanel.this.theSession, CatalogApplicationPanel.this.theApp, new ProductCatalogBean());
					window.addListener((Button.ClickListener) CatalogApplicationPanel.this.summaryTable);
					CatalogApplicationPanel.this.getWindow().addWindow(window);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error loading Product Settings window", e);
				}
			}
		});
		hLayout.addComponent(addButton);
		hLayout.setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
		theLayout.addComponent(hLayout);
		theLayout.setComponentAlignment(hLayout, Alignment.TOP_LEFT);
		this.summaryTable = CatalogSummaryTable.getInstance();
		this.summaryTable.init(this.theSession, this.theApp);
		this.loadTable();
		theLayout.addComponent(this.summaryTable);
		theLayout.setComponentAlignment(this.summaryTable, Alignment.TOP_LEFT);
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
	private void loadTable() throws Exception {
		List<ProductCatalogBean> catalogs = ProductsDAO.loadProductCatalogsByAdminUser(this.theSession.getLogin());
		CatalogSummaryTable.getInstance().loadData(catalogs);
		this.summaryTable.requestRepaint();
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		try {
			event.getButton().removeListener((Button.ClickListener)this);
			List<ProductCatalogBean> catalogs = ProductsDAO.loadProductCatalogsByAdminUser(this.theSession.getLogin());
			CatalogSummaryTable.getInstance().loadData(catalogs);
		} catch (Exception e) {
			
		}
		
	}
}
