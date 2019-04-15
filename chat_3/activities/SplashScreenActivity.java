package com.example.ahmad.chat_3.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.mvp.registration.RegistrationPresenter;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashScreenActivity extends BaseActivity {
    private static int SPLASH_TIME_OUT = 5000;


    ImageView circle;
    ImageView logo;
    ImageView argotta_name;
    ImageView white_background;

    private boolean isHidden;

    public void circleAnimation(){
        circle  = findViewById(R.id.circle);
        white_background = findViewById(R.id.white_background);

        white_background.animate().alpha(1).setDuration(4000);
        circle.animate().scaleXBy(0.4f).scaleYBy(0.4f).translationYBy(-1000f).setDuration(700);
        circle.animate().alpha(0).setDuration(1500);

    }
    public void logoAnimation(){

        logo =   findViewById(R.id.logo);

        logo.animate().alpha(1);
        logo.animate().rotation(3600f).setDuration(3000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isHidden) {
                    Intent homeIntent = new Intent(SplashScreenActivity.this, SwipeLeftActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        logo.animate().scaleY(0.4f).scaleX(0.4f).translationY(-10f).setDuration(4000);

    }

    public void nameAnimation(){
        argotta_name =  findViewById(R.id.argottaname);
        argotta_name.animate().setStartDelay(1000);
        argotta_name.animate().alpha(1).setDuration(1000);
        argotta_name.animate().scaleYBy(1.5f).scaleXBy(1.5f).setDuration(3000);
    }


    public void splashAnimation(){
        setContentView(R.layout.activity_splash);
        circleAnimation();
        logoAnimation();
        nameAnimation();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashAnimation();

        /*new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(SplashScreenActivity.this, SwipeLeftActivity.class);
            startActivity(homeIntent);
            finish();
        },SPLASH_TIME_OUT);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        isHidden = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        isHidden = false;
    }
}
