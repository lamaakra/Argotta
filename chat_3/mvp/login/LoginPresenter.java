package com.example.ahmad.chat_3.mvp.login;

import android.content.SharedPreferences;

import com.example.ahmad.chat_3.firebase.MyFirebaseInstanceIDService;
import com.example.ahmad.chat_3.models.responses.LogInResposne;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.models.requests.LoginRequestModel;

import com.example.ahmad.chat_3.models.responses.TokenResponse;
import com.example.ahmad.chat_3.api.AccountApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.security.MessageDigest;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by lama on 3/22/18.
 */

public class LoginPresenter extends LoginContract.Presenter{


    private AccountApi api;
    private SharedPreferences pref;
    public LoginPresenter(ApiConstructor apiConstructor,SharedPreferences preferences) {
        this.api = apiConstructor.getAccountApi();
        this.pref=preferences;

    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void login(String username, String password) {
        String hash=sha256(password);
        LoginRequestModel model = new LoginRequestModel(username, hash, FirebaseInstanceId.getInstance().getToken());
        Call<LogInResposne> response = api.login(model);

        response.enqueue(new Callback<LogInResposne>() {
            @Override
            public void onResponse(Call<LogInResposne> call, retrofit2.Response<LogInResposne> response) {
                if (response.isSuccessful()) {
                    LogInResposne logInResponse = response.body();
                    System.out.println("Response: " + new Gson().toJson(logInResponse));
                    if (logInResponse.succeeded) {

                        String token = logInResponse.token;
                        System.out.println("LoginPresenter: " + token);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("token", token);
                        editor.putString("full_name", logInResponse.fullName);
                        //editor.putString("language", logInResponse.lang);
                        editor.putString("email", logInResponse.email);
                        editor.putBoolean("loggedIn", true);
                        editor.apply();

                        if (isViewAttached()) {
                            System.out.println(2);
                            //pref.edit().putBoolean("loggedIn", true).apply();
                            getView().onLoginSuccess();
                        }
                    } else {
                        if (isViewAttached()) {
                            getView().showError(logInResponse.reason);
                        }
                    }
                } else {
                    System.out.println(response.code());
                    if (isViewAttached()) {
                        getView().showError("An error has occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<LogInResposne> call, Throwable t) {
                if (isViewAttached()) {
                    getView().showError("An error has occurred");
                }
            }
        });
    }
}