package com.ein.DaoImpl;

import com.ein.Dao.ProblemDao;
import com.ein.Model.Problem;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Repository(value = "ProblemDao")
public class ProblemDaoImpl implements ProblemDao {
    @Override
    public void save(Problem entity) {

    }

    @Override
    public void update(Problem entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public Problem findById(Serializable id) {
        return null;
    }

    @Override
    public Problem searchProblemById(int problemId) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        Problem problem = null;
        try {
            ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
            problem = problemDao.searchProblemById(problemId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return problem;
    }

    @Override
    public int addProblem(Problem problem) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
            insertRow = problemDao.addProblem(problem);
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
    public List<Problem> searchProblemsLimit(HashMap<String, Integer> pageLimit) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<Problem> problems = null;
        try {
            ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
            problems = problemDao.searchProblemsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return problems;
    }

    @Override
    public int deleteProblemById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int deleteRow = 0;
        try {
            ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
            deleteRow = problemDao.deleteProblemById(id);
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
    public int updataProblemProvider(Problem problem) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
            updataRow = problemDao.updataProblemProvider(problem);
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
            ProblemDao problemDao = sqlSession.getMapper(ProblemDao.class);
            countNum = problemDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return countNum;
    }


}
