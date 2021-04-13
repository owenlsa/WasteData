package com.okay.wastedata;

import android.content.Intent;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.internet_speed_testing.InternetSpeedBuilder;
import com.example.internet_speed_testing.ProgressionModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    // default download link
    private String dLink = "http://ipv4.scaleway.testdebit.info:80/1G/1G.zip";
    private InternetSpeedBuilder builder;
    long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        builder = new InternetSpeedBuilder(this);
        builder.setOnEventInternetSpeedListener(new InternetSpeedBuilder.OnEventInternetSpeedListener() {
            @Override
            public void onDownloadProgress(int count, ProgressionModel progressModel) {
//                System.out.println(count);
            }

            @Override
            public void onUploadProgress(int count, ProgressionModel progressModel) {
//                System.out.println(count);
            }

            @Override
            public void onTotalProgress(int count, ProgressionModel progressModel) {
                long nowTime = System.currentTimeMillis();
                if ((nowTime - lastTime) > 1000) {
                    int curSpeed = progressModel.getDownloadSpeed().intValue()/8000;
                    textView.setText(curSpeed + "");
                    textView2.setText("K/s");
                    lastTime = nowTime;
                }
            }
        });
        builder.start(dLink, 20);
    }

    public void reStartActivity(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}