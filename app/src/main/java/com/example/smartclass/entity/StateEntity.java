package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class StateEntity {

    /**
     * code : 40001
     * message : 成功
     * content : {"zt":"0","sysj":0}
     */

    private String code;
    private String message;
    private ContentBean content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * zt : 0
         * sysj : 0
         */

        private String zt;
        private int sysj;

        public String getZt() {
            return zt;
        }

        public void setZt(String zt) {
            this.zt = zt;
        }

        public int getSysj() {
            return sysj;
        }

        public void setSysj(int sysj) {
            this.sysj = sysj;
        }
    }
}
