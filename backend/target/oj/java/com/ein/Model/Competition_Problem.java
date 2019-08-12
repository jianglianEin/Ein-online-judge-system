package com.ein.Model;

import java.io.Serializable;

public class Competition_Problem implements Serializable{
    private Integer id;
    private Problem problem;
    private Competition competition;

    public Competition_Problem(){
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
