package com.example.apple.amazon.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.apple.amazon.R;

import butterknife.ButterKnife;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
    }
}
