package com.ein.Dao;

import com.ein.Dao.Provider.SolutionOfCompetitionDaoProvider;
import com.ein.Model.SolutionOfCompetition;
import com.ein.Model.User_Solution;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface SolutionOfCompetitionDao extends BaseDao<SolutionOfCompetition>{

    @Select("select * from solutionOfCompetition_db where competition_id = #{competitionId}")
    @Results(id = "solutionOfCompetition",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "languageType", property = "languageType", jdbcType = JdbcType.VARCHAR),
            @Result(column = "problem_id", property = "problem", javaType = com.ein.Model.Problem.class,
                    many = @Many(select = "com.ein.Dao.ProblemDao.searchProblemById")),
            @Result(column = "competition_id", property = "competition", javaType = com.ein.Model.Competition.class,
                    many = @Many(select = "com.ein.Dao.CompetitionDao.searchCompetitionById")),
            @Result(column = "state", property = "state", jdbcType = JdbcType.VARCHAR),
    })
    public List<SolutionOfCompetition> searchSolutionOfCompetitionByCompetitionId(int competitionId) throws Exception;

    @Select("select * from solutionOfCompetition_db where id = #{id}")
    @ResultMap(value = "solutionOfCompetition")
    public SolutionOfCompetition searchSolutionOfCompetitionById(int id) throws Exception;

    @Insert("insert into solutionOfCompetition_db(code,languageType,problem_id,competition_id,state) values " +
            "(#{code},#{languageType},#{problem.id},#{competition.id},#{state})")
    public int addSolutionOfCompetition(SolutionOfCompetition solutionOfCompetition) throws Exception;

    @Select("select * from solutionOfCompetition_db where code = #{code}")
    @ResultMap(value = "solutionOfCompetition")
    public SolutionOfCompetition searchSolutionOfCompetitionByCode(String code) throws Exception;


    @UpdateProvider(type = SolutionOfCompetitionDaoProvider.class,method = "updataSolutionOfCompetition")
    public int updataSolutionOfCompetitionProvider(@Param("solutionOfCompetition")SolutionOfCompetition solutionOfCompetition) throws Exception;



}