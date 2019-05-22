package com.ein.Utils;

import com.ein.Model.Problem;
import com.ein.Model.Solution;

import java.io.*;
import java.util.HashMap;

public class Tools {
    public static String readFile(String pathname) {
        // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        String data = "";
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
//                System.out.println(line);
                data += " "+line+'\n';
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static HashMap<String ,Object>  testJavaData(Problem problem,String[] cmds,String questionRootPath) throws FileNotFoundException {
        HashMap<String,String> result = null;

        String testRootPath = questionRootPath;

        String inputDataRootPath = testRootPath+problem.getId()+"/input";
        File inputFile = new File(inputDataRootPath);
        String[] inputFilenames = inputFile.list();

        String outputDataRootPath = testRootPath+problem.getId()+"/output";
        File outputFile = new File(outputDataRootPath);
        String[] outputFilenames = outputFile.list();

        int testdataNum = inputFilenames.length;
        int passdataNum = 0;
        JavaShellUtils.execute(cmds[0]);
        for (int i = 0; i < testdataNum;i++){
            System.out.println(inputFilenames[i]);
            System.out.println(outputFilenames[i]);

            FileInputStream inputdata = new FileInputStream(inputDataRootPath+"/input"+(i+1)+".txt");//从a.txt中读出
            BufferedReader inputdataReader = new BufferedReader(new InputStreamReader(inputdata));
            String dataline = "";
            String data = "";

            FileInputStream outputdata = new FileInputStream(outputDataRootPath+"/output"+(i+1)+".txt");//从a.txt中读出
            BufferedReader outputdataReader = new BufferedReader(new InputStreamReader(outputdata));
            String answerline = "";
            String answer = "";


            try {
                while ((dataline = inputdataReader.readLine()) != null) {
                    data += dataline;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while ((answerline = outputdataReader.readLine()) != null) {
                    answer += answerline;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            result =  JavaShellUtils.execute(cmds[1],data);

            String countAnswer = result.get("msg");



            System.out.println("answer: "+answer);
            System.out.println("countAnswer: "+countAnswer);


            if (answer.equals(countAnswer)){
                System.out.println("pass");
                passdataNum++;

            }else {
                System.out.println("not pass");
                passdataNum++;
                break;
            }
        }

        HashMap<String ,Object> testResult =new HashMap<String, Object>();
        if (result.get("err").isEmpty()){
            if (passdataNum/testdataNum == 1){
                testResult.put("isPass",true);
                testResult.put("faildNum",-1);
                testResult.put("state","Accept");
            }else {
                testResult.put("isPass",false);
                testResult.put("faildNum",passdataNum);
                testResult.put("state","Wrong Answer");
            }
        }else {
            testResult.put("isPass",false);
            testResult.put("faildNum",-2);
            testResult.put("state",result.get("err"));
        }


        return testResult;
    }


    public static HashMap<String ,Object>  testCppData(Problem problem,String[] cmds,String questionRootPath) throws FileNotFoundException {
        HashMap<String, String> result = null;

        String testRootPath = questionRootPath;

        String inputDataRootPath = testRootPath + problem.getId() + "/input";
        File inputFile = new File(inputDataRootPath);
        String[] inputFilenames = inputFile.list();

        String outputDataRootPath = testRootPath + problem.getId() + "/output";
        File outputFile = new File(outputDataRootPath);
        String[] outputFilenames = outputFile.list();

        int testdataNum = inputFilenames.length;
        int passdataNum = 0;
        JavaShellUtils.execute(cmds[0]);
        for (int i = 0; i < testdataNum; i++) {
            System.out.println(inputFilenames[i]);
            System.out.println(outputFilenames[i]);

            FileInputStream inputdata = new FileInputStream(inputDataRootPath + "/input" + (i + 1) + ".txt");//从a.txt中读出
            BufferedReader inputdataReader = new BufferedReader(new InputStreamReader(inputdata));
            String dataline = "";
            String data = "";

            FileInputStream outputdata = new FileInputStream(outputDataRootPath + "/output" + (i + 1) + ".txt");//从a.txt中读出
            BufferedReader outputdataReader = new BufferedReader(new InputStreamReader(outputdata));
            String answerline = "";
            String answer = "";


            try {
                while ((dataline = inputdataReader.readLine()) != null) {
                    data += dataline;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while ((answerline = outputdataReader.readLine()) != null) {
                    answer += answerline;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            result = JavaShellUtils.execute(cmds[1], data);

            String countAnswer = result.get("msg");


            System.out.println("answer: " + answer);
            System.out.println("countAnswer: " + countAnswer);


            if (answer.equals(countAnswer)) {
                System.out.println("pass");
                passdataNum++;

            } else {
                System.out.println("not pass");
                passdataNum++;
                break;
            }
        }

        HashMap<String, Object> testResult = new HashMap<String, Object>();
        if (result.get("err").isEmpty()) {
            if (passdataNum / testdataNum == 1) {
                testResult.put("isPass", true);
                testResult.put("faildNum", -1);
                testResult.put("state", "Accept");
            } else {
                testResult.put("isPass", false);
                testResult.put("faildNum", passdataNum);
                testResult.put("state", "Wrong Answer");
            }
        } else {
            testResult.put("isPass", false);
            testResult.put("faildNum", -2);
            testResult.put("state", result.get("err"));
        }
        return testResult;
    }
}
