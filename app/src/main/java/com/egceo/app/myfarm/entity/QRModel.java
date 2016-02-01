package com.egceo.app.myfarm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FreeMason on 2016/2/1.
 */
public class QRModel implements Serializable{
    private Resource resource;
    private List<OrderModel> orderModels;

    public List<OrderModel> getOrderModels() {
        return orderModels;
    }

    public void setOrderModels(List<OrderModel> orderModels) {
        this.orderModels = orderModels;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
