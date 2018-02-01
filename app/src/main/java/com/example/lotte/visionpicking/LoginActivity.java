package com.example.lotte.visionpicking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    //firebase
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference mConditionRef = databaseReference.child("Employee");

    @BindView(R.id.login_button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Log.d(TAG, "Activity start");

    }

    @OnClick(R.id.login_button)
    void click(View v) {
        Log.d(TAG, "clicked");
        if (v.getId() == R.id.login_button) {
            if (BasicValue.getInstance().getMode() == 1) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ID", "testID");
                startActivity(intent);
            } else {
                new IntentIntegrator(this).initiateScan();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scanning fail", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result.getContents());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("ID", object.getString("ID"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String employeeId = dataSnapshot.getValue(String.class);
                Log.d(TAG, employeeId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

