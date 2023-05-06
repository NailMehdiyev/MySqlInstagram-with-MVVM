package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Postrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class Homeviewmodel extends ViewModel {

   private Postrepostory postrepostory;

   private MutableLiveData<List<Post>>postlistlivedata,postsavelivedata;


    private MutableLiveData<String> message;


    @Inject
    public Homeviewmodel(Postrepostory postrepostory) {

        this.postrepostory=postrepostory;
        postlistlivedata=postrepostory.getPostlistrepostory();
        postsavelivedata=postrepostory.getPostlistsaverepostory() ;

        message = postrepostory.getpostmessaggerepostory();

    }


    public MutableLiveData<List<Post>> getPostlistviewmodel() {
        return postlistlivedata;
    }

    public MutableLiveData<List<Post>> getPostsaveviewmodel() {
        return postsavelivedata;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void getPostsfilterbyfollowingid (){

        postrepostory.getPostsfilterbyfollowingid();

    }


    public void  save(int postid){

        postrepostory.savepost(postid);

    }



    public void unsave(int postid){

        postrepostory.unsave(postid);
    }



    public void like(int postid){

        postrepostory.like(postid);

    }


    public void unlike(int postid){

        postrepostory.like(postid);

    }

    public void getSavedPosts(){

        postrepostory.getSavedPosts();

    }



    public void updatepost(Post post){

        postrepostory.updatepost(post);

    }




    public void deletepost(int postid){

        postrepostory.deletePost(postid);

    }

}
