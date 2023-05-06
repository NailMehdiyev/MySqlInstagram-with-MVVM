package com.nailmehdiyev.mymysqlinstagram.data.Responce;

import com.google.gson.annotations.SerializedName;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;


public class Postresponce {

    @SerializedName("code")
    private Integer code;


    @SerializedName("message")

    private String message;


    @SerializedName("post")

    private Post post;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
