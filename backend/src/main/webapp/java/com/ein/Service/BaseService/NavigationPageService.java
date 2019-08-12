package com.ein.Service.BaseService;

import com.ein.Utils.Result;

public interface NavigationPageService<T> {
    public Result searchByPage(Integer page, Integer pageNum);
    public Result searchResentEntity(Integer searchNum);
    public Result searchCount();
}
