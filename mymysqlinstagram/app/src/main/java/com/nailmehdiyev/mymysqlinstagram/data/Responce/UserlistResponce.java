package com.nailmehdiyev.mymysqlinstagram.data.Responce;

import com.google.gson.annotations.SerializedName;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;


import java.util.List;

public class UserlistResponce {

    @SerializedName("code")
    private Integer code;

    @SerializedName("message")
    private String message;

    @SerializedName("users")
    private List<User> users;



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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
