package com.example.lotte.visionpicking;

/**
 * Created by LOTTE on 2018-01-31.
 */

public class BasicValue {
    public static BasicValue ourInstance = new BasicValue();
    public static int mode = 1; // 0 no test (QRcode scanning skip)
                                // 1 test

    public static BasicValue getInstance() {
        return ourInstance;
    }

    public int getMode(){
        return mode;
    }

}

