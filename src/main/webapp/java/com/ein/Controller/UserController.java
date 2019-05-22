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

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource(name = "UserService")
    private UserServiceImpl userService;

    private static final Log logger = LogFactory.getLog(UserController.class);


    @ResponseBody
    @RequestMapping(value = "/login",method= RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password, HttpServletRequest request, HttpServletResponse response){

        System.out.println("login start");

        logger.info("username = "+username+"; password = "+password);

        Result result = userService.loginByPost(username,password);


        if (result.isSuccess()){
            request.getSession().setAttribute("user_id", request.getSession().getId());
//            request.getSession().setAttribute("user_msg",result.getMessage());
            request.getSession().setMaxInactiveInterval(50000);  // Session保存
            Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
            Cookie cookieUsername = new Cookie("username",username);
            cookie.setMaxAge(50000);  // 客户端的JSESSIONID也保存
            cookie.setPath("/");
            cookieUsername.setPath("/");
            response.addCookie(cookie);
            response.addCookie(cookieUsername);

//            String jsonStr = JSON.toJSON(user).toString();
            System.out.println("login success");
            return JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(result).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    public String login(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        System.out.println(session.getId());

        String username = "";

        if (session.getAttribute("user_id")!=null&&session.getAttribute("user_id").equals(session.getId())){
//            String user_msg =  session.getAttribute("user_msg").toString();
            Cookie cookies[] = request.getCookies();
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("username")) {
                    username = cookie.getValue();
                    System.out.println(username);
                }
            }
            Result result =  userService.getUserByUsername(username);
//            System.out.println(result.getMessage());
            return  JSON.toJSON(result).toString();
        }else {
            return JSON.toJSON(new Result(false,"cheak is login falied")).toString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/logout",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
    public String logout(HttpServletRequest request,HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(500);
        System.out.println(session.getId());

        if (session.getAttribute("user_id")!=null && session.getAttribute("user_id").equals(session.getId())) {
            Cookie cookie = new Cookie("JSESSIONID",null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
            session.removeAttribute("user");
            return JSON.toJSON(new Result(true,"logout success")).toString();
        }
        return JSON.toJSON(new Result(false,"logout falied")).toString();
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
