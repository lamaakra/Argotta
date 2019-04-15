package com.example.ahmad.chat_3.mvp.users;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;

import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.requests.Account;
import com.example.ahmad.chat_3.models.requests.UsersRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.api.AccountApi;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Iterator;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class UserPresenter extends  UserContract.Presenter {

    private AccountApi api;
    private ChatDao chatDao;
    private SharedPreferences prefs;

    public UserPresenter(ApiConstructor apiConstructor, ChatDao chatDao, SharedPreferences prefs) {
        this.api = apiConstructor.getAuthAccountApi();
        this.chatDao = chatDao;
        this.prefs = prefs;
    }

    @Override
    public void getUsers() {
        Call<UsersRequestModel> response = api.getUsers();

        response.enqueue(new Callback<UsersRequestModel>() {
            @Override
            public void onResponse(Call<UsersRequestModel> call, retrofit2.Response<UsersRequestModel> response) {
                if (response.isSuccessful()) {
                    UsersRequestModel usersRequestModel = response.body();
                    System.out.println("Response: " + new Gson().toJson(response.body()));
                    if (usersRequestModel.users != null) {
                        if (isViewAttached()) {
                            System.out.println("Users: " + new Gson().toJson(usersRequestModel.users));
                            Iterator<Account> iterator = usersRequestModel.users.iterator();
                            while (iterator.hasNext()) {
                                Account acc = iterator.next();
                                System.out.println("Username: " + prefs.getString("username", null));
                                if (acc.username.equals(prefs.getString("username", null))) {
                                    iterator.remove();
                                }
                            }
                            getView().displayUsers(usersRequestModel.users);
                        }
                    } else {
                        if (isViewAttached()) {
                            getView().showError(response.body().reason);
                        }
                    }
                } else {
                    if (isViewAttached()) {
                        getView().showError("An error has occurred");
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersRequestModel> call, Throwable t) {
                if (isViewAttached()) {
                    getView().showError("An error has occurred");
                }
            }
        });
    }

    @Override
    public void createChat(Account account) {
        System.out.println("Prseneter Acc: " + new Gson().toJson(account));
        Chat chat = new Chat();
        chat.setSenderId(account.userID);
        chat.setUserName(account.username);

        Completable.fromAction(() -> chatDao.insertAll(Arrays.asList(chat)))
                .subscribeOn(Schedulers.io())
                .subscribe(() -> {}, e -> {
                    if (!(e instanceof SQLiteConstraintException)) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
