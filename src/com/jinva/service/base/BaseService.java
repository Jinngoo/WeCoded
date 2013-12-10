package com.jinva.service.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinva.dao.base.BaseDao;
import com.jinva.dao.base.PageDao;
import com.jinva.support.page.Page;

@Service
public class BaseService {

    @Autowired
    protected BaseDao baseDao;
    
    @Autowired
    protected PageDao pageDao;

    public Serializable save(Object entity) {
        return baseDao.save(entity);
    }

    public void saveOrUpdate(Object entity) {
        baseDao.saveOrUpdate(entity);
    }

    public void update(Object entity) {
        baseDao.update(entity);
    }

    public <T> T get(Class<T> entityClass, String id) {
        return baseDao.get(entityClass, id);
    }

    public <T> List<T> select(Class<T> clazz) {
        return baseDao.select(clazz);
    }

    public List<Object> select(String hql, Object[] params) {
        return baseDao.select(hql, params);
    }

    public <T> List<T> selectByFileds(Class<T> clazz, String[] fieldNames, Object[] fieldValues) {
        return baseDao.selectByFileds(clazz, fieldNames, fieldValues);
    }

    public <T> T getByFields(Class<T> clazz, String[] fieldNames, Object[] fieldValues) {
        return baseDao.getByFields(clazz, fieldNames, fieldValues);
    }

    public long selectCount(Class<?> clazz) {
        return baseDao.selectCount(clazz);
    }

    public long selectCount(Class<?> clazz, String[] fieldNames, Object[] fieldValues) {
        return baseDao.selectCount(clazz, fieldNames, fieldValues);
    }

    public void delete(Object entity) {
        baseDao.delete(entity);
    }

    public void delete(Class<?> entityClass, String id) {
        baseDao.delete(entityClass, id);
    }

    public int deleteByFields(Class<?> entityClass, String[] fieldNames, Object[] fieldValues) {
        return baseDao.deleteByFields(entityClass, fieldNames, fieldValues);
    }

    public int bulkUpdate(String hql) {
        return baseDao.bulkUpdate(hql);
    }

    public int bulkUpdate(String hql, Object[] params) {
        return baseDao.bulkUpdate(hql, params);
    }
    
    //////////////////////////////////////////////
    
    public <T> Page<T> selectPage(Class<T> clazz, Criterion criterion, Order order, int pageNum, int pageSize) {
    	return pageDao.selectPage(clazz, criterion, order, pageNum, pageSize);
    }

}
