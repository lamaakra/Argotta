package com.example.ahmad.chat_3.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.ahmad.chat_3.generalUtils.garbage.ASmallHack;
import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.ServerRequest.Constants;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoContract;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoPresenter;
import com.mzaart.aquery.AQ;

import java.util.List;
import java.util.Map;


public class ProfileInfoActivity extends BaseActivity implements ProfileInfoContract.View {
    private EditText name, email, phoneNumber;
    private TextView language;
    private Button chat;

    private ProfileInfoPresenter presenterProfile;
    private ProfileInfoRequestModel model;

    private String lang;
    private String engLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info);

        lang = getPrefs().getString("language", "null");
        engLang = getPrefs().getString("english_language", "null");

        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.profile_toolbar_white)));

        model = new ProfileInfoRequestModel();

        name = findViewById(R.id.fullName);
        email = findViewById(R.id.email_ET);
        chat = findViewById(R.id.chat);
        language = findViewById(R.id.country_ET);

        language.setOnClickListener(v -> {
            Intent intent = new Intent(this, LanguagesActivity.class);
            intent.putExtra("fromProfile", true);
            startActivityForResult(intent, 1998);
        });

        presenterProfile = new ProfileInfoPresenter(new ApiConstructor(this), getPrefs());
        presenterProfile.attachView(this);
        SharedPreferences pref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        name.setText(pref.getString("full_name", "fullName"));
        email.setText(pref.getString("email", "email"));
        language.setText(lang);
        ((Spinner) findViewById(R.id.engine)).setSelection(pref.getInt("engine", 0));

        chat.setOnClickListener(v -> {
            onRequestStart();
            model.full_name = name.getText().toString();
            model.email = email.getText().toString();
            model.language = this.engLang;
            System.out.println("=================================== " + model.language);
            model.translationEngine = ((Spinner) findViewById(R.id.engine)).getSelectedItemPosition();
            pref.edit().putInt("engine", model.translationEngine).apply();
            presenterProfile.setProfileInfo(model);
        });
    }

    @Override
    public void onSuccess() {
        getPrefs().edit().putString("language", lang)
                .putString("english_language", engLang).apply();


        onRequestEnd();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String error) {
        onRequestEnd();
        AQ.toast(this, error);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String lang = bundle.getString("language");
            String engLang = bundle.getString("english_language");

            this.engLang = engLang;
            this.lang = lang;
            language.setText(lang);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
