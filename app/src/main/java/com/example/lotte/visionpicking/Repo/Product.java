package com.example.lotte.visionpicking.Repo;

import java.io.Serializable;

/**
 * Created by DEV on 2018-02-04.
 */

public class Product implements Serializable {
    private Long total;
    private String index;
    private String product_location;
    private String product_name;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public Product() {

    }

    public Product(Long total, String index, String product_location, String product_name) {

        this.total = total;
        this.index = index;
        this.product_location = product_location;
        this.product_name = product_name;
    }
}
