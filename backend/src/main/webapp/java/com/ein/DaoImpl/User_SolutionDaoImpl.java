package com.ein.DaoImpl;

import com.ein.Dao.User_SolutionDao;
import com.ein.Model.User_Solution;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Repository(value = "User_SolutionDao")
public class User_SolutionDaoImpl implements User_SolutionDao {
    @Override
    public void save(User_Solution entity) {

    }

    @Override
    public void update(User_Solution entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public User_Solution findById(Serializable id) {
        return null;
    }

    @Override
    public int addUser_Solution(User_Solution user_solution) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            User_SolutionDao user_solutionDao = sqlSession.getMapper(User_SolutionDao.class);
            System.out.println(user_solution.getId());
            insertRow = user_solutionDao.addUser_Solution(user_solution);
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
    public User_Solution searchUser_SolutionByUserAndSolution(HashMap<String, Integer> userAndSolution) throws Exception {
//        System.out.println("in searchUser_SolutionByUserAndSolution");
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        User_Solution user_solution = null;
        try {
            User_SolutionDao user_solutionDao = sqlSession.getMapper(User_SolutionDao.class);
            user_solution = user_solutionDao.searchUser_SolutionByUserAndSolution(userAndSolution);
//            System.out.println("success finish");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_solution;
    }

    @Override
    public int updataUser_SolutionProvider(User_Solution user_solution) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            User_SolutionDao user_solutionDao = sqlSession.getMapper(User_SolutionDao.class);
            updataRow = user_solutionDao.updataUser_SolutionProvider(user_solution);
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
    public List<User_Solution> searchUser_SolutionsLimit(HashMap<String, Integer> pageLimit) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<User_Solution> user_solutionList = null;
        try {
            User_SolutionDao user_solutionDao = sqlSession.getMapper(User_SolutionDao.class);
            user_solutionList = user_solutionDao.searchUser_SolutionsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_solutionList;
    }

    @Override
    public List<User_Solution> searchUser_SolutionsByUser(String user_id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<User_Solution> user_solutionList = null;
        try {
            User_SolutionDao user_solutionDao = sqlSession.getMapper(User_SolutionDao.class);
            user_solutionList = user_solutionDao.searchUser_SolutionsByUser(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_solutionList;
    }

}
