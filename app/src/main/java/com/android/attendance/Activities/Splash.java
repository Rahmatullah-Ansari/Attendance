package com.android.attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.android.attendance.databinding.ActivitySplashBinding;

import java.util.Objects;

public class Splash extends AppCompatActivity {
ActivitySplashBinding binding;
public static final int TIMER=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(Splash.this,HomeScreen.class));
            finish();
        }, TIMER);
    }

}