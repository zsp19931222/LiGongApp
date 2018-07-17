package com.example.app3.adapter.dragrecyclear.bean;

/**
 * Created by Administrator on 2017/9/8.
 */

public class DragBean {
    private String Url;
    private String name;
    private int msection;
    private int mposition;

    private String functionID;

    public DragBean() {
    }

    public DragBean(String url, String name, int section, int position, String functionID) {
        Url = url;
        this.name = name;
        this.msection = section;
        this.mposition = position;
        this.functionID = functionID;
    }

    public String getFunctionID() {
        return functionID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return mposition;
    }

    public void setPosition(int position) {
        this.mposition = position;
    }

    public int getSection() {
        return msection;
    }

    public void setSection(int section) {
        this.msection = section;
    }

    @Override
    public String toString() {
        return "DragBean{" +
                "GetAppUrl='" + Url + '\'' +
                ", name='" + name + '\'' +
                ", section=" + msection +
                ", position=" + mposition +
                '}';
    }
}
