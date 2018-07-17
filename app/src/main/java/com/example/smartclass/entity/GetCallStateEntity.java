package com.example.smartclass.entity;

/**
 * Created by Administrator on 2018/1/29 0029.
 */

public class GetCallStateEntity {

    /**
     * code : 40001
     * message : 成功
     * content : {"state":"1","sysj":0}
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
         * state : 1
         * sysj : 0
         */

        private String state;
        private int sysj;
        private int yzm;

        public int getYzm() {
            return yzm;
        }

        public void setYzm(int yzm) {
            this.yzm = yzm;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getSysj() {
            return sysj;
        }

        public void setSysj(int sysj) {
            this.sysj = sysj;
        }
    }
}
