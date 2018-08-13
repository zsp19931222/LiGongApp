package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/16 0016.
 */

public class MySelfEntity {

    private List<AllTagsListBean> allTagsList;

    public List<AllTagsListBean> getAllTagsList() {
        return allTagsList;
    }

    public void setAllTagsList(List<AllTagsListBean> allTagsList) {
        this.allTagsList = allTagsList;
    }

    public static class AllTagsListBean {
        /**
         * layout : fill_view
         * view : item_fill_h15dp_wm_bacf7f7f7
         * title : 我的二維碼
         * sort : 1526267386249
         * function_key : ["oI354ClCsJ14Vhs+awSXfYZNcphk+WAxHTv0iZX6Ie2d2Oz7+795mOagMtMuqkrYVhJAVHVS+/Kw\nSHZbvqaVaU9JU7p6dXYBz45bwRyROZNLUFl+fNC2XVdEpfl9GpDu2IG0as3/lKTVB8TeOsYPUS/K\nT4Vtyc9LN76NUpKM9UE=\n"]
         * img : http://192.168.0.108:8090/fs/filedown?id=105ljlu61lmtio8sgn23jfd1b2&fileName=%E6%94%BF%E6%B2%BB%E9%9D%A2%E8%B2%8C.png
         * funcid : 2018051601
         * type : 2
         * class_name : com.example.app3.activity.QRActivity
         * url : com.yhkj.cqgyxy
         */

        private String layout;
        private String view;
        private String sort;
        private int state;
        private String img;
        private String funcid;
        private int type;
        private String class_name;
        private String url;
        private String title;
        private String ios_notice;
        private int power;
        private String android_packname;
        private String ios_urlscheme;
        private String android_address;
        private String ios_adress;
        private List<String> function_key;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getIos_notice() {
            return ios_notice;
        }

        public void setIos_notice(String ios_notice) {
            this.ios_notice = ios_notice;
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

        public String getIos_urlscheme() {
            return ios_urlscheme;
        }

        public void setIos_urlscheme(String ios_urlscheme) {
            this.ios_urlscheme = ios_urlscheme;
        }

        public String getAndroid_address() {
            return android_address;
        }

        public void setAndroid_address(String android_address) {
            this.android_address = android_address;
        }

        public String getIos_adress() {
            return ios_adress;
        }

        public void setIos_adress(String ios_adress) {
            this.ios_adress = ios_adress;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getFuncid() {
            return funcid;
        }

        public void setFuncid(String funcid) {
            this.funcid = funcid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getFunction_key() {
            return function_key;
        }

        public void setFunction_key(List<String> function_key) {
            this.function_key = function_key;
        }
    }
}
