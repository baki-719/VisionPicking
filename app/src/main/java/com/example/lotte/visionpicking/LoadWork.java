package com.example.lotte.visionpicking;

import android.util.Log;

import com.example.lotte.visionpicking.Repo.Work;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by DEV on 2018-02-04.
 */

public class LoadWork extends Thread {
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Work");
    // TODO: 2018-02-04 make data loading is synchronized
}
