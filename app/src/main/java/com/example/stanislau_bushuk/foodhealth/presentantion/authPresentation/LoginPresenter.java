package com.example.stanislau_bushuk.foodhealth.presentantion.authPresentation;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.stanislau_bushuk.foodhealth.App;
import com.example.stanislau_bushuk.foodhealth.cicerone.OwnRouter;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;

import timber.log.Timber;

@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> implements CallBackLoginPresenter {

    @Inject
    LoginModel loginModel;

    @Inject
    OwnRouter router;

    public LoginPresenter() {
        App.getAppComponent().inject(this);
        loginModel.setCallBack(this);
    }

    public void signInUser(final String email, final String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            loginModel.signIn(email, password);
        } else {
            getViewState().checkEmptyLine();
        }
    }

    public void signInAnonymous() {
        loginModel.signInAnonymous();
    }

    public void registrationUser(final String email, final String password, final String confirm_password) {
        if (email.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
            getViewState().checkEmptyLine();
        } else {
            if (password.equals(confirm_password)) {
                loginModel.registrationUser(email, password);
            } else {
                getViewState().checkPassword();
            }
        }
    }

    @Override
    public void call(final AuthResult authResult) {
        if (authResult != null) {
            Timber.e(authResult.getUser().toString());
            getViewState().user(authResult.getUser());
        }
    }

    @Override
    public void fail(final Exception e) {
        getViewState().error(e);
    }

    public void goTo(final String screenKey) {
        router.newRootScreen(screenKey);
    }

    public void replace(final String screenKey) {
        router.newRootScreen(screenKey);
    }

    public void exit() {
        router.exit();
    }
}
