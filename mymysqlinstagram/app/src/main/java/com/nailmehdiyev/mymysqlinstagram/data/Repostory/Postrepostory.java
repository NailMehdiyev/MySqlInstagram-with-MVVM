package com.nailmehdiyev.mymysqlinstagram.data.Repostory;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.ApiResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.PostListResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.Postresponce;
import com.nailmehdiyev.mymysqlinstagram.data.model.Comment;
import com.nailmehdiyev.mymysqlinstagram.data.model.Post;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiInterface;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Postrepostory {


    private final ApiInterface apiInterface;

    private MutableLiveData<List<Post>> postlistlivedata, postsSavedlivedata;

    private MutableLiveData<Post> postlivedata;

    private MutableLiveData<String> messagelivedata; // post & comment crud messages -> post details & share post fragment

    private MutableLiveData<Boolean> statuslivedata; // return to back after post deleted, nav to feed after post shared -> post details & share post fragment

    private InputStream is = null;
    private String json = "";

    private JSONObject jbj = null;

    private RequestQueue requestQueue;

    Context context;

    public Postrepostory(ApiInterface apiInterface) {

        postlistlivedata = new MutableLiveData<>();
        postsSavedlivedata = new MutableLiveData<>();
        postlivedata = new MutableLiveData<>();
        statuslivedata = new MutableLiveData<>();
        messagelivedata = new MutableLiveData<>();
        this.apiInterface = apiInterface;

    }


    public MutableLiveData<Post> getpostrepostory() {

        postlivedata = new MutableLiveData<>();
        return postlivedata;
    }


    public MutableLiveData<List<Post>> getPostlistrepostory() {
        postlistlivedata = new MutableLiveData<>();
        return postlistlivedata;
    }


    public MutableLiveData<List<Post>> getPostlistsaverepostory() {
        postsSavedlivedata = new MutableLiveData<>();
        return postsSavedlivedata;
    }


    public MutableLiveData<String> getpostmessaggerepostory() {

        messagelivedata = new MutableLiveData<>();
        return messagelivedata;
    }


    public MutableLiveData<Boolean> getPoststatusrepostory() {
        statuslivedata = new MutableLiveData<>();
        return statuslivedata;
    }


    public void getPostsfilterbyfollowingid() {

        apiInterface.getPostsfilterbyfollowingid(Session.MyActiveUser.getUserid(), 1).enqueue(new Callback<PostListResponce>() {
            @Override
            public void onResponse(Call<PostListResponce> call, Response<PostListResponce> response) {

                if (response.isSuccessful() && response.body() != null && response.body().getPosts() != null) {

                    postlistlivedata.setValue(response.body().getPosts());

                } else {

                    postlistlivedata.setValue(new ArrayList<>());
                }

            }

            @Override
            public void onFailure(Call<PostListResponce> call, Throwable t) {

                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });


    }


    public void getPostsfilterbyuserid(int userid) {

        apiInterface.getPostsfilterbyuserid(userid, 0).enqueue(new Callback<PostListResponce>() {
            @Override
            public void onResponse(Call<PostListResponce> call, Response<PostListResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    postlistlivedata.setValue(response.body().getPosts());


                } else {

                    postlistlivedata.setValue(new ArrayList<>());
                }

            }

            @Override
            public void onFailure(Call<PostListResponce> call, Throwable t) {

                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });

    }

    public void getPostsdetailsfilterbypostid(int postid) {

        apiInterface.getPostsdetailsfilterbypostid(postid).enqueue(new Callback<Postresponce>() {
            @Override
            public void onResponse(Call<Postresponce> call, Response<Postresponce> response) {

                if (response.isSuccessful() && response.body() != null && response.body().getPost() != null) {

                    postlivedata.setValue(response.body().getPost());

                } else {

                    postlivedata.setValue(new Post());
                }

            }

            @Override
            public void onFailure(Call<Postresponce> call, Throwable t) {

                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });
    }


    public void addPost(File postfile, String postDescription) {


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), postfile);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", postfile.getName(), requestBody);

        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), postDescription);


        apiInterface.addPost(body, Session.MyActiveUser.getUserid(), description).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {

                if (response.isSuccessful() && response.body() != null) {
                      Log.w("errorr",response.body().toString());
                    messagelivedata.setValue(response.body().getMessage());

                    statuslivedata.setValue(true);


                } else {

                    try {
                        Log.w("errorr",response.errorBody().string());
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        messagelivedata.setValue(errorResponse.getString("message"));



                        System.out.println(response.errorBody().string());
                    } catch (Exception e) {
                        Log.w("errorr",e.getMessage());
                        messagelivedata.setValue("Something went wrong.");

                    }

                }

                messagelivedata.setValue("");


            }



            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                messagelivedata.setValue(" went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });


    }


    public void updatepost(Post post) {
        apiInterface.updatepost(post).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    messagelivedata.setValue(response.body().getMessage());

                } else {

                    try {
                        Log.i("log_error", response.errorBody().string());
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        messagelivedata.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        Log.i("log_error", e.getMessage());
                    }


                }

                messagelivedata.setValue("");
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {

                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());


            }
        });

    }


    public void deletePost(int postid) {
        apiInterface.deletepost(postid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    getPostsdetailsfilterbypostid(postid);
                    messagelivedata.setValue(response.body().getMessage());
                    statuslivedata.setValue(true); // return to back if this response is successful


                } else {
                    try {Log.v("sehvler",response.errorBody().string());
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        messagelivedata.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        messagelivedata.setValue("Sometnt wrong.");
                    }
                }

                messagelivedata.setValue("");
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });
    }


    public void like(int postid) {
        apiInterface.likepost(Session.MyActiveUser.getUserid(), postid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // liked
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d("log_error", t.getMessage());
            }
        });
    }


    public void unlike(int postid) {
        apiInterface.unlikepost(Session.MyActiveUser.getUserid(), postid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // unliked
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d("log_error", t.getMessage());
            }
        });
    }


    public void unsave(int postid) {
        apiInterface.unsavepost(Session.MyActiveUser.getUserid(), postid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // unsaved
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d("log_error", t.getMessage());
            }
        });
    }


    public void savepost(int postid) {
        apiInterface.savepost(Session.MyActiveUser.getUserid(), postid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // saved
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d("log_error", t.getMessage());
            }
        });
    }

    public void getSavedPosts() {
        apiInterface.getSavedPosts(Session.MyActiveUser.getUserid()).enqueue(new Callback<PostListResponce>() {
            @Override
            public void onResponse(Call<PostListResponce> call, Response<PostListResponce> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getPosts() != null)
                    postsSavedlivedata.setValue(response.body().getPosts());
                else postsSavedlivedata.setValue(new ArrayList<>());
            }

            @Override
            public void onFailure(Call<PostListResponce> call, Throwable t) {
                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });
    }


    public void addComment(String commenttext, int commentpostid) {

        apiInterface.addcomment(Session.MyActiveUser.getUserid(), commenttext, commentpostid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Log.i("log_error", response.body().getMessage());

                     messagelivedata.setValue(response.body().getMessage());



                    getPostsdetailsfilterbypostid(commentpostid);
                } else {

                    try {Log.i("log_error", response.errorBody().string());
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        messagelivedata.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        Log.i("log_error", e.getMessage());
                    }

                }

                messagelivedata.setValue("");


            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {

                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });

    }

    public void updateComment(Comment comment) {
        apiInterface.updateComment(comment).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messagelivedata.setValue(response.body().getMessage());
                    getPostsdetailsfilterbypostid(comment.getCommentpost());
                } else {
                    try {
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        messagelivedata.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        messagelivedata.setValue("Something went wrong.");
                    }
                }
                messagelivedata.setValue("");
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                messagelivedata.setValue("Something went wrong.");
                messagelivedata.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });
    }


    public void deleteComment(Comment comment) {
        apiInterface.deletecomment(comment.getCommentid()).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messagelivedata.setValue(response.body().getMessage());

                    getPostsdetailsfilterbypostid(comment.getCommentpost());
                } else {
                    try {
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        messagelivedata.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        messagelivedata.setValue("wrong.");
                    }
                }

                messagelivedata.setValue("");
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                messagelivedata.setValue("Something... ");
                Log.d("log_error", t.getMessage());
            }
        });
    }


}