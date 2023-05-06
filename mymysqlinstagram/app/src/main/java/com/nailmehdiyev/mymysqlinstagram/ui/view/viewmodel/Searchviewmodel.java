package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;
import com.nailmehdiyev.mymysqlinstagram.api.ApiInterface;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Searchviewmodel extends ViewModel {

    private UserRepostory userRepostory;

    private MutableLiveData<List<User>> userlistlivedata;

    @Inject
    public Searchviewmodel(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;
        userlistlivedata = userRepostory.getuserlistrepostory();
    }


    public MutableLiveData<List<User>> userlistviewmodel() {

        return userlistlivedata;
    }


    public void follow(int userid) {

        userRepostory.follow(userid);
    }


    public void unfollow(int userid) {

        userRepostory.unfollow(userid);
    }


    public void usernamefilterbyname(String username) {

        userRepostory.usernamefilterbyname(username);
    }

}
