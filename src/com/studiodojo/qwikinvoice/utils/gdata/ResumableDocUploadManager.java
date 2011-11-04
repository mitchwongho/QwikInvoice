/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.gdata;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.media.ResumableGDataFileUploader;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;

/**
 * @author mwho
 *
 */
public class ResumableDocUploadManager {
	private final static int MAX_CONCURRENT_UPLOADS = 10;
	private final static int PROGRESS_UPDATE_INTERVAL_MS = 1000;
	private final static int DEFAULT_CHUNK_SIZE = 10485760;
	
	private final static ResumableDocUploadManager theInstance = new ResumableDocUploadManager();
	
	private DocsService client;
	private ExecutorService mExecutor;
	
	List<ResumableGDataFileUploader> mUploaders = new ArrayList<ResumableGDataFileUploader>(MAX_CONCURRENT_UPLOADS);
	
	
	/**
	 * 
	 */
	private ResumableDocUploadManager() {
		this.client = new DocsService(QwikInvoiceApplication._GOOGLE_APP_NAME_);
		this.client.setProtocolVersion(DocsService.Versions.V1);
		//
		// SET OAUTH CREDENTIALS
		//
		/*
		TokenBean userToken = TokenStore.getToken(userKey);
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(QwikInvoiceApplication.CLIENT_ID);
		oauthParameters.setOAuthConsumerSecret(QwikInvoiceApplication.CLIENT_SECRET);
		oauthParameters.setOAuthToken(userToken.getOauthToken());
		oauthParameters.setOAuthTokenSecret(userToken.getOauthTokenSecret());
		client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
		*/
		// Pool for handling concurrent upload tasks
		mExecutor = Executors.newFixedThreadPool(MAX_CONCURRENT_UPLOADS);
		
		
	}
	
	/**
	 * @return
	 */
	public final static ResumableDocUploadManager getInstance() {
		return ResumableDocUploadManager.theInstance;
	}
	
	public void queueDocument(byte[] theFile, String fileName, String mediaType) {
		/*
		DocumentListEntry newDocument = new DocumentEntry();
		newDocument.setTitle(new PlainTextConstruct("QwikInvoice-ORDER.pdf"));
		newDocument.setMediaSource(new MediaByteArraySource(mediaBytes, DocumentListEntry.MediaType.PDF.getMimeType()));
		String folderURL = QwikInvoiceApplication._GOOGLE_DOC_FEED_URL_;
		DocumentListEntry entry = client.insert(new URL(folderURL), newDocument);
		
		MediaByteArraySource mediaFile = new MediaByteArraySource(theFile, mediaType);
		ResumableGDataFileUploader uploader = new ResumableGDataFileUploader(new URL(folderURL),
													mediaFile, this.client, DEFAULT_CHUNK_SIZE,
													mExecutor, null, PROGRESS_UPDATE_INTERVAL_MS);
		ResumableHttpFileUploader uploader = new ResumableHttpFileUploader()
		*/
	}
}
