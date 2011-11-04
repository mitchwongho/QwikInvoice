/**
 * 
 */
package com.studiodojo.qwikinvoice.ui;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.utils.gdata.TokenBean;
import com.studiodojo.qwikinvoice.utils.gdata.TokenStore;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class AuthSubWindow extends Window implements Serializable{

	private String REDIRECT_URL = "http://qwikinvoice.appspot.com/letmein";
	
	private final static String SCOPE = "http://docs.google.com/feeds/";
	private final static String MESSAGE = "<h1>Authorize</h1>To activate the full features of QwikInvoice, you'll need to authorize QwikInvoice to access your Google Doc account. Click the link to direct you to Google Account authorization" ;
	
	/**
	 * @param caption
	 */
	public AuthSubWindow(String caption) {
		super(caption);
	}

	/**
	 * @param token
	 */
	public void init(Key userKey) {
		super.setModal(true);
		super.setWidth("600px");
		super.setHeight("600px");
		VerticalLayout layout = (VerticalLayout)this.getContent();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponent(new com.vaadin.ui.Label(MESSAGE, Label.CONTENT_XHTML));
		
		String requestUrl = this.getOAuthAuthorizationURL(userKey);
		
		GridLayout linkLayout = new GridLayout(1,1);
		linkLayout.setWidth("100%");
		linkLayout.setHeight("300px");
		layout.addComponent(linkLayout);
		layout.setComponentAlignment(linkLayout, Alignment.MIDDLE_CENTER);
		Link authLink = new Link("Authorize", new ExternalResource(requestUrl, "_blank"));
		linkLayout.addComponent(authLink);
		linkLayout.setComponentAlignment(authLink, Alignment.MIDDLE_CENTER);
	}
	
	/**
	 * Get OAuth Authorization URL
	 * @param userKey identifies the user that is logged in. 
	 * @return the OAuth authorization URL.
	 */
	private String getOAuthAuthorizationURL(Key userKey) {
		String retval = null;
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(QwikInvoiceApplication.CLIENT_ID);
		oauthParameters.setOAuthConsumerSecret(QwikInvoiceApplication.CLIENT_SECRET);
		oauthParameters.setScope(SCOPE);
		oauthParameters.setOAuthCallback(REDIRECT_URL);
		try {
			//
			// FETCH A REQUEST TOKEN
			//
			GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
			oauthHelper.getUnauthorizedRequestToken(oauthParameters);
			String oauth_token = oauthParameters.getOAuthToken();
			String oauth_token_secret = oauthParameters.getOAuthTokenSecret();
			//Save/Update Tokens
			TokenBean userToken = new TokenBean();
			userToken.setId(userKey);
			userToken.setOauthToken(oauth_token);
			userToken.setOauthTokenSecret(oauth_token_secret);
			TokenStore.addToken(userKey, userToken);
			// 
			// Authorizing a request Token
			//
			retval = oauthHelper.createUserAuthorizationUrl(oauthParameters);
		} catch (OAuthException e) {
			//Log.log(Level.SEVERE, "OAuthException: "+e.getMessage(), e);
		} catch (Throwable t) {
			//Log.log(Level.SEVERE, t.getMessage(), t);
		}
		return retval;
	}
}
