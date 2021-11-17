package com.playplan.boot.surface.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.playplan.boot.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity {

    private String TAG = "jyt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        TextView tv = findViewById(R.id.tv);
        ViewGroup s;
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wwww.baidu.com";
                String url1 = "http://www.apple.com/cn/";
                String url2 = "http://svga.io/svga-preview.html";

                for (int i = 0; i < 10; i++) {
                    getData(url);
                }
                for (int i = 0; i < 10; i++) {
                    getData(url2);
                }
                for (int i = 0; i < 10; i++) {
                    getData(url1);
                }

                Log.d(TAG, "connectionPool: ====");
                Log.e(TAG, "connectionPool: " + okHttpClient.dispatcher().runningCallsCount());
                okHttpClient.dispatcher().executorService();


            }
        });
    }

    OkHttpClient okHttpClient = new OkHttpClient();

    public OkHttpClient getData(String url) {

        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //   Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //   Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
        return okHttpClient;
    }
}