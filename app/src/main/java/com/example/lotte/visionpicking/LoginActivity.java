package com.example.lotte.visionpicking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";


    @BindView(R.id.login_button)
    Button loginButton;

    //firebase
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employee");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.login_button)
    void onClicked() {
        switch (BasicValue.getInstance().getMode()) {
            case 0: // QRcode skip
                break;
            case 1: // QRcode not skip
                new IntentIntegrator(this).initiateScan();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "스캔에 실패하였습니다.", Toast.LENGTH_LONG).show();
            } else {
                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(result.getContents()).getAsJsonObject();
                final Employee loginEmployee = new Employee(object.get("name").getAsString(), object.get("position").getAsString(), object.get("index").getAsLong());
                Log.d(TAG, "qr result : "+loginEmployee.toString());
                checkId(loginEmployee);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkId(final Employee loginEmployee) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot tempSnapshot : dataSnapshot.getChildren()) {
                    Employee temp = tempSnapshot.getValue(Employee.class);
                    Log.d(TAG, "db value : "+temp.toString());
                    if(loginEmployee.getIndex().equals(temp.getIndex())){
                        login(loginEmployee);
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void login(Employee loginEmployee) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("OBJECT", loginEmployee);
        startActivity(intent);
    }
}

