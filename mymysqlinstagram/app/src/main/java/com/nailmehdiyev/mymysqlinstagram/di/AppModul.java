package com.nailmehdiyev.mymysqlinstagram.di;


import android.content.Context;

import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Notificationsrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.Repostory.Postrepostory;
import com.nailmehdiyev.mymysqlinstagram.data.Repostory.UserRepostory;
import com.nailmehdiyev.mymysqlinstagram.api.ApiInterface;
import com.nailmehdiyev.mymysqlinstagram.api.ApiUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
@Module
@InstallIn(SingletonComponent.class)
public class AppModul {

    @Provides
    @Singleton
    public UserRepostory provideuserrepostory(ApiInterface apiInterface,@ApplicationContext Context context) {

        return new UserRepostory(apiInterface, context);
    }


    @Provides
    @Singleton
    public Postrepostory providepostrepostory(ApiInterface apiInterface) {

        return new Postrepostory(apiInterface);

    }

    @Provides
    @Singleton
    public Notificationsrepostory providenotificationrepostory(ApiInterface apiInterface) {

        return new Notificationsrepostory(apiInterface);

    }


    @Provides
    @Singleton
    public ApiInterface provideApiInterface() {

        return ApiUtils.getApiService();

    }


}
