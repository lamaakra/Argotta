package com.example.ahmad.chat_3.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.generalUtils.garbage.ASmallHack;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.travelMode.TravelModeContract;
import com.example.ahmad.chat_3.mvp.travelMode.TravelModePresenter;
import com.mzaart.aquery.AQ;

public class TravelModeActivity extends BaseActivity implements TravelModeContract.View {

    private TravelModeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_mode);

        getSupportActionBar().setTitle(Html.fromHtml(getString(R.string.travel_mode_white)));

        presenter = new TravelModePresenter(new ApiConstructor(this).getChatApi());
        presenter.attachView(this);

        new AQ(this).ready(() -> AQ(R.id.button_translate).click(v -> {
            String langFrom = getPrefs().getString("language", null);
            int pos = ((Spinner) AQ(R.id.spinner).raw()).getSelectedItemPosition();
            String langTo = getResources().getStringArray(R.array.english_languages)[pos];
            String text = AQ(R.id.edittext_travel).text();

            ASmallHack hack = new ASmallHack();
            onRequestStart();
            presenter.translate(text, hack.getLangCode(langFrom), hack.getLangCode(langTo));
        }));
    }

    @Override
    public void displayTranslation(String translation) {
        onRequestEnd();
        AQ(R.id.translated_text).text(translation);
    }

    @Override
    public void displayError(String error) {
        onRequestStart();
        AQ.toast(this, error);
    }
}
