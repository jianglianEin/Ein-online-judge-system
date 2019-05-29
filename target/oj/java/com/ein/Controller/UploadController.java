package com.ein.Controller;

import com.alibaba.fastjson.JSON;
import com.ein.Utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "/user_icon",produces="text/html;charset=UTF-8")
    public String user_icon(@RequestParam("usericon") MultipartFile usericon, HttpServletRequest request){
        String path = cheakAndReturnFilePath(request);
        String fileName = cheakAndReturnFileName(usericon);
        File targetFile = new File(path, fileName);
        String result = createFileAndReturnResult(usericon,targetFile);
        return result;
    }

    private String cheakAndReturnFilePath(HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("img/user_icon");
        File filePath = new File(path);
        System.out.println("文件保存的路径："+path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录：" + filePath);
            filePath.mkdir();
        }
        return path;
    }

    private String cheakAndReturnFileName(MultipartFile usericon){
        //获取原始文件名称(包含格式)
        String originalFileName = usericon.getOriginalFilename();
        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        return fileName;
    }

    private String createFileAndReturnResult(MultipartFile usericon, File targetFile ){
        //在指定路径下创建一个文件
        try {
            usericon.transferTo(targetFile);
            return JSON.toJSON(new Result(true,"上传成功")).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return JSON.toJSON(new Result(false,"上传失败" )).toString();
        }
    }
}
