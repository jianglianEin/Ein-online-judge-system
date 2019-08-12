package com.ein.Dao.Provider;

import com.ein.Model.Solution;
import com.ein.Model.SolutionOfCompetition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SolutionOfCompetitionDaoProvider {
    public String updataSolutionOfCompetition(@Param("solutionOfCompetition") final SolutionOfCompetition solutionOfCompetition){
        SQL sql = new SQL(){{
            UPDATE("solutionOfCompetition_db");


            if (solutionOfCompetition.getCode() != null){
                SET("code = #{solutionOfCompetition.code}");
            }
            if (solutionOfCompetition.getLanguageType() != null){
                SET("languageType = #{solutionOfCompetition.languageType}");
            }
            if (solutionOfCompetition.getProblem() != null){
                SET("problem_id = #{solutionOfCompetition.problem.id}");
            }
            if (solutionOfCompetition.getCompetition() != null){
                SET("competition_id = #{solutionOfCompetition.competition.id}");
            }
            if (solutionOfCompetition.getState() != null){
                SET("state = #{solutionOfCompetition.state}");
            }
            WHERE("id = #{solutionOfCompetition.id}");
        }};
        return sql.toString();

    }
}
