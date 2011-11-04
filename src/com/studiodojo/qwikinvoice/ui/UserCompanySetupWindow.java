/**
 * 
 */
package com.studiodojo.qwikinvoice.ui;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.UserCompanyBean;
import com.studiodojo.qwikinvoice.data.UserDAO;
import com.studiodojo.qwikinvoice.ui.user.UserCompanySetupForm;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class UserCompanySetupWindow extends Window implements ClickListener {

	public final static String HEADING = "<h1>Company Details</h1><p/>Enter your company's details below. Click the 'Save' button to save the information or close the window to exit. Fields marked with (<font color='red'>*</font>) are mandatory";
	private UserCompanySetupForm theForm;
	private QwikInvoiceApplication theApp;
	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @param theApp
	 * @throws Exception
	 */
	public void init(QwikInvoiceApplication theApp) throws Exception {
		super.setModal(true);
		super.setCaption("Settings");
		super.setWidth("640px");
		super.setHeight("660px");
		super.setResizable(false);
		VerticalLayout theLayout = (VerticalLayout)super.getContent();
		theLayout.addComponent(new Label(UserCompanySetupWindow.HEADING, Label.CONTENT_XHTML));
		// INPUT FORM
		UserService us = UserServiceFactory.getUserService();
		UserCompanyBean ucBean = UserDAO.getUserCompanyBean(us.getCurrentUser().getEmail());
		this.theForm = UserCompanySetupForm.getInstance();
		theForm.setUserCompanyBean(ucBean);
		theLayout.addComponent(theForm);
		// SAVE BUTTON
		theForm.setWriteThrough(false);
		Button saveButton = new Button("Save", theForm, "commit"); //this will cause the form to commit the values to the entity bean
		saveButton.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				try {
					UserService us = UserServiceFactory.getUserService();
					UserDAO.addUpdateUserCompany(us.getCurrentUser().getEmail(), UserCompanySetupWindow.this.theForm.getUserCompanyBean());
					UserCompanySetupWindow.super.getParent().removeWindow(UserCompanySetupWindow.this); //close window when done
				} catch (Exception e) {
					UserCompanySetupWindow.super.getParent().showNotification("Error Saving", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});
		theLayout.addComponent(saveButton);
		theLayout.setComponentAlignment(saveButton, Alignment.TOP_CENTER);
	}
}
