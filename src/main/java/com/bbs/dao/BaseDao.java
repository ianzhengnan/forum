package com.bbs.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;


/**
 * Base class of Dao
 * @author I076453
 *
 * @param <T>
 */
public class BaseDao<T> {
	
	private Class<T> entityClass;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * User reflect to get subclass's generics class
	 */
	public BaseDao(){
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}
	
	/**
	 * Load instance according ID
	 * 
	 * @param id
	 * @return PO instance
	 */
	public T load(Serializable id){
		return (T) getHibernateTemplate().load(entityClass, id);
	}
	
	/**
	 * Get instance according ID
	 * 
	 * @param id
	 * @return PO instance
	 */
	public T get(Serializable id){
		return (T) getHibernateTemplate().load(entityClass, id);
	}
	
	/**
	 * Load all instance according ID
	 * 
	 * @return 
	 */
	public List<T> loadAll(Serializable id){
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	/**
	 * Save PO
	 * 
	 * @param entity
	 */
	public void save(T entity){
		getHibernateTemplate().save(entity);
	}
	
	/**
	 * Delete PO
	 * 
	 * @param entity
	 */
	public void remove(T entity){
		getHibernateTemplate().delete(entity);
	}
	
	/**
	 * Update PO
	 * 
	 * @param entity
	 */
	public void update(T entity){
		this.getHibernateTemplate().update(entity);
	}
	
	/**
	 * Execute HQL query
	 * 
	 * @param sql
	 * @return query result
	 */
	public List find(String hql, Object... params){
		return this.getHibernateTemplate().find(hql, params);
	}
	
	/**
	 * initial PO
	 * 
	 * @param entity
	 */
	public void initialize(Object entity){
		this.getHibernateTemplate().initialize(entity);
	}
	
	/**
	 * paging using hql
	 * 
	 * @param pageNo
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values){
		Assert.hasText(hql);
		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
		
		//count query
		String counQuerString = "select count(*) " + removeSelect(removeOrders(hql));
		List countList = getHibernateTemplate().find(countQueryString, values);
		long totalCount = (Long) countList.get(0);
		
		if(totalCount < 1)
			return new Page();
		// get paged objects after query
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		
		return new Page(startIndex, totalCound, pageSize, list);
		
	}
	
	/**
	 * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 调用方式如下：
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 *
	 * @param values 可变参数.
	 */
	public Query createQuery(String hql, Object... values){
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for(int i=0;i < values.length; i++){
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql){
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql: " + hql + " must has a keyword 'from'");
		return hql.substring(beginPos);
	}
	
	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 *
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql){
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public HibernateTemplate getHibernateTemplate(){
		return hibernateTemplate;
	}
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate){
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public Session getSession(){
		return SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), true);
	}
	
}
