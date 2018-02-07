package com.example.lotte.visionpicking;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by LOTTE on 2018-02-06.
 */

public class CustomCaptureActivity extends CaptureActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView title_view = new TextView(this);
        title_view.setLayoutParams(new LinearLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
        title_view.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        title_view.setPadding(150, 100, 100, 100);
        title_view.setTextColor(Color.parseColor("#FF7200"));
        title_view.setTextSize(30);
        title_view.setText("바코드 / QR 코드 입력화면");

        this.addContentView(title_view, layoutParams);
    }
}
