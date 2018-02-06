package com.example.lotte.visionpicking.Util;

/**
 * Created by LOTTE on 2018-01-31.
 */

public class BasicValue {
    public static BasicValue ourInstance = new BasicValue();
    public static int mode = 1; // 0 test (QRcode scanning skip)
                                // 1 test (QRcode not skip)

    public static BasicValue getInstance() {
        return ourInstance;
    }

    public int getMode(){
        return mode;
    }

}

