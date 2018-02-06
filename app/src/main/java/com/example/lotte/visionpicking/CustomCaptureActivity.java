package com.example.lotte.visionpicking;

import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by LOTTE on 2018-02-06.
 */

public class CustomCaptureActivity extends CaptureActivity {
    IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setCaptureActivity(CustomCaptureActivity.class);
//        integrator.setOrientationLocked(false);
//        integrator.initiateScan();
}
