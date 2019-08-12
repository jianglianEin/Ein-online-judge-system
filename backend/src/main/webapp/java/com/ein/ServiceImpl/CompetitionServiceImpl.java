package com.ein.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ein.Dao.*;
import com.ein.DaoImpl.*;
import com.ein.Model.*;
import com.ein.Service.CompetitionService;
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
@Service("CompetitionService")
public class CompetitionServiceImpl implements CompetitionService {

    @Resource(name = "SolutionOfCompetitionDao")
    private SolutionOfCompetitionDao solutionOfCompetitionDao;

    @Resource(name = "CompetitionDao")
    private CompetitionDao competitionDao;

    @Resource(name = "Competition_ProblemDao")
    private Competition_ProblemDao competition_problemDao;

    @Resource(name = "ProblemDao")
    private ProblemDao problemDao;

    @Resource(name = "UserDao")
    private UserDao userDao;

    @Resource(name = "User_SolutionOfCompetitionDao")
    private User_SolutionOfCompetitionDao user_solutionOfCompetitionDao;

    @Resource(name = "Tools")
    private Tools tools;




    @Override
    public Result save(Competition entity) {
        int insertRow = 0;
        try {
            insertRow = competitionDao.addCompetition(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (insertRow==0){
            return new Result(false,"添加比赛失败");
        }else {
            return new Result(true,"添加比赛成功");
        }
    }

    @Override
    public Result update(Competition entity) {
        int updataRow = 0;
        if (entity!=null){
            try {
                updataRow = competitionDao.updataCompetitionProvider(entity);
            } catch (Exception e) {
                return new Result(false,e.toString());
            }
        }
        if (updataRow!=0){
            return new Result(false,"更新比赛信息失败");
        }else {
            return new Result(true,""+updataRow);
        }
    }

    @Override
    public Result deleteById(Integer id) {
return null;
    }

    @Override
    public Result getById(Integer id) {
        Competition competition = null;
        try {
            competition = competitionDao.searchCompetitionById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (competition!=null){
            return new Result(true, JSON.toJSON(competition).toString());
        }else {
            return new Result(false,"没有该比赛！");
        }
    }

    @Override
    public Result searchByPage(Integer page, Integer pageNum) {
        List<Competition> competitions = null;
        int startNum = (page-1)*pageNum;
        HashMap<String,Integer> pageLimit = new HashMap<>();
        pageLimit.put("startNum",startNum);
        pageLimit.put("competitionsNum",pageNum);
        try {
            competitions = competitionDao.searchCompetitionsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (competitions!=null){
            return new Result(true, JSON.toJSON(competitions).toString());
        }else {
            return new Result(false,"没有更多的比赛了");
        }
    }

    @Override
    public Result searchResentEntity(Integer searchNum) {
        List<Competition> competitions = null;
        try {
            competitions = competitionDao.searchCompetitionByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }
        if (competitions!=null){
            return new Result(true, JSON.toJSON(competitions).toString());
        }else {
            return new Result(false,"没有比赛！");
        }
    }

    @Override
    public Result searchCount() {
        int countNum = 0;
        try {
            countNum = competitionDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (countNum!=0){
            return new Result(true, ""+countNum);
        }else {
            return new Result(false,"competition数据库为空");
        }
    }

    @Override
    public Result searchCompetition_ProblemsByCompetitionId(int competitionId) {
        List<Competition_Problem> competition_problems = null;
        try {
            competition_problems = competition_problemDao.searchCompetition_ProblemsByCompetitionId(competitionId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }
        if (competition_problems!=null){
            return new Result(true, JSON.toJSON(competition_problems).toString());
        }else {
            return new Result(false,"没有该比赛题目！");
        }
    }

    @Override
    public Result searchUser_SolutionOfCompetitionByGet(String competitionId, String userId) {
        return  null;
    }

    @Override
    public Result searchSolutionOfCompetitionByUsername(int competitionId,int problemId, String username,String languageType) {
        List<User_SolutionOfCompetition> user_solutionOfCompetitions = null;
        try {
            user_solutionOfCompetitions = getAllSolutionOfCompetitionsByUsername(username);
        } catch (Exception e) {
            return new Result(false, "没有该用户名的用户");
        }

        if (user_solutionOfCompetitions == null){
            return new Result(true, "new answer");
        }else {
            for (User_SolutionOfCompetition user_solutionOfCompetition : user_solutionOfCompetitions){
                if (cheakIsAnswerBefore(user_solutionOfCompetition,problemId,competitionId,languageType)){
                    Result result = setCodeFromUser_SolutionOfCompetition(user_solutionOfCompetition);
                    return result;
                }
            }
            return new Result(true, "new answer");
        }
    }

    private Result setCodeFromUser_SolutionOfCompetition(User_SolutionOfCompetition user_solutionOfCompetition){
        String rawCode = tools.readFile(user_solutionOfCompetition.getSolutionOfCompetition().getCode());
        String showCode = "";
        switch (user_solutionOfCompetition.getSolutionOfCompetition().getLanguageType()){
            case "java":
                String lines[] =  rawCode.split("\n",2);
                showCode = lines[1];
                break;
            default:
                showCode = rawCode;
                break;
        }
        user_solutionOfCompetition.getSolutionOfCompetition().setCode(showCode);
        return new Result(true, JSON.toJSON(user_solutionOfCompetition).toString());
    }

    private List<User_SolutionOfCompetition> getAllSolutionOfCompetitionsByUsername(String username) throws Exception {
        User user = null;
        user = userDao.searchUserByUserName(username);
        try {
            List<User_SolutionOfCompetition> user_solutionOfCompetitions = user_solutionOfCompetitionDao.searchUser_SolutionsOfCompetitionByUser(user.getId());
            return user_solutionOfCompetitions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean cheakIsAnswerBefore(User_SolutionOfCompetition user_solutionOfCompetition ,int problemId, int competitionId, String languageType){
        return  user_solutionOfCompetition.getSolutionOfCompetition().getProblem().getId() == problemId
                &&user_solutionOfCompetition.getSolutionOfCompetition().getLanguageType().equals(languageType)
                &&user_solutionOfCompetition.getSolutionOfCompetition().getCompetition().getId()==competitionId;
    }

    @Override
    public Result commitByPost(SolutionOfCompetition solutionOfCompetition,
                               String username,
                               Competition competition,
                               Problem problem,
                               String codeRootPath,
                               String questionRootPath) {

        boolean isFirstCommit = false;

        String systemPath = "/home/ein/IdeaProjects/oj/target/oj";

        String packagePath = "language" +"/"+ solutionOfCompetition.getLanguageType() +"/"+ username;

        String filename = "method" + problem.getId()+"."+solutionOfCompetition.getLanguageType();
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

        switch (solutionOfCompetition.getLanguageType()){
            case "java":
                String solutionPackage = "language" +"."+ solutionOfCompetition.getLanguageType() +"."+ username;
//                String solutionPackage =solution.getLanguageType() +"."+ username;
                try {

                    ps = new PrintStream(new FileOutputStream(file));
                    ps.println("package "+solutionPackage+";");// 往文件里写入字符串


                    ps.print(solutionOfCompetition.getCode());// 在已有的基础上添加字符串

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return new Result(false,"写入java文件失败！");
                }
//                String javaCmd1 = "javac "+filepath+"/"+filename+" -d . ";
                String javaCmd1 = "javac "+filepath+"/"+filename+" -d "+systemPath+"/CompetitionBuild";
//                String javaCmd2 = "java "+solutionPackage+"."+"method" + problem.getId();
                String javaCmd2 = "java -cp "+systemPath+"/CompetitionBuild"+" "+solutionPackage+"."+"method" + problem.getId();
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
                    ps.println(solutionOfCompetition.getCode());// 在已有的基础上添加字符串
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
        solutionOfCompetition.setState(JSON.toJSON(testResult).toString());

        solutionOfCompetition.setCode(filepath+"/"+filename);



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
        User_SolutionOfCompetition user_solutionOfCompetition = new User_SolutionOfCompetition();
        user_solutionOfCompetition.setUser(updataUser);
        user_solutionOfCompetition.setSolutionOfCompetition(solutionOfCompetition);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        user_solutionOfCompetition.setPostDate(df.format(new Date()));

        user_solutionOfCompetition.setState(JSON.toJSON(testResult).toString());

        if (isFirstCommit){
            user_solutionOfCompetition.setTimes(1);
            SolutionOfCompetition afterInsertSolutionOfCompetition = null;

            int insertSolutionOfCompetitionRow = 0;
            int insertUser_SolutionOfCompetitionRow = 0;
            try {
                insertSolutionOfCompetitionRow = solutionOfCompetitionDao.addSolutionOfCompetition(solutionOfCompetition);
                afterInsertSolutionOfCompetition = solutionOfCompetitionDao.searchSolutionOfCompetitionByCode(filepath+"/"+filename);
                user_solutionOfCompetition.setSolutionOfCompetition(afterInsertSolutionOfCompetition);
                insertUser_SolutionOfCompetitionRow = user_solutionOfCompetitionDao.addUser_SolutionOfCompetition(user_solutionOfCompetition);
                problemDao.updataProblemProvider(problem);
                userDao.updataUserProvider(updataUser);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false,"数据库添加新的解答失败！");

            }
            if (insertSolutionOfCompetitionRow==0||insertUser_SolutionOfCompetitionRow==0){
                return new Result(false,"添加新的解答未成功！");
            }else {
                return new Result(true,JSON.toJSON(afterInsertSolutionOfCompetition).toString());
            }
        }else {
            int updataSolutionOfCompetitionRow = 0;
            int UpdataUser_SolutionOfCompetitionRow = 0;
            try {
                SolutionOfCompetition oldSolutionOfCompetition = solutionOfCompetitionDao.searchSolutionOfCompetitionByCode(filepath+"/"+filename);
                solutionOfCompetition.setId(oldSolutionOfCompetition.getId());
                HashMap<String,Integer> userAndSolution = new HashMap<String, Integer>();
                userAndSolution.put("user_id",updataUser.getId());
                userAndSolution.put("solutionOfCompetition_id",solutionOfCompetition.getId());
                User_SolutionOfCompetition oldUser_SolutionOfCompetition = user_solutionOfCompetitionDao.searchUser_SolutionOfCompetitionByUserAndSolution(userAndSolution);
                user_solutionOfCompetition.setTimes(oldUser_SolutionOfCompetition.getTimes()+1);
                user_solutionOfCompetition.setId(oldUser_SolutionOfCompetition.getId());


//                System.out.println("start updata");

                updataSolutionOfCompetitionRow = solutionOfCompetitionDao.updataSolutionOfCompetitionProvider(solutionOfCompetition);
                UpdataUser_SolutionOfCompetitionRow = user_solutionOfCompetitionDao.updataUser_SolutionOfCompetitionProvider(user_solutionOfCompetition);

//                System.out.println((boolean)JSON.parseObject(oldSolution.getState()).get("isPass"));
                if (!(boolean)JSON.parseObject(oldSolutionOfCompetition.getState()).get("isPass")){
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
            if (updataSolutionOfCompetitionRow==0||UpdataUser_SolutionOfCompetitionRow==0){
                return new Result(false,"更新新的解答未成功！");
            }else {
                System.out.println("success commit");
                return new Result(true,JSON.toJSON(solutionOfCompetition).toString());
            }
        }
    }

    @Override
    public Result searchUser_SolutionOfCompetitionById(int user_solutionOfCompetition_id) {
        User_SolutionOfCompetition user_solutionOfCompetition = null;
        try {
            user_solutionOfCompetition = user_solutionOfCompetitionDao.searchUser_SolutionsOfCompetitionById(user_solutionOfCompetition_id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }
        if (user_solutionOfCompetition!=null){
            setCode(user_solutionOfCompetition);
            return new Result(true, JSON.toJSON(user_solutionOfCompetition).toString());
        }else {
            return new Result(false,"没有该比赛解答！");
        }
    }

    private void setCode(User_SolutionOfCompetition user_solutionOfCompetition){
        String code = tools.readFile(user_solutionOfCompetition.getSolutionOfCompetition().getCode());
        user_solutionOfCompetition.getSolutionOfCompetition().setCode(code);
    }

}
