package com.ein.Service;


import com.ein.Model.Problem;
import com.ein.Model.Solution;
import com.ein.Utils.Result;

public interface SolutionService extends BaseService<Solution>{
    public Result commitByPost(Solution solution, String username, Problem problem,String codeRootPath,String questionRootPath);
    public Result searchUser_SolutionsByPage(int page,int solutionsNum);
    public Result searchUser_SolutionByGet(String solutionId,String userId);
    public Result searchSolutionCount();
    public Result searchSolutionByProblem(String problemId,String username,String languageType);







}
