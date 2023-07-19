package com.example.firebasesample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class splash_screen_activity extends AppCompatActivity {
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    Boolean islogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        LottieAnimationView animationView
                = findViewById(R.id.animation_view);
        animationView
                .addAnimatorUpdateListener(
                        (ValueAnimator animation) -> {
                        });
        animationView
                .playAnimation();

        if (animationView.isAnimating()) {

        }
        preferences=getSharedPreferences("pref",MODE_PRIVATE);
        editor= preferences.edit();
        islogin=preferences.getBoolean("login",false);
        if(networkconnected())
        {
            Handler handler=new Handler();
            Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    if (islogin)
                    {
                        Intent intent=new Intent(splash_screen_activity.this, product_view_activity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent=new Intent(splash_screen_activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            handler.postDelayed(runnable,5000);
        }
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(splash_screen_activity.this);
            builder.setTitle("Alert...");
            builder.setMessage("Please Check Your Internet Connectin.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    private boolean networkconnected() {
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return (info.isConnected() && info!=null);
    }
}