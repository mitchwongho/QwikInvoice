/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.gdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * @author mwho
 * 
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType=IdentityType.APPLICATION, detachable="true")
public class TokenBean implements Serializable{
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Key id;
	@Persistent
	private String oauthToken;
	@Persistent
	private String oauthTokenSecret;

	/**
	 * Default constructor
	 */
	public TokenBean() {
		super();
	}

	/**
	 * @return the id
	 */
	public Key getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Key id) {
		this.id = id;
	}

	/**
	 * @return the oauthToken
	 */
	public String getOauthToken() {
		return oauthToken;
	}

	/**
	 * @param oauthToken the oauthToken to set
	 */
	public void setOauthToken(String oauthToken) {
		this.oauthToken = oauthToken;
	}

	/**
	 * @return the oauthTokenSecret
	 */
	public String getOauthTokenSecret() {
		return oauthTokenSecret;
	}

	/**
	 * @param oauthTokenSecret the oauthTokenSecret to set
	 */
	public void setOauthTokenSecret(String oauthTokenSecret) {
		this.oauthTokenSecret = oauthTokenSecret;
	}

	/**
	 * @param stream
	 * @return
	 * @throws IOException
	 */
	public static String streamToString(InputStream stream) throws IOException {
		if (stream == null) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		String line;

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));

			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} finally {
			stream.close();
		}

		return builder.toString();
	}
}
