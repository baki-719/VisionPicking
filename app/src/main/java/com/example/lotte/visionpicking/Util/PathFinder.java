package com.example.lotte.visionpicking.Util;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LOTTE on 2018-02-06.
 */

public class PathFinder {
    private static final String TAG = "PathFinder";

    private int[] distance = {6,4,2,6,4,2};
    private int[] contain = {0,0,0,0,0,0};
    private int sum=0;
    private String section;
    private String result = "";
    private char[] cut;

    public PathFinder(String section) {
        this.section = section;
        cut = section.toCharArray();
    }

    public String getPath() {
        for (int i = 0; i < cut.length; i++) {
            int num = section.charAt(i);
            contain[num-65]=1;
        }
        for (int i = 0; i < contain.length; i++) {
            distance[i]=distance[i]*contain[i];
            sum+=distance[i];
        }
        while(sum!=0) {
            for (int i = 0; i < contain.length; i++) {
                if(distance[i]==2) {
                    sum-=2;
                    distance[i]=0;
                    result = result.concat(Character.toString((char)(i+65)));
                }
                else if(distance[i]>0)
                    distance[i]-=2;
            }
            if(result.length() == section.length()) break;
        }

        Log.d(TAG, "PathFinder : " + result);
        String finalResult = "";
        for (int i = 0; i < result.length(); i++) {
            if(i == result.length()-1) finalResult += result.charAt(i);
            else {
                finalResult += result.charAt(i) + " → ";
            }
        }
        return finalResult;
    }


}
