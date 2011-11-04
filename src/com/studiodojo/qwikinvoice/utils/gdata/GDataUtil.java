/**
 * 
 */
package com.studiodojo.qwikinvoice.utils.gdata;

import java.net.URL;
import java.util.List;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.http.AuthSubUtil;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;

/**
 * @author mwho
 *
 */
public class GDataUtil {
		
	/**
	 * Default constructor
	 */
	private GDataUtil() {
		
	}
	
	/**
	 * @see http://code.google.com/appengine/articles/java/retrieving_gdata_feeds.html
	 * @throws Exception upon any error
	 */
	public final static String doAuthSub(String nextURL, String targetURL) throws Exception {
		String reqURL = AuthSubUtil.getRequestUrl(nextURL, targetURL, false, true);
		return reqURL;
	}
	
	public final static void doGDocStuff(String username, String password) throws Exception {
		try {
	        
	        // Create a new Documents service
	        DocsService myService = new DocsService("My Application");
	        myService.setUserCredentials(username,password);
	        
	        // Get a list of all entries
	        URL metafeedUrl = new URL("https://docs.google.com/feeds/default/private/full");
	        //System.out.println("Getting Documents entries...{username="+args[0]+",pw=" + args[1] + "}");
	        DocumentListFeed resultFeed = myService.getFeed(metafeedUrl, DocumentListFeed.class);
	        List<DocumentListEntry> entries = resultFeed.getEntries();
	        for(int i=0; i<entries.size(); i++) {
	          DocumentListEntry entry = entries.get(i);
	          System.out.println("\t" + entry.getTitle().getPlainText());
	        }
	        System.out.println("\nTotal Entries: "+entries.size());
	        //this.mainWindow.showNotification("Success", "Total Entries: "+entries.size(), Notification.TYPE_HUMANIZED_MESSAGE);
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(512);
			sb.append(e.getMessage()).append("\n\r");
			for (StackTraceElement ee : e.getStackTrace()) {
				sb.append(ee.getClassName()).append(':').append(ee.getMethodName()).append("() Line ").append(ee.getLineNumber()).append("\n\r");
			}
			//this.mainWindow.showNotification("Error", sb.toString(), Notification.TYPE_ERROR_MESSAGE);
		}
	}
}
