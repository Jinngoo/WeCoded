package com.jinva.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao extends HibernateDaoSupport {

    @Autowired
    public void setSessionFactoryOverride(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public Serializable save(Object entity) {
        return getHibernateTemplate().save(entity);
    }

    public void update(Object entity) {
        getHibernateTemplate().update(entity);
    }

    public void saveOrUpdate(Object entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public <T> T get(Class<T> entityClass, String id) {
        if (StringUtils.isNotEmpty(id)) {
            return getHibernateTemplate().get(entityClass, id);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> select(Class<T> clazz) {
        DetachedCriteria c = DetachedCriteria.forClass(clazz);
        return getHibernateTemplate().findByCriteria(c);
    }

    @SuppressWarnings("unchecked")
    public List<Object> select(String hql, Object[] params) {
        return getHibernateTemplate().find(hql, params);
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> select(Class<T> clazz, Criterion criterion, Order order) {
        DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
        criteria.add(criterion);
        criteria.addOrder(order);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> selectByFileds(Class<T> clazz, String[] fieldNames, Object[] fieldValues) {
        if (fieldNames == null || fieldValues == null || fieldNames.length != fieldValues.length) {
            return null;
        }
        DetachedCriteria c = DetachedCriteria.forClass(clazz);
        for (int i = 0; i < fieldNames.length; i++) {
            c.add(Restrictions.eq(fieldNames[i], fieldValues[i]));
        }
        return getHibernateTemplate().findByCriteria(c);
    }

    public <T> T getByFields(Class<T> clazz, String[] fieldNames, Object[] fieldValues) {
        List<T> result = selectByFileds(clazz, fieldNames, fieldValues);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        } else {
            return null;
        }
    }

    private long getCount(List<?> result) {
        return CollectionUtils.isNotEmpty(result) ? (Long) result.get(0) : 0;
    }

    public long selectCount(Class<?> clazz) {
        String hql = "select count(*) from " + clazz.getName();
        List<?> result = getHibernateTemplate().find(hql);
        return getCount(result);
    }

    public long selectCount(String hql, Object[] params) {
        hql = hql.trim();
        if (hql.substring(0, 4).equalsIgnoreCase("from")) {
            hql = "select count(*) " + hql;
        } else if (hql.substring(0, 6).equalsIgnoreCase("select")) {
            if (!hql.substring(6).trim().substring(0, 4).equalsIgnoreCase("count")) {
                int fromIndex = hql.indexOf("from") > -1 ? hql.indexOf("from") : hql.indexOf("FROM");
                hql = "select count(*) " + hql.substring(fromIndex);
            }
        }
        if (hql.contains("order by")) {
            hql = hql.substring(0, hql.indexOf("order by"));
        } else if (hql.contains("ORDER BY")) {
            hql = hql.substring(0, hql.indexOf("ORDER BY"));
        }

        List<?> result = params == null ? getHibernateTemplate().find(hql) : getHibernateTemplate().find(hql, params);
        return getCount(result);
    }

    public long selectCount(Class<?> clazz, String[] fieldNames, Object[] fieldValues) {
        String hql = "select count(*) from " + clazz.getName() + " where ";
        List<String> fieldList = new ArrayList<String>();
        for (String fieldName : fieldNames) {
            fieldList.add(fieldName + "=?");
        }
        hql = hql + StringUtils.join(fieldList, " and ");
        List<?> result = getHibernateTemplate().find(hql, fieldValues);
        return getCount(result);
    }

    public long selectCount(final DetachedCriteria detachedCriteria) {
        Object count = getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                return criteria.setProjection(Projections.rowCount()).uniqueResult();
            }
        });
        detachedCriteria.setProjection(null);
        if (count == null || count instanceof Collection) {
            logger.error("Select count fail.");
            return Long.valueOf(0);
        } else {
            return Long.valueOf(String.valueOf(count));
        }
    }

    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    public void delete(Class<?> entityClass, String id) {
        if (StringUtils.isNotEmpty(id)) {
            Object entity = getHibernateTemplate().get(entityClass, id);
            if (entity != null) {
                getHibernateTemplate().delete(entity);
            }
        }
    }

    public int deleteByFields(Class<?> entityClass, String[] fieldNames, Object[] fieldValues) {
        String hql = "delete from " + entityClass.getName() + " where ";
        List<String> fieldList = new ArrayList<String>();
        for (String fieldName : fieldNames) {
            fieldList.add(fieldName + "=?");
        }
        hql = hql + StringUtils.join(fieldList, " and ");
        return getHibernateTemplate().bulkUpdate(hql, fieldValues);
    }

    public int bulkUpdate(String hql) {
        return getHibernateTemplate().bulkUpdate(hql);
    }

    public int bulkUpdate(String hql, Object[] params) {
        return params == null ? getHibernateTemplate().bulkUpdate(hql) : getHibernateTemplate().bulkUpdate(hql, params);
    }

}
