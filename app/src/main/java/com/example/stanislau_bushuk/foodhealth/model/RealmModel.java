package com.example.stanislau_bushuk.foodhealth.model;

import com.example.stanislau_bushuk.foodhealth.model.pojo.Recipe;
import com.example.stanislau_bushuk.foodhealth.presentantion.favoritePresentation.CallBackRealmData;
import com.example.stanislau_bushuk.foodhealth.presentantion.favoritePresentation.FavoritePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class RealmModel {

    private Realm realm;
    private CallBackRealmData callBackRealmData;

    public RealmModel() {
        realm = Realm.getDefaultInstance();
    }

    public void setCallBack(final FavoritePresenter favoritePresenter) {
        callBackRealmData = favoritePresenter;
    }

    public void addDataToRealm(final Recipe recipe) {
        recipe.setChecked(true);
        realm.beginTransaction();
        realm.insertOrUpdate(recipe);
        realm.commitTransaction();
        Timber.e(String.valueOf(recipe.isChecked()));
    }

    public void removeDataToRealm(final Recipe recipe) {
        realm.beginTransaction();
        recipe.setChecked(false);
        realm.insertOrUpdate(recipe);
        realm.commitTransaction();
        Timber.e(String.valueOf(recipe.isChecked()));
    }


    public void getData() {
        final RealmResults<Recipe> recipes = realm.where(Recipe.class).equalTo("isChecked", true).findAll();

        if (recipes != null) {
            Timber.e("getData");
            final Observable<RealmResults<Recipe>> observable = recipes.asFlowable().toObservable();
            callBackRealmData.getDataRealm(observable.subscribeOn(AndroidSchedulers.mainThread()));
        } else {
            Timber.e("getData null");
        }
    }

}
