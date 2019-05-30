package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.Problem;
import com.ein.ServiceImpl.ProblemServiceImpl;
import com.ein.ServiceImpl.UserServiceImpl;
import com.ein.Utils.Result;
import com.ein.Utils.Tools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "/problem")
public class ProblemController {

    @Resource(name = "ProblemService")
    private ProblemServiceImpl problemService;

    @Resource(name = "Tools")
    private Tools tools;

    private static final Log logger = LogFactory.getLog(ProblemController.class);

    @ResponseBody
    @RequestMapping(value = "/searchProblem",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String searchProblem(HttpServletRequest request,
                                @RequestParam("problemId")int problemId) throws Exception {
        Result result = problemService.getById(problemId);
        return JSON.toJSON(result).toString();
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
        Problem problem = prepareNewProblem(title,discription,inputData,outputData,example,testData,0,0);
        Result result = problemService.save(problem);
        return JSON.toJSON(result).toString();
    }

    private Problem prepareNewProblem(String title,
                                      String discription,
                                      String inputData,
                                      String outputData,
                                      String example,
                                      String testData,
                                      int commit,
                                      int pass){
        ConcurrentHashMap<String, Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("title",title);
        valueMap.put("discription",discription);
        valueMap.put("inputData",inputData);
        valueMap.put("outputData",outputData);
        valueMap.put("example",example);
        valueMap.put("testData",testData);
        valueMap.put("commit",commit);
        valueMap.put("pass",pass);
        Problem problem = tools.fillBean(valueMap,Problem.class);
        return problem;
    }

    @ResponseBody
    @RequestMapping(value = "/getProblems",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getProblems(@RequestParam("pageNum")int page,
                              @RequestParam("problemsNum")int problemsNum) throws Exception {
        Result result = problemService.searchByPage(page,problemsNum);
        return JSON.toJSON(result).toString();

    }

    @ResponseBody
    @RequestMapping(value = "/showDetailedProblem",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedProblem(@RequestParam("problemId")int problemId) throws Exception {
        Result result = problemService.getById(problemId);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String delete(HttpServletRequest request,
                         @RequestParam("problemId")int id) throws Exception {
        Result result = problemService.deleteById(id);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(HttpServletRequest request){
        Result result = problemService.searchCount();
        return JSON.toJSON(result).toString();
    }
}
