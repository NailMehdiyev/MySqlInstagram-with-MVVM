package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;


import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Settingsviewmodel extends ViewModel {

    private UserRepostory userRepostory;
    private MutableLiveData<Boolean> statuslivedata;
    private MutableLiveData<String> messagelivedata;

    @Inject
    public Settingsviewmodel(UserRepostory userRepostory) {
        this.userRepostory = userRepostory;
        statuslivedata = userRepostory.getstatusrepostory();
        messagelivedata = userRepostory.getmessagerepostory();
    }


    public  MutableLiveData<Boolean>getStatusviewvmodel(){

        return statuslivedata;
    }


    public  MutableLiveData<String>getmesaggeviewvmodel(){

        return messagelivedata;
    }


    public void userchangeprofilphoto(File file){

        userRepostory.userchangeprofilphoto(file);

    }


    public void removeoldphoto(){


        userRepostory.removeoldphoto();

    }



    public void userupdate(User newuser){


        userRepostory.userupdate(newuser);
    }
}
