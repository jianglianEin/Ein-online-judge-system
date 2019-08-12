package com.ein.Dao.Provider;

import com.ein.Model.Competition;
import com.ein.Model.Problem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProblemDaoProvider {
    public String updataProblem(@Param("problem") final Problem problem){
        SQL sql = new SQL(){{
            UPDATE("problem_db");



            if (problem.getTitle() != null){
                SET("title = #{problem.title}");
            }
            if (problem.getDiscription() != null){
                SET("discription = #{problem.discription}");
            }
            if (problem.getInputData() != null){
                SET("inputData = #{problem.inputData}");
            }
            if (problem.getOutputData() != null){
                SET("outputData = #{problem.outputData}");
            }
            if (problem.getExample() != null){
                SET("example = #{problem.example}");
            }
            if (problem.getCommit() != null){
                SET("commit = #{problem.commit}");
            }
            if (problem.getPass() != null){
                SET("pass = #{problem.pass}");
            }
            if (problem.getTestData() != null){
                SET("testData = #{problem.testData}");
            }
            WHERE("id = #{problem.id}");
        }};
        return sql.toString();

    }
}
