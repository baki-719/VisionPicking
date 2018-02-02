package com.example.lotte.visionpicking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private CameraPreview mPreview;
    private Employee employee;

    @BindView(R.id.textureView) TextureView mCameraTextureView;
    @BindView(R.id.todo_fab)
    FloatingActionButton todoFab;
    @BindView(R.id.navi_fab)
    FloatingActionButton naviFab;
    @BindView(R.id.report_fab)
    FloatingActionButton reportFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        fullScreen();
        mPreview = new CameraPreview(this, mCameraTextureView);
    }

    private void init() {
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("OBJECT");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.onPause();
    }

    private void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @OnClick({R.id.todo_fab, R.id.navi_fab, R.id.report_fab}) void onClick(View v) {
        switch (v.getId()) {
            case R.id.todo_fab :
                break;
            case R.id.navi_fab :
                break;
            case R.id.report_fab :
                break;
        }
    }
}
