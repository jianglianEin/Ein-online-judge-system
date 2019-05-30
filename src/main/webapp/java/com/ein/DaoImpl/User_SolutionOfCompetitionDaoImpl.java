package com.ein.DaoImpl;

import com.ein.Dao.User_SolutionOfCompetitionDao;
import com.ein.Model.User_SolutionOfCompetition;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Repository(value = "User_SolutionOfCompetitionDao")
public class User_SolutionOfCompetitionDaoImpl implements User_SolutionOfCompetitionDao {
    @Override
    public void save(User_SolutionOfCompetition entity) {

    }

    @Override
    public void update(User_SolutionOfCompetition entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public User_SolutionOfCompetition findById(Serializable id) {
        return null;
    }

    @Override
    public List<User_SolutionOfCompetition> searchUser_SolutionsOfCompetitionByUser(int user_id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<User_SolutionOfCompetition> user_solutionOfCompetitions = null;
        try {
            User_SolutionOfCompetitionDao user_solutionOfCompetitionDao = sqlSession.getMapper(User_SolutionOfCompetitionDao.class);
            user_solutionOfCompetitions = user_solutionOfCompetitionDao.searchUser_SolutionsOfCompetitionByUser(user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_solutionOfCompetitions;
    }

    @Override
    public int addUser_SolutionOfCompetition(User_SolutionOfCompetition user_solutionOfCompetition) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            User_SolutionOfCompetitionDao user_solutionOfCompetitionDao = sqlSession.getMapper(User_SolutionOfCompetitionDao.class);
            System.out.println(user_solutionOfCompetition.getId());
            insertRow = user_solutionOfCompetitionDao.addUser_SolutionOfCompetition(user_solutionOfCompetition);
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
    public User_SolutionOfCompetition searchUser_SolutionOfCompetitionByUserAndSolution(HashMap<String, Integer> userAndSolution) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        User_SolutionOfCompetition user_solutionOfCompetition = null;
        try {
            User_SolutionOfCompetitionDao user_solutionOfCompetitionDao = sqlSession.getMapper(User_SolutionOfCompetitionDao.class);
            user_solutionOfCompetition = user_solutionOfCompetitionDao.searchUser_SolutionOfCompetitionByUserAndSolution(userAndSolution);
//            System.out.println("success finish");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_solutionOfCompetition;
    }

    @Override
    public int updataUser_SolutionOfCompetitionProvider(User_SolutionOfCompetition user_solutionOfCompetition) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            User_SolutionOfCompetitionDao user_solutionOfCompetitionDao = sqlSession.getMapper(User_SolutionOfCompetitionDao.class);
            updataRow = user_solutionOfCompetitionDao.updataUser_SolutionOfCompetitionProvider(user_solutionOfCompetition);
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
    public User_SolutionOfCompetition searchUser_SolutionsOfCompetitionById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        User_SolutionOfCompetition user_solutionOfCompetition = null;
        try {
            User_SolutionOfCompetitionDao user_solutionOfCompetitionDao = sqlSession.getMapper(User_SolutionOfCompetitionDao.class);
            user_solutionOfCompetition = user_solutionOfCompetitionDao.searchUser_SolutionsOfCompetitionById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return user_solutionOfCompetition;
    }
}
