package com.ein.DaoImpl;

import com.ein.Dao.SolutionDao;
import com.ein.Model.Problem;
import com.ein.Model.Solution;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Repository(value = "SolutionDao")
public class SolutionDaoImpl implements SolutionDao {
    @Override
    public void save(Solution entity) {

    }

    @Override
    public void update(Solution entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public Solution findById(Serializable id) {
        return null;
    }

    @Override
    public int addSolution(Solution solution) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            SolutionDao solutionDao = sqlSession.getMapper(SolutionDao.class);
            insertRow = solutionDao.addSolution(solution);
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
    public List<Solution> searchSolutionsLimit(HashMap<String, Integer> pageLimit) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<Solution> solutions = null;
        try {
            SolutionDao solutionDao = sqlSession.getMapper(SolutionDao.class);
            solutions = solutionDao.searchSolutionsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return solutions;
    }

    @Override
    public Solution searchSolutionById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        Solution solution = null;
        try {
            SolutionDao solutionDao = sqlSession.getMapper(SolutionDao.class);
            solution = solutionDao.searchSolutionById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return solution;
    }

    @Override
    public Solution searchSolutionByCode(String code) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        Solution solution = null;
        try {
            SolutionDao solutionDao = sqlSession.getMapper(SolutionDao.class);
            solution = solutionDao.searchSolutionByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return solution;
    }

    @Override
    public int updataSolutionProvider(Solution solution) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            SolutionDao solutionDao = sqlSession.getMapper(SolutionDao.class);
            updataRow = solutionDao.updataSolutionProvider(solution);
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
    public int searchCount() throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int countNum = 0;
        try {
            SolutionDao solutionDao = sqlSession.getMapper(SolutionDao.class);
            countNum = solutionDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return countNum;
    }
}
