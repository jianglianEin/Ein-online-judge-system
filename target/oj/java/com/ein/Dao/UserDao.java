package com.ein.Dao;


import com.ein.Dao.Provider.UserDaoProvider;
import com.ein.Model.User;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

public interface UserDao extends BaseDao<User>{
    @Select("select * from user_db where username = #{username}")
    public User searchUserByUserName(String username) throws Exception;


    @Select("select * from user_db where studentId = #{studentId}")
    public User searchUserByStudentId(String studentId) throws Exception;

    @UpdateProvider(type = UserDaoProvider.class,method = "updataUser")
    public int updataUserProvider(@Param("user")User user) throws Exception;

    @Select("select * from user_db where id = #{id}")
    public User searchUserById(int id) throws Exception;

    @Select("select * from user_db order by passNum desc limit #{searchNum}")
    public List<User> searchRankByTopNum(int searchNum) throws Exception;

    @Select("select count(1) from user_db")
    public int searchCount() throws Exception;

    @Select("select * from user_db order by passNum desc limit #{startNum},#{rankNum}")
    public List<User> searchRankLimitOrderByPassNum(HashMap<String,Integer> pageLimit) throws Exception;
}
