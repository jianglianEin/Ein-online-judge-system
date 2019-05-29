package com.ein.test;

import com.ein.Model.BBS;
import com.ein.Model.User;
import com.ein.Utils.Tools;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

public class Tools_test {
    @Test
    public  void fillBeanTest(){
        User user = new User();
        user.setUsername("EIN");
        ConcurrentHashMap<String,Object> valueMap = new ConcurrentHashMap<>();
        valueMap.put("id",1);
        valueMap.put("Lz",user);
        Tools tools = new Tools();
        BBS bbs = tools.fillBean(valueMap,BBS.class);

        System.out.println(bbs.toString());

    }
}
