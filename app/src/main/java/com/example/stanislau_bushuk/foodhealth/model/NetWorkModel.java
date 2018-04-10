package com.example.stanislau_bushuk.foodhealth.model;


import com.example.stanislau_bushuk.foodhealth.App;
import com.example.stanislau_bushuk.foodhealth.Constants;
import com.example.stanislau_bushuk.foodhealth.api.IAPI;
import com.example.stanislau_bushuk.foodhealth.model.pojo.Recipes;
import com.example.stanislau_bushuk.foodhealth.presentantion.searchPresentation.presenters.SearchPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetWorkModel {

    @Inject
    IAPI iapi;

    private CallBackSearchPresenter callBackSearchPresenter;

    public NetWorkModel() {
        App.getAppComponent().inject(this);
    }

    public void setCallBack(final SearchPresenter presenter) {
        callBackSearchPresenter = presenter;

    }

    public void getResponse(final String recipeName, final int from) {
        final Observable<Recipes> observable = iapi.getJson(recipeName, Constants.APP_ID, Constants.APP_KEY, String.valueOf(from), String.valueOf(from+10));
        callBackSearchPresenter.call(observable);
    }


}
