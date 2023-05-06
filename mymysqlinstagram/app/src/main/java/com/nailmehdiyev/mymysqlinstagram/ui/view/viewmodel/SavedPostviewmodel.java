package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Postrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;

import com.nailmehdiyev.mymysqlinstagram.api.ApiInterface;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel

public class SavedPostviewmodel extends ViewModel {


    private MutableLiveData<List<Post>> postsavelivedata;

    private Postrepostory postrepostory;


    private MutableLiveData<String>messagelivedata;


    @Inject
    public SavedPostviewmodel( Postrepostory postrepostory) {
        postsavelivedata = postrepostory.getPostlistsaverepostory();
        this.postrepostory = postrepostory;
        messagelivedata = postrepostory.getpostmessaggerepostory();
    }



    public MutableLiveData<String>getmesaageviewmodel(){

        return messagelivedata;
    }



    public MutableLiveData<List<Post>>getPostsaveviewmodel(){

        return postsavelivedata;

    }




    public void savepost(int postid){

        postrepostory.savepost(postid);

    }



    public void unsavepost(int postid){

        postrepostory.unsave(postid);

    }



    public void deletePost(int postid) {
        postrepostory.deletePost(postid);
    }

    public void like(int postid) {
        postrepostory.like(postid);
    }



    public void unlike(int postid) {
        postrepostory.unlike(postid);
    }

    public void getSavedPosts() {
        postrepostory.getSavedPosts();
    }

    public void updatePost(Post post) {
        postrepostory.updatepost(post);
    }

}
