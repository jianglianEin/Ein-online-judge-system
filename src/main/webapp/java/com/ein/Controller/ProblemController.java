package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.Problem;
import com.ein.ServiceImpl.ProblemServiceImpl;
import com.ein.ServiceImpl.UserServiceImpl;
import com.ein.Utils.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/problem")
public class ProblemController {

    @Resource(name = "ProblemService")
    private ProblemServiceImpl problemService;

    private static final Log logger = LogFactory.getLog(ProblemController.class);

    @ResponseBody
    @RequestMapping(value = "/searchProblem",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String searchProblem(HttpServletRequest request) throws Exception {
        String problemId = request.getParameter("problemId");
        if (problemId.isEmpty()){
            return JSON.toJSON(new Result(false,"输入题目号为空")).toString();
        }else {
            Result result = problemService.searchProblemByGet(problemId);
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String add(HttpServletRequest request,
                      @RequestParam("title")String title,
                      @RequestParam("discription")String discription,
                      @RequestParam("inputData")String inputData,
                      @RequestParam("outputData")String outputData,
                      @RequestParam("example")String example,
                      @RequestParam("testData")String testData) throws Exception {


        Problem problem = new Problem();
        problem.setTitle(title);
        problem.setDiscription(discription);
        problem.setInputData(inputData);
        problem.setOutputData(outputData);
        problem.setExample(example);
        problem.setTestData(testData);

        problem.setCommit(0);
        problem.setPass(0);

        Result result = problemService.addByPost(problem);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getProblems",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getProblems(HttpServletRequest request) throws Exception {
        int page = Integer.parseInt(request.getParameter("pageNum"));
        int problemsNum = Integer.parseInt(request.getParameter("problemsNum"));

        Result result = problemService.searchProblemsByPage(page,problemsNum);

        return JSON.toJSON(result).toString();

    }

    @ResponseBody
    @RequestMapping(value = "/showDetailedProblem",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedProblem(HttpServletRequest request) throws Exception {
        String problemId = request.getParameter("problemId");

        Result result = problemService.searchProblemByGet(problemId);


        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String delete(HttpServletRequest request) throws Exception {
        String problemId = request.getParameter("problemId");
        int id = Integer.parseInt(problemId);

        Result result = problemService.RemoveProblemById(id);


        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(HttpServletRequest request){

        Result result = problemService.searchProblemCount();

        return JSON.toJSON(result).toString();
    }
}
