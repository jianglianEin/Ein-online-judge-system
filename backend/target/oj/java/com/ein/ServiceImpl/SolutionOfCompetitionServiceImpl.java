package com.ein.ServiceImpl;

import com.ein.Model.SolutionOfCompetition;
import com.ein.Service.SolutionOfCompetitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
@Service("SolutionOfCompetitionService")
public class SolutionOfCompetitionServiceImpl implements SolutionOfCompetitionService {
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
    public SolutionOfCompetition getById(Serializable id) {
        return null;
    }
}
