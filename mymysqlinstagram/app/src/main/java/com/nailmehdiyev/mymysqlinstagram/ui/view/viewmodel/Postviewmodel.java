package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Postrepostory;

import java.io.Closeable;
import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Postviewmodel extends ViewModel {

    private Postrepostory postrepostory;

    private MutableLiveData<String>messagelivedata;
    private MutableLiveData<Boolean>statuslivedata;


    @Inject
    public Postviewmodel(Postrepostory postrepostory) {
        this.postrepostory = postrepostory;
        messagelivedata = postrepostory.getpostmessaggerepostory();
        statuslivedata = postrepostory.getPoststatusrepostory();
    }


    public MutableLiveData<String>getmessageviewmodel(){
        return messagelivedata;
    }

    public MutableLiveData<Boolean>getstatusviewmodel(){
        return statuslivedata;
    }


    public void addpost(File file,String postdescription){

        postrepostory.addPost(file,postdescription);
    }



}
