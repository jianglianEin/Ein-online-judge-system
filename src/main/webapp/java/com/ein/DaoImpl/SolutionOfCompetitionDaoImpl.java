package com.ein.DaoImpl;

import com.ein.Dao.SolutionOfCompetitionDao;
import com.ein.Model.SolutionOfCompetition;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository(value = "SolutionOfCompetitionDao")
public class SolutionOfCompetitionDaoImpl implements SolutionOfCompetitionDao {
    @Override
    public void save(SolutionOfCompetition entity) {

    }

    @Override
    public void update(SolutionOfCompetition entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public SolutionOfCompetition findById(Serializable id) {
        return null;
    }

    @Override
    public List<SolutionOfCompetition> searchSolutionOfCompetitionByCompetitionId(int competitionId) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<SolutionOfCompetition> solutionOfCompetitions = null;
        try {
            SolutionOfCompetitionDao solutionOfCompetitionDao = sqlSession.getMapper(SolutionOfCompetitionDao.class);
            solutionOfCompetitions = solutionOfCompetitionDao.searchSolutionOfCompetitionByCompetitionId(competitionId);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return solutionOfCompetitions;
    }

    @Override
    public SolutionOfCompetition searchSolutionOfCompetitionById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        SolutionOfCompetition solutionOfCompetition = null;
        try {
            SolutionOfCompetitionDao solutionOfCompetitionDao = sqlSession.getMapper(SolutionOfCompetitionDao.class);
            solutionOfCompetition = solutionOfCompetitionDao.searchSolutionOfCompetitionById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return solutionOfCompetition;
    }

    @Override
    public int addSolutionOfCompetition(SolutionOfCompetition solutionOfCompetition) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            SolutionOfCompetitionDao solutionOfCompetitionDao = sqlSession.getMapper(SolutionOfCompetitionDao.class);
            insertRow = solutionOfCompetitionDao.addSolutionOfCompetition(solutionOfCompetition);
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
    public SolutionOfCompetition searchSolutionOfCompetitionByCode(String code) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        SolutionOfCompetition solutionOfCompetition = null;
        try {
            SolutionOfCompetitionDao solutionOfCompetitionDao = sqlSession.getMapper(SolutionOfCompetitionDao.class);
            solutionOfCompetition = solutionOfCompetitionDao.searchSolutionOfCompetitionByCode(code);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return solutionOfCompetition;
    }

    @Override
    public int updataSolutionOfCompetitionProvider(SolutionOfCompetition solutionOfCompetition) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            SolutionOfCompetitionDao solutionOfCompetitionDao = sqlSession.getMapper(SolutionOfCompetitionDao.class);
            updataRow = solutionOfCompetitionDao.updataSolutionOfCompetitionProvider(solutionOfCompetition);
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
}
