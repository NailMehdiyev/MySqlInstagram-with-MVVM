package com.nailmehdiyev.mymysqlinstagram.data.Repostory;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.ApiResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.UserResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.UserlistResponce;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.local.Sharedpreferen;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepostory {

    private final Context context;

    private final ApiInterface apiInterfacep;

    private MutableLiveData<List<User>> userlistlivedata;  // Search fragment

    private MutableLiveData<User> userlivedata; // for user profile fragment & navigation drawer menu

    private MutableLiveData<String> message;  //toastmessage


    private MutableLiveData<Boolean> status;// proccess status for navigation


    public UserRepostory(ApiInterface apiInterfacep, Context context) {
        this.apiInterfacep = apiInterfacep;
        this.context = context;


        userlistlivedata = new MutableLiveData<>();
        userlivedata = new MutableLiveData<>();
        message = new MutableLiveData<>();
        status = new MutableLiveData<>();
    }


    public MutableLiveData<User> getuserrepostory() {

        userlivedata = new MutableLiveData<>();

        return userlivedata;

    }


    public MutableLiveData<List<User>> getuserlistrepostory() {

        userlistlivedata = new MutableLiveData<>();

        return userlistlivedata;

    }


    public MutableLiveData<Boolean> getstatusrepostory() {

        status = new MutableLiveData<>();
        return status;

    }

    public MutableLiveData<String> getmessagerepostory() {

        message = new MutableLiveData<>();
        return message;

    }

    public void getSharedPreferenceuser() {

       String username= Sharedpreferen.getSharedPreferenceuser(context, "username", "");
        String userpassword= Sharedpreferen.getSharedPreferenceuser(context, "userpassword", "");


        if (!username.isEmpty() && !userpassword.isEmpty()) {

            signin(username, userpassword);
        }

    }


    public void addSharedPreferenceuser(String username, String userpassword) {

        Sharedpreferen.addSharedPreferenceuser(context, "username", username);

        Sharedpreferen.addSharedPreferenceuser(context, "userpassword", userpassword);

    }


    public void removeSharedPreferenceuser(){

        Sharedpreferen.removeSharedPreferenceuser(context,"username");
        Sharedpreferen.removeSharedPreferenceuser(context,"userpassword");

    }



    public void signin(String username, String userpassword) {

        apiInterfacep.signin(username, userpassword).enqueue(new Callback<UserResponce>() {
            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Session.MyActiveUser = response.body().getUser();


                    System.out.println(response.body().getUser().getUserid());

                    message.setValue(response.body().getMessage());

                    status.setValue(true);
                    addSharedPreferenceuser(username, userpassword);

                } else {

                    try {
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        message.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        message.setValue("Something went wrong.");
                    }

                    message.setValue("");

                }


            }

            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {

                message.setValue("Something went wrong");
                message.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });

    }


    public void signup(String useremail, String username, String userpassword) {
        apiInterfacep.signup(useremail, username,userpassword).enqueue(new Callback<UserResponce>() {
            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {


                if (response.isSuccessful() && response.body() != null) {

                    Session.MyActiveUser = response.body().getUser();

                    message.setValue(response.body().getMessage());

                    status.setValue(true);
                    addSharedPreferenceuser(username, userpassword);

                } else {

                    try {
                        assert response.errorBody() != null;
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        message.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        message.setValue("went wrong.");
                    }
                    message.setValue("");

                }

            }

            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {

                message.setValue(" went wrong");
                message.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });

    }


    public void userchangeprofilphoto(File profilphoto) {


        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), profilphoto);

        MultipartBody.Part part = MultipartBody.Part.createFormData("image", profilphoto.getName(), requestBody);


        apiInterfacep.changeProfilephoto(Session.MyActiveUser.getUserid(), part).enqueue(new Callback<UserResponce>() {
            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    message.setValue(response.body().getMessage());

                    status.setValue(false);
                    status.setValue(null);

                    Session.MyActiveUser.setUserphoto(response.body().getUser().getUserphoto());

                    userlivedata.setValue(response.body().getUser());

                } else {
                    try {
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        message.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        message.setValue("Something went wrong.");
                    }

                }
            }

            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {
                message.setValue("Something went wrong.");
                message.setValue("");
                Log.d("log_error", t.getMessage());


            }
        });


    }


    public void removeoldphoto() {//Userphoto=String Imagename

        apiInterfacep.removeimage(Session.MyActiveUser.getUserid(), Session.MyActiveUser.getUserphoto()).enqueue(new Callback<UserResponce>() {
            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    message.setValue(response.body().getMessage());

                    Session.MyActiveUser.setUserphoto(response.body().getUser().getUserphoto());

                    userlivedata.setValue(response.body().getUser());

                    status.setValue(true);
                    status.setValue(null);
                } else {

                    try {
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        message.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        message.setValue("Something went wrong.");
                    }


                }

                message.setValue("");
            }


            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {

                message.setValue("Something went wrong.");
                message.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });


    }


    public void userupdate(User newuser) {

        apiInterfacep.updateuser(newuser).enqueue(new Callback<UserResponce>() {
            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {

                if (response.isSuccessful() && response.body() != null) {


                    message.setValue(response.body().getMessage());


                    if (!Session.MyActiveUser.getUserpassword().equals(response.body().getUser().getUserpassword())) {///eger halhazirda deyiseceyimiz password kohne passworddan ferqlidirse gelsin kohnenii silsin

                        removeSharedPreferenceuser();// // remove user data from shared preferences if user password has changed


                    }


                    Session.MyActiveUser = response.body().getUser();


                } else {

                    try {
                        JSONObject errorResponse = new JSONObject(response.errorBody().string());
                        message.setValue(errorResponse.getString("message"));
                    } catch (Exception e) {
                        message.setValue("Something went wrong.");
                    }
                    message.setValue("");


                }


            }

            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {


                message.setValue("Something went wrong.");
                message.setValue("");
                Log.d("log_error", t.getMessage());

            }
        });


    }


    public void usernamefilterbyname(String username) {


        apiInterfacep.usernamefilterbyname(username).enqueue(new Callback<UserlistResponce>() {
            @Override
            public void onResponse(Call<UserlistResponce> call, Response<UserlistResponce> response) {

                if (response.isSuccessful() && response.body() != null) {

                    userlistlivedata.setValue(response.body().getUsers());
                } else
                    userlistlivedata.setValue(new ArrayList<>());
            }

            @Override
            public void onFailure(Call<UserlistResponce> call, Throwable t) {

                message.setValue("Something went wrong.");
                message.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });


    }


    public void GetalldetailsbyUserid(int userid) {
        apiInterfacep.alluserdetailsbyuserId(userid).enqueue(new Callback<UserResponce>() {
            @Override
            public void onResponse(Call<UserResponce> call, Response<UserResponce> response) {

                if (response.isSuccessful() && response != null) {
                    userlivedata.setValue(response.body().getUser());

                }

            }

            @Override
            public void onFailure(Call<UserResponce> call, Throwable t) {

                message.setValue("Something went wrong.");
                message.setValue("");
                Log.d("log_error", t.getMessage());
            }
        });


    }


    public void follow(int userid) {
        apiInterfacep.followbutton(Session.MyActiveUser.getUserid(), userid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // follow
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d("log_error", t.getMessage());
            }
        });
    }

    public void unfollow(int userid) {
        apiInterfacep.unfollowbutton(Session.MyActiveUser.getUserid(), userid).enqueue(new Callback<ApiResponce>() {
            @Override
            public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // unfollow
                }
            }

            @Override
            public void onFailure(Call<ApiResponce> call, Throwable t) {
                Log.d("log_error", t.getMessage());
            }
        });
    }


}
