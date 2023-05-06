package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Notificationsrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Mainviewmodel extends ViewModel {

    private UserRepostory userRepostory;

    private Notificationsrepostory notificationsrepostory;

    private MutableLiveData<User> userlivedata;
    private MutableLiveData<Integer> getunseennotfymesaggelivedata;

    @Inject
    public Mainviewmodel(UserRepostory userRepostory, Notificationsrepostory notificationsrepostory) {
        this.userRepostory = userRepostory;
        this.notificationsrepostory = notificationsrepostory;
        this.userlivedata = userRepostory.getuserrepostory();
        this.getunseennotfymesaggelivedata = notificationsrepostory.unseennotifirepostory();
    }

    public MutableLiveData<User> getuserviewmodel() {

        return userlivedata;
    }


    public MutableLiveData<Integer> getunseennotfymesaggeviewmodel() {

        return getunseennotfymesaggelivedata;
    }


    public void getallnotfycationmessagges(){

        notificationsrepostory.getAllNotifications();
    }

    public void removelastSharedprefereces(){

        userRepostory.removeSharedPreferenceuser();
    }


    public void getallnotification(){

        notificationsrepostory.getAllNotifications();
    }
}
