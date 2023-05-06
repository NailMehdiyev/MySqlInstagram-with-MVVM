package com.nailmehdiyev.mymysqlinstagram.data.model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("commentid")
    private Integer commentid;

    @SerializedName("commenttext")
    private String commenttext;

    @SerializedName("commentpost")
    private Integer commentpost;

    @SerializedName("commentowner")
    private User commentowner;

    @SerializedName("createdat")
    private String createdat;


    public Comment() {
    }

    public Comment(Integer commentid, String commenttext, Integer commentpost, User commentowner, String createdat) {
        this.commentid = commentid;
        this.commenttext = commenttext;
        this.commentpost = commentpost;
        this.commentowner = commentowner;
        this.createdat = createdat;
    }


    public Integer getCommentid() {
        return commentid;
    }

    public void setCommentid(Integer commentid) {
        this.commentid = commentid;
    }

    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }

    public Integer getCommentpost() {
        return commentpost;
    }

    public void setCommentpost(Integer commentpost) {
        this.commentpost = commentpost;
    }

    public User getCommentowner() {
        return commentowner;
    }

    public void setCommentowner(User commentowner) {
        this.commentowner = commentowner;
    }

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
    }
}

