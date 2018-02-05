package com.example.lotte.visionpicking.Repo;

import java.io.Serializable;

/**
 * Created by LOTTE on 2018-02-02.
 */

public class Employee implements Serializable{

    private String index;
    private String name;
    private String position;

    public Employee() {
    }

    public Employee(String name, String position, String index) {
        this.name = name;
        this.position = position;
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String toString() {
        return "index : " + index + " / name : " + name + " / position : " + position;
    }
}
