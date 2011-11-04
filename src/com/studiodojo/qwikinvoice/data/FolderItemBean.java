/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;

import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.studiodojo.qwikinvoice.QwikInvoiceApplication;


/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class FolderItemBean implements Serializable {
	private String folderName = "Default";
	private String resourceId = "";
	private String docId = "";
	private String folderURL = QwikInvoiceApplication._GOOGLE_DOC_FEED_URL_;
	private String filename = "";
	
    /**
     * Default Constructor
     */
    public FolderItemBean() {
    	super();
    }
	/**
	 * @param id
	 * @param name
	 * @param docId
	 * @param resourceId
	 */
	public FolderItemBean(DocumentListEntry folderEntry) {
		super();
		this.folderName = folderEntry.getTitle().getPlainText();
		this.docId = folderEntry.getDocId();
		this.folderURL = ((MediaContent)folderEntry.getContent()).getUri();
		this.resourceId = folderEntry.getResourceId();
	}
	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}
	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	/**
	 * @return the docId
	 */
	public String getDocId() {
		return docId;
	}
	/**
	 * @return the folderURL
	 */
	public String getFolderURL() {
		return folderURL;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getFolderName();
	}
    
}
