package com.ein.Dao;

import com.ein.Dao.Provider.User_SolutionOfCompetitionDaoProvider;
import com.ein.Model.User_SolutionOfCompetition;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

public interface User_SolutionOfCompetitionDao extends BaseDao<User_SolutionOfCompetition>{

    @Select("select * from user_solutionOfCompetition_db where user_id=#{user_id}")
    @Results(id = "user_solutionOfCompetition",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "user_id", property = "user", javaType = com.ein.Model.User.class,
                    many = @Many(select = "com.ein.Dao.UserDao.searchUserById")),
            @Result(column = "solutionOfCompetition_id", property = "solutionOfCompetition", javaType = com.ein.Model.SolutionOfCompetition.class,
                    one = @One(select = "com.ein.Dao.SolutionOfCompetitionDao.searchSolutionOfCompetitionById")),
            @Result(column = "postDate", property = "postDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "times", property = "times", jdbcType = JdbcType.INTEGER),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR)
    })
    public List<User_SolutionOfCompetition> searchUser_SolutionsOfCompetitionByUser(String user_id) throws Exception;

    @Insert("insert into user_solutionOfCompetition_db(user_id,solutionOfCompetition_id,postDate,times,state) values " +
            "(#{user.id},#{solutionOfCompetition.id},#{postDate},#{times},#{state})")
    public int addUser_SolutionOfCompetition(User_SolutionOfCompetition user_solutionOfCompetition) throws Exception;


    @Select("select * from user_solutionOfCompetition_db where user_id=#{user_id} and solutionOfCompetition_id=#{solutionOfCompetition_id}")
    @ResultMap(value = "user_solutionOfCompetition")
    public User_SolutionOfCompetition searchUser_SolutionOfCompetitionByUserAndSolution(HashMap<String,Integer> userAndSolution) throws Exception;

    @UpdateProvider(type = User_SolutionOfCompetitionDaoProvider.class,method = "updataUser_SolutionOfCompetition")
    public int updataUser_SolutionOfCompetitionProvider(@Param("user_solutionOfCompetition")User_SolutionOfCompetition user_solutionOfCompetition) throws Exception;

    @Select("select * from user_solutionOfCompetition_db where id=#{id}")
    @ResultMap(value = "user_solutionOfCompetition")
    public User_SolutionOfCompetition searchUser_SolutionsOfCompetitionById(int id) throws Exception;

}
