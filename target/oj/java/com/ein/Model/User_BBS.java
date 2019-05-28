package com.ein.Model;

import java.io.Serializable;

public class User_BBS implements Serializable{
    private Integer id;
    private User user;
    private BBS bbs;
    private String msg;
    private String postDate;

    public User_BBS(){
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

    public BBS getBbs() {
        return bbs;
    }

    public void setBbs(BBS bbs) {
        this.bbs = bbs;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
}
