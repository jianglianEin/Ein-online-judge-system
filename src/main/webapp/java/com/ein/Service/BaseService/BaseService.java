package com.ein.Service.BaseService;

import com.ein.Utils.Result;

import java.io.Serializable;

public interface BaseService<T> {
    public Result save(T entity);

    public Result update(T entity);

    public Result deleteById(Integer id);

    public Result getById(Integer id);

}
