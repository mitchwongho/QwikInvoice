package com.studiodojo.qwikinvoice;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service;
import com.google.gdata.util.ServiceException;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.studiodojo.qwikinvoice.data.UserCompanyBean;
import com.studiodojo.qwikinvoice.data.UserDAO;
import com.studiodojo.qwikinvoice.ui.AboutWindow;
import com.studiodojo.qwikinvoice.ui.AuthSubWindow;
import com.studiodojo.qwikinvoice.ui.IApplicationPanel;
import com.studiodojo.qwikinvoice.ui.UserCompanySetupWindow;
import com.studiodojo.qwikinvoice.ui.invoice.InvoiceApplicationPanel;
import com.studiodojo.qwikinvoice.ui.order.FFOrderApplicationPanel;
import com.studiodojo.qwikinvoice.ui.product.CatalogApplicationPanel;
import com.studiodojo.qwikinvoice.ui.product.ProductApplicationPanel;
import com.studiodojo.qwikinvoice.ui.product.ProductCategorySettingsWindow;
import com.studiodojo.qwikinvoice.ui.product.ProductSettingsWindow;
import com.studiodojo.qwikinvoice.utils.gdata.TokenBean;
import com.studiodojo.qwikinvoice.utils.gdata.TokenStore;
import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

/**
 * The <code>QwikInvoiceApplication</code> class is the Vaadin Application
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class QwikInvoiceApplication extends Application {
	
	private Window mainWindow;
	private HashMap<Class, IApplicationPanel> map =  new HashMap<Class, IApplicationPanel>();
	public final static String _VERSION_ = "2.0";
	public final static String _GOOGLE_APP_NAME_ = "QwikInvoice";
	public final static String _GOOGLE_DOC_FEED_URL_ = "https://docs.google.com/feeds/default/private/full";
	
	public final static String CLIENT_ID = "1065936357729.apps.googleusercontent.com";
	public final static String CLIENT_SECRET = "Rijii5ToHoc5PEZpB8rv5/Ks";
	
	public final static String APP_WIDTH = "850px";
	public final static String APP_HEIGHT = "100%"; //1360px
	
	private String logoutURL; // the URL used to log users out
	private SessionBean theSession;
	private static final Logger Log = Logger.getLogger(QwikInvoiceApplication.class.getName());
	private Key userKey; //identifies the user
	private IApplicationPanel activePanel = null;
	/**
	 * Constructor
	 */
	public QwikInvoiceApplication() {
		super();
		this.map.put(InvoiceApplicationPanel.class, new InvoiceApplicationPanel());
		this.map.put(FFOrderApplicationPanel.class, new FFOrderApplicationPanel());
		this.map.put(ProductApplicationPanel.class, new ProductApplicationPanel());
		this.map.put(CatalogApplicationPanel.class, new CatalogApplicationPanel());
	}
	/* (non-Javadoc)
	 * @see com.vaadin.Application#init()
	 */
	@Override
	public void init() {
		this.mainWindow = new Window("QwikInvoice CRM Tools - Developed by StudioDojo. Engineered by Vaadin. Powered by Google.");
		setMainWindow(mainWindow);
		//
		// Check if a user is logged in
		//
		UserService us = UserServiceFactory.getUserService();
		this.logoutURL = us.createLogoutURL(super.getURL().toExternalForm());
		if (us.getCurrentUser() == null || us.getCurrentUser().getEmail() == null) {
			super.setLogoutURL(logoutURL);
			super.close();
			return;
		}
		String login = us.getCurrentUser().getEmail();
		this.userKey = KeyFactory.createKey(TokenBean.class.getSimpleName(), us.getCurrentUser().getEmail());
		//
		Key ucKey = KeyFactory.createKey(UserCompanyBean.class.getSimpleName(), us.getCurrentUser().getEmail());
		UserCompanyBean ucBean = UserDAO.getUserCompanyBean(us.getCurrentUser().getEmail());
		
		this.theSession = new SessionBean(login, ucBean);
		//
		// SETUP WORKING AREA
		//
		HorizontalLayout appLayout = new HorizontalLayout();
		appLayout.setSizeFull();
		// The Main Layout
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setWidth(APP_WIDTH);
		mainLayout.setHeight(APP_HEIGHT);
		appLayout.addComponent(mainLayout);
		appLayout.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
		appLayout.setExpandRatio(mainLayout, 2);
		//
		// Setup Header (Welcome Message)
		//
		Label welcome = new Label("<h1>QWIK!NVOICE</h1> You are "+(us.isUserLoggedIn() ? "logged in" : "logged out")+" as <b>"+us.getCurrentUser().getNickname()+"</b>", Label.CONTENT_XHTML);
		mainLayout.addComponent(welcome);
		mainLayout.setComponentAlignment(welcome, Alignment.TOP_LEFT);
		//
		// Menu Bar
		//
		MenuBar menuBar = new MenuBar();
		menuBar.setWidth(APP_WIDTH);
		MenuBar.MenuItem fileMenuItem = menuBar.addItem("File", null, null);
		MenuItem newMenuItem = fileMenuItem.addItem("New...",null, null);
		newMenuItem.addItem("Invoice/Quote", new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					QwikInvoiceApplication.this.showPanel(InvoiceApplicationPanel.class);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error loading panel", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
				
			}
		});
		newMenuItem.addItem("Order", new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					QwikInvoiceApplication.this.showPanel(FFOrderApplicationPanel.class);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error loading panel", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
				
			}
		});
		/** SAVE */
		fileMenuItem.addItem("Save",new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					TokenBean userTokenBean = TokenStore.getToken(QwikInvoiceApplication.this.userKey);
					// User must have an OAuth AUTH Token to access Google Doc service
					if (userTokenBean != null) {
						/*
						GDocFileWindow saveWindow = new GDocFileWindow("Save As...");
						saveWindow.init(QwikInvoiceApplication.this, PdfWriter.getFilename(QwikInvoiceApplication.this.theSession));
						QwikInvoiceApplication.this.mainWindow.addWindow(saveWindow);
						*/
						QwikInvoiceApplication.this.activePanel.validate();
						QwikInvoiceApplication.this.activePanel.onSave();
					} else {
						AuthSubWindow authsubWindow = new AuthSubWindow("Service Authorization Required");
						authsubWindow.init(QwikInvoiceApplication.this.userKey);
						QwikInvoiceApplication.this.mainWindow.addWindow(authsubWindow);
					}
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error Saving file", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});
		/**
		 * SETTINGS
		 */
		fileMenuItem.addSeparator();
		MenuItem settingsMenuItem = fileMenuItem.addItem("Settings...", null, null);
		settingsMenuItem.addItem("Profile",new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					UserCompanySetupWindow aWindow = new UserCompanySetupWindow();
					aWindow.init(QwikInvoiceApplication.this);
					QwikInvoiceApplication.this.mainWindow.addWindow(aWindow);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error Saving Profile", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});
		fileMenuItem.addSeparator();
		
		fileMenuItem.addItem("Logout", new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				QwikInvoiceApplication.this.setLogoutURL(logoutURL);
				QwikInvoiceApplication.this.close();
				
			}
		});
		/**
		 * Products
		 */
		MenuBar.MenuItem productsMenuItem = menuBar.addItem("Products", null, null);
		productsMenuItem.addItem("Products", null, new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					QwikInvoiceApplication.this.showPanel(ProductApplicationPanel.class);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error loading products", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});
		productsMenuItem.addItem("Categories", null, new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					ProductCategorySettingsWindow window = new ProductCategorySettingsWindow();
					window.setCaption("Product Category");
					window.init(QwikInvoiceApplication.this.theSession, QwikInvoiceApplication.this);
					QwikInvoiceApplication.this.mainWindow.addWindow(window);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error Loading Products", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});
		productsMenuItem.addItem("Catalogs", null, new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				try {
					QwikInvoiceApplication.this.showPanel(CatalogApplicationPanel.class);
				} catch (Exception e) {
					Log.log(Level.SEVERE, "Error loading catalogs", e);
					QwikInvoiceApplication.this.mainWindow.showNotification("Error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});
		/**
		 * Help
		 */
		MenuBar.MenuItem helpMenuItem = menuBar.addItem("Help", null, new MenuBar.Command() {
			
			public void menuSelected(MenuItem selectedItem) {
				AboutWindow aboutWindow = new AboutWindow();
				aboutWindow.init();
				QwikInvoiceApplication.this.mainWindow.addWindow(aboutWindow);
			}
		});
		helpMenuItem.addItem("About...",null, null);
		mainLayout.addComponent(menuBar);
		mainLayout.setComponentAlignment(menuBar, Alignment.TOP_CENTER);
		//
		// Load Main Panel
		//
		IApplicationPanel invoiceApplicationPanel = (IApplicationPanel)this.map.get(InvoiceApplicationPanel.class);
		try {
			invoiceApplicationPanel.init(this.theSession, this);
			mainLayout.addComponent((Component)invoiceApplicationPanel);
			mainLayout.setComponentAlignment((Component)invoiceApplicationPanel, Alignment.TOP_CENTER);
			this.activePanel = invoiceApplicationPanel;
		} catch (Exception e) {}
		//
		// Setup Footer
		//
		//Label footerMessage = new Label("QwikInvoice <b>version "+_VERSION_+"</b>. This service is provided as is. E&O accepted. Developed by <a href='mailto:public@studiodojo.com?subject=QwikInvoice' target='_blank'>StudioDojo</a>. Engineered by Vaadin. Powered by Google. Apache License 2.0", Label.CONTENT_XHTML);
		//mainLayout.addComponent(footerMessage);
		//mainLayout.setComponentAlignment(footerMessage, Alignment.TOP_CENTER);
		Panel mainPanel = new Panel();
		mainPanel.setScrollable(true);
		mainPanel.setContent(appLayout);
		this.mainWindow.setContent(mainPanel);
	}
	/**
	 * @param aClass
	 */
	public void showPanel(Class aClass) {
		Panel mainPanel = (Panel)this.mainWindow.getContent();
		HorizontalLayout appLayout = (HorizontalLayout)mainPanel.getContent();
		VerticalLayout mainLayout = (VerticalLayout)appLayout.getComponent(0); // the ApplicationLayout has one component which is the VerticalLayout 'mainLayout'
		IApplicationPanel panel = this.map.get(aClass);
		if (panel != null || !(aClass.equals(this.activePanel.getClass()))) {
			try {
				Log.info("Loading panel");
				/*
				 * mainLayout.getComponent(0) = Title
				 * mainLayout.getComponent(1) = Menubar
				 * mainLayout.getComponent(2) = IApplicationPanel
				 */
				((IApplicationPanel)mainLayout.getComponent(2)).validate(); //make sure it's safe to navigate off the panel (opportunity to save data)
				panel.init(this.theSession, this);
				mainLayout.replaceComponent(mainLayout.getComponent(2), (Component)panel);
				this.activePanel = panel;
			} catch (Exception e) {
				this.mainWindow.getWindow().showNotification(e.getMessage(), e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			}
		}
		else
			Log.warning("Panel does not exist");
	}
	
	/**
	 * Return <code>true</code> is the specified component is the IApplicationPanel instance that is visible
	 * @param aClass
	 * @return
	 */
	private boolean isPanelVisable(Class aClass) {
		HorizontalLayout appLayout = (HorizontalLayout)this.mainWindow.getContent();
		VerticalLayout mainLayout = (VerticalLayout)appLayout.getComponent(0); // the ApplicationLayout has one component which is the VerticalLayout 'mainLayout'
		if (mainLayout.getComponent(2).equals((Component)this.map.get(aClass)))
			return true;
		else
			return false;
	}
	
	/**
	 * Fetches and returns the specified feed from the specified service or null
	 * if the passed client or feedUrl parameters are null.
	 *
	 * @throws ServiceException if the passed client is not set up correctly or
	 *                          an error is thrown by the Google Data service in
	 *                          response to the fetch attempt
	 * @throws IOException      if an I/O error prevents a connection from being
	 *                          opened or otherwise causes request transmission
	 *                          to fail
	 */
	public static String fetchFeed(GoogleService client, String feedUrl) throws
	    ServiceException, IOException {
	  if (client == null || feedUrl == null) {
	    return null;
	  }

	  // Attempt to fetch the feed.
	  Service.GDataRequest feedRequest = client.createFeedRequest(
	      new URL(feedUrl));
	  feedRequest.execute();

	  return TokenBean.streamToString(feedRequest.getResponseStream());
	}
	/**
	 * @return the userKey
	 */
	public Key getUserKey() {
		return userKey;
	}
}
