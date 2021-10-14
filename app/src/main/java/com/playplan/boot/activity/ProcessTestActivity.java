package com.playplan.boot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.playplan.boot.databinding.ActivityMainBinding;


public class ProcessTestActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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
                launcher.launch(new Intent(getBaseContext(), TwoActivity.class));


            }
        });

    }
}