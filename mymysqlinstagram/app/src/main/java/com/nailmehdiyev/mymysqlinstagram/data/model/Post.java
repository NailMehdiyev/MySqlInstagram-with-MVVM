package com.nailmehdiyev.mymysqlinstagram.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {

    @SerializedName("postid")
    private Integer postid;

    @SerializedName("postphoto")
    private String postphoto;

    @SerializedName("postdescription")
    private String postdescription;

    @SerializedName("postowner")
    private User postowner;

    @SerializedName("createdat")
    private String createdat;

    @SerializedName("likers")// getlikersuserslist
    private List<User> likers;

    @SerializedName("comments") //getcommentsuserslist
    private List<Comment> comments;



    public Post() {
    }

    public Post(Integer postid, String postphoto, String postdescription, User postowner, String createdat, List<User> likers, List<Comment> comments) {
        this.postid = postid;
        this.postphoto = postphoto;
        this.postdescription = postdescription;
        this.postowner = postowner;
        this.createdat = createdat;
        this.likers = likers;
        this.comments = comments;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public String getPostphoto() {
        return postphoto;
    }

    public void setPostphoto(String postphoto) {
        this.postphoto = postphoto;
    }

    public String getPostdescription() {
        return postdescription;
    }

    public void setPostdescription(String postdescription) {
        this.postdescription = postdescription;
    }

    public User getPostowner() {
        return postowner;
    }

    public void setPostowner(User postowner) {
        this.postowner = postowner;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }

    public List<User> getLikers() {
        return likers;
    }

    public void setLikers(List<User> likers) {
        this.likers = likers;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}






