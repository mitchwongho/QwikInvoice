package com.studiodojo.qwikinvoice.ui.order;

import java.util.List;

import com.studiodojo.qwikinvoice.QwikInvoiceApplication;
import com.studiodojo.qwikinvoice.data.AlbumOrderBean;
import com.studiodojo.qwikinvoice.data.OrderBean;
import com.studiodojo.qwikinvoice.data.OrderBean.AlbumSizes;
import com.studiodojo.qwikinvoice.data.SessionBean;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextField;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class AlbumOrderTable extends Table implements TableFieldFactory{

	private static final Object[] NATURAL_COL_ORDER = new Object[] { "albumSize", "orderQuantity" };
    private final static String[] COL_HEADERS = new String[] { "Album Size", "Quantity"};
    private final static int TABLE_PAGE_LENGTH = 20;
    
    private SessionBean theSession;
    private QwikInvoiceApplication theApp;
    
	/**
	 * @throws Exception
	 */
	public void init(SessionBean theSession, QwikInvoiceApplication theApp) throws Exception {
		
        this.theApp = theApp;
		this.theSession = theSession;
		List<AlbumOrderBean> items = this.theSession.getAlbums();
		if (items.isEmpty()) {
			items.add(new AlbumOrderBean(AlbumSizes._16x16_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._12x16_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._12x12_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._10x10_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._8x12_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._7x7_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._5x7_.ordinal(), 0)); // add an updateable row
			items.add(new AlbumOrderBean(AlbumSizes._5x5_.ordinal(), 0)); // add an updateable row
		}
		
		super.setWidth("100%");
		super.setHeight("150px");
        super.setWriteThrough(true); //update value after a commit
        super.setColumnReorderingAllowed(false); //users can't move the columns
        super.setColumnCollapsingAllowed(false);
        super.setEditable(true);
        super.setContainerDataSource(new BeanItemContainer(AlbumOrderBean.class, items));
        
        super.setVisibleColumns(AlbumOrderTable.NATURAL_COL_ORDER);
        super.setColumnHeaders(AlbumOrderTable.COL_HEADERS);
        super.setTableFieldFactory(this);
        super.setPageLength(AlbumOrderTable.TABLE_PAGE_LENGTH);
	}

	/* (non-Javadoc)
	 * @see com.vaadin.ui.TableFieldFactory#createField(com.vaadin.data.Container, java.lang.Object, java.lang.Object, com.vaadin.ui.Component)
	 */
	public Field createField(Container container, Object itemId,
			Object propertyId, Component uiContext) {
		Field fld = null;
        AlbumOrderBean order = (AlbumOrderBean)itemId;
        if ("albumSize".equals(propertyId)) {
                Select select = new Select();
                fld = OrderBean.addSelectItems((String)propertyId, select, new Integer(order.getAlbumSize()));
                fld.setReadOnly(true);
        } else if ("orderQuantity".equals(propertyId)) {
        	fld = new TextField();
        	fld.setReadOnly(false);
        }
        return fld;
	}

}
