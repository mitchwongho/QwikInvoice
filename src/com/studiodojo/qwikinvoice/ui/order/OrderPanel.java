/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.order;

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
public class OrderPanel extends Panel  implements IApplicationPanel {
	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	private final static String HEADER = "<h1>Edit Order Details</h1><p/>Fields marked with a (<font color=\"red\">*</font>) are required.<p/>";
	/**
	 * @param theSession
	 * @param theApp
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		this.theApp = theApp;
		this.theSession = theSession;
		
		
		VerticalLayout theLayout = new VerticalLayout();
		theLayout.setMargin(false);
		theLayout.setHeight("100%");
		theLayout.setWidth("100%");
		
		super.setContent(theLayout);	
		
		Label header = new Label(HEADER, Label.CONTENT_XHTML);
		theLayout.addComponent(header);
		
		OrderForm oForm = new OrderForm();
		oForm.init(theSession, theApp);
		theLayout.addComponent(oForm);
		theLayout.setComponentAlignment(oForm, Alignment.TOP_LEFT);
	}
	/* (non-Javadoc)
	 * @see com.studiodojo.furifineartform.ui.IApplicationPanel#validate()
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
