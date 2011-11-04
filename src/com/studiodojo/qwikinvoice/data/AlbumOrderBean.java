package com.studiodojo.qwikinvoice.data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Vector;

import com.studiodojo.qwikinvoice.data.OrderBean.AlbumSizes;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
public class AlbumOrderBean implements Serializable {

	private Long id;
	private int albumSize = AlbumSizes._10x10_.ordinal(); //default album size option
	private int orderQuantity = 0;
	
	/**
     * @return a list of field names used for table column names.
     */
    public static final Vector<String> getFields() {
        Vector<String> v = new Vector<String>();
        for (Field fld : AlbumOrderBean.class.getDeclaredFields()) {
                if (!fld.getType().equals(AlbumOrderBean.class)) v.add(fld.getName()); //ignore constants
        }
        return v;
    }
    
    /**
     * @param albumSize
     * @param orderQuantity
     */
    public AlbumOrderBean(int albumSize, int orderQuantity) {
    	this.albumSize = albumSize;
    	this.orderQuantity = orderQuantity;
    }
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the albumSize
	 */
	public int getAlbumSize() {
		return albumSize;
	}
	/**
	 * @param albumSize the albumSize to set
	 */
	public void setAlbumSize(int albumSize) {
		this.albumSize = albumSize;
	}
	/**
	 * @return the orderQuantity
	 */
	public int getOrderQuantity() {
		return orderQuantity;
	}
	/**
	 * @param orderQuantity the orderQuantity to set
	 */
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
}
