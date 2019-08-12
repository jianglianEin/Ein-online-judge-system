package com.ein.DaoImpl;

import com.ein.Dao.CompetitionDao;
import com.ein.Dao.ProblemDao;
import com.ein.Model.Competition;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Repository(value = "CompetitionDao")
public class CompetitionDaoImpl implements CompetitionDao {
    @Override
    public void save(Competition entity) {

    }

    @Override
    public void update(Competition entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public Competition findById(Serializable id) {
        return null;
    }

    @Override
    public Competition searchCompetitionById(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        Competition competition = null;
        try {
            CompetitionDao competitionDao = sqlSession.getMapper(CompetitionDao.class);
            competition = competitionDao.searchCompetitionById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return competition;
    }

    @Override
    public int addCompetition(Competition competition) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();
//        System.out.println("test");

        int insertRow = 0;
        try {
            CompetitionDao competitionDao = sqlSession.getMapper(CompetitionDao.class);
            insertRow = competitionDao.addCompetition(competition);
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
    public List<Competition> searchCompetitionsLimit(HashMap<String, Integer> pageLimit) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<Competition> competitions = null;
        try {
            CompetitionDao competitionDao = sqlSession.getMapper(CompetitionDao.class);
            competitions = competitionDao.searchCompetitionsLimit(pageLimit);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return competitions;
    }

    @Override
    public int updataCompetitionProvider(Competition competition) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int updataRow = 0;
        try {
            CompetitionDao competitionDao = sqlSession.getMapper(CompetitionDao.class);
            updataRow = competitionDao.updataCompetitionProvider(competition);
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
    public List<Competition> searchCompetitionByTopNum(int searchNum) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<Competition> competitions = null;
        try {
            CompetitionDao competitionDao = sqlSession.getMapper(CompetitionDao.class);
            competitions = competitionDao.searchCompetitionByTopNum(searchNum);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return competitions;
    }

    @Override
    public int searchCount() throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        int countNum = 0;
        try {
            CompetitionDao competitionDao = sqlSession.getMapper(CompetitionDao.class);
            countNum = competitionDao.searchCount();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return countNum;
    }
}
