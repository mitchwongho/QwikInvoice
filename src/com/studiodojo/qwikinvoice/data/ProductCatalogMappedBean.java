/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Parent;

/**
 * @author mwho
 *
 */
@SuppressWarnings("serial")
@Cached
public class ProductCatalogMappedBean extends PersistableBean {
	@Id private Long id;
	@Parent private Key<ProductCatalogBean> catalog;
	@Embedded private List<ProductBean> products;
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the catalog
	 */
	public Key<ProductCatalogBean> getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(Key<ProductCatalogBean> catalog) {
		this.catalog = catalog;
	}
	
	
	/* (non-Javadoc)
	 * @see com.studiodojo.qwikinvoice.data.PersistableBean#validate()
	 */
	@Override
	public void validate() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the productKeys
	 */
	public List<ProductBean> getProducts() {
		return products;
	}

	/**
	 * @param productKeys the productKeys to set
	 */
	public void setProducts(List<ProductBean> products) {
		this.products = products;
	}

}
