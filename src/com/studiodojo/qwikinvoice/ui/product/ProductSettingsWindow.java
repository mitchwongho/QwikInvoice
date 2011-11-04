package com.studiodojo.qwikinvoice.ui.product;

import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductBean;
import com.studiodojo.qwikinvoice.data.ProductUnitRateBean;
import com.studiodojo.qwikinvoice.data.ProductsDAO;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Property;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class ProductSettingsWindow extends Window {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private ProductBean theProduct;
	private List<ProductUnitRateBean> rates;
	private Button saveButton;
	//private TextField tfName, tfCode;
	private static final Logger Log = Logger.getLogger(ProductSettingsWindow.class.getName());
	/**
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp, ProductBean product) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		if (product == null) throw new Exception("Product must be specified");
		this.theProduct = product;
		this.rates = (product.getId() == null) ? new Vector<ProductUnitRateBean>() : ProductsDAO.loadProductRatesByProduct(product);
		this.theProduct.setAdminUserId(this.theSession.getLogin());
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
			formLayout.addComponent(new Label("Product Name"));
			TextField tfName = new TextField();
			tfName.setInputPrompt("e.g. Wedding Photography Package 2011/12");
			tfName.setMaxLength(64);
			tfName.setRequired(true);
			tfName.setWidth("300px");
			tfName.setImmediate(false);
			tfName.setPropertyDataSource(new Property() {
				
				public void setValue(Object newValue) throws ReadOnlyException,
						ConversionException {
					ProductSettingsWindow.this.theProduct.setProductName((String)newValue);
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
					return ProductSettingsWindow.this.theProduct.getProductName();
				}
				
			});
			tfName.setNullRepresentation("");
			formLayout.addComponent(tfName);
			// Product Code
			formLayout.addComponent(new Label("Product Code"));
			TextField tfCode = new TextField();
			tfCode.setInputPrompt("e.g. ABC123");
			tfCode.setMaxLength(6);
			tfCode.setRequired(false);
			tfCode.setWidth("100px");
			tfCode.setImmediate(false);
			tfCode.setWriteThrough(true);
			tfCode.setPropertyDataSource(new Property() {
				
				public void setValue(Object newValue) throws ReadOnlyException,
						ConversionException {
					ProductSettingsWindow.this.theProduct.setProductCode((String)newValue);
				}
				
				public void setReadOnly(boolean newStatus) {
					
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
					return ProductSettingsWindow.this.theProduct.getProductCode();
				}
				
			});
			formLayout.addComponent(tfCode);
			// Rate Type
			formLayout.addComponent(new Label("Rate Type"));
			Select selector = new Select();
			selector.addItem(ProductBean.RateType.RANGE);
			selector.addItem(ProductBean.RateType.OPTION);
			selector.setPropertyDataSource(new Property() {
				
				public void setValue(Object newValue) throws ReadOnlyException,
						ConversionException {
					ProductSettingsWindow.this.theProduct.setRateType(((ProductBean.RateType)newValue).ordinal());
				}
				
				public void setReadOnly(boolean newStatus) {
					
				}
				
				public boolean isReadOnly() {
					return false;
				}
				
				public Object getValue() {
					return ProductBean.RateType.values()[ProductSettingsWindow.this.theProduct.getRateType()];
				}
				
				public Class<?> getType() {
					return ProductBean.RateType.class;
				}

				/* (non-Javadoc)
				 * @see java.lang.Object#toString()
				 */
				@Override
				public String toString() {
					return ProductBean.RateType.values()[ProductSettingsWindow.this.theProduct.getRateType()].name();
				}
				
			});
			selector.setNullSelectionAllowed(false);
			selector.setMultiSelect(false);
			selector.setNewItemsAllowed(false);
			selector.setRequired(true);
			formLayout.addComponent(selector);
		};
		
		//
		// RATE Table
		//
		ProductUnitRateTable table = new ProductUnitRateTable();
		Label rateLabel = new Label("<H2>Product Rates</H2>Add unit rates for this product.", Label.CONTENT_XHTML);
		HorizontalLayout h_layout = new HorizontalLayout();
		theLayout.addComponent(h_layout);
		h_layout.setSizeFull();
		h_layout.addComponent(rateLabel);
		h_layout.setComponentAlignment(rateLabel, Alignment.BOTTOM_LEFT);
		Button addRateButton = new Button("Add Rate", (Button.ClickListener)table);
		h_layout.addComponent(addRateButton);
		h_layout.setComponentAlignment(addRateButton, Alignment.BOTTOM_RIGHT);
		
		table.init(this.theSession, this.theApp);
		// table.loadDate()
		table.loadData(this.rates);
		theLayout.addComponent(table);
		//
		// SAVE Button
		//
		this.saveButton = new Button("Save");
		theLayout.addComponent(saveButton);
		theLayout.setComponentAlignment(saveButton, Alignment.MIDDLE_CENTER);
		saveButton.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				try {
					// remove insert record/bean
					for (ProductUnitRateBean rate : ProductSettingsWindow.this.rates) {
						if (rate.isInsertBean()) {
							rates.remove(rate);
							break;
						}
					}
					ProductsDAO.insertProduct(ProductSettingsWindow.this.theProduct);
					ProductsDAO.insertProductRates(ProductSettingsWindow.this.rates, ProductSettingsWindow.this.theProduct);
				} catch (Exception e) {
					ProductSettingsWindow.Log.log(Level.WARNING, "Error inserting product", e);
				} finally {
					ProductSettingsWindow.this.theApp.getMainWindow().removeWindow(ProductSettingsWindow.this);
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
