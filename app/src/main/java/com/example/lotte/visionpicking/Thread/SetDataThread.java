package com.example.lotte.visionpicking.Thread;

import android.util.Log;

import com.example.lotte.visionpicking.Repo.Product;
import com.example.lotte.visionpicking.Repo.WorkDetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * Created by LOTTE on 2018-02-06.
 */

public class SetDataThread extends Thread {

    private static final String TAG = "SetDataThread";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private Handler handler;
    private String workListIndex;
    private String productIndex;
    private ArrayList<WorkDetail> finishedWorks;
    private ArrayList<Product> inventory;
    private Map<String, Object> taskMap = new HashMap<String, Object>();


    public SetDataThread(Handler handler) {
        this.handler = handler;
    }

    public SetDataThread(ArrayList<WorkDetail> finishedWorks, ArrayList<Product> inventory, String workListIndex, String productIndex) {
        this.finishedWorks = finishedWorks;
        this.inventory = inventory;
        this.workListIndex = workListIndex;
        this.productIndex = productIndex;
    }

    private void makeProductMap() {
        findProductIndex();
        for (WorkDetail temp : finishedWorks) {
            int finishedCount = (int) (inventory.get(findByProductName(inventory, temp.getProduct_name())).getTotal() - temp.getDoneCount());
            taskMap.put("Inventory/" + temp.getProductIndex() + "/total", finishedCount);
        }
    }

    private void findProductIndex() {
        for (int j = 0; j < finishedWorks.size(); j++) {
            for (int i = 0; i < inventory.size(); i++) {
                if (finishedWorks.get(j).getProduct_name().equals(inventory.get(i).getProduct_name())) finishedWorks.get(j).setProductIndex(inventory.get(i).getIndex());
            }
        }
    }

    private int findByProductName(ArrayList<Product> inventory, String productName) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getProduct_name().equals(productName)) return i;
        }
        return 95654;
    }

    private void makeWorkListMap() {
        taskMap.put("WorkList/" + sdf.format(new Date()) + "/" + workListIndex + "/isfinish", "true");
    }

    @Override
    public void run() {
        super.run();
        makeProductMap();
        makeWorkListMap();

        databaseReference.updateChildren(taskMap);
    }
}
