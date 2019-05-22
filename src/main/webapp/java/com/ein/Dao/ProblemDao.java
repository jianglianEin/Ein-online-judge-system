package com.ein.Dao;

import com.ein.Dao.Provider.ProblemDaoProvider;
import com.ein.DaoImpl.ProblemDaoImpl;
import com.ein.Model.Problem;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

public interface ProblemDao extends BaseDao<Problem>{
    @Select("select * from problem_db where id = #{id}")
    public Problem searchProblemById(int id) throws Exception;

    @Insert("insert into problem_db(title,discription,inputData,outPutData,example,commit,pass,testData) values " +
            "(#{title},#{discription},#{inputData},#{outPutData},#{example},#{commit},#{pass},#{testData})")
    public int addProblem(Problem problem) throws Exception;

    @Select("select * from problem_db limit #{startNum},#{problemsNum}")
    public List<Problem> searchProblemsLimit(HashMap<String,Integer> pageLimit) throws Exception;

    @Delete("delete from problem_db where id = #{id}")
    public int deleteProblemById(int id) throws Exception;

    @UpdateProvider(type =ProblemDaoProvider.class,method = "updataProblem")
    public int updataProblemProvider(@Param("problem")Problem problem) throws Exception;

    @Select("select count(1) from problem_db")
    public int searchCount() throws Exception;


}
