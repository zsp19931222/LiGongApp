package com.example.app4.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class ApplicationClassifyEntity {

    /**
     * content : [{"name":"asdd","lx":[{"img":"http://app.cqut.edu.cn/content/image/zslxd.png","funcid":"30000102","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"掌上离校单","function_key":"697E39F0237047DBA36B5F280C398630","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"},{"img":"http://app.cqut.edu.cn/content/image/bgdhcx.png","funcid":"30000002","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"办公电话","function_key":"6D45838739C74CBCA1C73B0F3923B4CD","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"}],"classid":"3003442"},{"name":"111","lx":[{"img":"http://app.cqut.edu.cn/content/image/xndt.png","funcid":"20150128","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"在线报修","function_key":"null","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"},{"img":"http://app.cqut.edu.cn/content/image/oabg.png","funcid":"30000101","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"公文处理","function_key":"4F940E0CCF3B4DADFD34EFF6843350D7","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"},{"img":"http://app.cqut.edu.cn/content/image/zsyx.png","funcid":"20170601","type":1,"class_name":"yh.app.acticity.JiYuActivity","ios_address":"null","url":"null","title":"掌上迎新","function_key":"FA8DB7A6F65840D290AE7E97DE503F65","ios_notice":"PalmNewYear","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"},{"img":"http://app.cqut.edu.cn/content/image/cjcx.png","funcid":"20150106","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"成绩查询","function_key":"BFC50080088744F0959AA34D7BEAE5F1","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"},{"img":"http://app.cqut.edu.cn/content/image/dbsy.png","funcid":"20150104","type":1,"class_name":"yh.app.function.WantToDo","ios_address":"null","url":"null","title":"待办事宜","function_key":"16DAA0F3B2AF431FB97E2E5EF4831CC1","ios_notice":"segueForTodo","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"}],"classid":"48657"}]
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
         * name : asdd
         * lx : [{"img":"http://app.cqut.edu.cn/content/image/zslxd.png","funcid":"30000102","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"掌上离校单","function_key":"697E39F0237047DBA36B5F280C398630","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"},{"img":"http://app.cqut.edu.cn/content/image/bgdhcx.png","funcid":"30000002","type":2,"class_name":"yh.app.web.Web","ios_address":"null","url":"null","title":"办公电话","function_key":"6D45838739C74CBCA1C73B0F3923B4CD","ios_notice":"segueForWebView","power":1,"android_packname":"null","ios_urlscheme":"null","android_address":"null"}]
         * classid : 3003442
         */

        private String name;
        private String classid;
        private List<LxBean> lx;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public List<LxBean> getLx() {
            return lx;
        }

        public void setLx(List<LxBean> lx) {
            this.lx = lx;
        }

        public static class LxBean {
            /**
             * img : http://app.cqut.edu.cn/content/image/zslxd.png
             * funcid : 30000102
             * type : 2
             * class_name : yh.app.web.Web
             * ios_address : null
             * url : null
             * title : 掌上离校单
             * function_key : 697E39F0237047DBA36B5F280C398630
             * ios_notice : segueForWebView
             * power : 1
             * android_packname : null
             * ios_urlscheme : null
             * android_address : null
             */

            private String img;
            private String funcid;
            private int type;
            private String class_name;
            private String ios_address;
            private String url;
            private String title;
            private List<String> function_key;
            private String ios_notice;
            private int power;
            private String android_packname;
            private String ios_urlscheme;
            private String android_address;

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

            public String getIos_address() {
                return ios_address;
            }

            public void setIos_address(String ios_address) {
                this.ios_address = ios_address;
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

            public List<String> getFunction_key() {
                return function_key;
            }

            public void setFunction_key(List<String> function_key) {
                this.function_key = function_key;
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
        }
    }
}
