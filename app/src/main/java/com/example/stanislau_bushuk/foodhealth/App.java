package com.example.stanislau_bushuk.foodhealth;

import android.app.Application;

import com.example.stanislau_bushuk.foodhealth.Component.AppComponent;
import com.example.stanislau_bushuk.foodhealth.Component.DaggerAppComponent;
import com.example.stanislau_bushuk.foodhealth.Modul.Api;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        setRealm();
        appComponent=buildComponent();
    }

    public void setRealm() {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("realm.realm")
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
    public AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .api(new Api())
                .build();
    }
}
