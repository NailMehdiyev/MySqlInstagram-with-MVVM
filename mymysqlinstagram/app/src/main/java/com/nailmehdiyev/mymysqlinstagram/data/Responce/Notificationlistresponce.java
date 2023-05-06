package com.nailmehdiyev.mymysqlinstagram.data.Responce;

import com.google.gson.annotations.SerializedName;
import com.nailmehdiyev.mymysqlinstagram.data.model.Notification;


import java.util.List;

public class Notificationlistresponce {

    @SerializedName("code")
    private Integer code;

    @SerializedName("message")

    private String message;

    @SerializedName("unseennotificationscount")

    private Integer unseennotificationscount;

    @SerializedName("notifications")

    private List<Notification>notifications;


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

    public Integer getUnseennotificationscount() {
        return unseennotificationscount;
    }

    public void setUnseennotificationscount(Integer unseennotificationscount) {
        this.unseennotificationscount = unseennotificationscount;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
