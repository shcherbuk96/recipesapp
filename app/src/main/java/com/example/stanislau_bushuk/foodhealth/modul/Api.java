package com.example.stanislau_bushuk.foodhealth.modul;

import com.example.stanislau_bushuk.foodhealth.Constats;
import com.example.stanislau_bushuk.foodhealth.api.IAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class Api {

    @Provides
    @Singleton
    public IAPI getIAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constats.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IAPI iapi = retrofit.create(IAPI.class);
        return iapi;
    }
}
