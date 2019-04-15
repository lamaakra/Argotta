package com.example.ahmad.chat_3.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.models.requests.ProfileInfoRequestModel;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.profileInfo.ProfileInfoPresenter;

import java.util.Locale;

public class LanguagesActivity extends BaseActivity {
    private String[] languages;
    private String[] englishLanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);
        languages = getResources().getStringArray(R.array.all_languages);
        englishLanguages = getResources().getStringArray(R.array.english_languages);
        final ListAdapter aadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);
        final ListView aadapterListView = findViewById(R.id.languages);

        aadapterListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(LanguagesActivity.this, LoginActivity.class);
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) { // entering the activity login/register
                bundle = new Bundle();
                bundle.putString("language", languages[position]);
                bundle.putString("english_language", englishLanguages[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            } else { // entering the activity from profile
                Intent finishingIntent = new Intent();
                finishingIntent.putExtra("language", languages[position]);
                finishingIntent.putExtra("english_language", englishLanguages[position]);
                setResult(Activity.RESULT_OK, finishingIntent);
            }

            getPrefs().edit().putString("language", languages[position])
                .putString("english_language", englishLanguages[position]).apply();
            finish();
        });

        aadapterListView.setAdapter(aadapter);

    }

    private void setLocale(String lang) {
        String l;
        if (lang.equals("Arabic")) {
            l = "ar";
        } else if (lang.equals("French")) {
            l = "fr";
        } else {
            l = "en";
        }
        Locale locale = new Locale(l);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        finish();
    }
}
