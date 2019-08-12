package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.Problem;
import com.ein.Model.Solution;
import com.ein.ServiceImpl.ProblemServiceImpl;
import com.ein.ServiceImpl.SolutionServiceImpl;
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

@Controller
@RequestMapping(value = "/solution")
public class SolutionController {
    @Resource(name = "SolutionService")
    private SolutionServiceImpl solutionService;

    @Resource(name = "ProblemService")
    private ProblemServiceImpl problemService;

    @Resource(name = "Tools")
    private Tools tools;

    private static final Log logger = LogFactory.getLog(SolutionController.class);

    @ResponseBody
    @RequestMapping(value = "/commit",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String commit(HttpServletRequest request,
                         @RequestParam("code")String code,
                         @RequestParam("languageType")String languageType,
                         @RequestParam("problemId")int problemId,
                         @RequestParam("username")String username) throws Exception {

        String codeRootPath = request.getSession().getServletContext().getRealPath("code")+"/";
        String questionRootPath = request.getSession().getServletContext().getRealPath("question")+"/";
        Result problemResult = problemService.getById(problemId);
        Problem problem = (Problem) tools.analysisResult(problemResult,Problem.class);
        Solution solution = new Solution();
        solution.setCode(code);
        solution.setLanguageType(languageType);
        solution.setProblem(problem);
        Result result = solutionService.commitByPost(solution,username,problem,codeRootPath,questionRootPath);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getUser_Solutions",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getUser_Solutions(HttpServletRequest request,
                                    @RequestParam("page")int page,
                                    @RequestParam("solutionsNum")int solutionsNum){
        Result result = solutionService.searchUser_SolutionsByPage(page,solutionsNum);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/showDetailedUser_Solution",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedUser_Solution(HttpServletRequest request,
                                            @RequestParam("solutionId")String solutionId,
                                            @RequestParam("userId")String userId){
        Result result = solutionService.searchUser_SolutionByGet(solutionId,userId);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(){
        Result result = solutionService.searchSolutionCount();
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/showSolutionByProblemAndLanguageType",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showSolutionByProblemAndLanguageType(@RequestParam("problemId")String problemId,
                                                       @RequestParam("username")String username,
                                                       @RequestParam("languageType")String languageType){
        Result result = solutionService.searchSolutionByProblem(problemId,username,languageType);
        return JSON.toJSON(result).toString();
    }
}