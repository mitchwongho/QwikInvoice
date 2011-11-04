/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.invoice;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.ui.IApplicationPanel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class InvoicePanel extends Panel implements IApplicationPanel {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private InvoiceForm iForm;
	
	private final static String HEADER = "<h1>Edit Invoice Details</h1><p/>Fields marked with (<font color='red'>*</font>) are mandatory<p/>";
	
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.ui.IApplicationPanel#init(com.studiodojo.qwikinvoice.QwikInvoiceApplication)
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		super.setHeight("990px");
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(false);
		layout.setWidth("100%");
		//layout.setHeight("100%");
		super.setScrollable(false);
		
		Label header = new Label(HEADER, Label.CONTENT_XHTML);
		layout.addComponent(header);
		layout.setComponentAlignment(header, Alignment.TOP_LEFT);
		
		try {
			if (iForm == null) {
				this.iForm = new InvoiceForm();
				layout.addComponent(iForm);
				layout.setComponentAlignment(iForm, Alignment.TOP_LEFT);
			}
			iForm.init(theSession, theApp);
		} catch (Exception e) {}
		
		layout.setExpandRatio(header, 1);
		layout.setExpandRatio(iForm, 9);
		super.addComponent(layout);
	}
	/**
	 * Commits all data
	 */
	public void commit() {
		this.iForm.commit();
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
}
