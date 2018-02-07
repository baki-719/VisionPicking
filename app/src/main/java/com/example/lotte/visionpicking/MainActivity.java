package com.example.lotte.visionpicking;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.lotte.visionpicking.Repo.Employee;
import com.example.lotte.visionpicking.Repo.Product;
import com.example.lotte.visionpicking.Repo.WorkDetail;
import com.example.lotte.visionpicking.Repo.WorkList;
import com.example.lotte.visionpicking.Thread.SetDataThread;
import com.example.lotte.visionpicking.Util.CameraPreview;
import com.example.lotte.visionpicking.Util.ListAdapter;
import com.example.lotte.visionpicking.Util.PathFinder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    private CameraPreview mPreview;
    private Employee staff;
    private String myWorksIndex;
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
    @BindView(R.id.pathText)
    TextView pathText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fullScreen();
        mPreview = new CameraPreview(this, mCameraTextureView);
        init();
        Toast.makeText(this, staff.getName()+"님 오늘 하루도 힘내세요 !" , Toast.LENGTH_SHORT).show();
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
                        if (workDetailArrayList.get(j).getIndex().equals(temp.getWork_lists().get(i)) && !temp.isFinished()) {
                            myWorks.add(workDetailArrayList.get(j));
                            myWorksIndex = temp.getIndex();
                        }
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
        pathText.setText(new PathFinder(makePath()).getPath());
    }

    private String makePath() {
        String result = "";
        for (WorkDetail temp : myWorks) {
            result += temp.getProduct_location();
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        todoList.setAdapter(new ListAdapter(this, myWorks));
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

    @SuppressLint("Range")
    private void naviImageVisible(int visible) {
        pathText.setVisibility(visible);
        pathText.setAlpha(179);
        mapImage.setImageResource(R.drawable.map);
        mapImage.setImageAlpha(179);
        mapImage.setVisibility(visible);
    }

    @OnClick({R.id.todo_fab, R.id.navi_fab, R.id.report_fab, R.id.scan_fab, R.id.imageView})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.todo_fab:
                if (todoList.getVisibility() == View.VISIBLE)
                    todoList.setVisibility(View.GONE);
                else {
                    todoList.setAdapter(new ListAdapter(this, myWorks));
                    todoList.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.navi_fab:
                if (mapImage.getVisibility() == View.VISIBLE) {
                    naviImageVisible(View.INVISIBLE);
                } else {
                    naviImageVisible(View.VISIBLE);
                }
                break;
            case R.id.report_fab:
                // TODO: 2018-02-03 캡처후 서버에 전송
                break;
            case R.id.scan_fab:
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
                break;
            case R.id.imageView:
                if (mapImage.getVisibility() == View.VISIBLE)
                    mapImage.setVisibility(View.GONE);
                    pathText.setVisibility(View.GONE);
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
                boolean isMyWork = false;
                for(WorkDetail temp : myWorks) if(temp.getProduct_name().equals(object.get("product_name").getAsString())) isMyWork = true;
                if (object.get("type").getAsString().equals("product")&& isMyWork) {
                    Toast.makeText(this, object.get("product_name") + "1개 피킹 완료", Toast.LENGTH_SHORT).show();
                    String prodcutIndex = object.get("index").getAsString();
                    int index = 0;
                    for (WorkDetail temp : myWorks) {
                        if (temp.getProduct_name().equals(object.get("product_name").getAsString()))
                            myWorks.get(index).setCount(myWorks.get(index).getCount() - 1);
                        index++;
                    }
                    for (int i = 0; i < myWorks.size(); i++) {
                        if (myWorks.get(i).getCount() == 0) {
                            finishedWorks.add(myWorks.get(i));
                            myWorks.remove(i);
                            if (myWorks.isEmpty()) {
                                SetDataThread setDataThread = new SetDataThread(finishedWorks, productArrayList, myWorksIndex, prodcutIndex);
                                setDataThread.start();
                            }
                        }
                    }
                } else Toast.makeText(this, "잘못된 QR코드 입니다.", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
