package com.hausontech.hrs.daoImpl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.api.IBaseDao;

/**
 * 
 * @author  
 * 
 */
@Repository("baseDao")
public class HibernateBaseDaoImpl<T> implements IBaseDao<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession()  {
		return sessionFactory.getCurrentSession();
	}
	
	private Session openSession()  {
		return sessionFactory.openSession();
	}

	public void save(T o) {
		Session hibernateSession = this.openSession();
		Transaction tran= hibernateSession.beginTransaction();
		try {
			hibernateSession.save(o);
		} catch (Exception e) {
			throw e;
		}
        tran.commit();
        hibernateSession.flush(); 
        hibernateSession.close();
	}

	public void update(T o) {
		Session hibernateSession = this.openSession();
		Transaction tran= hibernateSession.beginTransaction();
		try {
			hibernateSession.update(o);
		}  catch (Exception e) {
			throw e;
		}
        tran.commit();
        hibernateSession.flush(); 
        hibernateSession.close();
		
	}

	public void saveOrUpdate(T o) {
		Session hibernateSession = this.openSession();
		Transaction tran= hibernateSession.beginTransaction();
		try {
			hibernateSession.saveOrUpdate(o);
		}  catch (Exception e) {
			throw e;
		}
        tran.commit();
        hibernateSession.flush(); 
        hibernateSession.close();
		
	}

	public void merge(T o) {
		Session hibernateSession = this.openSession();
		Transaction tran= hibernateSession.beginTransaction();
		try {
			hibernateSession.merge(o);
		}  catch (Exception e) {
			throw e;
		}
        tran.commit();
        hibernateSession.flush(); 
        hibernateSession.close();
	}

	public void delete(T o) {
		Session hibernateSession = this.openSession();
		Transaction tran= hibernateSession.beginTransaction();
		try {
			hibernateSession.delete(o);
		}  catch (Exception e) {
			throw e;
		}
        tran.commit();
        hibernateSession.flush(); 
        hibernateSession.close();
	}

	public List<T> find(String hql, LinkedList<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	public List<T> find(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public List<T> find(String hql, int page, int rows, LinkedList<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> find(String hql, int page, int rows, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object... param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}

	public T get(String hql, List<Object> param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return (T) l.get(0);
		}
		return null;
	}

	public T load(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().load(c, id);
	}

	public Long count(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, LinkedList<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

	public Integer executeHql(String hql) {
		Query q = this.getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}
	
	
	public List findByDefinedSql(String sqlName,Map<String, Object> paramMap) {
		Query query = 
				this.getCurrentSession().getNamedQuery(sqlName);		
		if (query != null) {
			Iterator<String> keyIter = paramMap.keySet().iterator();
			while(keyIter.hasNext()) {
				String paraName = keyIter.next();
				query.setString(paraName, String.valueOf(paramMap.get(paraName)));
			}
		}
		return query.list();
	}
	
	public List findByDefinedSql(String sqlName, int page, int rows, Map<String, Object> paramMap) {
		Query query = 
				this.getCurrentSession().getNamedQuery(sqlName);		
		if (query != null) {
			Iterator<String> keyIter = paramMap.keySet().iterator();
			while(keyIter.hasNext()) {
				String paraName = keyIter.next();				
				query.setString(paraName, String.valueOf(paramMap.get(paraName)));
			}
		}
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	public int countByDefinedSql(String sqlName,Map<String, Object> paramMap) {
		Query query = 
				this.getCurrentSession().getNamedQuery(sqlName);		
		if (query != null) {
			Iterator<String> keyIter = paramMap.keySet().iterator();
			while(keyIter.hasNext()) {
				String paraName = keyIter.next();
				query.setString(paraName, String.valueOf(paramMap.get(paraName)));
			}
		}
		return ((java.math.BigDecimal)query.uniqueResult()).intValue();
	}
}
