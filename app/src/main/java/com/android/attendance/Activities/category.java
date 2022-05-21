package com.android.attendance.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.attendance.databinding.ActivityCategoryBinding;

public class category extends AppCompatActivity {
    ActivityCategoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}