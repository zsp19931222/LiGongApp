package com.example.app3.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/9.
 */

public class Contact implements Serializable {
    private String mName;
    private String mUrl;
    private String fqr;
    private int mType;

    public Contact(String name, int type, String url, String fqr) {
        mName = name;
        mType = type;
        mUrl = url;
        this.fqr = fqr;
    }

    public String getFqr() {
        return fqr;
    }

    public String getmName() {
        return mName;
    }

    public String getmUrl() {
        return mUrl;
    }

    public int getmType() {
        return mType;
    }

}
