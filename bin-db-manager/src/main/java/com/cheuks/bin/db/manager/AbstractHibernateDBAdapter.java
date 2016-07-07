package com.cheuks.bin.db.manager;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import freemarker.template.TemplateException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractHibernateDBAdapter implements DBAdapter {

	private QueryFactory queryFactory;

	public abstract Session getSession();

	public <T> List<T> getList(Class<?> c) throws Throwable {
		return getList(c, -1, -1);
	}

	public <T> List<T> getList(Class<?> c, int page, int size) throws Throwable {
		Query query = getSession().createQuery(String.format("FROM %s a", c.getName()));
		List list = page > 0 ? page(query, page, size).list() : query.list();
		return null == list ? null : list;
	}

	public <T> List<T> getListByHQL(String hql, Object... params) throws Throwable {
		return getListByHQL(hql, -1, -1, params);
	}

	public <T> List<T> getListByHQL(String hql, int page, int size, Object... params) throws Throwable {
		Query query = fillParams(getSession().createQuery(hql), params);
		List list = page > 0 ? page(query, page, page).list() : query.list();
		return null == list ? null : list;
	}

	public <T> List<T> getListBySQL(String sql, Object... params) throws Throwable {
		return getListBySQL(sql, -1, -1, params);
	}

	public <T> List<T> getListBySQL(String sql, int page, int size, Object... params) throws Throwable {
		Query query = fillParams(getSession().createSQLQuery(sql), params);
		List list = page > 0 ? page(query, page, page).list() : query.list();
		return null == list ? null : list;
	}

	public <T> List<T> getListByXqlQueryName(String queryName, boolean isHQL, Object... params) throws Throwable {
		return getListByXqlQueryName(queryName, isHQL, -1, -1, params);
	}

	public <T> List<T> getListByXqlQueryName(String queryName, boolean isHQL, int page, int size, Object... params) throws Throwable {
		String xql = queryFactory.getXQL(queryName, false, null);
		Query query = fillParams(isHQL ? getSession().createQuery(xql) : getSession().createSQLQuery(xql), params);
		List list = page > 0 ? page(query, page, size).list() : query.list();
		return null == list ? null : list;
	}

	// public <T> List<T> getListByHqlQueryName(String queryName, Map<String, Object> params) throws Throwable {
	// return getListByHqlQueryName(queryName, params, -1, -1);
	// }
	//
	// public <T> List<T> getListByHqlQueryName(String queryName, Map<String, Object> params, int page, int size) throws Throwable {
	// Query query = fillParams(getSession().createQuery(queryFactory.getXQL(queryName, params, true)), params);
	// List list = page > 0 ? page(query, page, size).list() : query.list();
	// return null == list ? null : list;
	// }

	public Object uniqueResult(String xql, boolean isHQL, Object... params) {
		Query query = fillParams(isHQL ? getSession().createQuery(xql) : getSession().createSQLQuery(xql), params);
		Object o = query.uniqueResult();
		return null == o ? null : o;
	}

	public Object uniqueResult(String queryName, boolean isHQL, boolean isFormat, Map<String, Object> params) throws TemplateException, IOException {
		String xql = queryFactory.getXQL(queryName, isFormat, params);
		Query query = fillParams(isHQL ? getSession().createQuery(xql) : getSession().createSQLQuery(xql), params);
		Object o = query.uniqueResult();
		return null == o ? null : o;
	}

	public <T> List<T> getListByXqlQueryName(String queryName, boolean isHQL, boolean isFormat, Map<String, Object> params) throws Throwable {
		return getListByXqlQueryName(queryName, isHQL, isFormat, params, -1, -1);
	}

	public <T> List<T> getListByXqlQueryName(String queryName, boolean isHQL, boolean isFormat, Map<String, Object> params, int page, int size) throws Throwable {
		String xql = queryFactory.getXQL(queryName, isFormat, params);
		// System.err.println("XQL:" + xql);
		Query query = fillParams(isHQL ? getSession().createQuery(xql) : getSession().createSQLQuery(xql), params);
		List list = page > 0 ? page(query, page, size).list() : query.list();
		return null == list ? null : list;
	}

	public <T> T get(Class<T> clazz, Serializable id) throws Throwable {
		Object o = getSession().get(clazz, id);
		return (T) (null == o ? null : o);
	}

	public <T> T load(Class<T> clazz, Serializable id) throws Throwable {
		Object o = getSession().load(clazz, id);
		return (T) (null == o ? null : o);
	}

	public void delete(Object obj) throws Throwable {
		getSession().delete(obj);
	}

	public int deleteList(List<?> list) throws Throwable {
		int count = 0;
		Session s = getSession();
		for (int i = 0, len = list.size(); i < len; i++) {
			s.delete(list.get(i));
			if (i % 30 == 0) {
				s.flush();
				s.clear();
			}
			count++;
		}
		return count;
	}

	public void update(Object o) throws Throwable {
		getSession().update(o);
	}

	public int updateList(List<?> list) throws Throwable {
		int count = 0;
		Session s = getSession();
		for (int i = 0, len = list.size(); i < len; i++) {
			s.update(list.get(i));
			if (i % 30 == 0) {
				s.flush();
				s.clear();
			}
			count++;
		}
		return count;
	}

	public int saveList(List<?> list) throws Throwable {
		int count = 0;
		Session s = getSession();
		for (int i = 0, len = list.size(); i < len; i++) {
			s.save(list.get(i));
			if (i % 30 == 0) {
				s.flush();
				s.clear();
			}
			count++;
		}
		return count;
	}

	public <T> T save(T o) throws Throwable {
		getSession().save(o);
		return o;
	}

	public <T> T replicate(T o, String ReplicationMode) throws Throwable {
		getSession().replicate(o, org.hibernate.ReplicationMode.valueOf(ReplicationMode));
		return o;
	}

	public void saveOrUpdate(Object t) throws Throwable {
		getSession().saveOrUpdate(t);
	}

	public int executeUpdate(String xql, boolean isHql) throws Throwable {
		Query query = isHql ? getSession().createQuery(xql) : getSession().createSQLQuery(xql);
		return query.executeUpdate();
	}

	public int executeUpdate(String queryName, Map<String, Object> params, boolean isHql, boolean isFromat) throws Throwable {
		String xql = queryFactory.getXQL(queryName, isFromat, params);
		Query query = fillParams(isHql ? getSession().createQuery(xql) : getSession().createSQLQuery(xql), params);
		return query.executeUpdate();

	}

	protected Query fillParams(Query q, Object... o) {
		if (null == o || null == q) {
			return q;
		}
		for (int i = 0, len = o.length; i < len; i++) {
			q.setParameter(i, o[i]);
		}
		return q;
	}

	protected Query fillParams(Query q, Map<String, ?> o) {
		if (null == o || null == q) {
			return q;
		}
		for (Entry<String, ?> en : o.entrySet())
			try {
				q.setParameter(en.getKey(), en.getValue());
			} catch (Exception e) {
			}
		return q;
	}

	protected Query page(Query q, int pageNum, int size) {
		if (pageNum >= 0 && size >= 0) {
			q.setFirstResult(size * (pageNum - 1));
			q.setMaxResults(size);
		}
		return q;
	}

	public String queryNameFormat(Class<?> entry, String queryName) {
		return String.format("%s.%s", entry.getName(), queryName).toLowerCase();
	}

	public QueryFactory getQueryFactory() {
		return queryFactory;
	}

	public AbstractHibernateDBAdapter setQueryFactory(QueryFactory queryFactory) {
		this.queryFactory = queryFactory;
		return this;
	}
}
