package com.ein.DaoImpl;

import com.ein.Dao.Competition_ProblemDao;
import com.ein.Model.Competition_Problem;
import com.ein.Utils.sqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository(value = "Competition_ProblemDao")
public class Competition_ProblemDaoImpl implements Competition_ProblemDao {
    @Override
    public void save(Competition_Problem entity) {

    }

    @Override
    public void update(Competition_Problem entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public Competition_Problem findById(Serializable id) {
        return null;
    }

    @Override
    public List<Competition_Problem> searchCompetition_ProblemsByCompetitionId(int id) throws Exception {
        SqlSession sqlSession = sqlSessionFactoryUtil.openSqlSession();

        List<Competition_Problem> competition_problems = null;
        try {
            Competition_ProblemDao competition_problemDao = sqlSession.getMapper(Competition_ProblemDao.class);
            competition_problems = competition_problemDao.searchCompetition_ProblemsByCompetitionId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.rollback();
            sqlSession.close();
        }
        return competition_problems;
    }
}
