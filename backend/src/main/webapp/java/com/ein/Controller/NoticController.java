package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.BBS;
import com.ein.Model.User;
import com.ein.Model.User_BBS;
import com.ein.ServiceImpl.BBSServiceImpl;
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
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "/notic")
public class NoticController {

    @Resource(name = "UserService")
    private UserServiceImpl userService;

    @Resource(name = "BBSService")
    private BBSServiceImpl bbsService;

    @Resource(name = "Tools")
    private Tools tools;

    private static final Log logger = LogFactory.getLog(NoticController.class);

    @ResponseBody
    @RequestMapping(value = "/showDetailedNotic",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedNotic(HttpServletRequest request,
                                    @RequestParam("bbsId")int bbsId){
        Result result = bbsService.getById(bbsId);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/add",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String add(HttpServletRequest request,
                      @RequestParam("Lz_name")String Lz_name,
                      @RequestParam("discription")String discription,
                      @RequestParam("startDate")String startDate,
                      @RequestParam("title")String title) throws Exception {
        Result userResult = userService.getUserByUsername(Lz_name);
        BBS bbs = prepareNewBBS(userResult,discription,startDate,title);
        Result result = bbsService.save(bbs);
        return JSON.toJSON(result).toString();
    }

    private BBS prepareNewBBS(Result userResult,
                              String discription,
                              String startDate,
                              String title){
        User user = (User) tools.analysisResult(userResult,User.class);
        ConcurrentHashMap<String, Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("Lz",user);
        valueMap.put("discription",discription);
        valueMap.put("startDate",startDate);
        valueMap.put("title",title);
        BBS bbs = tools.fillBean(valueMap,BBS.class);
        return bbs;
    }

    @ResponseBody
    @RequestMapping(value = "/reply",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String reply(HttpServletRequest request,
                      @RequestParam("username")String username,
                      @RequestParam("bbsId")int bbsId,
                      @RequestParam("msg")String msg,
                      @RequestParam("postDate")String postDate) throws Exception {
        Result userResult = userService.getUserByUsername(username);
        User user = (User) tools.analysisResult(userResult,User.class);

        Result bbsResult = bbsService.getById(bbsId);
        BBS bbs = (BBS) tools.analysisResult(bbsResult,BBS.class);

        User_BBS user_bbs = prepareNewReply(user,bbs,msg,postDate);
        Result result = bbsService.addReply(user_bbs);
        return JSON.toJSON(result).toString();
    }

    private User_BBS prepareNewReply(User user,
                                     BBS bbs,
                                     String msg,
                                     String postDate){
        ConcurrentHashMap<String, Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("user",user);
        valueMap.put("bbs",bbs);
        valueMap.put("msg",msg);
        valueMap.put("postDate",postDate);
        User_BBS user_bbs = tools.fillBean(valueMap,User_BBS.class);
        return user_bbs;
    }

    @ResponseBody
    @RequestMapping(value = "/getNotics",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getNotics(HttpServletRequest request,
                            @RequestParam("pageNum")int page,
                            @RequestParam("bbsNum")int bbsNum){
        Result result = bbsService.searchByPage(page,bbsNum);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getReplys",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getReplys(@RequestParam("bbsId")int id){
        Result result = bbsService.searchReplyByBBSId(id);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(){
        Result result = bbsService.searchCount();
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String delete(HttpServletRequest request,
                         @RequestParam("bbsId")int id) throws Exception {
        Result result = bbsService.deleteById(id);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_resent_notic",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String get_resent_notic(){
        int searchNum = 5;
        Result result = bbsService.searchResentEntity(searchNum);
        return JSON.toJSON(result).toString();
    }
}
