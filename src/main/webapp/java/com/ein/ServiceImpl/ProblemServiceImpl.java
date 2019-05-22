package com.ein.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ein.DaoImpl.ProblemDaoImpl;
import com.ein.Model.Problem;
import com.ein.Service.ProblemService;
import com.ein.Utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service("ProblemService")
public class ProblemServiceImpl implements ProblemService {

    @Resource(name = "ProblemDao")
    private ProblemDaoImpl problemDao;
    @Override
    public void save(Problem entity) {

    }

    @Override
    public void update(Problem entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public Problem getById(Serializable id) {
        return null;
    }

    @Override
    public Result searchProblemByGet(String problemId) {
        int id = Integer.parseInt(problemId);
        Problem problem = null;
        try {
            problem = problemDao.searchProblemById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (problem!=null){
            return new Result(true, JSON.toJSON(problem).toString());
        }else {
            return new Result(false,"没有该题号的题目");
        }
    }

    @Override
    public Result addByPost(Problem problem) {
        int insertRow = 0;
        try {
            insertRow = problemDao.addProblem(problem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (insertRow==0){
            return new Result(false,"添加题目失败");
        }else {
            return new Result(true,"添加题目成功");
        }
    }

    @Override
    public Result searchProblemsByPage(int page, int problemsNum) {
        List<Problem> problems = null;
        int startNum = (page-1)*problemsNum;


        HashMap<String,Integer> pageLimit = new HashMap<>();
        pageLimit.put("startNum",startNum);
        pageLimit.put("problemsNum",problemsNum);


        try {
            problems = problemDao.searchProblemsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (problems!=null){
            return new Result(true, JSON.toJSON(problems).toString());
        }else {
            return new Result(false,"没有更多的题目了");
        }
    }

    @Override
    public Result RemoveProblemById(int id) {
        int deleteRow = 0;
        try {
            deleteRow = problemDao.deleteProblemById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (deleteRow==0){
            return new Result(false,"删除题目失败");
        }else {
            return new Result(true,"删除题目成功");
        }
    }

    @Override
    public Result searchProblemCount() {
        int countNum = 0;
        try {
            countNum = problemDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (countNum!=0){
            return new Result(true, ""+countNum);
        }else {
            return new Result(false,"problem数据库为空");
        }
    }
}
