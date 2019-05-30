package com.ein.Service;

import com.ein.Model.Competition;
import com.ein.Model.Problem;
import com.ein.Model.SolutionOfCompetition;
import com.ein.Service.BaseService.BaseService;
import com.ein.Service.BaseService.NavigationPageService;
import com.ein.Utils.Result;

public interface CompetitionService extends BaseService<Competition>,NavigationPageService<Competition> {
    public Result searchCompetition_ProblemsByCompetitionId(int competitionId);
    public Result searchUser_SolutionOfCompetitionByGet(String competitionId,String userId);
    public Result searchSolutionOfCompetitionByUsername(int competitionId,int problemId,String username,String languageType);
    public Result commitByPost(SolutionOfCompetition solutionOfCompetition, String username,Competition competition, Problem problem, String codeRootPath, String questionRootPath);
    public Result searchUser_SolutionOfCompetitionById(int user_solutionOfCompetition_id);

}
