package com.ein.Dao;

import com.ein.Dao.Provider.CompetitionDaoProvider;
import com.ein.Model.Competition;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

public interface CompetitionDao extends BaseDao<Competition>{


    @Select("select * from competition_db where id = #{id}")
    @Results(id = "competition",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "sponsor_studentId", property = "sponsor", javaType = com.ein.Model.User.class,
                    one = @One(select = "com.ein.Dao.UserDao.searchUserByStudentId")),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "startDate", property = "startDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "endDate", property = "endDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "isOpen", property = "isOpen", jdbcType = JdbcType.BOOLEAN),
            @Result(column = "languageType", property = "languageType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "discription", property = "discription", jdbcType = JdbcType.VARCHAR),
    })
    public Competition searchCompetitionById(int id) throws Exception;

    @Insert("insert into competition_db(sponsor_studentId,title,startDate,endStart,isOpen,languageType,discription) values " +
            "(#{sponsor.studentId},#{title},#{startDate},#{endStart},#{isOpen},#{languageType},#{discription})")
    public int addCompetition(Competition competition) throws Exception;

    @Select("select * from competition_db limit #{startNum},#{competitionsNum}")
    @ResultMap(value = "competition")
    public List<Competition> searchCompetitionsLimit(HashMap<String,Integer> pageLimit) throws Exception;

    @UpdateProvider(type =CompetitionDaoProvider.class,method = "updataCompetition")
    public int updataCompetitionProvider(@Param("competition")Competition competition) throws Exception;

    @Select("select * from competition_db order by id desc limit #{searchNum}")
    @ResultMap(value = "competition")
    public List<Competition> searchCompetitionByTopNum(int searchNum) throws Exception;

    @Select("select count(1) from competition_db")
    public int searchCount() throws Exception;

}
