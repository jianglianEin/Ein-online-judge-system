package com.ein.Dao;


import com.ein.Model.User_BBS;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface User_BBSDao extends BaseDao<User_BBS>{

    @Insert("insert into user_BBS_db(user_id,BBS_id,msg,postDate) values " +
            "(#{user.id},#{bbs.id},#{msg},#{postDate})")
    public int addUser_BBS(User_BBS user_bbs) throws Exception;


    @Select("select * from user_BBS_db where BBS_id = #{bbsId}")
    @Results(id = "user_bbs",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "user_id", property = "user", javaType = com.ein.Model.User.class,
                    many = @Many(select = "com.ein.Dao.UserDao.searchUserById")),
            @Result(column = "BBS_id", property = "bbs", javaType = com.ein.Model.BBS.class,
                    many = @Many(select = "com.ein.Dao.BBSDao.searchBBSById")),
            @Result(column = "msg", property = "msg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "postDate", property = "postDate", jdbcType = JdbcType.VARCHAR),
    })
    public List<User_BBS> searchUser_BBSByBBSId(int bbsId) throws Exception;
}
