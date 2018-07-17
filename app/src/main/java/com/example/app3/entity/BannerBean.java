package com.example.app3.entity;

/**
 * Created by Administrator on 2017/10/17.
 */

public class BannerBean {
    private String imageUrl;
    private String url;

    public BannerBean(String imageUrl, String url) {
        this.imageUrl = imageUrl;
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
