package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.Competition;
import com.ein.Model.Problem;
import com.ein.Model.SolutionOfCompetition;
import com.ein.Model.User;
import com.ein.ServiceImpl.CompetitionServiceImpl;
import com.ein.ServiceImpl.ProblemServiceImpl;
import com.ein.ServiceImpl.UserServiceImpl;
import com.ein.Utils.Result;
import com.ein.Utils.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "/competition")
public class CompetitionController {
    @Resource(name = "CompetitionService")
    private CompetitionServiceImpl competitionService;

    @Resource(name = "UserService")
    private UserServiceImpl userService;

    @Resource(name = "ProblemService")
    private ProblemServiceImpl problemService;

    @Resource(name = "Tools")
    private Tools tools;

    private static final Log logger = LogFactory.getLog(CompetitionController.class);

    @ResponseBody
    @RequestMapping(value = "/getCompetition_Problems",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCompetition_Problems(@RequestParam("competitionId")int competitionId){
        Result result = competitionService.searchCompetition_ProblemsByCompetitionId(competitionId);
        return JSON.toJSON(result).toString();
    }



    @ResponseBody
    @RequestMapping(value = "/showDetailedUser_SolutionOfCompetition",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedUser_SolutionOfCompetition(@RequestParam("user_solutionOfCompetition_id")int user_solutionOfCompetition_id){
        Result result = competitionService.searchUser_SolutionOfCompetitionById(user_solutionOfCompetition_id);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showDetailedCompetition",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedCompetition(@RequestParam("competitionId")int competitionId){
        Result result = competitionService.getById(competitionId);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String add(
                      @RequestParam("sponsor")String sponsor,
                      @RequestParam("title")String title,
                      @RequestParam("startDate")String startDate,
                      @RequestParam("endDate")String endDate,
                      @RequestParam("languageType")String languageType,
                      @RequestParam("discription")String discription) throws Exception {
        Result userResult = userService.getUserByUsername(sponsor);
        User user = (User) tools.analysisResult(userResult,User.class);
        Competition competition = prepareNewCompetition(user,title,startDate,endDate,languageType,discription);
        Result result = competitionService.save(competition);
        return JSON.toJSON(result).toString();
    }

    private Competition prepareNewCompetition(User sponsor,
                                              String title,
                                              String startDate,
                                              String endDate,
                                              String languageType,
                                              String discription){
        ConcurrentHashMap<String, Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("sponsor",sponsor);
        valueMap.put("title",title);
        valueMap.put("startDate",startDate);
        valueMap.put("endDate",endDate);
        valueMap.put("languageType",languageType);
        valueMap.put("discription",discription);
        Competition competition = tools.fillBean(valueMap,Competition.class);
        return competition;
    }

    @ResponseBody
    @RequestMapping(value = "/getCompetitions",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCompetitions(HttpServletRequest request,
                                  @RequestParam("page")int page,
                                  @RequestParam("competitionsNum")int competitionsNum){
        Result result = competitionService.searchByPage(page,competitionsNum);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/get_resent_competition",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String get_resent_competition(){
        int searchNum = 5;
        Result result = competitionService.searchResentEntity(searchNum);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(HttpServletRequest request){
        Result result = competitionService.searchCount();
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/showSolutionOfCompetitionByProblemAndLanguageType",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showSolutionOfCompetitionByProblemAndLanguageType(HttpServletRequest request,
                                                                    @RequestParam("competitionId")int competitionId,
                                                                    @RequestParam("problemId")int  problemId,
                                                                    @RequestParam("username")String username,
                                                                    @RequestParam("languageType")String languageType){
        Result result = competitionService.searchSolutionOfCompetitionByUsername(competitionId,problemId,username,languageType);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/commit",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String commit(HttpServletRequest request,
                         @RequestParam("code")String code,
                         @RequestParam("languageType")String languageType,
                         @RequestParam("competitionId")int competitionId,
                         @RequestParam("problemId")int problemId,
                         @RequestParam("username")String username) throws Exception {

        String codeRootPath = request.getSession().getServletContext().getRealPath("CompetitionCode")+"/";
        String questionRootPath = request.getSession().getServletContext().getRealPath("question")+"/";

        Result problemResult = problemService.getById(problemId);
        Result competitionResult = competitionService.getById(competitionId);

        Problem problem = JSON.parseObject(problemResult.getMessage(),Problem.class);
        Competition competition = JSON.parseObject(competitionResult.getMessage(),Competition.class);

        SolutionOfCompetition solutionOfCompetition = new SolutionOfCompetition();
        solutionOfCompetition.setCode(code);
        solutionOfCompetition.setLanguageType(languageType);
        solutionOfCompetition.setProblem(problem);
        solutionOfCompetition.setCompetition(competition);


        Result result = competitionService.commitByPost(solutionOfCompetition,username,competition,problem,codeRootPath,questionRootPath);

        return JSON.toJSON(result).toString();
    }
}
