package com.ein.Dao.Provider;

import com.ein.Model.Solution;
import com.ein.Model.User_Solution;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class User_SolutionDaoProvider {
    public String updataUser_Solution(@Param("user_solution") final User_Solution user_solution){
        SQL sql = new SQL(){{
            UPDATE("user_solution_db");


            if (user_solution.getUser() != null){
                SET("user_id = #{user_solution.user.id}");
            }
            if (user_solution.getSolution() != null){
                SET("solution_id = #{user_solution.solution.id}");
            }
            if (user_solution.getPostDate() != null){
                SET("postDate = #{user_solution.postDate}");
            }
            if (user_solution.getTimes() != null){
                SET("times = #{user_solution.times}");
            }
            if (user_solution.getState() != null){
                SET("state = #{user_solution.state}");
            }
            WHERE("id = #{user_solution.id}");
        }};
        return sql.toString();

    }
}
