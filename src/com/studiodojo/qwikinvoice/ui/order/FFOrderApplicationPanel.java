package com.studiodojo.qwikinvoice.ui.order;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.media.MediaByteArraySource;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.ui.IApplicationPanel;
import com.studiodojo.qwikinvoice.utils.gdata.TokenBean;
import com.studiodojo.qwikinvoice.utils.gdata.TokenStore;
import com.studiodojo.qwikinvoice.utils.pdf.v2011.FFOrderPdfWriter;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * The <code>FFOrderApplicationPanel</code> class encloses the <i>Furi Fineart Books</i> order application panel.
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class FFOrderApplicationPanel extends Panel implements IApplicationPanel {

	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	private OrderForm oForm;
	private final static String FORM_HEADER = "<h1>Edit Order Details</h1><p/>Fields marked with a (<font color=\"red\">*</font>) are required.";
	private final static String ALBUM_TABLE_HEADER = "<h1>Add/Update Albums Order</h1><p/>Specify the quantity for a particular album size.";
	private final static String BOX_TABLE_HEADER = "<h1>Add/Update Boxes Order</h1><p/>Specify the quantity for a particular box size.";
	
	private static final Logger Log = Logger.getLogger(FFOrderApplicationPanel.class.getName());
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.ui.IApplicationPanel#init(com.studiodojo.qwikinvoice.data.SessionBean, com.studiodojo.qwikinvoice.QwikInvoiceApplication)
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp)
			throws Exception {
		if (this.theSession != null || this.theApp != null) return;
		this.theSession = theSession;
		this.theApp = theApp;
		VerticalLayout theLayout = (VerticalLayout)super.getContent();
		theLayout.setHeight("100%");
		theLayout.setWidth("100%");
		theLayout.setMargin(false);
		/*
		OrderPanel oPanel = new OrderPanel();
		oPanel.init(this.theSession, this.theApp);
		*/
		theLayout.addComponent(new Label(FORM_HEADER, Label.CONTENT_XHTML));
		
		this.oForm = new OrderForm();
		oForm.init(theSession, theApp);
		theLayout.addComponent(oForm);
		
		//theLayout.addComponent(oPanel);
		//theLayout.setComponentAlignment(oPanel, Alignment.TOP_CENTER);
		//
		theLayout.addComponent(new Label(ALBUM_TABLE_HEADER, Label.CONTENT_XHTML));
		AlbumOrderTable aoTable = new AlbumOrderTable();
		aoTable.init(this.theSession, this.theApp);
		theLayout.addComponent(aoTable);
		//
		theLayout.addComponent(new Label(BOX_TABLE_HEADER, Label.CONTENT_XHTML));
		BoxOrderTable boTable = new BoxOrderTable();
		boTable.init(this.theSession, this.theApp);
		theLayout.addComponent(boTable);
		//
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
		try {
			Key userKey = this.theApp.getUserKey();
			DocsService client = new DocsService(QwikInvoiceApplication._GOOGLE_APP_NAME_);
			client.setProtocolVersion(DocsService.Versions.V3);
			//
			// SET OAUTH CREDENTIALS
			//
			TokenBean userToken = TokenStore.getToken(userKey);
			GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
			oauthParameters.setOAuthConsumerKey(QwikInvoiceApplication.CLIENT_ID);
			oauthParameters.setOAuthConsumerSecret(QwikInvoiceApplication.CLIENT_SECRET);
			oauthParameters.setOAuthToken(userToken.getOauthToken());
			oauthParameters.setOAuthTokenSecret(userToken.getOauthTokenSecret());
			client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
			//
			// COMMIT FORM DATA
			//
			this.oForm.commit();
			// Process Form data
			//this.theSession.getInvoiceBean().validate();
			FFOrderPdfWriter pdf = new FFOrderPdfWriter();
			pdf.init();
			byte[] mediaBytes = pdf.pdfToArray(this.theSession);
			//
			//
			DocumentListEntry newDocument = new DocumentEntry();
			newDocument.setTitle(new PlainTextConstruct("QwikInvoice-ORDER.pdf"));
			newDocument.setMediaSource(new MediaByteArraySource(mediaBytes, DocumentListEntry.MediaType.PDF.getMimeType()));
			String folderURL = QwikInvoiceApplication._GOOGLE_DOC_FEED_URL_;
			DocumentListEntry entry = client.insert(new URL(folderURL), newDocument);
			//Log.finest("Save As... {dir="+folderItem.getFolderName()+",resourceId="+folderItem.getResourceId()+",filename="+folderItem.getFilename()+'}');
			this.theApp.getMainWindow().showNotification("Order Saved", "Success", Notification.TYPE_HUMANIZED_MESSAGE);
		} catch (Exception e) {
			Log.log(Level.SEVERE, "Error Saving Order", e);
			this.theApp.getMainWindow().showNotification("Error Saving Order", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}

	}

}
