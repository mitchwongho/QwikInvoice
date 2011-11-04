/**
 * 
 */
package com.studiodojo.qwikinvoice.ui.invoice;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.ui.IApplicationPanel;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class InvoiceItemPanel extends Panel  implements IApplicationPanel {
	
	private SessionBean theSession;
	private QwikInvoiceApplication theApp;
	
	//private InvoiceItemForm iiForm;
	private InvoiceItemTable iiTable;
	
	private final static String HEADER = "<h1>Add/Update Invoice Items</h1><p/>To add an item, complete the last empty row and click the <b>Add</b> button. "+
	"To remove an invoice item, right-click on the applicable row to display the context menu and select <b>.<p/>";
	
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.ui.IApplicationPanel#init(com.studiodojo.qwikinvoice.data.SessionBean, com.studiodojo.qwikinvoice.QwikInvoiceApplication)
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp)
			throws Exception {
		
		if (this.theApp != null) return; // panel already initialised
		
		this.theApp = theApp;
		this.theSession = theSession;
		
		super.setSizeUndefined();
		super.setWidth("100%");
		super.setHeight("100%");
		super.setScrollable(true);
		
		VerticalLayout theLayout = new VerticalLayout();
		theLayout.setMargin(false);
		theLayout.setWidth("100%");
		theLayout.setHeight("100%");
		
		super.setContent(theLayout);
		
		this.iiTable = new InvoiceItemTable();
		this.iiTable.init(this.theSession, this.theApp);
		// HEADER (Label + AddButton)
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setHeight("115px");
		headerLayout.setWidth("100%");
		Label header = new Label(HEADER, Label.CONTENT_XHTML);
		Button addButton = new Button("Add", (Button.ClickListener)this.iiTable);
		headerLayout.addComponent(header);
		headerLayout.setComponentAlignment(header, Alignment.TOP_LEFT);
		headerLayout.addComponent(addButton);
		headerLayout.setComponentAlignment(addButton, Alignment.BOTTOM_RIGHT);
		headerLayout.setExpandRatio(header, 3);
		headerLayout.setExpandRatio(addButton, 1);
		
		theLayout.addComponent(headerLayout);
		theLayout.addComponent(this.iiTable);
		theLayout.setComponentAlignment(this.iiTable, Alignment.TOP_LEFT);
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
