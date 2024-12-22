package com.example.chatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatapplication.AllUsers.UserActivity;
import com.example.chatapplication.Auth.LoginActivity;
import com.example.chatapplication.util.SharedPreference;

public class SplashscreenActivity extends AppCompatActivity {
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splashscreen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreference = new SharedPreference(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // on below line we are
                // creating a new intent
              //  Intent i = new Intent(SplashscreenActivity.this, RegisterActivity.class);

                // on below line we are
                // starting a new activity.
                if(sharedPreference.isLogin())
                {
                    startActivity(new Intent(SplashscreenActivity.this, UserActivity.class));
                    finish();
                }

                else {
                    startActivity(new Intent(SplashscreenActivity.this, LoginActivity.class));
                    finish();
                }

             //   startActivity(new Intent(SplashscreenActivity.this, MainActivity.class));

                // on the below line we are finishing
                // our current activity.
                finish();
            }
        }, 2000);



    }
}