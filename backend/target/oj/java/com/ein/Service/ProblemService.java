package com.ein.Service;

import com.ein.Model.Problem;
import com.ein.Utils.Result;

public interface ProblemService extends BaseService<Problem>{
    public Result searchProblemByGet(String problemId);
    public Result addByPost(Problem problem);
    public Result searchProblemsByPage(int page,int problemsNum);
    public Result RemoveProblemById(int id);
    public Result searchProblemCount();

}
