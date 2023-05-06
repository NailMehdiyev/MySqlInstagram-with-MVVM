package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Notificationsrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.Notification;


import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel

public class Notificationviewmodel extends ViewModel {

    private MutableLiveData<List<Notification>>notificationlistlivedata;

    private MutableLiveData<Integer>unseennotificationlivedata;

    private Notificationsrepostory notificationsrepostory;


    @Inject
    public Notificationviewmodel(Notificationsrepostory notificationsrepostory) {
        this.notificationlistlivedata = notificationsrepostory.getNotificationlistrepostory();
        this.unseennotificationlivedata = notificationsrepostory.unseennotifirepostory();
        this.notificationsrepostory = notificationsrepostory;
    }

    public MutableLiveData<List<Notification>>getnotificationviewmodel(){

        return notificationlistlivedata;

    }


    public MutableLiveData<Integer>getUnseenNotificationCount(){

        return unseennotificationlivedata;

    }



    public void getallnotif(){

        notificationsrepostory.getAllNotifications();


    }



    public void deletenotif(){

        notificationsrepostory.deleteAllNotifications();


    }


    public void markAllNotificationsAsSeen() {
        notificationsrepostory.gorulengelennotification();
    }

}
