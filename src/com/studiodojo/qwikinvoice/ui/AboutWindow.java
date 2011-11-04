/**
 * 
 */
package com.studiodojo.qwikinvoice.ui;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class AboutWindow extends Window {

	private final static String ABOUT = "<h1>About</h1>QwikInvoice <b>version "+QwikInvoiceApplication._VERSION_+"</b>. This service is provided as is. E&O accepted. <p>Developed by <a href='mailto:public@studiodojo.com?subject=QwikInvoice' target='_blank'>StudioDojo</a>. Engineered by Vaadin. Powered by Google. Apache License 2.0" ;
	
	/**
	 * 
	 */
	public void init() {
		super.setCaption("About");
		super.setModal(true);
		super.setWidth("600px");
		super.setHeight("600px");
		VerticalLayout layout = (VerticalLayout)this.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponent(new com.vaadin.ui.Label(ABOUT, Label.CONTENT_XHTML));
	}
}
