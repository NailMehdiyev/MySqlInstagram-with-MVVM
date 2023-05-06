package com.nailmehdiyev.mymysqlinstagram.data.Repostory;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nailmehdiyev.mymysqlinstagram.data.model.Notification;
import com.nailmehdiyev.mymysqlinstagram.data.model.User;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.ApiResponce;
import com.nailmehdiyev.mymysqlinstagram.data.Responce.Notificationlistresponce;
import com.nailmehdiyev.mymysqlinstagram.Session.Session;
import com.nailmehdiyev.mymysqlinstagram.api.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notificationsrepostory {
    private final ApiInterface apiInterface;
    private MutableLiveData<List<Notification>> notificationlistlivedata;
    private  MutableLiveData<Integer> unseennotifilivedata;

    public Notificationsrepostory(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;

        unseennotifilivedata = new MutableLiveData<>();
        notificationlistlivedata = new MutableLiveData<>();

    }


   public MutableLiveData<Integer>unseennotifirepostory(){

        unseennotifilivedata=new MutableLiveData<>();
        return   unseennotifilivedata;
   }


    public MutableLiveData<List<Notification>> getNotificationlistrepostory() {

        notificationlistlivedata=new MutableLiveData<>();
        return notificationlistlivedata;
    }


    public void getAllNotifications() {
          apiInterface.getAllNotifications(Session.MyActiveUser.getUserid()).enqueue(new Callback<Notificationlistresponce>() {
            @Override
            public void onResponse(Call<Notificationlistresponce> call, Response<Notificationlistresponce> response) {

                if (response.isSuccessful() && response.body() != null && response.body().getNotifications() != null) {
                    unseennotifilivedata.setValue(response.body().getUnseennotificationscount());
                    notificationlistlivedata.setValue(response.body().getNotifications());
                } else {
                    unseennotifilivedata.setValue(0);
                    notificationlistlivedata.setValue(new ArrayList<>());
                }

            }

            @Override
            public void onFailure(Call<Notificationlistresponce> call, Throwable t) {

                Log.d("log_error", t.getMessage());
            }
        });

    }

        public void gorulengelennotification() {

            apiInterface.markAllNotificationsAsSeen(Session.MyActiveUser).enqueue(new Callback<ApiResponce>() {
                @Override
                public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        // getAllNotifications();
                    }

                }

                @Override
                public void onFailure(Call<ApiResponce> call, Throwable t) {

                    Log.d("log_error", t.getMessage());
                }
            });


        }



    public void deleteAllNotifications() {

    apiInterface.deleteAllNotifications(Session.MyActiveUser.getUserid()).enqueue(new Callback<ApiResponce>() {
        @Override
        public void onResponse(Call<ApiResponce> call, Response<ApiResponce> response) {

            if (response.isSuccessful() && response.body() != null) {
                getAllNotifications();
            }
        }

        @Override
        public void onFailure(Call<ApiResponce> call, Throwable t) {

            Log.d("log_error", t.getMessage());
        }
    });

    }




}