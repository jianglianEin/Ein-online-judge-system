package com.ein.DaoImpl;


import com.ein.Dao.UserDao;
import com.ein.Model.User;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository(value = "UserDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    public User searchUserByUserName(String username) throws Exception{
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        User user = null;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            user = userDao.searchUserByUserName(username);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user;
    }


    @Override
    public User searchUserByStudentId(String studentId) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        User user = null;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            user = userDao.searchUserByStudentId(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user;
    }

    @Override
    public int updataUserProvider(User user) throws Exception{
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            updataRow = userDao.updataUserProvider(user);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (updataRow==1) {
                sqlSession.commit();
                sqlSession.rollback();
                sqlSession.close();
                return updataRow;

            }
        }
        return 0;
    }

    @Override
    public User searchUserById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        User user = null;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            user = userDao.searchUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user;
    }

    @Override
    public List<User> searchRankByTopNum(int searchNum) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<User> userList = null;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userList = userDao.searchRankByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return userList;
    }

    @Override
    public int searchCount() throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int countNum = 0;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            countNum = userDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return countNum;
    }

    @Override
    public List<User> searchRankLimitOrderByPassNum(HashMap<String, Integer> pageLimit) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<User> userList = null;
        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userList = userDao.searchRankLimitOrderByPassNum(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return userList;
    }
}
