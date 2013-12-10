package com.jinva.dao.base;

import java.sql.SQLException;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.jinva.support.page.Page;

@Repository
public class PageDao extends BaseDao {

    @SuppressWarnings("unchecked")
    public <T> Page<T> selectPage(Class<T> clazz, Criterion criterion, Order order, int pageNum, int pageSize) {
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clazz);
        if (criterion != null) {
            detachedCriteria.add(criterion);
        }
        long totalCount = selectCount(detachedCriteria);
        if (order != null) {
            detachedCriteria.addOrder(order);
        }

        Collection<T> data = null;
        if (totalCount == 0) {
            data = CollectionUtils.EMPTY_COLLECTION;
        } else if (pageNum < 1 || pageSize < 1) {
            pageNum = -1;
            pageSize = -1;
            data = getHibernateTemplate().findByCriteria(detachedCriteria);
        } else {
            int firstResult = (pageNum - 1) * pageSize;
            int maxResults = pageSize;
            data = getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
        }

        return buildPage(data, pageNum, pageSize, totalCount);
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> selectPage(final String hql, final Object[] params, int pageNum, int pageSize) {
        long totalCount = selectCount(hql, params);

        Collection<T> data = null;
        if (totalCount == 0) {
            data = CollectionUtils.EMPTY_COLLECTION;
        } else if (pageNum < 1 || pageSize < 1) {
            pageNum = -1;
            pageSize = -1;
            data = params == null ? getHibernateTemplate().find(hql) : getHibernateTemplate().find(hql, params);
        } else {
            final int firstResult = (pageNum - 1) * pageSize;
            final int maxResults = pageSize;
            data = getHibernateTemplate().executeFind(new HibernateCallback<Collection<T>>() {
                public Collection<T> doInHibernate(Session session) throws HibernateException, SQLException {
                    Query query = session.createQuery(hql);
                    if (params != null) {
                        for (int i = 0, length = params.length; i < length; i++) {
                            query.setParameter(i, params[i]);
                        }
                    }
                    query.setFirstResult(firstResult);
                    query.setMaxResults(maxResults);
                    return (Collection<T>) query.list();
                }
            });
        }
        return buildPage(data, pageNum, pageSize, totalCount);
    }

    private <T> Page<T> buildPage(Collection<T> data, int pageNum, int pageSize, long totalCount) {
        Page<T> page = new Page<T>();
        page.setTotalCount(totalCount);
        page.setData(data);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        return page;
    }

}
