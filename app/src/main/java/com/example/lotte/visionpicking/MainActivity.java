package com.example.lotte.visionpicking;

import android.content.Intent;
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
import com.example.lotte.visionpicking.Repo.WorkDetail;
import com.example.lotte.visionpicking.Repo.WorkList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private CameraPreview mPreview;
    private Employee staff;
    private ArrayList<Product> productArrayList = new ArrayList<Product>();
    private ArrayList<WorkList> workListArrayList = new ArrayList<WorkList>();
    private ArrayList<WorkDetail> workDetailArrayList = new ArrayList<WorkDetail>();
    private ArrayList<WorkDetail> myWorks = new ArrayList<WorkDetail>();
    private ArrayList<WorkDetail> finishedWorks = new ArrayList<WorkDetail>();

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
        fullScreen();
        mPreview = new CameraPreview(this, mCameraTextureView);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        staff = (Employee) intent.getExtras().getSerializable("whoLogin");
        productArrayList = (ArrayList<Product>) intent.getExtras().getSerializable("productArrayList");
        workListArrayList = (ArrayList<WorkList>) intent.getExtras().getSerializable("workListArrayList");
        workDetailArrayList = (ArrayList<WorkDetail>) intent.getExtras().getSerializable("workDetailArrayList");
        makeMyWorks();
    }

    private void makeMyWorks() {
        for (WorkList temp : workListArrayList) {
            if (temp.getName().equals(staff.getName())) {
                for (int i = 0; i < temp.getWork_lists().size(); i++) {
                    for (int j = 0; j < workDetailArrayList.size(); j++) {
                        if (workDetailArrayList.get(j).getIndex().equals(temp.getWork_lists().get(i)))
                            myWorks.add(workDetailArrayList.get(j));
                    }
                }
            }
        }
        Collections.sort(myWorks, new Comparator<WorkDetail>() {
            @Override
            public int compare(WorkDetail workDetail, WorkDetail t1) {
                return workDetail.getProduct_location().compareTo(t1.getProduct_location());
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
                if (todoList.getVisibility() == View.VISIBLE)
                    todoList.setVisibility(View.INVISIBLE);
                else {
                    todoList.setAdapter(new ListAdapter(this, myWorks));
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
                if (object.get("type").getAsString().equals("product")) {
                    Toast.makeText(this, object.get("product_name") + "scan success", Toast.LENGTH_SHORT).show();
                    // TODO: 2018-02-03 db에서 제고수량 줄이여야됨
                    int index = 0;
                    for (WorkDetail temp : myWorks) {
                        if (temp.getProduct_name().equals(object.get("product_name").getAsString()))
                            myWorks.get(index).setCount(myWorks.get(index).getCount() - 1);
                        index++;
                    }
                    for (int i = 0; i < myWorks.size(); i++)
                        if (myWorks.get(i).getCount() == 0) {
                            finishedWorks.add(myWorks.get(i));
                            myWorks.remove(i);
                        }
                } else Toast.makeText(this, "잘못된 QR코드 입니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
