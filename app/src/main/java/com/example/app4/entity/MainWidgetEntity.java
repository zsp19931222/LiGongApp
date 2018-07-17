package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public class MainWidgetEntity {

    /**
     * content : [{"diy_img":"http://192.168.0.108:8090/fs/filedown?id=5u9af7hoc1qtj72d2bqrrpnaoc&fileName=d.png","pitch_img":"http://192.168.0.108:8090/fs/filedown?id=7suqsl8ntm6209vaiopog16df2&fileName=stu_mbgl.png","sort":"1","state":"0","funcid":"null","type":3,"class_name":"null","url":"123","title":"首页","function_key":["oI354ClCsJ14Vhs+awSXfYZNcphk+WAxHTv0iZX6Ie2d2Oz7+795mOagMtMuqkrYVhJAVHVS+/Kw SHZbvqaVaU9JU7p6dXYBz45bwRyROZNLUFl+fNC2XVdEpfl9GpDu2IG0as3/lKTVB8TeOsYPUS/K T4Vtyc9LN76NUpKM9UE= "],"ios_notice":"null","name":"首页","class_type":1},{"diy_img":"http://192.168.0.108:8090/fs/filedown?id=60lktk2uo7t5rcori45fec12go&fileName=stu_sthd.png","pitch_img":"http://192.168.0.108:8090/fs/filedown?id=5u9af7hoc1qtj72d2bqrrpnaoc&fileName=d.png","sort":"2","state":"0","funcid":"null","type":3,"class_name":"null","url":"123","title":"消息","function_key":["oI354ClCsJ14Vhs+awSXfYZNcphk+WAxHTv0iZX6Ie2d2Oz7+795mOagMtMuqkrYVhJAVHVS+/Kw SHZbvqaVaU9JU7p6dXYBz45bwRyROZNLUFl+fNC2XVdEpfl9GpDu2IG0as3/lKTVB8TeOsYPUS/K T4Vtyc9LN76NUpKM9UE= "],"ios_notice":"null","name":"消息","class_type":2},{"diy_img":"http://192.168.0.108:8090/fs/filedown?id=60lktk2uo7t5rcori45fec12go&fileName=stu_sthd.png","pitch_img":"http://192.168.0.108:8090/fs/filedown?id=5u9af7hoc1qtj72d2bqrrpnaoc&fileName=d.png","sort":"3","state":"1","funcid":"null","type":3,"class_name":"null","url":"123","title":"我的","function_key":["oI354ClCsJ14Vhs+awSXfYZNcphk+WAxHTv0iZX6Ie2d2Oz7+795mOagMtMuqkrYVhJAVHVS+/Kw SHZbvqaVaU9JU7p6dXYBz45bwRyROZNLUFl+fNC2XVdEpfl9GpDu2IG0as3/lKTVB8TeOsYPUS/K T4Vtyc9LN76NUpKM9UE= "],"ios_notice":"null","name":"我的","class_type":3}]
     * message : 成功
     * code : 40001
     */

    private String message;
    private String code;
    private List<ContentBean> content;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * diy_img : http://192.168.0.108:8090/fs/filedown?id=5u9af7hoc1qtj72d2bqrrpnaoc&fileName=d.png
         * pitch_img : http://192.168.0.108:8090/fs/filedown?id=7suqsl8ntm6209vaiopog16df2&fileName=stu_mbgl.png
         * sort : 1
         * state : 0
         * funcid : null
         * type : 3
         * class_name : null
         * url : 123
         * title : 首页
         * function_key : ["oI354ClCsJ14Vhs+awSXfYZNcphk+WAxHTv0iZX6Ie2d2Oz7+795mOagMtMuqkrYVhJAVHVS+/Kw SHZbvqaVaU9JU7p6dXYBz45bwRyROZNLUFl+fNC2XVdEpfl9GpDu2IG0as3/lKTVB8TeOsYPUS/K T4Vtyc9LN76NUpKM9UE= "]
         * ios_notice : null
         * name : 首页
         * class_type : 1
         */

        private String diy_img;
        private String pitch_img;
        private String name;
        private int class_type;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
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

        public String getDiy_img() {
            return diy_img;
        }

        public void setDiy_img(String diy_img) {
            this.diy_img = diy_img;
        }

        public String getPitch_img() {
            return pitch_img;
        }

        public void setPitch_img(String pitch_img) {
            this.pitch_img = pitch_img;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIos_notice() {
            return ios_notice;
        }

        public void setIos_notice(String ios_notice) {
            this.ios_notice = ios_notice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getClass_type() {
            return class_type;
        }

        public void setClass_type(int class_type) {
            this.class_type = class_type;
        }

        public List<String> getFunction_key() {
            return function_key;
        }

        public void setFunction_key(List<String> function_key) {
            this.function_key = function_key;
        }
    }
}
