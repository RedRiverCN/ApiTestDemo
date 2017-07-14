package com.red.apitestdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.red.apitestdemo.module.video.videoList.data.VideoContent;
import com.red.apitestdemo.module.video.videoList.VideoListFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new VideoListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {

    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = VideoDetailsActivity.newIntent(this, 1);
//        startActivity(intent);
//    }
}
