package com.egceo.app.myfarm.entity;

import java.util.Date;

/**
 * Created by FreeMason on 2016/2/28.
 */
public class aa53458768RecordModel{
    private FarmSetModel farmSetModel;
    private String id;
    private String type;
    private Double value;
    private Date createdTime;

    public FarmSetModel getFarmSetModel() {
        return farmSetModel;
    }

    public void setFarmSetModel(FarmSetModel farmSetModel) {
        this.farmSetModel = farmSetModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
