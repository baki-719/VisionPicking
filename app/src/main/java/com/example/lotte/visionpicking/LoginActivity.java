package com.example.lotte.visionpicking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.lotte.visionpicking.Repo.Employee;
import com.example.lotte.visionpicking.Repo.Product;
import com.example.lotte.visionpicking.Repo.WorkDetail;
import com.example.lotte.visionpicking.Repo.WorkList;
import com.example.lotte.visionpicking.Thread.LoadDataThread;
import com.example.lotte.visionpicking.Util.BasicValue;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    private ArrayList<Product> productArrayList = new ArrayList<Product>();
    private ArrayList<WorkList> workListArrayList = new ArrayList<WorkList>();
    private ArrayList<WorkDetail> workDetailArrayList = new ArrayList<WorkDetail>();

    @BindView(R.id.login_button)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        LoadDataThread loadDataThread = new LoadDataThread(new LoadDataHandler());
        loadDataThread.start();
    }

    @OnClick(R.id.login_button)
    void onClicked() {
        switch (BasicValue.getInstance().getMode()) {
            case 0: // QRcode skip
                break;
            case 1: // QRcode not skip
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
                break;
        }
    }

    private class LoadDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            employeeArrayList = (ArrayList<Employee>) msg.getData().getSerializable("employeeArrayList");
            productArrayList = (ArrayList<Product>) msg.getData().getSerializable("productArrayList");
            workListArrayList = (ArrayList<WorkList>) msg.getData().getSerializable("workListArrayList");
            workDetailArrayList = (ArrayList<WorkDetail>) msg.getData().getSerializable("workDetailArrayList");
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
                Log.d(TAG, object.toString());
                if (checkId(object)) login(
                        new Employee(object.get("name").getAsString(), object.get("position").getAsString(), object.get("index").getAsString()),
                        productArrayList,
                        workListArrayList,
                        workDetailArrayList
                );
                else Toast.makeText(this, "로그인 정보를 확인해주세요", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkId(JsonObject object) {
        for (Employee temp : employeeArrayList) {
            if (object.get("index").getAsString().equals(temp.getIndex())) return true;
            Log.d(TAG, temp.getIndex());
        }
        return false;
    }

    private void login(Employee whoLogin, ArrayList<Product> productArrayList, ArrayList<WorkList> workListArrayList, ArrayList<WorkDetail> workDetailArrayList) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("whoLogin", whoLogin);
        intent.putExtra("productArrayList", productArrayList);
        intent.putExtra("workListArrayList", workListArrayList);
        intent.putExtra("workDetailArrayList", workDetailArrayList);
        startActivity(intent);
        finish();
    }
}

