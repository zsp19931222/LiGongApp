package com.example.app3.search;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */

public class Group implements Serializable {
    private String function_id;
    private String function_icon;
    private String function_name;
    private String function_type;
    private String function_cls;
    private int power;
    private String android_packname;
    private String android_address;
    private int type;
    private List<String> function_key;
    private String url;

    public Group(String function_id, String function_icon, String function_name, String function_type, String function_cls) {
        this.function_id = function_id;
        this.function_icon = function_icon;
        this.function_name = function_name;
        this.function_type = function_type;
        this.function_cls = function_cls;
    }

    public Group(String function_id, String function_icon, String function_name, String function_type, String function_cls, int power, String android_packname, String android_address, int type, List<String> function_key, String url) {
        this.function_id = function_id;
        this.function_icon = function_icon;
        this.function_name = function_name;
        this.function_type = function_type;
        this.function_cls = function_cls;
        this.power = power;
        this.android_packname = android_packname;
        this.android_address = android_address;
        this.type = type;
        this.function_key = function_key;
        this.url = url;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getAndroid_packname() {
        return android_packname;
    }

    public void setAndroid_packname(String android_packname) {
        this.android_packname = android_packname;
    }

    public String getAndroid_address() {
        return android_address;
    }

    public void setAndroid_address(String android_address) {
        this.android_address = android_address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getFunction_key() {
        return function_key;
    }

    public void setFunction_key(List<String> function_key) {
        this.function_key = function_key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFunction_id() {
        return function_id;
    }

    public void setFunction_id(String function_id) {
        this.function_id = function_id;
    }

    public String getFunction_icon() {
        return function_icon;
    }

    public void setFunction_icon(String function_icon) {
        this.function_icon = function_icon;
    }

    public String getFunction_name() {
        return function_name;
    }

    public void setFunction_name(String function_name) {
        this.function_name = function_name;
    }

    public String getFunction_type() {
        return function_type;
    }

    public void setFunction_type(String function_type) {
        this.function_type = function_type;
    }

    public String getFunction_cls() {
        return function_cls;
    }

    public void setFunction_cls(String function_cls) {
        this.function_cls = function_cls;
    }

    @Override
    public String toString() {
        return "Group{" +
                "function_id='" + function_id + '\'' +
                ", function_icon='" + function_icon + '\'' +
                ", function_name='" + function_name + '\'' +
                ", function_type='" + function_type + '\'' +
                ", function_cls='" + function_cls + '\'' +
                '}';
    }
}
