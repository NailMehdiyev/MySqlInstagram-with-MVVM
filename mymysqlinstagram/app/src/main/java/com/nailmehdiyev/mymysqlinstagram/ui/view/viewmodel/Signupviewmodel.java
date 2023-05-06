package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Signupviewmodel extends ViewModel {

    private UserRepostory userRepostory;

    private MutableLiveData<Boolean>statuslivedata;

    private MutableLiveData<String>mesaggelivedata;



    @Inject
    public Signupviewmodel(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;

        statuslivedata=userRepostory.getstatusrepostory();
        mesaggelivedata=userRepostory.getmessagerepostory();

    }


    public MutableLiveData<Boolean>getstatusviewmodel(){

        return statuslivedata;
    }



    public MutableLiveData<String >getmesaggeviewmodel(){

        return mesaggelivedata;
    }


    public void signup(String useremail,String username,String userpassword){

        userRepostory.signup(useremail,username,userpassword);
    }
}
