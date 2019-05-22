package com.ein.Model;

import java.io.Serializable;

public class BBS implements Serializable{
    private Integer id;
    private User Lz;
    private String discription;
    private String startDate;
    private String title;

    public BBS(){
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getLz() {
        return Lz;
    }

    public void setLz(User lz) {
        Lz = lz;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
