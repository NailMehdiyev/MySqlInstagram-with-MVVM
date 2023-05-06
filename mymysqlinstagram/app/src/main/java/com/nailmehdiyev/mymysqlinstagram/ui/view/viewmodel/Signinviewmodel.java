package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Signinviewmodel extends ViewModel {

    private UserRepostory userRepostory;
    private MutableLiveData<String>mesaggelivedata;
    private MutableLiveData<Boolean>statuslivedata;


    @Inject
    public Signinviewmodel(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;

        mesaggelivedata=userRepostory.getmessagerepostory();
        statuslivedata=userRepostory.getstatusrepostory();

    }


    public MutableLiveData<Boolean>getstatusviewmodel(){

        return  statuslivedata;

    }


    public  MutableLiveData<String>getmesaggeviewmodel(){

        return mesaggelivedata;

    }


    public void signin(String username,String userpassword){

        userRepostory.signin(username,userpassword);
    }


    public void getLastSessionUser(){

        userRepostory.getSharedPreferenceuser();

    }
}
