package com.ein.Service;

import com.ein.Model.User;
import com.ein.Service.BaseService.BaseService;
import com.ein.Service.BaseService.NavigationPageService;
import com.ein.Utils.Result;

public interface UserService extends BaseService<User>,NavigationPageService<User> {
    public Result loginByPost(String username, String password);
    public Result getUserByUsername(String username);
    public Result registerByPost(User user);
    public Result confirmPassword(String studentId, String old_password, String password);

}
