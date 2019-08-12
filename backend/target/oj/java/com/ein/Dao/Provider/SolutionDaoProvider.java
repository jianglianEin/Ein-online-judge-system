package com.ein.Dao.Provider;

import com.ein.Model.Solution;
import com.ein.Model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SolutionDaoProvider {
    public String updataSolution(@Param("solution") final Solution solution){
        SQL sql = new SQL(){{
            UPDATE("solution_db");


            if (solution.getCode() != null){
                SET("code = #{solution.code}");
            }
            if (solution.getLanguageType() != null){
                SET("languageType = #{solution.languageType}");
            }
            if (solution.getProblem() != null){
                SET("problem_id = #{solution.problem.id}");
            }
            if (solution.getState() != null){
                SET("state = #{solution.state}");
            }
            WHERE("id = #{solution.id}");
        }};
        return sql.toString();

    }
}
