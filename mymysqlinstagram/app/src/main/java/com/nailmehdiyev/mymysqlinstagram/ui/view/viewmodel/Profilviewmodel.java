package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Postrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Profilviewmodel extends ViewModel {

    private MutableLiveData<List<Post>>postlistlivedata;

    private MutableLiveData<User>userlivedata;

    private UserRepostory userRepostory;

    private Postrepostory postrepostory;

    @Inject
    public Profilviewmodel(UserRepostory userRepostory,Postrepostory postrepostory) {

        this.postrepostory=postrepostory;
        this.userRepostory=userRepostory;

        userlivedata=userRepostory.getuserrepostory();
        postlistlivedata=postrepostory.getPostlistrepostory();

    }


    public MutableLiveData<User>getuserviewmodel(){

        userlivedata=new MutableLiveData<>();

        return userlivedata;

    }

    public MutableLiveData<List<Post>>getpostviewmodel(){
        postlistlivedata=new MutableLiveData<>();

        return postlistlivedata;

    }



    public void getPostsfilterbyuserid(int userid){

        postrepostory.getPostsfilterbyuserid(userid);
    }


    public void getuserdetailsbyuserid(int userid){

        userRepostory.GetalldetailsbyUserid(userid);

    }


    public void follow(int userid){

        userRepostory.follow(userid);
    }

    public void unfollow(int userid) {
        userRepostory.unfollow(userid);
    }

}
