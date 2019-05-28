package com.ein.DaoImpl;

import com.ein.Dao.BBSDao;
import com.ein.Model.BBS;
import com.ein.Model.Competition;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Repository(value = "BBSDao")
public class BBSDaoImpl implements BBSDao {
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
    public BBS findById(Serializable id) {
        return null;
    }

    @Override
    public BBS searchBBSById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        BBS bbs = null;
        try {
            BBSDao bbsDao = sqlSession.getMapper(BBSDao.class);
            bbs = bbsDao.searchBBSById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return bbs;
    }

    @Override
    public int addBBS(BBS bbs) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            BBSDao bbsDao = sqlSession.getMapper(BBSDao.class);
            insertRow = bbsDao.addBBS(bbs);
//            System.out.println(insertRow);
            if (insertRow==1) {
//                System.out.println("test2");
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return insertRow;
    }

    @Override
    public List<BBS> searchBBSLimit(HashMap<String, Integer> pageLimit) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<BBS> bbsList = null;
        try {
            BBSDao competitionDao = sqlSession.getMapper(BBSDao.class);
            bbsList = competitionDao.searchBBSLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return bbsList;
    }

    @Override
    public int deleteBBSById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int deleteRow = 0;
        try {
            BBSDao bbsDao = sqlSession.getMapper(BBSDao.class);
            deleteRow = bbsDao.deleteBBSById(id);
//            System.out.println(insertRow);
            if (deleteRow==1) {
//                System.out.println("test2");
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return deleteRow;
    }

    @Override
    public List<BBS> searchBBSByTopNum(int searchNum) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<BBS> bbsList = null;
        try {
            BBSDao bbsDao = sqlSession.getMapper(BBSDao.class);
            bbsList = bbsDao.searchBBSByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return bbsList;
    }

    @Override
    public int searchCount() throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int countNum = 0;
        try {
            BBSDao bbsDao = sqlSession.getMapper(BBSDao.class);
            countNum = bbsDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return countNum;
    }
}
