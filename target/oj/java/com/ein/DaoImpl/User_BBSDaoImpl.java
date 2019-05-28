package com.ein.DaoImpl;

import com.ein.Dao.User_BBSDao;
import com.ein.Model.User_BBS;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository(value = "User_BBSDao")
public class User_BBSDaoImpl implements User_BBSDao {
    @Override
    public void save(User_BBS entity) {

    }

    @Override
    public void update(User_BBS entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public User_BBS findById(Serializable id) {
        return null;
    }

    @Override
    public int addUser_BBS(User_BBS user_bbs) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            User_BBSDao user_bbsDao = sqlSession.getMapper(User_BBSDao.class);
            insertRow = user_bbsDao.addUser_BBS(user_bbs);
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
    public List<User_BBS> searchUser_BBSByBBSId(int bbsId) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<User_BBS> user_bbsList = null;
        try {
            User_BBSDao user_bbsDao = sqlSession.getMapper(User_BBSDao.class);
            user_bbsList = user_bbsDao.searchUser_BBSByBBSId(bbsId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_bbsList;
    }
}
