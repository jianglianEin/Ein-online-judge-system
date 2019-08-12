package com.ein.Dao;

import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

public interface BaseDao<T> {
    public void save(T entity);

    public void update(T entity);

    public void delete(Serializable id);

    public T findById(Serializable id);
}
