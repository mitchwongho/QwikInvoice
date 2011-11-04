/**
 * 
 */
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
public class BoxOrderBean  implements Serializable{

	private Long id;
	private int boxSize = AlbumSizes._10x10_.ordinal();
	private int orderQuantity;
	
	/**
     * @return a list of field names used for table column names.
     */
    public static final Vector<String> getFields() {
        Vector<String> v = new Vector<String>();
        for (Field fld : BoxOrderBean.class.getDeclaredFields()) {
                if (!fld.getType().equals(BoxOrderBean.class)) v.add(fld.getName()); //ignore constants
        }
        return v;
    }
    
    /**
     * @param boxSize
     * @param orderQuantity
     */
    public BoxOrderBean(int boxSize, int orderQuantity) {
    	this.boxSize = boxSize;
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
	 * @return the boxSize
	 */
	public int getBoxSize() {
		return boxSize;
	}
	/**
	 * @param boxSize the boxSize to set
	 */
	public void setBoxSize(int boxSize) {
		this.boxSize = boxSize;
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
