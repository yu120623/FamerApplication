package com.egceo.app.myfarm.entity;

import java.util.List;

public class RecommendTagModel extends Tag{
    private List<Tag> areaTags;
    private List<Tag> serviceTags;

    public List<Tag> getAreaTags() {
        return areaTags;
    }

    public void setAreaTags(List<Tag> areaTags) {
        this.areaTags = areaTags;
    }

    public List<Tag> getServiceTags() {
        return serviceTags;
    }

    public void setServiceTags(List<Tag> serviceTags) {
        this.serviceTags = serviceTags;
    }
}
