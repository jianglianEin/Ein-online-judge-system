package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Model.User;
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

    private static final Log logger = LogFactory.getLog(UserController.class);


    @ResponseBody
    @RequestMapping(value = "/login",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String login(@RequestParam("username")String username,
                        @RequestParam("password")String password,
                        HttpServletRequest request,
                        HttpServletResponse response)
    {
        logger.info("username = "+username+"; password = "+password);
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
    public String register(HttpServletRequest request) throws Exception {
        String studentId = request.getParameter("studentId");
//        System.out.println(studentId);
        String username = request.getParameter("username");
//        System.out.println(username);
        String password = request.getParameter("password");
//        System.out.println(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setStudentId(studentId);


        Result result = userService.registerByPost(user);
        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/showDetailedUser",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String showDetailedUser(HttpServletRequest request) throws Exception {
        String userId = request.getParameter("id");
        int id = Integer.parseInt(userId);

        Result result = userService.getUserById(id);

        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
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

        User user = new User();

        if (!username.isEmpty())user.setUsername(username);
        if (!studentId.isEmpty())user.setStudentId(studentId);
        if (!password.isEmpty()) user.setPassword(password);
        if (!rights.isEmpty())user.setRights(rights);
        if (!icon.isEmpty())user.setIcon(icon);
        if (!major.isEmpty())user.setMajor(major);
        if (!grade.isEmpty())user.setGrade(grade);
        if (!QQ.isEmpty())user.setQQ(QQ);
        if (!email.isEmpty())user.setEmail(email);
        user.setSex(sex);
        if (!discription.isEmpty())user.setDiscription(discription);


        Result result = userService.changeMsgByPost(user);

        if (result.isSuccess()){
            return JSON.toJSON(result).toString();

        }else {
            //可处理错误
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getRankUser",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getRankUser(HttpServletRequest request){

        int page = Integer.parseInt(request.getParameter("pageNum"));
        int rankNum = Integer.parseInt(request.getParameter("rankNum"));

        Result result = userService.searchRankByPage(page,rankNum);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/get_resent_rank",method=RequestMethod.GET ,produces="text/html;charset=UTF-8")
    public String get_resent_rank(HttpServletRequest request){

        int searchNum = 5;

        Result result = userService.get_resent_rank(searchNum);

        return JSON.toJSON(result).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/confirmPassword",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String confirmPassword(HttpServletRequest request){
        String studentId = request.getParameter("studentId");
        String old_password = request.getParameter("old_password");
        String password = request.getParameter("password");
        Result result = userService.getPasswordByStudentId(studentId);
        if (result.isSuccess()){
            String passwordInSql = result.getMessage();
            if (passwordInSql.equals(old_password)){
                return JSON.toJSON(new Result(true,password)).toString();
            }else {
                return JSON.toJSON(new Result(false,"密码错误")).toString();
            }

        }else {
            return JSON.toJSON(new Result(false,result.getMessage())).toString();
        }

    }


    @ResponseBody
    @RequestMapping(value = "/getCount",method= RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String getCount(HttpServletRequest request){

        Result result = userService.searchUserCount();

        return JSON.toJSON(result).toString();
    }
}
