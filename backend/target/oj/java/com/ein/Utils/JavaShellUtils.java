package com.ein.Utils;

import java.io.*;
import java.util.HashMap;

public class JavaShellUtils {

    public static HashMap<String,String> execute(String command) {
        HashMap<String,String> result = new HashMap<String, String>();
        String errString = "";
        String inputString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            System.out.println("开始转换");
            System.out.println(command);
            pro = runTime.exec(command);


            BufferedReader errStream = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(pro.getOutputStream()));
            output.write("1 2");
            output.close();
            String line;
            while ((line = errStream.readLine()) != null) {
                System.out.println("errline: " + line);
                errString = errString + line + "\n";
            }
            result.put("err",errString);

            while ((line = inputStream.readLine()) != null) {
                System.out.println("inputline: " + line);
                inputString = inputString + line + "\n";
            }
            result.put("msg",inputString);

            errStream.close();
            inputStream.close();
//            output.close();
            pro.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static HashMap<String,String> execute(String command,String args) {
        HashMap<String,String> result = new HashMap<String, String>();
        String errString = "";
        String inputString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            System.out.println("开始转换");
            System.out.println(command);
            pro = runTime.exec(command);
            


            BufferedReader errStream = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(pro.getOutputStream()));
            output.write(args);
            output.close();
            String line;
            while ((line = errStream.readLine()) != null) {
                System.out.println("errline: " + line);
                errString = errString + line + "\n";
            }
            result.put("err",errString);

            while ((line = inputStream.readLine()) != null) {
                System.out.println("inputline: " + line);
                inputString = inputString + line;
            }
            result.put("msg",inputString);

            errStream.close();
            inputStream.close();
//            output.close();
            pro.destroy();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

