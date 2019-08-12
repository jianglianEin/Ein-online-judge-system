package com.ein.Service;

import java.io.Serializable;

public interface BaseService<T> {
    public void save(T entity);

    public void update(T entity);

    public void delete(Serializable id);

    public T getById(Serializable id);

}
