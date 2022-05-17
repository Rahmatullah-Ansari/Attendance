package com.android.attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.attendance.databinding.ActivityHomeScreenBinding;

public class HomeScreen extends AppCompatActivity {
    ActivityHomeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}