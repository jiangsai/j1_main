package com.playplan.boot.Jump;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.boot.databinding.ActivityTwoBinding;

public class TwoActivity extends AppCompatActivity {
    private ActivityTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(0, new Intent().putExtra("111", "x"));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}