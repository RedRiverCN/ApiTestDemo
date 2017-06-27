package com.red.apitestdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.red.apitestdemo.module.video.VideoDetailsActivity;

import static com.red.apitestdemo.module.video.VideoDetailsActivity.newIntent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button playVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playVideo = (Button) findViewById(R.id.btn_play);
        playVideo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = VideoDetailsActivity.newIntent(this, 1);
        startActivity(intent);
    }
}
