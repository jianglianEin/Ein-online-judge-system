package com.ein.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ein.Dao.*;
import com.ein.DaoImpl.ProblemDaoImpl;
import com.ein.DaoImpl.SolutionDaoImpl;
import com.ein.DaoImpl.UserDaoImpl;
import com.ein.DaoImpl.User_SolutionDaoImpl;
import com.ein.Model.*;
import com.ein.Service.SolutionService;
import com.ein.Utils.DeleteDirectory;
import com.ein.Utils.JavaShellUtils;
import com.ein.Utils.Result;
import com.ein.Utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service("SolutionService")
public class SolutionServiceImpl implements SolutionService {

    @Resource(name = "SolutionDao")
    private SolutionDao solutionDao;
    @Resource(name = "ProblemDao")
    private ProblemDao problemDao;
    @Resource(name = "UserDao")
    private UserDao userDao;
    @Resource(name = "User_SolutionDao")
    private User_SolutionDao user_solutionDao;
    @Resource(name = "Tools")
    private Tools tools;
    @Override
    public Result save(Solution entity) {
return null;
    }

    @Override
    public Result update(Solution entity) {
return null;
    }

    @Override
    public Result deleteById(Integer id) {
return null;
    }

    @Override
    public Result getById(Integer id) {
        return null;
    }

    @Override
    public Result commitByPost(Solution solution, String username, Problem problem,String codeRootPath,String questionRootPath) {

        boolean isFirstCommit = false;

        String systemPath = "/home/ein/IdeaProjects/oj/target/oj";

        String packagePath = "language" +"/"+ solution.getLanguageType() +"/"+ username;

        String filename = "method" + problem.getId()+"."+solution.getLanguageType();
        String filepath = codeRootPath + packagePath;

        File dir = new File(filepath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File file=new File(filepath+"/"+filename);
        System.out.println(filepath+"/"+filename);
        if(!file.exists()) {
            try {
                file.createNewFile();
                isFirstCommit = true;
//                System.out.print(isFirstCommit);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result(false,"创建文件失败！");
            }
        }

//        System.out.print(isFirstCommit);
        HashMap<String ,Object>  testResult = new HashMap<String, Object>();
        PrintStream ps = null;

        switch (solution.getLanguageType()){
            case "java":
                String solutionPackage = "language" +"."+ solution.getLanguageType() +"."+ username;
//                String solutionPackage =solution.getLanguageType() +"."+ username;
                try {

                    ps = new PrintStream(new FileOutputStream(file));
                    ps.println("package "+solutionPackage+";");// 往文件里写入字符串


                    ps.print(solution.getCode());// 在已有的基础上添加字符串

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return new Result(false,"写入java文件失败！");
                }
//                String javaCmd1 = "javac "+filepath+"/"+filename+" -d . ";
                String javaCmd1 = "javac "+filepath+"/"+filename+" -d "+systemPath+"/build";
//                String javaCmd2 = "java "+solutionPackage+"."+"method" + problem.getId();
                String javaCmd2 = "java -cp "+systemPath+"/build"+" "+solutionPackage+"."+"method" + problem.getId();
                String javaCmds[] = new String[2];
                javaCmds[0] = javaCmd1;
                javaCmds[1] = javaCmd2;
                try {
                    System.out.println("testJavaData start");
                    testResult = tools.testJavaData(problem,javaCmds,questionRootPath);
                    System.out.println(testResult);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return new Result(false,"测试数据失败！");
                }
//                DeleteDirectory.deleteDir(new File("language"));
                break;
            case "cpp":
                try {
                    ps = new PrintStream(new FileOutputStream(file));
                    ps.println(solution.getCode());// 在已有的基础上添加字符串
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return new Result(false,"写入cpp文件失败！");
                }

                String cppCmd1 = "g++ -o "+"method" + problem.getId()+" "+filepath+"/"+filename;
                String cppCmd2 = "./"+"method" + problem.getId();
                String cppCmds[] = new String[2];
                cppCmds[0] = cppCmd1;
                cppCmds[1] = cppCmd2;
                try {
                    testResult = tools.testCppData(problem,cppCmds,questionRootPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return new Result(false,"测试数据失败！");
                }
//                DeleteDirectory.deleteDir(new File("method" + problem.getId()));
                break;
            default:
                testResult.put("defaultMsg","没有匹配到相应的语言类型！");
                break;
        }
        solution.setState(JSON.toJSON(testResult).toString());

        solution.setCode(filepath+"/"+filename);



        User updataUser= null;
        try {
            updataUser = userDao.searchUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"查询该解答的作者失败！");
        }

        if ((Boolean)testResult.get("isPass")){
            updataUser.setPassNum(updataUser.getPassNum()+1);
            problem.setPass(problem.getPass()+1);
        }

        problem.setCommit(problem.getCommit()+1);
        User_Solution user_solution = new User_Solution();
        user_solution.setUser(updataUser);
        user_solution.setSolution(solution);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        user_solution.setPostDate(df.format(new Date()));

        user_solution.setState(JSON.toJSON(testResult).toString());

        if (isFirstCommit){
            user_solution.setTimes(1);
            Solution afterInsertSolution = null;

            int insertSolutionRow = 0;
            int insertUser_SolutionRow = 0;
            try {
                insertSolutionRow = solutionDao.addSolution(solution);
                afterInsertSolution = solutionDao.searchSolutionByCode(filepath+"/"+filename);
                user_solution.setSolution(afterInsertSolution);
                insertUser_SolutionRow = user_solutionDao.addUser_Solution(user_solution);
                problemDao.updataProblemProvider(problem);
                userDao.updataUserProvider(updataUser);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,"数据库添加新的解答失败！");

            }
            if (insertSolutionRow==0||insertUser_SolutionRow==0){
                return new Result(false,"添加新的解答未成功！");
            }else {
                return new Result(true,JSON.toJSON(afterInsertSolution).toString());
            }
        }else {
            int updataSolutionRow = 0;
            int UpdataUser_SolutionRow = 0;
            try {
                Solution oldSolution = solutionDao.searchSolutionByCode(filepath+"/"+filename);
                solution.setId(oldSolution.getId());
                HashMap<String,Integer> userAndSolution = new HashMap<String, Integer>();
                userAndSolution.put("user_id",updataUser.getId());
                userAndSolution.put("solution_id",solution.getId());
                User_Solution oldUser_Solution = user_solutionDao.searchUser_SolutionByUserAndSolution(userAndSolution);
                user_solution.setTimes(oldUser_Solution.getTimes()+1);
                user_solution.setId(oldUser_Solution.getId());


//                System.out.println("start updata");

                updataSolutionRow = solutionDao.updataSolutionProvider(solution);
                UpdataUser_SolutionRow = user_solutionDao.updataUser_SolutionProvider(user_solution);

//                System.out.println((boolean)JSON.parseObject(oldSolution.getState()).get("isPass"));
                if (!(boolean)JSON.parseObject(oldSolution.getState()).get("isPass")){
                    problemDao.updataProblemProvider(problem);
                }

                userDao.updataUserProvider(updataUser);

//                System.out.println("updata end");

//                System.out.println("updataSolutionRow: "+updataSolutionRow);
//                System.out.println("UpdataUser_SolutionRow: "+UpdataUser_SolutionRow);

            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,"数据库更新新的解答失败！");

            }
            if (updataSolutionRow==0||UpdataUser_SolutionRow==0){
                return new Result(false,"更新新的解答未成功！");
            }else {
                System.out.println("success commit");
                return new Result(true,JSON.toJSON(solution).toString());
            }
        }

    }

    @Override
    public Result searchUser_SolutionsByPage(int page, int solutionsNum) {
//        System.out.println("in searchUser_SolutionsByPage");
        List<User_Solution> user_solutions = null;
        int startNum = (page-1)*solutionsNum;


        HashMap<String,Integer> pageLimit = new HashMap<>();
        pageLimit.put("startNum",startNum);
        pageLimit.put("solutionsNum",solutionsNum);


        try {
            user_solutions = user_solutionDao.searchUser_SolutionsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (user_solutions!=null){
            return new Result(true, JSON.toJSON(user_solutions).toString());
        }else {
            return new Result(false,"没有更多的解答了");
        }
    }

    @Override
    public Result searchUser_SolutionByGet(String solutionId,String userId) {
        System.out.println("in searchUser_SolutionByGet");
        int id = Integer.parseInt(solutionId);
        User_Solution user_solution = null;

        HashMap<String,Integer> userAndSolution = new HashMap<String, Integer>();
        userAndSolution.put("user_id",Integer.parseInt(userId));
        userAndSolution.put("solution_id",Integer.parseInt(solutionId));
        try {
            user_solution = user_solutionDao.searchUser_SolutionByUserAndSolution(userAndSolution);
            String codePath = user_solution.getSolution().getCode();
            user_solution.getSolution().setCode(tools.readFile(codePath));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (user_solution!=null){
            return new Result(true, JSON.toJSON(user_solution).toString());
        }else {
            return new Result(false,"没有该解答！");
        }
    }

    @Override
    public Result searchSolutionCount() {
        int countNum = 0;
        try {
            countNum = solutionDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (countNum!=0){
            return new Result(true, ""+countNum);
        }else {
            return new Result(false,"solution数据库为空");
        }
    }

    @Override
    public Result searchSolutionByProblem(String problemId, String username,String languageType) {
        List<User_Solution> user_solutions = null;
        User user = null;
        try {
            user = userDao.searchUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            user_solutions = user_solutionDao.searchUser_SolutionsByUser(String.valueOf(user.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }



        if (user_solutions.isEmpty()){
            return new Result(true, "new answer");
        }else {
            for (User_Solution user_solution:user_solutions){
                if (user_solution.getSolution().getProblem().getId() == Integer.parseInt(problemId)
                        &&user_solution.getSolution().getLanguageType().equals(languageType)){
                    String rawCode = tools.readFile(user_solution.getSolution().getCode());
                    String showCode = "";

                    switch (user_solution.getSolution().getLanguageType()){
                        case "java":
                            String lines[] =  rawCode.split("\n",2);
                            
                            showCode = lines[1];
                            break;
                        default:
                            showCode = rawCode;
                            break;


                    }

                    user_solution.getSolution().setCode(showCode);

                    return new Result(true, JSON.toJSON(user_solution).toString());
                }
            }
            return new Result(true, "new answer");
        }

    }
}
