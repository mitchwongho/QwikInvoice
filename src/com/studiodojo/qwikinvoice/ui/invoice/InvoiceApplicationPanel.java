package com.studiodojo.qwikinvoice.ui.invoice;

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
import com.studiodojo.qwikinvoice.utils.pdf.v2011.PdfWriter;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

/**
 * The <code>InvoiceApplicationPanel</code> class encloses the Invoice Application of QwikInvoice
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class InvoiceApplicationPanel extends Panel implements IApplicationPanel {
	private QwikInvoiceApplication theApp;
	private SessionBean theSession;
	/**maintains a reference to the Invoice Form*/
	private InvoiceForm iForm;
	private InvoiceItemTable iiTable;
	
	private final static String FORM_TITLE = "<h1>Edit Invoice Details</h1><p/>Fields marked with (<font color='red'>*</font>) are mandatory";
	private final static String TABLE_TITLE = "<h1>Add/Update Invoice Items</h1><p/>To add an item, complete the last empty row and click the <b>Add</b> button. "+
	"To remove an invoice item, right-click on the applicable row to display the context menu and select <b>.";
	
	private static final Logger Log = Logger.getLogger(InvoiceApplicationPanel.class.getName());
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
		theLayout.setWidth(QwikInvoiceApplication.APP_WIDTH);
		theLayout.setMargin(false);
		super.setScrollable(false);
		// The Form
		theLayout.addComponent(new Label(InvoiceApplicationPanel.FORM_TITLE, Label.CONTENT_XHTML));
		this.iForm = new InvoiceForm();
		this.iForm.init(this.theSession, this.theApp);
		theLayout.addComponent(this.iForm);
		// The Items table
		theLayout.addComponent(new Label(InvoiceApplicationPanel.TABLE_TITLE, Label.CONTENT_XHTML));
		
		Button addButton = new Button("Add");
		theLayout.addComponent(addButton);
		theLayout.setComponentAlignment(addButton, Alignment.TOP_RIGHT);
				
		this.iiTable = new InvoiceItemTable();
		this.iiTable.init(this.theSession, this.theApp);
		theLayout.addComponent(iiTable);
		theLayout.setComponentAlignment(iiTable, Alignment.TOP_CENTER);
		
		addButton.addListener((Button.ClickListener)iiTable);
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
			this.iForm.commit();
			// Process Form data
			this.theSession.getInvoiceBean().validate();
			//
			// Invoice Items
			//
			this.theSession.getItems().clear();
			this.theSession.getItems().addAll(this.iiTable.getItems());
			PdfWriter pdf = new PdfWriter();
			pdf.init();
			byte[] mediaBytes = pdf.pdfToArray(this.theSession);
			//
			//
			DocumentListEntry newDocument = new DocumentEntry();
			newDocument.setTitle(new PlainTextConstruct("QwikInvoice-INVOICE.pdf"));
			newDocument.setMediaSource(new MediaByteArraySource(mediaBytes, DocumentListEntry.MediaType.PDF.getMimeType()));
			String folderURL = QwikInvoiceApplication._GOOGLE_DOC_FEED_URL_;
			DocumentListEntry entry = client.insert(new URL(folderURL), newDocument);
			//Log.finest("Save As... {dir="+folderItem.getFolderName()+",resourceId="+folderItem.getResourceId()+",filename="+folderItem.getFilename()+'}');
			this.theApp.getMainWindow().showNotification("Invoice Saved", "Success", Notification.TYPE_HUMANIZED_MESSAGE);
		} catch (Exception e) {
			Log.log(Level.SEVERE, "Error Saving file", e);
			this.theApp.getMainWindow().showNotification("Error Saving Form", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
		}
	}

}
