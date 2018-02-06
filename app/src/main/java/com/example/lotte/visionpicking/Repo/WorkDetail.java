package com.example.lotte.visionpicking.Repo;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by LOTTE on 2018-02-05.
 */

public class WorkDetail implements Serializable {

    private Long count;
    private Long doneCount;
    private boolean isFinish;
    private int picking_time;
    private String product_location;
    private String product_name;
    private String index;
    private String productIndex;

    public String getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(String productIndex) {
        this.productIndex = productIndex;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public int getPicking_time() {
        return picking_time;
    }

    public void setPicking_time(int picking_time) {
        this.picking_time = picking_time;
    }

    public String getProduct_location() {
        return product_location;
    }

    public void setProduct_location(String product_location) {
        this.product_location = product_location;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Long getDoneCount() {
        return doneCount;
    }

    public void setDoneCount() {
        this.doneCount = count;
    }


    public WorkDetail() {

    }

    public WorkDetail(Long count, boolean isFinish, int picking_time, String product_location, String product_name) {
        this.count = count;
        this.isFinish = isFinish;
        this.picking_time = picking_time;
        this.product_location = product_location;
        this.product_name = product_name;

    }
}
