package com.nailmehdiyev.mymysqlinstagram.data.Responce;



import com.google.gson.annotations.SerializedName;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;


import java.util.List;

public class PostListResponce {

    @SerializedName("code")
    private  Integer code;


    @SerializedName("message")
    private String  message;

    @SerializedName("posts")

    private List<Post>posts;


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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
