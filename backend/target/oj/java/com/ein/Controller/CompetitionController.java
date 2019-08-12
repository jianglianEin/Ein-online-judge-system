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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/competition")
public class CompetitionController {
    @Resource(name = "CompetitionService")
    private CompetitionServiceImpl competitionService;

    @Resource(name = "UserService")
    private UserServiceImpl userService;

    @Resource(name = "ProblemService")
    private ProblemServiceImpl problemService;

    private static final Log logger = LogFactory.getLog(CompetitionController.class);

    @ResponseBody
    @RequestMapping(value = "/getCompetition_Problems",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCompetition_Problems(HttpServletRequest request){
        String competitionId = request.getParameter("competitionId");

        Result result = competitionService.searchCompetition_Problems(competitionId);

        return JSON.toJSON(result).toString();
    }



    @ResponseBody
    @RequestMapping(value = "/showDetailedUser_SolutionOfCompetition",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedUser_SolutionOfCompetition(HttpServletRequest request){
        String user_solutionOfCompetition_id = request.getParameter("user_solutionOfCompetition_id");

        Result result = competitionService.searchUser_SolutionOfCompetitionById(user_solutionOfCompetition_id);


        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
            return JSON.toJSON(result).toString();
        }
    }


    @ResponseBody
    @RequestMapping(value = "/showDetailedCompetition",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedCompetition(HttpServletRequest request){
        String competitionId = request.getParameter("competitionId");

        Result result = competitionService.searchCompetitionByGet(competitionId);


        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String add(HttpServletRequest request,
                      @RequestParam("sponsor")String sponsor,
                      @RequestParam("title")String title,
                      @RequestParam("startDate")String startDate,
                      @RequestParam("endDate")String endDate,
                      @RequestParam("languageType")String languageType,
                      @RequestParam("discription")String discription) throws Exception {

        Result userResult = userService.getUserByUsername(sponsor);


        User user = JSON.parseObject(userResult.getMessage(),User.class);

        Competition competition = new Competition();
        competition.setSponsor(user);
        competition.setTitle(title);
        competition.setStartDate(startDate);
        competition.setEndDate(endDate);
        competition.setOpen(true);
        competition.setLanguageType(languageType);
        competition.setDiscription(discription);


        Result result = competitionService.addByPost(competition);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getCompetitions",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCompetitions(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int competitionsNum = Integer.parseInt(request.getParameter("competitionsNum"));

        Result result = competitionService.searchCompetitionsByPage(page,competitionsNum);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/switchCompetition",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String switchCompetition(HttpServletRequest request){
        boolean isOpen =Boolean.parseBoolean(request.getParameter("isOpen"));
        String competitionId = request.getParameter("competitionId");
        int id = Integer.parseInt(competitionId);

        Competition competition = new Competition();
        competition.setId(id);
        competition.setOpen(isOpen);


        Result result = competitionService.changeCompetitionMsgByPost(competition);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/get_resent_competition",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String get_resent_competition(HttpServletRequest request){
        int searchNum = 5;

        Result result = competitionService.get_resent_competition(5);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(HttpServletRequest request){

        Result result = competitionService.searchCompetitionCount();

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/showSolutionOfCompetitionByProblemAndLanguageType",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showSolutionOfCompetitionByProblemAndLanguageType(HttpServletRequest request){
        System.out.println("in showSolutionOfCompetitionByProblemAndLanguageType");
        String competitionId = request.getParameter("competitionId");
        String problemId = request.getParameter("problemId");
        String username = request.getParameter("username");
        String languageType = request.getParameter("languageType");

        Result result = competitionService.searchSolutionOfCompetitionByProblem(competitionId,problemId,username,languageType);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/commit",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String commit(HttpServletRequest request,
                         @RequestParam("code")String code,
                         @RequestParam("languageType")String languageType,
                         @RequestParam("competitionId")String competitionId,
                         @RequestParam("problemId")String problemId,
                         @RequestParam("username")String username) throws Exception {

        String codeRootPath = request.getSession().getServletContext().getRealPath("CompetitionCode")+"/";
        String questionRootPath = request.getSession().getServletContext().getRealPath("question")+"/";
//        String path = request.getSession().getServletContext().getRealPath("oj")+"/";
//        System.out.println(path);

        Result problemResult = problemService.searchProblemByGet(problemId);
        Result competitionResult = competitionService.searchCompetitionByGet(competitionId);


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
