package com.nailmehdiyev.mymysqlinstagram.ui.view.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Postrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.model.Comment;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;



import java.io.Closeable;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PostDetailsviewmodel extends ViewModel {

    private Postrepostory postrepostory;

    private MutableLiveData<String> message;
    private MutableLiveData<Boolean> status;

    private MutableLiveData<List<Post>> getpostsavelistviewmodel;

    private MutableLiveData<Post> getpostviewmodel;

    @Inject
    public PostDetailsviewmodel(Postrepostory postrepostory) {
        this.postrepostory = postrepostory;
        message = postrepostory.getpostmessaggerepostory();
        status = postrepostory.getPoststatusrepostory();
        getpostsavelistviewmodel = postrepostory.getPostlistrepostory();
        getpostviewmodel = postrepostory.getpostrepostory();
    }


    public MutableLiveData<List<Post>> getpostsavelist() {

        getpostsavelistviewmodel=new MutableLiveData<>();
        return getpostsavelistviewmodel;
    }


    public MutableLiveData<Post> getpost() {

        getpostviewmodel=new MutableLiveData<>();
        return getpostviewmodel;
    }


    public MutableLiveData<String> getMessage() {
        message=new MutableLiveData<>();
        return message;
    }

    public MutableLiveData<Boolean> getStatus() {
        status=new MutableLiveData<>();
        return status;
    }


    public void getPostDetailsById(int postid) {
        postrepostory.getPostsdetailsfilterbypostid(postid);
    }

    public void updatePost(Post post) {
        postrepostory.updatepost(post);
    }

    public void deletePost(int post_id) {
        postrepostory.deletePost(post_id);
    }

    public void like(int post_id) {
        postrepostory.like(post_id);
    }

    public void unlike(int post_id) {
        postrepostory.unlike(post_id);
    }

    public void getSavedPosts() {
        postrepostory.getSavedPosts();
    }

    public void save(int post_id) {
        postrepostory.savepost(post_id);
    }

    public void unsave(int post_id) {
        postrepostory.unsave(post_id);
    }

    public void addComment(String comment_text, int comment_post) {
        postrepostory.addComment(comment_text, comment_post);
    }

    public void updateComment(Comment comment) {
        postrepostory.updateComment(comment);
    }

    public void deleteComment(Comment comment) {
        postrepostory.deleteComment(comment);
    }


}
