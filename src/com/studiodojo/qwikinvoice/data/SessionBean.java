package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import com.studiodojo.qwikinvoice.data.AlbumOrderBean;
import com.studiodojo.qwikinvoice.data.BoxOrderBean;
import com.studiodojo.qwikinvoice.data.OrderBean;

@SuppressWarnings("serial")
public class SessionBean implements Serializable {

	private InvoiceBean invoiceBean;
	private List<InvoiceItemBean> items;
	private OrderBean theOrder;
	private List<AlbumOrderBean> albums;
	private List<BoxOrderBean> boxes;
	private String login; //user login (usually their email address)
	
	private UserCompanyBean ucBean;
	
	/**
	 * Default constructor
	 */
	private SessionBean() {
		this.invoiceBean = new InvoiceBean();
		this.theOrder = new OrderBean();
		this.items = new Vector<InvoiceItemBean>();
		this.albums = new Vector<AlbumOrderBean>();
		this.boxes = new Vector<BoxOrderBean>();
	}
	/**
	 * Default constructor
	 */
	public SessionBean(String login) {
		this();
		this.login = login;
	}
	
	/**
	 * @param login
	 * @param ucBean
	 */
	public SessionBean(String login, UserCompanyBean ucBean) {
		this(login);
		this.ucBean = ucBean;
		if (ucBean != null) {
			this.invoiceBean.setCompanyAddress(ucBean.getPhysicalAddress());
			this.invoiceBean.setCompanyEmailAddress(ucBean.getEmailAddress());
			this.invoiceBean.setCompanyFaxNumber(ucBean.getFaxNumber());
			this.invoiceBean.setCompanyName(ucBean.getCompanyName());
			this.invoiceBean.setCompanyPhoneNumber(ucBean.getPhoneNumber());
			this.invoiceBean.setCompanyTaxNumber(ucBean.getTaxNumber());
			
			this.theOrder.setCompanyName(ucBean.getCompanyName());
			this.theOrder.setEmail(ucBean.getEmailAddress());
			this.theOrder.setMobileNumber(ucBean.getMobileNumber());
			this.theOrder.setPhoneNumber(ucBean.getPhoneNumber());
			this.theOrder.setShippingAddress(ucBean.getPostalAddress());
			this.theOrder.setWebsiteAddress(ucBean.getWebsite());
		}
	}
	/**
	 * @param invoiceBean
	 * @param login
	 */
	public SessionBean(InvoiceBean invoiceBean, String login) {
		this(login);
		this.invoiceBean = invoiceBean;
	}

	/**
	 * @param invoiceBean
	 * @param items
	 * @param login
	 */
	public SessionBean(InvoiceBean invoiceBean, List<InvoiceItemBean> items,
			String login) {
		this(invoiceBean, login);
		this.items = items;
	}
	
	/**
	 * Resets session data
	 */
	public void clearAllData() {
		this.invoiceBean = new InvoiceBean();
		this.theOrder = new OrderBean();
		this.boxes.clear();
		this.items.clear();
		this.albums.clear();
	}
	/**
	 * @return the invoiceBean
	 */
	public InvoiceBean getInvoiceBean() {
		return invoiceBean;
	}
	/**
	 * @param invoiceBean the invoiceBean to set
	 */
	public void setInvoiceBean(InvoiceBean invoiceBean) {
		this.invoiceBean = invoiceBean;
	}
	/**
	 * @return the items
	 */
	public List<InvoiceItemBean> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<InvoiceItemBean> items) {
		this.items = items;
	}
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return the theOrder
	 */
	public OrderBean getTheOrder() {
		return theOrder;
	}
	/**
	 * @param theOrder the theOrder to set
	 */
	public void setTheOrder(OrderBean theOrder) {
		this.theOrder = theOrder;
	}
	/**
	 * @return the albums
	 */
	public List<AlbumOrderBean> getAlbums() {
		return albums;
	}
	/**
	 * @param albums the albums to set
	 */
	public void setAlbums(List<AlbumOrderBean> albums) {
		this.albums = albums;
	}
	/**
	 * @return the boxes
	 */
	public List<BoxOrderBean> getBoxes() {
		return boxes;
	}
	/**
	 * @param boxes the boxes to set
	 */
	public void setBoxes(List<BoxOrderBean> boxes) {
		this.boxes = boxes;
	}
	
}
