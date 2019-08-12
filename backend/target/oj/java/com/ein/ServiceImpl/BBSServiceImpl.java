package com.ein.ServiceImpl;

import com.alibaba.fastjson.JSON;
import com.ein.Dao.BBSDao;
import com.ein.Dao.User_BBSDao;
import com.ein.DaoImpl.BBSDaoImpl;
import com.ein.DaoImpl.User_BBSDaoImpl;
import com.ein.Model.BBS;
import com.ein.Model.User_BBS;
import com.ein.Service.BBSService;
import com.ein.Utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Transactional
@Service("BBSService")
public class BBSServiceImpl implements BBSService {
    @Resource(name = "BBSDao")
    private BBSDao bbsDao;

    @Resource(name = "User_BBSDao")
    private User_BBSDao user_bbsDao;

    @Override
    public void save(BBS entity) {

    }

    @Override
    public void update(BBS entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public BBS getById(Serializable id) {
        return null;
    }

    @Override
    public Result searchBBSByGet(String bbsId) {
        int id = Integer.parseInt(bbsId);
        BBS bbs = null;
        try {
            bbs = bbsDao.searchBBSById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (bbs!=null){
            return new Result(true, JSON.toJSON(bbs).toString());
        }else {
            return new Result(false,"没有该讨论！");
        }
    }

    @Override
    public Result addByPost(BBS bbs) {
        int insertRow = 0;
        try {
            insertRow = bbsDao.addBBS(bbs);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (insertRow==0){
            return new Result(false,"添加讨论失败");
        }else {
            return new Result(true,"添加讨论成功");
        }
    }

    @Override
    public Result searchBBSByPage(int page, int bbsNum) {
        List<BBS> bbsList = null;
        int startNum = (page-1)*bbsNum;


        HashMap<String,Integer> pageLimit = new HashMap<>();
        pageLimit.put("startNum",startNum);
        pageLimit.put("bbsNum",bbsNum);


        try {
            bbsList = bbsDao.searchBBSLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (bbsList!=null){
            return new Result(true, JSON.toJSON(bbsList).toString());
        }else {
            return new Result(false,"没有更多的讨论了");
        }
    }

    @Override
    public Result RemoveBBSById(int id) {
        int deleteRow = 0;
        try {
            deleteRow = bbsDao.deleteBBSById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (deleteRow==0){
            return new Result(false,"删除讨论失败");
        }else {
            return new Result(true,"删除讨论成功");
        }
    }

    @Override
    public Result get_resent_notic(int searchNum) {
        List<BBS> bbsList = null;
        try {
            bbsList = bbsDao.searchBBSByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }
        if (bbsList!=null){
            return new Result(true, JSON.toJSON(bbsList).toString());
        }else {
            return new Result(false,"没有讨论！");
        }
    }

    @Override
    public Result searchBBSCount() {
        int countNum = 0;
        try {
            countNum = bbsDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (countNum!=0){
            return new Result(true, ""+countNum);
        }else {
            return new Result(false,"BBS数据库为空");
        }
    }

    @Override
    public Result addReplyByPost(User_BBS user_bbs) {
        int insertRow = 0;
        try {
            insertRow = user_bbsDao.addUser_BBS(user_bbs);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (insertRow==0){
            return new Result(false,"添加回复失败");
        }else {
            return new Result(true,"添加回复成功");
        }
    }

    @Override
    public Result searchReplyByBBSId(int bbsId) {
        System.out.println("in searchReplyByBBSId");
        List<User_BBS> user_bbsList = null;

        try {
            user_bbsList = user_bbsDao.searchUser_BBSByBBSId(bbsId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,e.toString());
        }

        if (user_bbsList!=null){
            return new Result(true, JSON.toJSON(user_bbsList).toString());
        }else {
            return new Result(false,"没有更多的回复了");
        }
    }
}
