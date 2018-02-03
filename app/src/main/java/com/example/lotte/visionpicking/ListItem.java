package com.example.lotte.visionpicking;

/**
 * Created by DEV on 2018-02-03.
 */

public class ListItem {
    private String name;
    private String index;
    private String location;

    public ListItem(String name, String index, String location) {
        this.name = name;
        this.index = index;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
