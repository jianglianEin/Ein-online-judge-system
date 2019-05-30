package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.User;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource(name = "UserService")
    private UserServiceImpl userService;

    @Resource(name = "Tools")
    private Tools tools;

    private static final Log logger = LogFactory.getLog(UserController.class);

    @ResponseBody
    @RequestMapping(value = "/login",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpServletRequest request,
                        HttpServletResponse response)
    {
        Result result = userService.loginByPost(username,password);
        if (result.isSuccess()){
            prepareLogin(request,response);
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    private void prepareLogin(HttpServletRequest request, HttpServletResponse response){
        int survivalTime = 50000;

        HttpSession session = request.getSession();
        prepareLoginSession(session,survivalTime);

        String username = request.getParameter("username");
        String sessionId = request.getSession().getId();
        ConcurrentHashMap<String,String> cookieValueMap = new ConcurrentHashMap<>();
        cookieValueMap.put("JSESSIONID",sessionId);
        cookieValueMap.put("username",username);
        ConcurrentHashMap<String,Cookie>  cookieMap =prepareLoginCookies(cookieValueMap,survivalTime);

        response.addCookie(cookieMap.get("JSESSIONID"));
        response.addCookie(cookieMap.get("username"));
    }

    private ConcurrentHashMap<String,Cookie> prepareLoginCookies(ConcurrentHashMap<String,String> cookieValueMap, int survivalTime){
        Cookie cookie = new Cookie("JSESSIONID",cookieValueMap.get("JSESSIONID"));
        Cookie cookieUsername = new Cookie("username",cookieValueMap.get("username"));
        cookie.setMaxAge(survivalTime);  // 客户端的JSESSIONID也保存
        cookie.setPath("/");
        cookieUsername.setPath("/");

        ConcurrentHashMap<String,Cookie> cookieMap = new ConcurrentHashMap<>();
        cookieMap.put("JSESSIONID",cookie);
        cookieMap.put("username",cookieUsername);
        return cookieMap;
    }

    private  void prepareLoginSession(HttpSession session, int survivalTime){
        session.setAttribute("JSESSIONID", session.getId());
        session.setMaxInactiveInterval(survivalTime);  // Session保存
    }

    @ResponseBody
    @RequestMapping(value = "/login",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String login(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        Cookie cookies[] = request.getCookies();
        if (isUserLoginBefore(session)){
            return restoreAndReturnUserData(cookies);
        }
        return JSON.toJSON(new Result(false,"login falied")).toString();
    }

    private boolean isUserLoginBefore(HttpSession session){
        boolean isUser_idCorrect;
        boolean isHaveUser_id = session.getAttribute("JSESSIONID") != null;
        if (isHaveUser_id){
            String nowSessionId = session.getId();
            isUser_idCorrect = session.getAttribute("JSESSIONID").equals(nowSessionId);
            return isUser_idCorrect;
        }
        return false;
    }

    private String restoreAndReturnUserData(Cookie cookies[]){
        String username;
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("username")) {
                username = cookie.getValue();
                Result result =  userService.getUserByUsername(username);
                return  JSON.toJSON(result).toString();
            }
        }
        return JSON.toJSON(new Result(false,"cookies do not have username")).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/logout",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(500);

        if (isUserLoginBefore(session)) {
            prepareLogout(session, response);
            return JSON.toJSON(new Result(true,"logout success")).toString();
        }
        return JSON.toJSON(new Result(false,"logout falied")).toString();
    }

    private void prepareLogout(HttpSession session, HttpServletResponse response){
        Cookie cookie = new Cookie("JSESSIONID",null);
        Cookie cookieUsername = new Cookie("username",null);
        cookieUsername.setMaxAge(0);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookieUsername.setPath("/");

        session.removeAttribute("JSESSIONID");
        response.addCookie(cookie);
        response.addCookie(cookieUsername);
    }

    @ResponseBody
    @RequestMapping(value = "/register",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String register(HttpServletRequest request,
                           @RequestParam("username")String username,
                           @RequestParam("password")String password,
                           @RequestParam("studentId")String studentId) throws Exception {
        User user = prepareRegisterUser(username,password,studentId);
        Result result = userService.registerByPost(user);
        return JSON.toJSON(result).toString();
    }

    private User prepareRegisterUser(String username, String password, String studentId){
        ConcurrentHashMap<String, Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("username",username);
        valueMap.put("password",password);
        valueMap.put("studentId",studentId);
        User user = tools.fillBean(valueMap,User.class);
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/showDetailedUser",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String showDetailedUser(HttpServletRequest request,
                                   @RequestParam("id")int userId) throws Exception {
        Result result = userService.getById(userId);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/changeMsg",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String changeMsg(HttpServletRequest request,
                            @RequestParam("username")String username,
                            @RequestParam("studentId")String studentId,
                            @RequestParam("password")String password,
                            @RequestParam("rights")String rights,
                            @RequestParam("icon")String icon,
                            @RequestParam("major")String major,
                            @RequestParam("grade")String grade,
                            @RequestParam("QQ")String QQ,
                            @RequestParam("email")String email,
                            @RequestParam("sex")boolean sex,
                            @RequestParam("discription")String discription) throws Exception {
        User user = prepareUpdataUser(username,studentId,password,rights,icon,major,grade,QQ,email,sex,discription);
        Result result = userService.update(user);
        if (result.isSuccess()){
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    private User prepareUpdataUser(String username,
                                   String studentId,
                                   String password,
                                   String rights,
                                   String icon,
                                   String major,
                                   String grade,
                                   String QQ,
                                   String email,
                                   boolean sex,
                                   String discription){
        ConcurrentHashMap<String, Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("username",username);
        valueMap.put("studentId",studentId);
        valueMap.put("password",password);
        valueMap.put("rights",rights);
        valueMap.put("icon",icon);
        valueMap.put("major",major);
        valueMap.put("grade",grade);
        valueMap.put("QQ",QQ);
        valueMap.put("email",email);
        valueMap.put("sex",sex);
        valueMap.put("discription",discription);
        User user = tools.fillBean(valueMap,User.class);
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/getRankUser",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getRankUser(HttpServletRequest request,
                              @RequestParam("pageNum")int page,
                              @RequestParam("rankNum")int rankNum){
        Result result = userService.searchByPage(page,rankNum);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/get_resent_rank",method=RequestMethod.GET ,produces="text/html;charset=UTF-8")
    public String get_resent_rank(){
        int searchNum = 5;
        Result result = userService.searchResentEntity(searchNum);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/confirmPassword",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String confirmPassword(
                                  @RequestParam("studentId")String studentId,
                                  @RequestParam("old_password")String old_password,
                                  @RequestParam("password")String password){
        Result result = userService.confirmPassword(studentId,old_password,password);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(){
        Result result = userService.searchCount();
        return JSON.toJSON(result).toString();
    }
}