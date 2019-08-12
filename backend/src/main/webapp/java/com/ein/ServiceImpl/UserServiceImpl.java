package com.ein.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ein.Dao.UserDao;
import com.ein.DaoImpl.UserDaoImpl;
import com.ein.Model.User;
import com.ein.Service.UserService;
import com.ein.Utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Resource(name = "UserDao")
    private UserDao userDao;

    @Override
    public Result save(User entity) {
return null;
    }

    @Override
    public Result update(User entity) {
        int updataRow = 0;
        if (entity!=null){
            try {
                updataRow = userDao.updataUserProvider(entity);
            } catch (Exception e) {
                return new Result(false,e.toString());
            }
        }

        if (updataRow!=0){
            return new Result(true,""+updataRow);

        }else {
            return new Result(false,"没有该学号的对象");
        }
    }

    @Override
    public Result deleteById(Integer id) {
return null;
    }

    @Override
    public Result getById(Integer id) {
        User user = null;
        try {
            user = userDao.searchUserById(id);
        } catch (Exception e) {
            return new Result(false,e.toString());
        }

        user.setPassword("");
        if (user!=null){
            return new Result(true,JSON.toJSON(user).toString());
        }
        return new Result(false,"there is no this user");
    }

    @Override
    public Result loginByPost(String username, String password) {
        User user = null;
        try {
            user = userDao.searchUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (user != null && user.getPassword().equals(password)){
            user.setPassword("");
            return  new Result(true, JSON.toJSON(user).toString());
        }else {
            return new Result(false,"login failed");
        }
    }

    @Override
    public Result getUserByUsername(String username) {
        User user = null;
        try {
            user = userDao.searchUserByUserName(username);
        } catch (Exception e) {
            return new Result(false,e.toString());
        }
        user.setPassword("");
        if (user!=null){
            return new Result(true,JSON.toJSON(user).toString());
        }
        return new Result(false,"there is no this user");
    }

    @Override
    public Result registerByPost(User newUser) {
        boolean isRegisterUserLegal = false;
        try {
            isRegisterUserLegal = isRegisterUserLegal(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"用户查询错误");
        }

        int updataRow = 0;
        if (isRegisterUserLegal){
            try {
                setDefaultUserValues(newUser);
                updataRow = userDao.updataUserProvider(newUser);
            } catch (Exception e) {
                return new Result(false,"注册用户数据写入失败");
            }
        }else {
            return new Result(false,"注册用户非法");
        }

        if (updataRow != 0){
            return new Result(true,""+updataRow);
        }else {
            return new Result(false,"注册用户数据写入失败");
        }
    }

    private void setDefaultUserValues(User newUser){
        newUser.setIcon("img/user_icon/no_user.jpg");
        newUser.setDiscription("请添加您有相关描述，让大家更加了解你吧");
    }

    private boolean isRegisterUserLegal(User newUser) throws Exception {
        User cheakUserStudentId = userDao.searchUserByStudentId(newUser.getStudentId());
        boolean isStudentIdLegal = cheakUserStudentId != null
                &&cheakUserStudentId.getUsername() == null;
        if (isStudentIdLegal){
            User cheakUserUsername = userDao.searchUserByUserName(newUser.getUsername());
            boolean isUsernameLegal = cheakUserUsername == null;
            return isUsernameLegal;
        }else {
            return isStudentIdLegal;
        }
    }

    public Result confirmPassword(String studentId, String old_password, String password){
        User user = null;
        try {
            user = userDao.searchUserByStudentId(studentId);
        } catch (Exception e) {
            return new Result(false,e.toString());
        }

        if (user != null){
            boolean confirmPassword = user.getPassword().equals(old_password);
            if (confirmPassword){
                return new Result(true,password);
            }else {
                return new Result(false,"密码错误");
            }
        }else {
            return new Result(false,"无该用户");
        }
    }

    @Override
    public Result searchByPage(Integer page, Integer pageNum) {
        List<User> userList = null;
        int startNum = (pageNum-1)*pageNum;


        HashMap<String,Integer> pageLimit = new HashMap<>();
        pageLimit.put("startNum",startNum);
        pageLimit.put("rankNum",pageNum);

        try {
            userList = userDao.searchRankLimitOrderByPassNum(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (userList!=null){
            return new Result(true, JSON.toJSON(userList).toString());
        }else {
            return new Result(false,"没有更多的用户了");
        }
    }

    @Override
    public Result searchResentEntity(Integer searchNum) {
        List<User> userList = null;
        try {
            userList = userDao.searchRankByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }
        if (userList!=null){
            for (User user:userList) {
                user.setPassword("");
            }
            return new Result(true, JSON.toJSON(userList).toString());
        }else {
            return new Result(false,"没有用户！");
        }
    }

    @Override
    public Result searchCount() {
        int countNum = 0;
        try {
            countNum = userDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (countNum!=0){
            return new Result(true, ""+countNum);
        }else {
            return new Result(false,"user数据库为空");
        }
    }
}
