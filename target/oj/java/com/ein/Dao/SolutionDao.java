package com.ein.Dao;


import com.ein.Dao.Provider.SolutionDaoProvider;
import com.ein.Model.Problem;
import com.ein.Model.Solution;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.HashMap;
import java.util.List;

//private Integer id;
//private String code;
//private String languageType;
//private Problem problem;
//private String state;

public interface SolutionDao extends BaseDao<Solution>{

    @Insert("insert into solution_db(code,languageType,problem_id,state) values " +
            "(#{code},#{languageType},#{problem.id},#{state})")
    public int addSolution(Solution solution) throws Exception;

    @Select("select * from solution_db limit #{startNum},#{solutionsNum}")
    @Results(id = "solution",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "languageType", property = "languageType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "problem_id", property = "problem", javaType = com.ein.Model.Problem.class,
                    many = @Many(select = "com.ein.Dao.ProblemDao.searchProblemById")),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR),
    })
    public List<Solution> searchSolutionsLimit(HashMap<String,Integer> pageLimit) throws Exception;

    @Select("select * from solution_db where id = #{id}")
    @ResultMap(value = "solution")
    public Solution searchSolutionById(int id) throws Exception;

    @Select("select * from solution_db where code = #{code}")
    @ResultMap(value = "solution")
    public Solution searchSolutionByCode(String code) throws Exception;


    @UpdateProvider(type = SolutionDaoProvider.class,method = "updataSolution")
    public int updataSolutionProvider(@Param("solution")Solution solution) throws Exception;

    @Select("select count(1) from solution_db")
    public int searchCount() throws Exception;

}
