package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.BBS;
import com.ein.Model.User;
import com.ein.Model.User_BBS;
import com.ein.ServiceImpl.BBSServiceImpl;
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
@RequestMapping(value = "/notic")
public class NoticController {

    @Resource(name = "UserService")
    private UserServiceImpl userService;

    @Resource(name = "BBSService")
    private BBSServiceImpl bbsService;

    private static final Log logger = LogFactory.getLog(NoticController.class);

    @ResponseBody
    @RequestMapping(value = "/showDetailedNotic",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String showDetailedNotic(HttpServletRequest request){
        String bbsId = request.getParameter("bbsId");

//        System.out.println(bbsId);
        Result result = bbsService.searchBBSByGet(bbsId);


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
                      @RequestParam("Lz_name")String Lz_name,
                      @RequestParam("discription")String discription,
                      @RequestParam("startDate")String startDate,
                      @RequestParam("title")String title) throws Exception {

        Result userResult = userService.getUserByUsername(Lz_name);


        User user = JSON.parseObject(userResult.getMessage(),User.class);


        BBS bbs = new BBS();
        bbs.setLz(user);
        bbs.setDiscription(discription);
        bbs.setStartDate(startDate);
        bbs.setTitle(title);

        Result result = bbsService.addByPost(bbs);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/reply",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String reply(HttpServletRequest request,
                      @RequestParam("username")String username,
                      @RequestParam("bbsId")String bbsId,
                      @RequestParam("msg")String msg,
                      @RequestParam("postDate")String postDate) throws Exception {

        Result userResult = userService.getUserByUsername(username);

        User user = JSON.parseObject(userResult.getMessage(),User.class);

        Result bbsResult = bbsService.searchBBSByGet(bbsId);

        BBS bbs = JSON.parseObject(bbsResult.getMessage(),BBS.class);


        User_BBS user_bbs = new User_BBS();
        user_bbs.setUser(user);
        user_bbs.setBbs(bbs);
        user_bbs.setMsg(msg);
        user_bbs.setPostDate(postDate);

        Result result = bbsService.addReplyByPost(user_bbs);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getNotics",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getNotics(HttpServletRequest request){

        int page = Integer.parseInt(request.getParameter("pageNum"));
        int bbsNum = Integer.parseInt(request.getParameter("bbsNum"));

        Result result = bbsService.searchBBSByPage(page,bbsNum);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getReplys",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getReplys(HttpServletRequest request){
//        System.out.println("int getReplys");
        String bbsId = request.getParameter("bbsId");
//        System.out.println(bbsId);
        int id  = Integer.parseInt(bbsId);

        Result result = bbsService.searchReplyByBBSId(id);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(HttpServletRequest request){

        Result result = bbsService.searchBBSCount();

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/delete",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String delete(HttpServletRequest request) throws Exception {
        String bbsId = request.getParameter("bbsId");
        int id = Integer.parseInt(bbsId);

        Result result = bbsService.RemoveBBSById(id);


        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_resent_notic",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String get_resent_notic(HttpServletRequest request){
        int searchNum = 5;

        Result result = bbsService.get_resent_notic(5);

        return JSON.toJSON(result).toString();
    }
}
