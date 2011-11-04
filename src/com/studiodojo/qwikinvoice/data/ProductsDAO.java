/**
 * 
 */
package com.studiodojo.qwikinvoice.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.studiodojo.qwikinvoice.utils.gdata.PersistenceManagerFactoryAction;

/**
 * The <code>ProductsDAO</code> class is responsible for handling the data access to persisted Product information
 * @author mwho
 */
public class ProductsDAO {

	private static final Logger Log = Logger.getLogger(ProductsDAO.class.getName());
	/**
	 * Static Constructor
	 */
	static {
		ObjectifyService.register(ProductCategoryBean.class);
		ObjectifyService.register(ProductUnitRateBean.class);
		ObjectifyService.register(ProductBean.class);
		ObjectifyService.register(ProductCatalogBean.class);
		ObjectifyService.register(ProductCatalogMappedBean.class);
	}
	/**
	 * Inserts a <code>ProductCategoryBean</code> instance into the DataStore
	 * @param bean the bean instance to insert
	 * @return the persisted bean (id should be non-zero)
	 * @throws Exception upon any error
	 */
	final static public ProductCategoryBean insertProductCategory(ProductCategoryBean bean) throws Exception {
		bean.validate();
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bean);
		assert bean.getId() != null;
		return bean;
	}
	/**
	 * Loads <code>ProductCategoryBean</code> instances for the specified <code>adminUser</code>
	 * @param adminUser identifies a system user
	 * @return a list of <code>ProductCategoryBean</code> instances, else an empty list.
	 * @throws Exception upon any error
	 */
	final public static List<ProductCategoryBean> loadProductCategoryByAdminUser(String adminUser) throws Exception {
		List<ProductCategoryBean> retval = new ArrayList<ProductCategoryBean>();
		Objectify ofy = ObjectifyService.begin();
		Query<ProductCategoryBean> q = ofy.query(ProductCategoryBean.class).filter("adminUserId = ", adminUser);
		retval.addAll(q.list());
		return retval;
	}
	
	/**
	 * Deletes the specified <code>ProductCatagoryBean</code> instance.
	 * @param bean the instance to delete.
	 * @throws Exception upon any error.
	 */
	final static public void deleteProductCatagory(ProductCategoryBean bean) throws Exception {
		PersistenceManager pm = PersistenceManagerFactoryAction.get().getPersistenceManager();
		try {
			pm.currentTransaction().begin();
			bean = pm.getObjectById(ProductCategoryBean.class, bean.getId());
			pm.deletePersistent(bean);
			pm.currentTransaction().commit();
		} finally {
			pm.close();
		}
	}
	/**
	 * Inserts a <code>ProductBean</code> instance into the DataStore
	 * @param bean the bean instance to insert
	 * @return the persisted bean (id should be non-zero)
	 * @throws Exception upon any error
	 */
	final static public ProductBean insertProduct(ProductBean bean) throws Exception {
		bean.validate();
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bean);
		assert bean.getId() != null;
		return bean;
	}
	/**
	 * Loads <code>ProductBean</code> instances for the specified <code>adminUser</code>
	 * @param adminUser identifies a system user
	 * @return a list of <code>ProductBean</code> instances, else an empty list.
	 * @throws Exception upon any error
	 */
	final public static List<ProductBean> loadProductByAdminUser(String adminUser) throws Exception {
		List<ProductBean> retval = new Vector<ProductBean>();
		Objectify ofy = ObjectifyService.begin();
		Query<ProductBean> q = ofy.query(ProductBean.class).filter("adminUserId = ", adminUser);
		retval.addAll(q.list());
		return retval;
	}
	/**
	 * Inserts a <code>ProductUnitRateBean</code> instance into the DataStore
	 * @param bean the bean instance to insert
	 * @return the persisted bean (id should be non-zero)
	 * @throws Exception upon any error
	 */
	final static public void insertProductRates (List<ProductUnitRateBean> beans, ProductBean parent) throws Exception {
		Key<ProductBean> parentKey = new Key<ProductBean>(ProductBean.class, parent.getId());
		for (ProductUnitRateBean bean : beans) {
			bean.validate();
			bean.setParent(parentKey);
		}
		Objectify ofy = ObjectifyService.begin();
		Map<Key<ProductUnitRateBean>, ProductUnitRateBean> r = ofy.put(beans.toArray(new ProductUnitRateBean[beans.size()]));
		assert !r.isEmpty();
	}
	/**
	 * @param product
	 * @return
	 * @throws Exception
	 */
	final static public List<ProductUnitRateBean> loadProductRatesByProduct(ProductBean product) throws Exception {
		List<ProductUnitRateBean> retval = new Vector<ProductUnitRateBean>();
		Objectify ofy = ObjectifyService.begin();
		QueryResultIterable<ProductUnitRateBean> rates = ofy.query(ProductUnitRateBean.class).ancestor(product).fetch();
		QueryResultIterator<ProductUnitRateBean> i = rates.iterator();
		while (i.hasNext()) {
			retval.add(i.next());
		}
		return retval;
	}
	
	/**
	 * @param product the product to delete
	 * @param cascade <code>true</code> if the delete must cascade to rates
	 * @throws Exception upon any error
	 */
	final static public void removeProduct(ProductBean product, boolean cascade) throws Exception {
		Objectify ofy = null;
		try {
			ofy = ObjectifyService.beginTransaction();
			if (cascade) {
				List<Key<ProductUnitRateBean>> keyRates = ofy.query(ProductUnitRateBean.class).ancestor(product).listKeys();
				ofy.delete(keyRates);
			}
			ofy.delete(product);
			ofy.getTxn().commit();
		} finally {
			if (ofy != null && ofy.getTxn().isActive()) ofy.getTxn().rollback();
		}
	}
	
	/**
	 * Loads <code>ProductCatalogBean</code> instances for the specified user. 
	 * @param adminUser identifies a user.
	 * @return a list of <code>ProductCatalogBean</code> instances
	 * @throws Exception upon any error
	 */
	final static public List<ProductCatalogBean> loadProductCatalogsByAdminUser(String adminUser) throws Exception {
		List<ProductCatalogBean> retval = new Vector<ProductCatalogBean>();
		Objectify ofy = ObjectifyService.begin();
		Query<ProductCatalogBean> q = ofy.query(ProductCatalogBean.class).filter("adminUserId = ", adminUser);
		retval.addAll(q.list());
		return retval;
	}
	/**
	 * Inserts a <code>ProductCatalogBean</code> instance into the DataStore
	 * @param bean the bean instance to insert
	 * @return the persisted bean (id should be non-zero)
	 * @throws Exception upon any error
	 */
	final static public ProductCatalogBean insertorUpdateProductCatalog(ProductCatalogBean bean) throws Exception {
		bean.validate();
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bean);
		assert bean.getId() != null;
		return bean;
	}
	
	/**
	 * Insert or update <code>ProductCatalogMappedBean</code> instances.
	 * @param bean the bean instance to insert/update
	 * @throws Exception upon any error
	 */
	final static public ProductCatalogMappedBean insertUpdateProductCatalogMapping(ProductCatalogMappedBean bean) throws Exception {
		bean.validate();
		Objectify ofy = ObjectifyService.begin();
		ofy.put(bean);
		assert bean.getId() != null;
		return bean;
	}
	
	/**
	 * @param catalog
	 * @return
	 * @throws Exception
	 */
	final static public List<ProductBean> loadProductsByCatalog(ProductCatalogBean catalog) throws Exception {
		Objectify ofy = ObjectifyService.begin();
		Query<ProductCatalogMappedBean> q = ofy.query(ProductCatalogMappedBean.class).ancestor(catalog);
		List<ProductBean> mappedProducts = q.get().getProducts();
		return mappedProducts; //@TODO return real value
	}
}
