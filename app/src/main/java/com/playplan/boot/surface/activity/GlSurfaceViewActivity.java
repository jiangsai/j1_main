package com.playplan.boot.surface.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.boot.databinding.ActivityGlSurfaceviewBinding;

public class GlSurfaceViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGlSurfaceviewBinding binding = ActivityGlSurfaceviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}