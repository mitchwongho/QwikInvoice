/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.NotSaved;
import com.googlecode.objectify.annotation.Parent;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
@Cached
public class ProductUnitRateBean extends PersistableBean {
	@Id private Long id;
	@Parent private Key<ProductBean> parent;
	private Integer orderIndex;
	private Double costPerUnit;
	private Double pricePerUnit;
	/** <code>true</code> if this is the insert bean*/
	@NotSaved private boolean isInsertBean;
	
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#getId()
	 */
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the orderIndex
	 */
	public Integer getOrderIndex() {
		return orderIndex;
	}
	/**
	 * @param orderIndex the orderIndex to set
	 */
	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	/**
	 * @return the costPerUnit
	 */
	public double getCostPerUnit() {
		return costPerUnit;
	}
	/**
	 * @param costPerUnit the costPerUnit to set
	 */
	public void setCostPerUnit(double costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	/**
	 * @return the pricePerUnit
	 */
	public double getPricePerUnit() {
		return pricePerUnit;
	}
	/**
	 * @param pricePerUnit the pricePerUnit to set
	 */
	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	/**
	 * @return the profit
	 */
	public double getProfit() {
		return this.pricePerUnit - this.costPerUnit;
	}
	/**
	 * @return the margin
	 */
	public double getMargin() {
		return (this.pricePerUnit != 0.0d) ? (this.getProfit()/this.pricePerUnit) * 100 : 0.0d;
	}
	/**
	 * @return the isInsertBean
	 */
	public boolean isInsertBean() {
		return isInsertBean;
	}
	/**
	 * @param isInsertBean the isInsertBean to set
	 */
	public void setInsertBean(boolean isInsertBean) {
		this.isInsertBean = isInsertBean;
	}
	
	/**
	 * @return the parent
	 */
	public Key<ProductBean> getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Key<ProductBean> parent) {
		this.parent = parent;
	}
	/**
	 * @throws Exception
	 */
	public void validate() throws Exception {
		if (this.orderIndex == null) throw new Exception("Index value expected");
	}
}
