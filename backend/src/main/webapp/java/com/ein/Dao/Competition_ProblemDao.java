package com.ein.Dao;

import com.ein.Model.Competition_Problem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface Competition_ProblemDao extends BaseDao<Competition_Problem>{

    @Select("select * from competition_problem_db where competition_id = #{id}")
    @Results(id = "competition_problem",value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "problem_id", property = "problem", javaType = com.ein.Model.Problem.class,
                    many = @Many(select = "com.ein.Dao.ProblemDao.searchProblemById")),
            @Result(column = "competition_id", property = "competition", javaType = com.ein.Model.Competition.class,
                    many = @Many(select = "com.ein.Dao.CompetitionDao.searchCompetitionById")),

    })
    public List<Competition_Problem> searchCompetition_ProblemsByCompetitionId(int id) throws Exception;


}
