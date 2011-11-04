/**
 * 
 */
package com.studiodojo.qwikinvoice.ui;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.SessionBean;

/**
 * The <code>IApplicationPanel</code> interface defines the application panel component
 * @author mwho
 *
 */
public interface IApplicationPanel {
	/** Defines the panels height*/
	final static public String PANEL_HEIGHT = "1000px";
	/**
	 * Initialises the Panel.
	 * @param theApp a reference to the application.
	 * @param theSession session data
	 * @throws Exception upon any error.
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception;
	/**
	 * Invoked by the state-machine before moving off the panal.
	 * @throws Exception upon any error.
	 */
	public void validate() throws Exception;
	
	/**
	 * Invoked by the application when the 'Save' option is invoked
	 * @throws Exception upon any error
	 */
	public void onSave() throws Exception;
}
