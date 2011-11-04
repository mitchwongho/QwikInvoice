package com.studiodojo.qwikinvoice.ui.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductBean;
import com.studiodojo.qwikinvoice.data.ProductCatalogBean;
import com.studiodojo.qwikinvoice.data.ProductCatalogMappedBean;
import com.studiodojo.qwikinvoice.data.ProductsDAO;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class CatalogSettingsWindow extends Window {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private ProductCatalogBean theCatalog;
	private List<ProductBean> products;
	private Button saveButton;
	//private TextField tfName, tfCode;
	private static final Logger Log = Logger.getLogger(CatalogSettingsWindow.class.getName());
	/**
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp, ProductCatalogBean catalog) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		if (catalog == null) throw new Exception("Product must be specified");
		this.theCatalog = catalog;
		//@TODO is <code>catalog</code> is new ProductCatalogBean instance or one selected for updating
		this.products = (this.theCatalog.getId() == null) ? ProductsDAO.loadProductByAdminUser(this.theSession.getLogin()) : ProductsDAO.loadProductsByCatalog(catalog);
		this.theCatalog.setAdminUserId(this.theSession.getLogin());
		super.setModal(true);
		super.setWidth("640px");
		super.setHeight("820px");
		super.setResizable(false);
		VerticalLayout theLayout = (VerticalLayout)super.getContent();
		//Title
		theLayout.addComponent(new Label("<H1>"+super.getCaption()+"</H1>", Label.CONTENT_XHTML));
		theLayout.setSpacing(true);
		//
		{
			GridLayout formLayout = new GridLayout(2,1);
			formLayout.setSpacing(true);
			theLayout.addComponent(formLayout);
			// Product name
			formLayout.addComponent(new Label("Catalog Name"));
			TextField tfName = new TextField();
			tfName.setInputPrompt("e.g. Wedding Photography Package 2011/12");
			tfName.setMaxLength(64);
			tfName.setRequired(true);
			tfName.setWidth("300px");
			tfName.setImmediate(false);
			tfName.setPropertyDataSource(new Property() {
				
				public void setValue(Object newValue) throws ReadOnlyException,
						ConversionException {
					CatalogSettingsWindow.this.theCatalog.setName((String)newValue);
				}
				
				public void setReadOnly(boolean newStatus) {
					// TODO Auto-generated method stub
				}
				
				public boolean isReadOnly() {
					return false;
				}
				
				public Object getValue() {
					return this.toString();
				}
				
				public Class<?> getType() {
					return java.lang.String.class;
				}

				/* (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString() {
					return CatalogSettingsWindow.this.theCatalog.getName();
				}
				
			});
			tfName.setNullRepresentation("");
			formLayout.addComponent(tfName);
		};
		// Products table
		Label rateLabel = new Label("<H2>Products</H2>Select one or more products to add to this catalog", Label.CONTENT_XHTML);
		theLayout.addComponent(rateLabel);
		//// The table
		ProductSummaryTable productTable = ProductSummaryTable.getInstance();
		productTable.makeSelectable(true);
		productTable.loadData(this.products);
		productTable.setHeight("400px");
		theLayout.addComponent(productTable);
		//
		// SAVE Button
		//
		this.saveButton = new Button("Save");
		theLayout.addComponent(saveButton);
		theLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);
		saveButton.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				try {
					ProductsDAO.insertorUpdateProductCatalog(CatalogSettingsWindow.this.theCatalog);
					ProductCatalogMappedBean bean = new ProductCatalogMappedBean();
					Set<ProductBean> setSelectedProducts = ProductSummaryTable.getInstance().getSelectedProducts();
					bean.setCatalog(new Key<ProductCatalogBean>(ProductCatalogBean.class, CatalogSettingsWindow.this.theCatalog.getId()));
					bean.setProducts(new ArrayList<ProductBean>(setSelectedProducts));
					ProductsDAO.insertUpdateProductCatalogMapping(bean);
				} catch (Exception e) {
					CatalogSettingsWindow.Log.log(Level.WARNING, "Error inserting product", e);
				} finally {
					CatalogSettingsWindow.this.theApp.getMainWindow().removeWindow(CatalogSettingsWindow.this);
				}
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.Panel#addListener(com.vaadin.event.MouseEvents.ClickListener)
	 */
	public void addListener(Button.ClickListener listener) {
		this.saveButton.addListener((Button.ClickListener)listener);
	}
	
	/* (non-Javadoc)
	 * @see com.vaadin.ui.Panel#removeListener(com.vaadin.event.MouseEvents.ClickListener)
	 */
	public void removeListener(Button.ClickListener listener) {
		this.saveButton.removeListener((Button.ClickListener)listener);
	}
}
