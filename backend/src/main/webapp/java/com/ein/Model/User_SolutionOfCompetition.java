package com.ein.Model;

public class User_SolutionOfCompetition {
    private Integer id;
    private User user;
    private SolutionOfCompetition solutionOfCompetition;
    private String postDate;
    private Integer times;
    private String state;

    public User_SolutionOfCompetition(){
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public SolutionOfCompetition getSolutionOfCompetition() {
        return solutionOfCompetition;
    }

    public void setSolutionOfCompetition(SolutionOfCompetition solutionOfCompetition) {
        this.solutionOfCompetition = solutionOfCompetition;
    }
}
