package com.ein.Service;

import com.ein.Model.Competition;
import com.ein.Model.Problem;
import com.ein.Model.SolutionOfCompetition;
import com.ein.Utils.Result;

public interface CompetitionService extends BaseService<Competition>{
    public Result searchCompetitionByGet(String competitionId);
    public Result addByPost(Competition competition);
    public Result searchCompetitionsByPage(int page,int competitionsNum);
    public Result changeCompetitionMsgByPost(Competition competition);
    public Result get_resent_competition(int searchNum);
    public Result searchCompetitionCount();
    public Result searchCompetition_Problems(String competitionId);
    public Result searchUser_SolutionOfCompetitionByGet(String competitionId,String userId);
    public Result searchSolutionOfCompetitionByProblem(String competitionId,String problemId,String username,String languageType);
    public Result commitByPost(SolutionOfCompetition solutionOfCompetition, String username,Competition competition, Problem problem, String codeRootPath, String questionRootPath);
    public Result searchUser_SolutionOfCompetitionById(String user_solutionOfCompetition_id);

}
