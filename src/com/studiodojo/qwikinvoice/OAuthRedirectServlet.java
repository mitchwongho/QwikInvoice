/**
 * 
 */
package com.studiodojo.qwikinvoice;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.studiodojo.qwikinvoice.utils.gdata.TokenBean;
import com.studiodojo.qwikinvoice.utils.gdata.TokenStore;

/**
 * Handles the OAuth authorization response from the user prompt.
 * @author mwho
 * @see http://code.google.com/apis/gdata/docs/auth/oauth.html#NoLibrary for <b>OAuth in the Google Data Protocol Client Libraries</b>
 */
@SuppressWarnings("serial")
public class OAuthRedirectServlet extends HttpServlet {

	private Logger Log = Logger.getLogger(OAuthRedirectServlet.class.getName());
	
	private UserService userService = UserServiceFactory.getUserService();
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User user = userService.getCurrentUser();
		Key keyDS = KeyFactory.createKey(TokenBean.class.getSimpleName(), user.getEmail());
		TokenBean token = TokenStore.getToken(keyDS);
		
		GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(QwikInvoiceApplication.CLIENT_ID);
		oauthParameters.setOAuthConsumerSecret(QwikInvoiceApplication.CLIENT_SECRET);
		//oauthParameters.setOAuthToken(token.getOauthToken());
		oauthParameters.setOAuthTokenSecret(token.getOauthTokenSecret());
		Log.finest("Before: OAuth Access Token{accessToken=" + token.getOauthToken()+",tokenSecret="+token.getOauthTokenSecret()+'}');
		
		//
		try {
			//
			// EXTRACT TOKEN FROM CALLBACK
			//
			oauthHelper.getOAuthParametersFromCallback(req.getQueryString(), oauthParameters);
			//
			// UPGRADE TO ACCESS TOKEN
			//
			String accessToken = oauthHelper.getAccessToken(oauthParameters);
			String accessTokenSecret = oauthParameters.getOAuthTokenSecret();
			
			Log.finest("After: OAuth Access Token{accessToken=" + accessToken+",tokenSecret="+accessTokenSecret+'}');
			token.setOauthToken(accessToken);
			token.setOauthTokenSecret(accessTokenSecret);
			TokenStore.updateToken(token);
			resp.sendRedirect("VAADIN/"); //return to the application
		} catch (OAuthException e) {
			Log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
