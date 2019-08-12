package com.ein.test;


import com.ein.Model.Problem;
import com.ein.Utils.DeleteDirectory;
import com.ein.Utils.JavaShellUtils;
import com.ein.Utils.Tools;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class code_test {
    @Test
    public  void test() throws IOException {
        HashMap<String,String> result = new HashMap<String, String>();

//        JavaShellUtils.execute("javac src/main/webapp/java/com/ein/language/java/HelloWorld.java -d . ");
//        JavaShellUtils.execute("java com.ein.language.java.HelloWorld");
//        DeleteDirectory.deleteDir(new File("language"));

//        JavaShellUtils.execute("g++ -o test src/main/java/language/cpp/test.cpp");
//        JavaShellUtils.execute("./test");
//        DeleteDirectory.deleteDir(new File("test"));

//        String path = "src/main/webapp/code/language/cpp/jianglianZwei";
//        String filename = "method2.cpp";
//
//        File dir = new File(path);
//        if (!dir.exists()){
//            dir.mkdir();
//        }
//        File file=new File(path+"/"+filename);
//        if(!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        PrintStream ps = null;
//        try {
//            ps = new PrintStream(new FileOutputStream(file));
//            ps.println("http://www.jb51.net");// 往文件里写入字符串
//            ps.println("http://www.jb51.net2");// 在已有的基础上添加字符串
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

//


//        String filename = "src/main/webapp/question/1/input/input1.txt";
//        String data = Tools.readFile(filename);
//        String args[] =data.split(" ");
//        for (String arg : args){
//            System.out.println(arg);
//        }
//        Problem problem = new Problem();
//        problem.setId(1);
//
//        Tools.getTestData(problem);

        FileInputStream inputdata = new FileInputStream("src/main/webapp/question/1/input/input1.txt");//从a.txt中读出
        BufferedReader inputdataReader = new BufferedReader(new InputStreamReader(inputdata));
        String dataline = "";
        String data = "";

        FileInputStream outputdata = new FileInputStream("src/main/webapp/question/1/output/output1.txt");//从a.txt中读出
        BufferedReader outputdataReader = new BufferedReader(new InputStreamReader(outputdata));
        String answerline = "";
        String answer = "";


        while ((dataline = inputdataReader.readLine()) != null) {
            data += dataline;
        }
        while ((answerline = outputdataReader.readLine()) != null) {
            answer += answerline;
        }

        System.out.println(data);
        System.out.println(answer);
//        Scanner scanner = new Scanner(System.in);
//        int a = scanner.nextInt();
//        int b = scanner.nextInt();
//        System.out.println(a+b);

        JavaShellUtils.execute("javac src/main/webapp/code/language/java/jianglianEin/method1.java -d . ");
        result = JavaShellUtils.execute("java language.java.jianglianEin.method1",data);
        System.out.println(result.get("msg"));
        System.out.println(result.get("err")=="");
        String countData = result.get("msg");

        if (answer.equals(countData)){
            System.out.println("pass");
        }else {
            System.out.println("not pass");
        }



    }
}
