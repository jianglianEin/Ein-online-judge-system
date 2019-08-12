package com.ein.Dao;

import com.ein.Model.BBS;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

public interface BBSDao extends BaseDao<BBS>{

    @Select("select * from BBS_db where id = #{id}")
    @Results(id = "bbs",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "Lz_studentId", property = "Lz", javaType = com.ein.Model.User.class,
                    one = @One(select = "com.ein.Dao.UserDao.searchUserByStudentId")),
            @Result(column = "discription", property = "discription", jdbcType = JdbcType.VARCHAR),
            @Result(column = "startDate", property = "startDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR)
    })
    public BBS searchBBSById(int id) throws Exception;


    @Insert("insert into BBS_db(Lz_studentId,discription,startDate,title) values " +
            "(#{Lz.studentId},#{discription},#{startDate},#{title})")
    public int addBBS(BBS bbs) throws Exception;


    @Select("select * from BBS_db order by id desc limit #{startNum},#{bbsNum}")
    @ResultMap(value = "bbs")
    public List<BBS> searchBBSLimit(HashMap<String,Integer> pageLimit) throws Exception;

    @Delete("delete from BBS_db where id = #{id}")
    public int deleteBBSById(int id) throws Exception;

    @Select("select * from BBS_db order by id desc limit #{searchNum}")
    @ResultMap(value = "bbs")
    public List<BBS> searchBBSByTopNum(int searchNum) throws Exception;


    @Select("select count(1) from BBS_db")
    public int searchCount() throws Exception;
}
