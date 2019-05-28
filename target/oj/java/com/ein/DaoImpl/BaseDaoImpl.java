package com.ein.DaoImpl;


import com.ein.Dao.BaseDao;

import java.io.Serializable;

public class BaseDaoImpl<T> implements BaseDao<T> {

    @Override
    public void save(T entity) {

    }

    @Override
    public void update(T entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public T findById(Serializable id) {
        return null;
    }
}
