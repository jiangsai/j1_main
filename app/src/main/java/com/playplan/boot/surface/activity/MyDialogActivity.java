package com.playplan.boot.surface.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hjq.toast.ToastUtils;
import com.playplan.boot.R;
import com.playplan.boot.databinding.ActivityMyDialogBinding;

public class MyDialogActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyDialogBinding binding = ActivityMyDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyDialog();
            }
        });
    }

    private void showMyDialog() {
        builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("最普通dialog")
                .setMessage("我是最简单的dialog").setPositiveButton("确定（积极）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        ToastUtils.show("111");
                    }
                }).setNegativeButton("取消（消极）", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        ToastUtils.show("xx");
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
}