/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.product;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.ProductCategoryBean;
import com.studiodojo.qwikinvoice.data.ProductsDAO;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class ProductCategorySettingsWindow extends Window {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private List<ProductCategoryBean> productCategories;
	private ComboBox categoryCombo;
	private static final Logger Log = Logger.getLogger(ProductCategorySettingsWindow.class.getName());
	private ProductCategoryBean selectedBean;
	/**
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		super.setModal(true);
		super.setWidth("640px");
		super.setHeight("640px");
		super.setResizable(false);
		VerticalLayout theLayout = (VerticalLayout)super.getContent();
		//Title
		theLayout.addComponent(new Label("<H1>"+super.getCaption()+"</H1>Enter a new catagory name or select one from the drop-down and click <b>Delete</b> to delete the catagory", Label.CONTENT_XHTML));
		theLayout.setSpacing(true);
		{
			GridLayout formLayout = new GridLayout(3,1);
			formLayout.setSpacing(true);
			theLayout.addComponent(formLayout);
			//
			formLayout.addComponent(new Label("Product Category"));
			//
			this.categoryCombo = new ComboBox();
			this.categoryCombo.setNullSelectionAllowed(false);
			this.categoryCombo.setNewItemsAllowed(true);
			this.categoryCombo.setWidth("270px");
			this.loadDataSource();
			this.categoryCombo.setNewItemHandler(new ComboBox.NewItemHandler() {
				
				public void addNewItem(String newItemCaption) {
					boolean isNew = true;
					try {
						for (ProductCategoryBean bean : ProductCategorySettingsWindow.this.productCategories) {
							if (bean.getName().equals(newItemCaption.trim())) isNew = false;
						}
						if (isNew)
							ProductCategorySettingsWindow.this.addNewCatalog(newItemCaption.trim());
					} catch (Exception e) {
						//TODO
						Log.log(Level.SEVERE, "Error Adding New Catagory", e);
					}
				}
			});
			categoryCombo.addListener(new Property.ValueChangeListener() {
				
				public void valueChange(ValueChangeEvent event) {
					ProductCategorySettingsWindow.this.selectedBean = (ProductCategoryBean)event.getProperty().getValue();
				}
			});
			formLayout.addComponent(this.categoryCombo);
			//Save button
			Button deleteButton = new Button("Delete", new Button.ClickListener() {
				
				public void buttonClick(ClickEvent event) {
					try {
						if (ProductCategorySettingsWindow.this.selectedBean != null) {
							ProductsDAO.deleteProductCatagory(ProductCategorySettingsWindow.this.selectedBean);
							ProductCategorySettingsWindow.this.productCategories.remove(ProductCategorySettingsWindow.this.selectedBean);
							ProductCategorySettingsWindow.this.showNotification("Deleted '"+ProductCategorySettingsWindow.this.selectedBean.getName()+"'");
							ProductCategorySettingsWindow.this.selectedBean = null;
							ProductCategorySettingsWindow.this.loadDataSource();
						}
						//ProductCategorySettingsWindow.this.categoryCombo.get
					} catch (Exception e) {
						Log.log(Level.SEVERE, "Error Deleting Catagory", e);
					}
				}
			});
			deleteButton.setDescription("Delete the selected catgory");
			formLayout.addComponent(deleteButton);
			
		}
	}
	
	/**
	 * @throws Exception
	 */
	public void loadDataSource() throws Exception {
		if (this.productCategories == null) this.productCategories = ProductsDAO.loadProductCategoryByAdminUser(this.theSession.getLogin());
		this.categoryCombo.setContainerDataSource(new BeanItemContainer<ProductCategoryBean>(ProductCategoryBean.class, productCategories));
		this.categoryCombo.setItemCaptionPropertyId("name");
		this.categoryCombo.setInputPrompt("Enter or select a product category");
		this.categoryCombo.setImmediate(true);
	}
	
	/**
	 * @param newCatalog
	 * @throws Exception
	 */
	private void addNewCatalog(String newCatalog) throws Exception {
		ProductCategoryBean bean = new ProductCategoryBean();
		bean.setName(newCatalog);
		bean.setAdminUserId(ProductCategorySettingsWindow.this.theSession.getLogin());
		bean = ProductsDAO.insertProductCategory(bean);
		ProductCategorySettingsWindow.this.productCategories.add(bean);
		ProductCategorySettingsWindow.this.categoryCombo.addItem(bean);
		this.selectedBean = bean;
	}
}
