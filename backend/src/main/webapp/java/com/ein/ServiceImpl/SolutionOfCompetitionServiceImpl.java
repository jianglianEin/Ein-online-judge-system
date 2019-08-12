package com.ein.ServiceImpl;

import com.ein.Model.SolutionOfCompetition;
import com.ein.Service.SolutionOfCompetitionService;
import com.ein.Utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional
@Service("SolutionOfCompetitionService")
public class SolutionOfCompetitionServiceImpl implements SolutionOfCompetitionService {
    @Override
    public Result save(SolutionOfCompetition entity) {
return null;
    }

    @Override
    public Result update(SolutionOfCompetition entity) {
return null;
    }

    @Override
    public Result deleteById(Integer id) {
return null;
    }

    @Override
    public Result getById(Integer id) {
        return null;
    }
}
