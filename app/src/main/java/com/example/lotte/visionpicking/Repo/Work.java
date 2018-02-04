package com.example.lotte.visionpicking.Repo;

import java.util.ArrayList;

/**
 * Created by DEV on 2018-02-04.
 */

public class Work {

    private String date;
    private Long staff;
    private boolean isDone;
    private ArrayList<Long> workList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getStaff() {
        return staff;
    }

    public void setStaff(Long staff) {
        this.staff = staff;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public ArrayList<Long> getWorkList() {
        return workList;
    }

    public void setWorkList(ArrayList<Long> workList) {
        this.workList = workList;
    }

    public Work() {

    }

    public Work(String date, Long staff, boolean isDone, ArrayList<Long> workList) {
        this.date = date;
        this.staff = staff;
        this.isDone = isDone;
        this.workList = workList;
    }
}
