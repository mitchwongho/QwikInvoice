/**
 * 
 */
package com.studiodojo.qwikinvoice.ui;

import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.FolderItemBean;
import com.studiodojo.qwikinvoice.utils.gdata.TokenBean;
import com.studiodojo.qwikinvoice.utils.gdata.TokenStore;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * A File View Window to open/save a file
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class GDocFileWindow extends Window implements Button.ClickListener, ValueChangeListener {
	private FolderItemBean selectedFolderItem;
	private Button cancelButton = null, okButton = null;
	private Tree tree;
	private TextField selectedFolderField;
	private TextField filenameField;
	private OkButtonClickedListener okButtonListener;
	private CheckBox cbRootFolder;
	private List<FolderItemBean> folders = new Vector<FolderItemBean>();
	// a handle to a logger
	private final static Logger Log = Logger.getLogger(GDocFileWindow.class.getName());
	private final static Logger logger = Logger.getLogger("com.google.api.client"); 
	/**
	 * Interface to handle OK button clicks
	 * @author mwho
	 */
	public interface OkButtonClickedListener {
		public void onOKButtonClicked(FolderItemBean folderItem);
	}
	/**
	 * Default Constructor
	 */
	public GDocFileWindow() {
		super();
	}
	/**
	 * Default Constructor
	 * @param caption the window caption
	 */
	public GDocFileWindow(String caption) {
		super(caption);
	}
	/**
	 * Initialises the window
	 * @throws Exception
	 */
	public void init(OkButtonClickedListener okButtonClickListener, String aFilename) throws Exception {
		logger.setLevel(Level.ALL); 
		this.okButtonListener = okButtonClickListener;
		this.selectedFolderItem = new FolderItemBean();
		super.setModal(true);
		super.setWidth("600px");
		super.setHeight("740px");
		VerticalLayout layout = (VerticalLayout)super.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		// Control Buttons
		this.cancelButton = new Button("Cancel", (Button.ClickListener)this);
		this.okButton = new Button("OK", (Button.ClickListener)this);
		GridLayout buttonLayout = new GridLayout(2,1);
		buttonLayout.setHeight("80px");
		buttonLayout.setWidth("180px");
		buttonLayout.addComponent(this.cancelButton);
		buttonLayout.setComponentAlignment(this.cancelButton, Alignment.MIDDLE_CENTER);
		buttonLayout.addComponent(this.okButton);
		buttonLayout.setComponentAlignment(this.okButton, Alignment.MIDDLE_CENTER);
		// Tree
		this.initFolderTree();
		// ROOT Folder checkbox
		this.cbRootFolder = new CheckBox("Default location");
		this.cbRootFolder.setValue(true);
		this.cbRootFolder.setImmediate(true);
		
		if (!this.folders.isEmpty()) {
			this.cbRootFolder.setEnabled(true);
			this.cbRootFolder.addListener(new ValueChangeListener() {
				
				public void valueChange(ValueChangeEvent event) {
					Boolean state = (Boolean)cbRootFolder.getValue();
					Log.finest("Checkbox value change {value="+((Boolean)cbRootFolder.getValue()).booleanValue()+'}');
					if (state.booleanValue()) {
						selectedFolderItem = new FolderItemBean();
						tree.setEnabled(false);
					} else {
						
						selectedFolderItem = ((FolderItemBean)tree.getValue() == null) ? folders.get(0) : (FolderItemBean)tree.getValue();
						tree.setEnabled(true);
					}
					updateSelectedFolderField();
					tree.requestRepaint();
				}
			});
		} else {
			this.cbRootFolder.setEnabled(false);
		}
		// Folder textfield
		this.selectedFolderField = new TextField("Selected folder:");
		this.selectedFolderField.setMaxLength(100);
		this.selectedFolderField.setWidth("400px");
		this.selectedFolderField.setImmediate(true);
		this.selectedFolderField.setEnabled(false);
		this.selectedFolderField.setValue(this.selectedFolderItem.getFolderName());
		// Filename textfield
		this.filenameField = new TextField("File name:");
		filenameField.setMaxLength(100);
		filenameField.setWidth("400px");
		filenameField.setImmediate(true);
		//filenameField.setInputPrompt("Enter a filename");
		filenameField.setValue(aFilename);
		//
		layout.addComponent(new Label("<h2>Select the destination folder</h2>", Label.CONTENT_XHTML));
		layout.addComponent(this.cbRootFolder);
		layout.addComponent(this.tree);
		layout.addComponent(this.selectedFolderField);
		layout.addComponent(filenameField);
		layout.addComponent(buttonLayout);
		layout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
	}
	/* (non-Javadoc)
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	public void buttonClick(ClickEvent event) {
		if (event.getButton().equals(this.okButton)) {
			try {
				this.selectedFolderItem.setFilename((String)this.filenameField.getValue());
				GDocFileWindow.validate(this.selectedFolderItem);
				if (this.okButtonListener != null) this.okButtonListener.onOKButtonClicked(this.selectedFolderItem);
				super.getParent().removeWindow(this);
			} catch (Exception e) {
				Log.log(Level.WARNING, "Validation failure", e);
				super.getParent().showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			}
		} else // Cancel
			super.getParent().removeWindow(this);
	}
	
	/**
	 * Loads the directories/folders in the user's Google Docs account
	 * @throws Exception upon any error
	 */
	private void initFolderTree() throws Exception {
		this.tree = new Tree("Folders:");
		this.tree.setMultiSelect(false);
		this.tree.setImmediate(true);
		this.tree.setEnabled(false);
		this.tree.addListener((ValueChangeListener)this);
		User user = UserServiceFactory.getUserService().getCurrentUser();
		
		TokenBean userToken = TokenStore.getToken(KeyFactory.createKey(TokenBean.class.getSimpleName(), user.getEmail()));
		//
		// BUILD OAUTH PARAMETERS
		//
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(QwikInvoiceApplication.CLIENT_ID);
		oauthParameters.setOAuthConsumerSecret(QwikInvoiceApplication.CLIENT_SECRET);
		oauthParameters.setOAuthToken(userToken.getOauthToken());
		oauthParameters.setOAuthTokenSecret(userToken.getOauthTokenSecret());
		//
		// READ SERVICE FEED
		//
		DocsService theService = new DocsService(QwikInvoiceApplication._GOOGLE_APP_NAME_);
		theService.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
		
		DocumentListFeed feed = theService.getFeed(new URL(QwikInvoiceApplication._GOOGLE_DOC_FEED_URL_ + "/-/folder"), DocumentListFeed.class);
		List<DocumentListEntry> entries = feed.getEntries();
		for (DocumentListEntry entry : entries) {
			this.folders.add(new FolderItemBean(entry));
		}
		this.tree.setContainerDataSource(new BeanItemContainer<FolderItemBean>(folders));
	}
	/* (non-Javadoc)
	 * @see com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data.Property.ValueChangeEvent)
	 */
	public void valueChange(ValueChangeEvent event) {
		this.selectedFolderItem = (FolderItemBean)event.getProperty().getValue();
		this.updateSelectedFolderField();
	}
	
	private void updateSelectedFolderField() {
		this.selectedFolderField.setValue((String)this.selectedFolderItem.getFolderName());
		this.selectedFolderField.requestRepaint();
	}
	/**
	 * Validate the data
	 */
	private final static void validate(FolderItemBean bean) throws Exception {
		if (bean == null) throw new Exception("Folder details is null");
		if (bean.getFilename() == null || bean.getFilename().length() == 0) throw new Exception("File name not specified");
	}
}
