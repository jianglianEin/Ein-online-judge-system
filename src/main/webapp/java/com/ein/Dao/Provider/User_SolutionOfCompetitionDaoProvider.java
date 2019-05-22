package com.ein.Dao.Provider;

import com.ein.Model.User_Solution;
import com.ein.Model.User_SolutionOfCompetition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class User_SolutionOfCompetitionDaoProvider {
    public String updataUser_SolutionOfCompetition(@Param("user_solutionOfCompetition") final User_SolutionOfCompetition user_solutionOfCompetition){
        SQL sql = new SQL(){{
            UPDATE("user_solutionOfCompetition_db");


            if (user_solutionOfCompetition.getUser() != null){
                SET("user_id = #{user_solutionOfCompetition.user.id}");
            }
            if (user_solutionOfCompetition.getSolutionOfCompetition() != null){
                SET("solutionOfCompetition_id = #{user_solutionOfCompetition.solutionOfCompetition.id}");
            }
            if (user_solutionOfCompetition.getPostDate() != null){
                SET("postDate = #{user_solutionOfCompetition.postDate}");
            }
            if (user_solutionOfCompetition.getTimes() != null){
                SET("times = #{user_solutionOfCompetition.times}");
            }
            if (user_solutionOfCompetition.getState() != null){
                SET("state = #{user_solutionOfCompetition.state}");
            }
            WHERE("id = #{user_solutionOfCompetition.id}");
        }};
        return sql.toString();

    }
}
