package com.ein.Dao;


import com.ein.Dao.Provider.User_SolutionDaoProvider;
import com.ein.Model.User_BBS;
import com.ein.Model.User_Solution;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

public interface User_SolutionDao extends BaseDao<User_Solution>{
    @Insert("insert into user_solution_db(user_id,solution_id,postDate,times,state) values " +
            "(#{user.id},#{solution.id},#{postDate},#{times},#{state})")
    public int addUser_Solution(User_Solution user_solution) throws Exception;


    @Select("select * from user_solution_db where user_id=#{user_id} and solution_id=#{solution_id}")
    @Results(id = "user_solution",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "user_id", property = "user", javaType = com.ein.Model.User.class,
            many = @Many(select = "com.ein.Dao.UserDao.searchUserById")),
            @Result(column = "solution_id", property = "solution", javaType = com.ein.Model.Solution.class,
                    one = @One(select = "com.ein.Dao.SolutionDao.searchSolutionById")),
            @Result(column = "postDate", property = "postDate", jdbcType = JdbcType.VARCHAR),
            @Result(column = "times", property = "times", jdbcType = JdbcType.INTEGER),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR)
    })
    public User_Solution searchUser_SolutionByUserAndSolution(HashMap<String,Integer> userAndSolution) throws Exception;

    @UpdateProvider(type = User_SolutionDaoProvider.class,method = "updataUser_Solution")
    public int updataUser_SolutionProvider(@Param("user_solution")User_Solution user_solution) throws Exception;

    @Select("select * from user_solution_db limit #{startNum},#{solutionsNum}")
    @ResultMap(value = "user_solution")
    public List<User_Solution> searchUser_SolutionsLimit(HashMap<String,Integer> pageLimit) throws Exception;

    @Select("select * from user_solution_db where user_id=#{user_id}")
    @ResultMap(value = "user_solution")
    public List<User_Solution> searchUser_SolutionsByUser(String user_id) throws Exception;




}
