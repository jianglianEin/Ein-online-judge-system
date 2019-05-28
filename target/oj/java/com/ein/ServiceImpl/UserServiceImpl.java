package com.ein.ServiceImpl;

import com.alibaba.fastjson.JSON;
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
    private UserDaoImpl userDao;

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public User getById(Serializable id) {
        return null;
    }

    @Override
    public Result loginByPost(String username, String password) {
        System.out.println("In userservice loginByPost");
        User user = null;
        try {
            user = userDao.searchUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (user!=null&&user.getPassword().equals(password)){

            user.setPassword("");
            if (user.getIcon()==null||user.getIcon().isEmpty()){
                user.setIcon("img/user_icon/no_user.jpg");
//                System.out.println("icon=" + user.getIcon());
            }
            String jsonStr = JSON.toJSON(user).toString();
            return  new Result(true,jsonStr);
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
        if (user.getIcon()==null||user.getIcon().isEmpty()){
            user.setIcon("img/user_icon/no_user.jpg");
//            System.out.println("icon=" + user.getIcon());
        }
        if (user!=null){
            return new Result(true,JSON.toJSON(user).toString());
        }
        return new Result(false,"there is no this user");
    }

    @Override
    public Result registerByPost(User newUser) {
        User user = null;
        User user1 = null;
        int updataRow = 0;
        try {
            user = userDao.searchUserByStudentId(newUser.getStudentId());
            user1 = userDao.searchUserByUserName(newUser.getUsername());
        } catch (Exception e) {
            return new Result(false,e.toString());
        }
        if (user!=null&&user.getUsername()==null&&user1==null){
            try {
                updataRow = userDao.updataUserProvider(newUser);
            } catch (Exception e) {
                return new Result(false,"该用户名已经被创建");
            }
        }else {
            return new Result(false,"该学号的对象已经完成注册或者该用户名已经被创建");
        }

        if (updataRow!=0){
            return new Result(true,""+updataRow);
        }else {
            return new Result(false,"没有该学号的对象");
        }

    }

    @Override
    public Result changeMsgByPost(User user) {
        int updataRow = 0;
        if (user!=null){
            try {
                updataRow = userDao.updataUserProvider(user);
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
    public Result getPasswordByStudentId(String studentId) {
        User user = null;
        try {
            user = userDao.searchUserByStudentId(studentId);
        } catch (Exception e) {
            return new Result(false,e.toString());
        }

        if (user!=null){
            return new Result(true,user.getPassword());
        }else {
            return new Result(false,"没有该学号的对象");
        }

    }

    @Override
    public Result get_resent_rank(int searchNum) {
        List<User> userList = null;
        try {
            userList = userDao.searchRankByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }
        if (userList!=null){
            for (User user:userList
                 ) {
                user.setPassword("");
                if (user.getDiscription()==null){
                    user.setDiscription("还没有留言，快去写写吧");
                }
            }
            return new Result(true, JSON.toJSON(userList).toString());
        }else {
            return new Result(false,"没有用户！");
        }
    }

    @Override
    public Result getUserById(int id) {
        User user = null;
        try {
            user = userDao.searchUserById(id);
        } catch (Exception e) {
            return new Result(false,e.toString());
        }
        user.setPassword("");
        if (user.getIcon()==null||user.getIcon().isEmpty()){
            user.setIcon("img/user_icon/no_user.jpg");
//            System.out.println("icon=" + user.getIcon());
        }
        if (user!=null){
            return new Result(true,JSON.toJSON(user).toString());
        }
        return new Result(false,"there is no this user");
    }

    @Override
    public Result searchUserCount() {
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

    @Override
    public Result searchRankByPage(int pageNum, int rankNum) {
        List<User> userList = null;
        int startNum = (pageNum-1)*rankNum;


        HashMap<String,Integer> pageLimit = new HashMap<>();
        pageLimit.put("startNum",startNum);
        pageLimit.put("rankNum",rankNum);


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
}
