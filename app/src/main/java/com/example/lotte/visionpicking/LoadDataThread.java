package com.example.lotte.visionpicking;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.lotte.visionpicking.Repo.Employee;
import com.example.lotte.visionpicking.Repo.Product;
import com.example.lotte.visionpicking.Repo.WorkDetail;
import com.example.lotte.visionpicking.Repo.WorkList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DEV on 2018-02-04.
 */

public class LoadDataThread extends Thread {

    private final String TAG = "LodadDataThread";
    private Handler handler;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    private ArrayList<Product> productArrayList = new ArrayList<Product>();
    private ArrayList<WorkList> workListArrayList = new ArrayList<WorkList>();
    private ArrayList<WorkDetail> workDetailArrayList = new ArrayList<WorkDetail>();

    public LoadDataThread(Handler handler) {
        super();
        this.handler = handler;
    }

    public LoadDataThread() {
    }

    @Override
    public void run() {
        super.run();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //load employee data
                for (DataSnapshot temp : dataSnapshot.child("Employee").getChildren()) {
                    Employee tempEmp = new Employee(temp.child("name").getValue().toString(), temp.child("position").getValue().toString(), temp.getKey());
                    employeeArrayList.add(tempEmp);
                }
                //load product data
                for (DataSnapshot temp : dataSnapshot.child("Inventory").getChildren()) {
                    Product tempProd = new Product((Long) temp.child("total").getValue(), temp.getKey(), temp.child("product_location").getValue().toString(), temp.child("product_name").getValue().toString());
                    productArrayList.add(tempProd);
                }
                //load workDetail
                for (DataSnapshot temp : dataSnapshot.child("WorkDetails").getChildren()) {
                    WorkDetail tempWorkDetail = temp.getValue(WorkDetail.class);
                    tempWorkDetail.setIndex(temp.getKey());
                    workDetailArrayList.add(tempWorkDetail);
                }
                //load workList
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (DataSnapshot temp : dataSnapshot.child("WorkList").child(sdf.format(new Date())).getChildren()) {
                    WorkList tempWorkList = temp.getValue(WorkList.class);
                    tempWorkList.setIndex(temp.getKey());
                    workListArrayList.add(tempWorkList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "employeeArrayList : " + employeeArrayList.size());
        Message msg = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putSerializable("employeeArrayList", employeeArrayList);
        bundle.putSerializable("productArrayList", productArrayList);
        bundle.putSerializable("workListArrayList", workListArrayList);
        bundle.putSerializable("workDetailArrayList", workDetailArrayList);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}
