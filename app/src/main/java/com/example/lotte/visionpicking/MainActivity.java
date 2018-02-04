package com.example.lotte.visionpicking;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lotte.visionpicking.Repo.Employee;
import com.example.lotte.visionpicking.Repo.Product;
import com.example.lotte.visionpicking.Repo.Work;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private CameraPreview mPreview;
    private Employee employee;
    private ArrayList<ListItem> listItems;
    private ArrayList<Product> productList = new ArrayList<Product>();
    private ArrayList<Work> works = new ArrayList<Work>();

    private DatabaseReference databaseReferenceProduct = FirebaseDatabase.getInstance().getReference().child("Product");

    @BindView(R.id.textureView)
    TextureView mCameraTextureView;
    @BindView(R.id.todo_fab)
    FloatingActionButton todoFab;
    @BindView(R.id.navi_fab)
    FloatingActionButton naviFab;
    @BindView(R.id.report_fab)
    FloatingActionButton reportFab;
    @BindView(R.id.scan_fab)
    FloatingActionButton scansFab;
    @BindView(R.id.imageView)
    ImageView mapImage;
    @BindView(R.id.listView)
    ListView todoList;

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
        synchronized (works) {
            makeProductList();
            makeTodoList();
        }
    }

    private synchronized void makeTodoList(){
        Log.d(TAG, "makeTodoList start");
        ArrayList<Work> myWorks = new ArrayList<Work>();
        for(int i  = 0 ; i < works.size(); i++) {
            Log.d(TAG, "work : " + works.get(i).getStaff());
            if(works.get(i).getStaff().equals(employee.getIndex()) && !works.get(i).isDone()) myWorks.add(works.get(i));
        }
        for(int i = 0 ; i < myWorks.size(); i++) {
            Date today = new Date();
            Log.d(TAG, "date : "+today.toString());
        }
    }


    private void makeProductList() {
        databaseReferenceProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot tempSnapshot : dataSnapshot.getChildren()) {
                    Product temp = tempSnapshot.getValue(Product.class);
                    productList.add(temp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    @OnClick({R.id.todo_fab, R.id.navi_fab, R.id.report_fab, R.id.scan_fab, R.id.imageView})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.todo_fab:
                // TODO: 2018-02-03 listView or recyclerView
                if (todoList.getVisibility() == View.VISIBLE)
                    todoList.setVisibility(View.INVISIBLE);
                else {
                    todoList.setAdapter(new ListAdapter(this, listItems));
                    todoList.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.navi_fab:
                if (mapImage.getVisibility() == View.VISIBLE)
                    mapImage.setVisibility(View.INVISIBLE);
                else {
                    mapImage.setImageResource(R.drawable.navi_sample);
                    mapImage.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.report_fab:
                // TODO: 2018-02-03 캡처후 서버에 전송
                break;
            case R.id.scan_fab:
                new IntentIntegrator(this).initiateScan();
                break;
            case R.id.imageView:
                if (mapImage.getVisibility() == View.VISIBLE)
                    mapImage.setVisibility(View.INVISIBLE);
                else {
                    mapImage.setImageResource(R.drawable.navi_sample);
                    mapImage.setVisibility(View.VISIBLE);
                }
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
                Toast.makeText(this, object.get("name") + "scan success", Toast.LENGTH_SHORT).show();
                // TODO: 2018-02-03 db에서 제고수량 줄이여야됨
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
