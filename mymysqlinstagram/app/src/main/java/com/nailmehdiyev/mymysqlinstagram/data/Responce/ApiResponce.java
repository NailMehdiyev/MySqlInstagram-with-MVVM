package com.nailmehdiyev.mymysqlinstagram.data.Responce;

import com.google.gson.annotations.SerializedName;

public class ApiResponce {

    @SerializedName("code")
    private Integer code;


    @SerializedName("message")

    private String message;


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
}
