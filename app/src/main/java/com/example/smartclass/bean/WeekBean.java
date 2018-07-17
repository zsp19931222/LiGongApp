package com.example.smartclass.bean;

/**
 * Created by Administrator on 2018/1/5 0005.
 */

public class WeekBean {
    private String week,eng;
    private int id;

    public WeekBean(String week, String eng, int id) {
        this.week = week;
        this.eng = eng;
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
