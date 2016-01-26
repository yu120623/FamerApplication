package com.egceo.app.myfarm.entity;

import java.io.Serializable;
import java.util.Date;
public class OrderDateModel implements Serializable{
    private Date date;
    private String price;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
