package com.example.lotte.visionpicking.Repo;

/**
 * Created by DEV on 2018-02-04.
 */

public class Product {
    private Long count;
    private Long index;
    private String location;
    private String name;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product() {

    }

    public Product(Long count, Long index, String location, String name) {

        this.count = count;
        this.index = index;
        this.location = location;
        this.name = name;
    }
}
