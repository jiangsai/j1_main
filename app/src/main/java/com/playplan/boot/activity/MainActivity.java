package com.playplan.boot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.playplan.boot.databinding.ActivityMainBinding;
import com.playplan.boot.surface.CameraSurfaceView;
import com.playplan.boot.surface.MediaPlayerSurfaceView;
import com.playplan.boot.surface.adapter.TestAdapter;
import com.playplan.boot.surface.bean.Test;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Test> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Test());
        }
        binding.rvList.setAdapter(new TestAdapter(list));
        binding.rvList.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        binding.fl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.fl1.getChildCount() == 0) {
                    binding.fl1.addView(new MediaPlayerSurfaceView(getBaseContext()));
                }
            }
        });
        binding.fl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.fl2.getChildCount() == 0) {
                    binding.fl2.addView(new CameraSurfaceView(getBaseContext()));
                }
            }
        });
        binding.fl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.fl3.getChildCount() == 0) {
                    binding.fl3.addView(new CameraSurfaceView(getBaseContext()));
                }
            }
        });
        binding.btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("jyt", binding.fl3.getChildCount() + "==" + binding.fl2.getChildCount());
                if (binding.fl2.getChildCount() == 1 && binding.fl3.getChildCount() == 0) {
                    View view2 = binding.fl2.getChildAt(0);
                    binding.fl2.removeAllViews();
                    binding.fl3.addView(view2);
                } else if (binding.fl3.getChildCount() == 1 && binding.fl2.getChildCount() == 0) {
                    View view1 = binding.fl3.getChildAt(0);
                    binding.fl3.removeAllViews();
                    binding.fl2.addView(view1);
                }
                Log.e("jyt", binding.fl3.getChildCount() + "==" + binding.fl2.getChildCount());
            }
        });


        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            switch (result.getResultCode()) {
                case 0:
                    Intent intent = result.getData();
                    if (intent != null) {
                        Log.e("jyt", "-----onActivityResult" + intent.getStringExtra("111"));
                    }
                    break;
                    }

                }
        );

        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PermissionX.init(MainActivity.this)
//                        .permissions(Manifest.permission.READ_CONTACTS,
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.CALL_PHONE)
//                        .request((allGranted, grantedList, deniedList) -> {
//                            if (allGranted) {
//                                ToastUtils.show("All permissions are granted");
//                            } else {
//                                ToastUtils.show("These permissions are denied: $deniedList");
//                            }
//                        });
                /**
                 * 跳转
                 */
                //  launcher.launch(new Intent(getBaseContext(), ProcessTestActivity.class));

                /**
                 * 清空
                 */
                //  binding.sfView.reDraw();

                binding.sfView.setTranslationY(100);
            }
        });

    }
}