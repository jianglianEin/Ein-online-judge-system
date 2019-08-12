package com.ein.DaoImpl;

import com.ein.Dao.User_CompetitionDao;
import com.ein.Model.User_Competition;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository(value = "User_CompetitionDao")
public class User_CompetitionDaoImpl implements User_CompetitionDao {
    @Override
    public void save(User_Competition entity) {

    }

    @Override
    public void update(User_Competition entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public User_Competition findById(Serializable id) {
        return null;
    }
}
