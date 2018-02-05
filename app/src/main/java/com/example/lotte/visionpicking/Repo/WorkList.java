package com.example.lotte.visionpicking.Repo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by DEV on 2018-02-04.
 */

public class WorkList implements Serializable {

    private String index;
    private String name;
    private String position;
    private Long total;
    private ArrayList<String> work_lists = new ArrayList<String>();

    public WorkList() {
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public ArrayList<String> getWork_lists() {
        return work_lists;
    }

    public void setWork_lists(ArrayList<String> work_lists) {
        this.work_lists = work_lists;
    }

    public WorkList(String name, String position, Long total, ArrayList<String> work_lists) {

        this.name = name;
        this.position = position;
        this.total = total;
        this.work_lists = work_lists;
    }

}
