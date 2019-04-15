package com.example.ahmad.chat_3.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.chat_3.MyApplication;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.adapters.MessagesAdapter;
import com.example.ahmad.chat_3.api.ChatApi;
import com.example.ahmad.chat_3.db.dao.MessageDao;
import com.example.ahmad.chat_3.generalUtils.NotificationUtils;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.db.Message;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.chat.ChatContract;
import com.example.ahmad.chat_3.mvp.chat.ChatPresenter;
import com.google.gson.Gson;
import com.mzaart.aquery.AQ;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivity extends BaseActivity implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private RecyclerView recyclerView;
    private String senderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chattoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        });

        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter("messageReceived"));


        new AQ(this).ready(() -> {
            new AQ(this, R.id.sender_user_name).text(getIntent().getStringExtra("senderUserName"));
            {
                recyclerView = findViewById(R.id.reyclerview_message_list);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                registerForContextMenu(recyclerView);
            }

            // message send
            new AQ(this, R.id.button_chatbox_send).click(v -> {
                String text = new AQ(this, R.id.edittext_chatbox).text();
                if (AQ.validator().present(text)) {
                    presenter.sendMessage(text);
                    new AQ(this, R.id.edittext_chatbox).text("");

                    if (recyclerView != null) {
                        Message m = new Message();
                        m.setContent(text);
                        m.setFromUser(true);
                        ((MessagesAdapter) recyclerView.getAdapter()).addItem(m);
                        int itemCount = recyclerView.getAdapter().getItemCount();
                        if (itemCount > 0)
                            recyclerView.smoothScrollToPosition(itemCount - 1);
                    }
                }
            });


            new AQ(this, R.id.edittext_chatbox).click(v -> {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if (recyclerView.getAdapter().getItemCount() > 0) {
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 350);
            });

            senderId = getIntent().getStringExtra("senderId");
            MessageDao messageDao = ((MyApplication) getApplicationContext()).getDb().messageDao();
            ChatApi chatApi = new ApiConstructor(this).getChatApi();
            presenter = new ChatPresenter(senderId, messageDao, chatApi);
            presenter.attachView(this);
            presenter.getMessages();

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.chat_message_context_menu, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyApplication) getApplication()).setChatActivityVisibile(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MyApplication) getApplication()).setChatActivityVisibile(false);
    }

    @Override
    public void displayMessages(List<Message> messages) {
        recyclerView.setAdapter(new MessagesAdapter(messages));
        int itemCount = recyclerView.getAdapter().getItemCount();
        if (itemCount > 0)
            recyclerView.smoothScrollToPosition(itemCount - 1);
    }

    @Override
    public void displayError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = new Gson().fromJson(intent.getStringExtra("messageJson"), Message.class);
            if (message.getSenderId().equals(senderId) && recyclerView != null) {
                ((MessagesAdapter) recyclerView.getAdapter()).addItem(message);
            } else {
                Intent onClickIntent = new Intent(ChatActivity.this, MainActivity.class);
                NotificationUtils.sendNotification(ChatActivity.this,
                        "You've got a new message!", message.getContent(), onClickIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // show home
        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
