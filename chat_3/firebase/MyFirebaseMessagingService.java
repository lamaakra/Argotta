package com.example.ahmad.chat_3.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ahmad.chat_3.MyApplication;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.activities.MainActivity;
import com.example.ahmad.chat_3.generalUtils.NotificationUtils;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.db.Message;
import com.example.ahmad.chat_3.models.requests.Account;
import com.example.ahmad.chat_3.models.requests.UsersRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final static String MESSAGE_PAYLOAD = "message";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        String payloadType = data.get("type").toLowerCase();

        if (payloadType.equals(MESSAGE_PAYLOAD)) {
            Intent intent = new Intent("messageReceived");
            intent.putExtra("messageJson", data.get("messages"));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        System.out.println("HANDLE INTENTTT");

        String payloadType = intent.getStringExtra("type").toLowerCase();

        if (payloadType.equals(MESSAGE_PAYLOAD)) {
            System.out.println(1);
            // add to DB
            Message message = new Gson().fromJson(intent.getStringExtra("messages"), Message.class);
            System.out.println("Message: " + intent.getStringExtra("messages"));
            ((MyApplication) getApplication())
                    .getDb()
                    .messageDao()
                    .insertAll(Collections.singletonList(message));

            Completable.fromAction(() -> {
                try {
                    Chat c = new Chat();
                    c.setUserName(message.senderUserName);
                    c.setSenderId(message.getSenderId());
                    c.setSenderLangCode(message.getSenderLangCode());
                    ((MyApplication) getApplication())
                            .getDb()
                            .chatDao()
                            .insertAll(Collections.singletonList(c));
                } catch (SQLiteConstraintException e) {

                }
            })
            .subscribeOn(Schedulers.io())
            .subscribe();

            if (!((MyApplication) getApplication()).isChatActvityVisibile()) {
                Intent onClickIntent = new Intent(this, MainActivity.class);
                NotificationUtils.sendNotification(this,
                        "You've got a new message!", message.getContent(), onClickIntent);
                Intent intent1 = new Intent("recreateBroadcast");
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
            }
        }
    }


}