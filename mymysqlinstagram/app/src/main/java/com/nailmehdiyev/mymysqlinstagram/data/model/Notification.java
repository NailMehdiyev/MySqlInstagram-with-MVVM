package com.nailmehdiyev.mymysqlinstagram.data.model;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("notificationid")
    private Integer notificationid;

    @SerializedName("notificationtext")
    private String notificationtext;

    @SerializedName("receivedat")
    private String receivedat;

    @SerializedName("isseen")
    private Integer isseen;

    @SerializedName("post")
    private Post post;

    @SerializedName("user")
    private User user;

    public Notification() {
    }

    public Notification(Integer notificationid, String notificationtext, String receivedat, Integer isseen, Post post, User user) {
        this.notificationid = notificationid;
        this.notificationtext = notificationtext;
        this.receivedat = receivedat;
        this.isseen = isseen;
        this.post = post;
        this.user = user;
    }


    public Integer getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(Integer notificationid) {
        this.notificationid = notificationid;
    }

    public String getNotificationtext() {
        return notificationtext;
    }

    public void setNotificationtext(String notificationtext) {
        this.notificationtext = notificationtext;
    }

    public String getReceivedat() {
        return receivedat;
    }

    public void setReceivedat(String receivedat) {
        this.receivedat = receivedat;
    }

    public Integer getIsseen() {
        return isseen;
    }

    public void setIsseen(Integer isseen) {
        this.isseen = isseen;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}





