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

import com.google.gdata.client.authn.oauth.GoogleOAuthHelper;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;

/**
 * @author mwho
 * @see http://code.google.com/apis/accounts/docs/OAuth2.html#Registering to register you API with Google
 */
@SuppressWarnings("serial")
public class TestOAuthServlet extends HttpServlet {

	private int OATUH_TYPE_2_0 = 1;
	private String CLIENT_ID = "1065936357729.apps.googleusercontent.com";
	private String CLIENT_SECRET = "Rijii5ToHoc5PEZpB8rv5/Ks";
	private String REDIRECT_URL = "http://qwikinvoice.appspot.com/letmein";
	
	private String ACCESS_KEY = "AIzaSyBRZtjV7VAFm8MHl78Kikts0Vfx0FZwZL0";

	String SCOPE = "https://docs.google.com/feeds/";
	
	private Logger Log = Logger.getLogger(TestOAuthServlet.class.getName());
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//
		String parameter = req.getQueryString();
		
		if (parameter == null) {
			//
			Log.finest("Get Unauthorized Request Token");
			GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
			oauthParameters.setOAuthConsumerKey(CLIENT_ID);
			oauthParameters.setOAuthConsumerSecret(CLIENT_SECRET);
			oauthParameters.setScope(SCOPE);
			oauthParameters.setOAuthCallback(REDIRECT_URL);
			try {
				//
				// GET OATUH AUTHORIZATION URL
				//
				GoogleOAuthHelper oauthHelper = new GoogleOAuthHelper(new OAuthHmacSha1Signer());
				oauthHelper.getUnauthorizedRequestToken(oauthParameters);
				String oauth_token = oauthParameters.getOAuthToken();
				String oauth_token_secret = oauthParameters.getOAuthTokenSecret();
				resp.getWriter().print("<p>Got unauthorized request token {token="+oauth_token+",secret="+oauth_token_secret+'}');
				
				//Authorizing a request Token
				Log.finest("Create User Authorization URL");
				String approvalPageUrl = oauthHelper.createUserAuthorizationUrl(oauthParameters);
				resp.getWriter().print("<p><a href=\""+approvalPageUrl+"\">Click here to authorize (OAuth) this application</a>");
			} catch (OAuthException e) {
				Log.log(Level.SEVERE, "OAuthException: "+e.getMessage(), e);
			} catch (Throwable t) {
				Log.log(Level.SEVERE, t.getMessage(), t);
			}
		}
	}

}
