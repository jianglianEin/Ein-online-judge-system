package com.ein.Service;

import com.ein.Model.User;
import com.ein.Utils.Result;

public interface UserService extends BaseService<User>{
    public Result loginByPost(String username, String password);
    public Result getUserByUsername(String username);
    public Result registerByPost(User user);
    public Result changeMsgByPost(User user);
    public Result getPasswordByStudentId(String studentId);
    public Result get_resent_rank(int searchNum);
    public Result getUserById(int id);
    public Result searchUserCount();
    public Result searchRankByPage(int pageNum,int rankNum);

}
